package com.foo.bar.helpers;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationHelper {
    private Validator itsValidator;

    public ValidationHelper() {
        itsValidator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public <T> boolean validateSimple(T o) {
        return validationWithMessage(o).isEmpty();
    }

    public <T> Collection<String> validationWithMessage(T o) {
        Set<ConstraintViolation<T>> violations = itsValidator.validate(o);
        return violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
    }

    public <T> boolean validateCollectionSimple(Collection<T> theC) {
        return validationCollectionWithMessage(theC).isEmpty();
    }

    public <T> Collection<String> validationCollectionWithMessage(Collection<T> theC) {
        return theC.stream().map(o -> itsValidator.validate(o)).flatMap(cons -> cons.stream()).map(ConstraintViolation::getMessage).collect(Collectors.toSet());
    }

    public <T> boolean validationCollection(T... theC) {
        return validationCollectionWithMessage(Arrays.asList(theC)).isEmpty();
    }

    public <T> Collection<String> validationCollectionWithMessage(T... theC) {
        return validationCollectionWithMessage(Arrays.asList(theC));
    }
}
