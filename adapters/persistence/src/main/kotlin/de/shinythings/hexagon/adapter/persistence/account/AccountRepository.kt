package de.shinythings.hexagon.adapter.persistence.account

import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<AccountEntity, Long>
