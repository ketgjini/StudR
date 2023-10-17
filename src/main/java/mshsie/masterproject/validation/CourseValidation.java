package mshsie.masterproject.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import mshsie.masterproject.model.Course;
import mshsie.masterproject.model.User;
import mshsie.masterproject.service.CourseService;
import mshsie.masterproject.service.UserService;

/**Validon te dhenat e kursit kur regjistrohet*/

@Component
public class CourseValidation implements Validator {

	@Autowired
    private CourseService courseService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Course.class.equals(aClass);
    }
    
    @Override
    public void validate(Object o, Errors errors) {
        Course course = (Course) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "courseName", "NotEmpty");
        if (course.getCourseName().length() < 3 || course.getCourseName().length() > 40) {
            errors.rejectValue("courseName", "Size.courseForm.name");
        }
        if (courseService.courseNameExists((course.getCourseName()))) {
            errors.rejectValue("courseName", "Duplicate.courseForm.name");
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "department", "NotEmpty");
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dega", "NotEmpty");
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "semester", "NotEmpty");
        if (course.getSemester() == -1) {
            errors.rejectValue("semester", "NotEmpty");
        }
    }
}
