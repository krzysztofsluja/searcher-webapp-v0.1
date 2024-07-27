package org.sluja.searcher.webapp.aspect.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.sluja.searcher.webapp.annotation.validation.InputValidation;
import org.sluja.searcher.webapp.exception.message.IncorrectMessageCodeForReaderException;
import org.sluja.searcher.webapp.utils.message.implementation.ErrorMessageReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Aspect
@Component
@Validated
public class InputValidationAspect {

    private final Validator validator;
    private final ErrorMessageReader errorMessageReader;

    @Autowired
    public InputValidationAspect(final ErrorMessageReader errorMessageReader) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
        this.errorMessageReader = errorMessageReader;
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
            violations.stream()
                    .map(violation -> {
                        try {
                            return errorMessageReader.getPropertyValue(violation.getMessage());
                        } catch (final IncorrectMessageCodeForReaderException e) {
                            //TODO logging
                            return StringUtils.EMPTY;
                        }
                    })
                    .filter(StringUtils::isNotEmpty)
                    .forEach(sb::append);
            //TODO add error handling
            throw new IllegalArgumentException(sb.toString());
        }
    }
}
