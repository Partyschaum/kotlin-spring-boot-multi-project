package de.shinythings.hexagon.application.usecase

import de.shinythings.hexagon.application.MoneyTransferProperties
import de.shinythings.hexagon.application.ThresholdExceededException
import de.shinythings.hexagon.application.port.input.SendMoneyPort
import de.shinythings.hexagon.application.port.out.AccountLockPort
import de.shinythings.hexagon.application.port.out.LoadAccountPort
import de.shinythings.hexagon.application.port.out.UpdateAccountPort
import de.shinythings.hexagon.common.UseCase
import java.time.LocalDateTime
import javax.transaction.Transactional

@UseCase
@Transactional
class SendMoneyUseCase(
        private val loadAccountPort: LoadAccountPort,
        private val updateAccountPort: UpdateAccountPort,
        private val accountLockPort: AccountLockPort,
        private val moneyTransferProperties: MoneyTransferProperties

) : SendMoneyPort() {

    override fun handle(command: SendMoneyCommand) {

        checkThreshold(command)

        val baselineDate = LocalDateTime.now()

        val sourceAccountId = command.sourceAccountId
        val targetAccountId = command.targetAccountId

        val sourceAccount = loadAccountPort.loadAccount(sourceAccountId, baselineDate)
                ?: throw IllegalStateException("Source account not found for source account ID $sourceAccountId")

        val targetAccount = loadAccountPort.loadAccount(targetAccountId, baselineDate)
                ?: throw IllegalStateException("Target account not found for target account ID $targetAccountId")

        accountLockPort.lockAccount(sourceAccountId)
        if (!sourceAccount.withdraw(command.money, sourceAccountId)) {
            accountLockPort.releaseAccount(sourceAccountId)
            return
        }

        accountLockPort.lockAccount(targetAccountId)
        if (!targetAccount.deposit(command.money, sourceAccountId)) {
            accountLockPort.releaseAccount(targetAccountId)
            return
        }

        updateAccountPort.updateActivities(sourceAccount)
        updateAccountPort.updateActivities(targetAccount)

        accountLockPort.releaseAccount(sourceAccountId)
        accountLockPort.releaseAccount(targetAccountId)
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
