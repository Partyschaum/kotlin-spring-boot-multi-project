package de.shinythings.hexagon.application.port

import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import javax.validation.Validation

abstract class Validator<T> {

    private val validator = Validation.buildDefaultValidatorFactory().validator

    protected fun validate(validate: T) {
        val violations: Set<ConstraintViolation<T>> = validator.validate(validate)
        if (violations.isNotEmpty()) {
            throw ConstraintViolationException(violations)
        }
    }
}
