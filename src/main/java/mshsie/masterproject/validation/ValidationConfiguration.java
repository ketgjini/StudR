package mshsie.masterproject.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class ValidationConfiguration {

	@Bean
	UserValidation userValidator() {
		return new UserValidation();
	}
	
	@Bean
	CourseValidation courseValidator() {
		return new CourseValidation();
	}
	
	@Bean
	ChapterValidation chapterValidator() {
		return new ChapterValidation();
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("validation");
		return messageSource;
	}

}
