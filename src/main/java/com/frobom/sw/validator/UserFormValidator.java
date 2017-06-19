package com.frobom.sw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.frobom.sw.entity.User;
import com.frobom.sw.service.UserService;

@Component("userFormValidator")
public class UserFormValidator implements Validator {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "email.alreadyExists", "Email already taken.");
        }
    }

}
