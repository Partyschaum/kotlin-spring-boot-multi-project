package de.shinythings.hexagon.application.port

interface Input<REQUEST, RESPONSE> {
    fun handle(request: REQUEST): RESPONSE
}
