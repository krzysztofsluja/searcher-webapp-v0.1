package org.sluja.searcher.webapp.annotation.validation.date.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.sluja.searcher.webapp.annotation.validation.date.FutureDate;

import java.time.LocalDate;

public class FutureDateValidator implements ConstraintValidator<FutureDate, LocalDate> {

    @Override
    public void initialize(FutureDate constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        if (date == null) {
            return true; // Use @NotNull to enforce non-null validation
        }
        return date.isAfter(LocalDate.now());
    }
}
