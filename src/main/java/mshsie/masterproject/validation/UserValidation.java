package mshsie.masterproject.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import mshsie.masterproject.model.User;
import mshsie.masterproject.service.UserService;

/**Validon te dhenat e perdoruesit kur regjistrohet*/

@Component
public class UserValidation implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }
    
    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

       // ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername() == null || user.getUsername().equals("")) {
            errors.rejectValue("username", "NotEmpty");
        }
        else {
	        if (user.getUsername().length() < 6 || user.getUsername().length() > 30) {
	            errors.rejectValue("username", "Size.userForm.username");
	        }
	        
	        if (userService.usernameExists((user.getUsername()))) {
	            errors.rejectValue("username", "Duplicate.userForm.username");
	        }
        }
        
      //  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "NotEmpty");
        if (user.getFirstname() == null || user.getFirstname().equals("")) {
            errors.rejectValue("firstname", "NotEmpty");
        }
        else {
	        if (user.getFirstname().length() < 3 || user.getFirstname().length() > 30) {
	            errors.rejectValue("firstname", "Size.userForm.textfield");
	        }
        }
        
      //  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "NotEmpty");
        if (user.getLastname() == null || user.getLastname().equals("")) {
            errors.rejectValue("firstname", "NotEmpty");
        }
        else {
        	 if (user.getLastname().length() < 3 || user.getLastname().length() > 30) {
                 errors.rejectValue("lastname", "Size.userForm.textfield");
             }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if ((userService.emailExists((user.getEmail())))) {
            errors.rejectValue("email", "Duplicate.userForm.email");
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 30) {
            errors.rejectValue("password", "Size.userForm.password");
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty");
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "Diff.userForm.confirmPassword");
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "role", "NotEmpty");
        
        if (user.getRole().equals("student")) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dega", "NotEmpty");
        }
    }
}