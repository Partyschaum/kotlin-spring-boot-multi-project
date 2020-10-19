package de.shinythings.hexagon.adapter.persistence.account

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "account")
data class AccountEntity(

        @Id
        @GeneratedValue
        val id: Long?
)
