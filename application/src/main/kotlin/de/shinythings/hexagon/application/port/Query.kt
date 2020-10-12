package de.shinythings.hexagon.application.port

abstract class Query<Q, R> : Validator<Q>() {

    operator fun invoke(query: Q): R {
        validate(query)
        return handle(query)
    }

    protected abstract fun handle(query: Q): R
}
