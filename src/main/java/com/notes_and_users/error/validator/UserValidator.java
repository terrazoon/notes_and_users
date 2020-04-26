package com.notes_and_users.error.validator;

import com.notes_and_users.models.User;
import com.notes_and_users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class UserValidator implements ConstraintValidator<ValidUser, Long> {

    List<Long> users = Arrays.asList(1L, 2L);

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return true;
        //return (users.contains(value));
    }
}
