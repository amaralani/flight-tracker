//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package ir.mfava.modfava.pardazesh.validator;

import ir.mfava.modfava.pardazesh.model.User;
import ir.mfava.modfava.pardazesh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    public UserValidator() {
    }

    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {
        User user = (User)o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "field.required");
        if(user.getUsername().length() < 4) {
            errors.rejectValue("username", "username.too.short");
        }

        if(user.getUsername().length() > 32) {
            errors.rejectValue("username", "username.too.long");
        }

        if(this.userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "duplicate.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required");
        if(user.getPassword().length() < 6) {
            errors.rejectValue("password", "password.too.short");
        }

        if(user.getPassword().length() > 32) {
            errors.rejectValue("password", "password.too.long");
        }

    }
}
