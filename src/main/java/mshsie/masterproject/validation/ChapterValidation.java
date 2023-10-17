package mshsie.masterproject.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import mshsie.masterproject.model.Chapter;
import mshsie.masterproject.model.Course;
import mshsie.masterproject.service.ChapterService;

@Component
public class ChapterValidation implements Validator {
	
	@Autowired
    private ChapterService chapterService;

	 @Override
	    public boolean supports(Class<?> aClass) {
	        return Chapter.class.equals(aClass);
	    }

	@Override
	public void validate(Object o, Errors errors) {
		Chapter chapter = (Chapter) o;
	}

}
