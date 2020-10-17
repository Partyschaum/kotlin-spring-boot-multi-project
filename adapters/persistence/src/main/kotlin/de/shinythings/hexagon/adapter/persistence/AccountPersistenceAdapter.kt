package de.shinythings.hexagon.adapter.persistence

//@PersistenceAdapter
//class AccountPersistenceAdapter(
//        private val accountRepository: AccountRepository,
//        private val activityRepository: ActivityRepository,
////        private val accountMapper: AccountMapper
//) : LoadAccountPort, UpdateAccountPort {
//
//    override fun loadAccount(accountId: AccountId, baselineDate: LocalDateTime): Account? {
//
//        val account = accountRepository.findByIdOrNull(accountId.value)
//                ?: throw EntityNotFoundException()
//
//        val activities = activityRepository.findByOwnerSince(accountId.value, baselineDate)
//
//        val withdrawalBalance = activityRepository.depositBalanceUntil(accountId.value, baselineDate)
//
//    }
//
//    override fun updateActivities(account: Account) {
//        TODO("Not yet implemented")
//    }
//}
//
//private fun Long.orZero(): Any {
//
//}
