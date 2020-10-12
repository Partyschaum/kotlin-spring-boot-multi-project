package de.shinythings.hexagon.application.port

abstract class Command<C> : Validator<C>() {

    operator fun invoke(command: C) {
        validate(command)
        handle(command)
    }

    protected abstract fun handle(command: C)
}
