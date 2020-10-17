package de.shinythings.hexagon.application.port.output

import de.shinythings.hexagon.domain.Account

interface UpdateAccountPort {

    fun updateActivities(account: Account)
}
