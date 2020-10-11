package de.shinythings.hexagon.application

import de.shinythings.hexagon.application.port.Input

abstract class UseCase<REQUEST, RESPONSE> : Input<REQUEST, RESPONSE> {
    operator fun invoke(request: REQUEST): RESPONSE {
        // Here is the place where I can validate the request object
        return handle(request)
    }
}
