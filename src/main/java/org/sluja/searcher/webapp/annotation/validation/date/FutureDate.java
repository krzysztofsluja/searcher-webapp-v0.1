package org.sluja.searcher.webapp.annotation.validation.date;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.sluja.searcher.webapp.annotation.validation.date.validator.FutureDateValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FutureDateValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureDate {

    String message() default "Date must be in the future";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
