package de.shinythings.hexagon.application.usecase

import de.shinythings.hexagon.application.MoneyTransferProperties
import de.shinythings.hexagon.application.ThresholdExceededException
import de.shinythings.hexagon.application.port.input.SendMoney
import de.shinythings.hexagon.application.port.out.AccountLock
import de.shinythings.hexagon.application.port.out.LoadAccount
import de.shinythings.hexagon.application.port.out.UpdateAccount
import de.shinythings.hexagon.common.UseCase
import java.time.LocalDateTime
import javax.transaction.Transactional

@UseCase
@Transactional
class SendMoney(
        private val loadAccount: LoadAccount,
        private val updateAccount: UpdateAccount,
        private val accountLock: AccountLock,
        private val moneyTransferProperties: MoneyTransferProperties

) : SendMoney() {

    override fun handle(command: SendMoneyCommand) {

        checkThreshold(command)

        val baselineDate = LocalDateTime.now()

        val sourceAccountId = command.sourceAccountId
        val targetAccountId = command.targetAccountId

        val sourceAccount = loadAccount.loadAccount(sourceAccountId, baselineDate)
                ?: throw IllegalStateException("Source account not found for source account ID $sourceAccountId")

        val targetAccount = loadAccount.loadAccount(targetAccountId, baselineDate)
                ?: throw IllegalStateException("Target account not found for target account ID $targetAccountId")

        accountLock.lockAccount(sourceAccountId)
        if (!sourceAccount.withdraw(command.money, sourceAccountId)) {
            accountLock.releaseAccount(sourceAccountId)
            return
        }

        accountLock.lockAccount(targetAccountId)
        if (!targetAccount.deposit(command.money, sourceAccountId)) {
            accountLock.releaseAccount(targetAccountId)
            return
        }

        updateAccount.updateActivities(sourceAccount)
        updateAccount.updateActivities(targetAccount)

        accountLock.releaseAccount(sourceAccountId)
        accountLock.releaseAccount(targetAccountId)
    }

    private fun checkThreshold(command: SendMoneyCommand) {
        if (command.money >= moneyTransferProperties.maximumTransferThreshold) {
            throw ThresholdExceededException(
                    threshold = moneyTransferProperties.maximumTransferThreshold,
                    actual = command.money
            )
        }
    }
}
