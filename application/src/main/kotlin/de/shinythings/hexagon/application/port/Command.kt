package de.shinythings.hexagon.application.port

import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import javax.validation.Validation

abstract class Command<C> {

    private val validator = Validation.buildDefaultValidatorFactory().validator

    private fun validate(command: C) {
        val violations: Set<ConstraintViolation<C>> = validator.validate(command)
        if (violations.isNotEmpty()) {
            throw ConstraintViolationException(violations)
        }
    }

    operator fun invoke(command: C) {
        validate(command)
        handle(command)
    }

    protected abstract fun handle(command: C)
}
