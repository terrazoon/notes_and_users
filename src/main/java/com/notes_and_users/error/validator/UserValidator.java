package com.notes_and_users.error.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class UserValidator implements ConstraintValidator<ValidUser, String> {

    List<String> users = Arrays.asList("user", "admin");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return true;
        //return users.contains(value);

    }
}
