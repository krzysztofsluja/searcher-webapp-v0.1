package org.sluja.searcher.webapp.aspect.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.sluja.searcher.webapp.annotation.validation.InputValidation;
import org.sluja.searcher.webapp.exception.validation.ValidationNotPassedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;
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
    public void validateMethodParameters(JoinPoint joinPoint, InputValidation inputValidation) throws ValidationNotPassedException {
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

    private <T> void validate(T object) throws ValidationNotPassedException {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            final List<String> errorCodes =violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .toList();
            throw new ValidationNotPassedException(errorCodes);
        }
    }
}
