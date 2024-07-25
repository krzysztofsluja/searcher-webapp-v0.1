package org.sluja.searcher.webapp.aspect.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.sluja.searcher.webapp.annotation.validation.InputValidation;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Aspect
@Component
@Validated
public class InputValidationAspect {

    private final Validator validator;

    public InputValidationAspect() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Before("@annotation(inputValidation)")
    public void validateMethodParameters(JoinPoint joinPoint, InputValidation inputValidation) {
        Object[] args = joinPoint.getArgs();
        Class<?>[] inputClasses = inputValidation.inputs();

        for (Object arg : args) {
            for (Class<?> clazz : inputClasses) {
                if (clazz.isInstance(arg)) {
                    validate(arg);
                }
            }
        }
    }

    private <T> void validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder("Validation failed: ");
            for (ConstraintViolation<T> violation : violations) {
                sb.append(violation.getPropertyPath()).append(" ").append(violation.getMessage()).append("; ");
            }
            //TODO add error handling
            throw new IllegalArgumentException(sb.toString());
        }
    }
}
