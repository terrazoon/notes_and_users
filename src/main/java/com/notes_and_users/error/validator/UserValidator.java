package com.notes_and_users.error.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class UserValidator implements ConstraintValidator<User, String> {

    List<String> users = Arrays.asList("Santideva", "Marie Kondo", "Martin Fowler");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return users.contains(value);

    }
}
