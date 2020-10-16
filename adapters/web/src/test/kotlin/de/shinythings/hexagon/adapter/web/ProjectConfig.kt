package de.shinythings.hexagon.adapter.web

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.listeners.Listener
import io.kotest.spring.SpringListener

object ProjectConfig : AbstractProjectConfig() {

    override fun listeners(): List<Listener> = listOf(SpringListener)
}
