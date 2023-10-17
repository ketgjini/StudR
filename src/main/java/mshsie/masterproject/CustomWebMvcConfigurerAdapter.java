package mshsie.masterproject;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomWebMvcConfigurerAdapter implements WebMvcConfigurer  {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("").setViewName("redirect:/index");
        registry.addViewController("/index").setViewName("forward:/index");
        registry.addViewController("/courses").setViewName("forward:/courses.html");
        registry.addViewController("/login").setViewName("redirect:/login");
        registry.addViewController("/news").setViewName("redirect:/news.html");
        registry.addViewController("/newspost").setViewName("redirect:/news_post.html");
        registry.addViewController("/teachers").setViewName("redirect:/teachers.html");
        registry.addViewController("/elements").setViewName("redirect:/elements.html");
        registry.addViewController("/user_profile").setViewName("redirect:/user_profile.html");
        registry.addViewController("/edit_profile").setViewName("redirect:/edit_profile.html");
        registry.addViewController("**/teacher/course_registration").setViewName("redirect:/teacher/course_registration.html");
        registry.addViewController("**/teacher/add_chapter").setViewName("redirect:/add_chapter.html");
        registry.addViewController("/forgot_password").setViewName("redirect:/forgot_password.html");
        registry.addViewController("/about").setViewName("redirect:/about.html");
        registry.addViewController("/about.html").setViewName("redirect:/about.html");
        registry.addViewController("/all_courses").setViewName("redirect:/all_courses.html");
    }
    
}
