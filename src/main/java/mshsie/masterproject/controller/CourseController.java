package mshsie.masterproject.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mshsie.masterproject.model.Course;
import mshsie.masterproject.model.Message;
import mshsie.masterproject.model.User;
import mshsie.masterproject.service.CourseService;
import mshsie.masterproject.service.UserService;
import mshsie.masterproject.validation.CourseValidation;

@Controller
public class CourseController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	private CourseValidation courseValidation;

	@GetMapping("/teacher/course_registration")
	public String addCourse(Model model, Authentication authentication) {
		
		model.addAttribute("course", new Course());

		return "/teacher/course_registration";
	}
	
	@PostMapping("/saveCourse")
	public String saveCourse(@RequestParam(name = "startTime", required = false) String sDate,
			@RequestParam(name = "endTime", required = false) String eDate,
			@Valid @ModelAttribute Course course, BindingResult bindingResult, 
			Model model, Authentication authentication) throws ParseException {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		Date endDate = null;
		if (!sDate.isEmpty())
			startDate = format.parse(sDate);
		if (!eDate.isEmpty())
			endDate = format.parse(eDate);

		courseValidation.validate(course, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/teacher/course_registration";
		}
		
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		String loggedUser = userPrincipal.getUsername();
		User user = userService.findByUsername(loggedUser);
		Set<User> userList = new HashSet<User>();
		userList.add(user);
		course.setUsers(userList);
		course.setStartTime(startDate);
		course.setEndTime(endDate);
		courseService.saveCourse(course);
		
		model.addAttribute("user", user);

		return "redirect:/user_courses";
	}
	
	@GetMapping("/user_courses")
	public String displayTeacherCourses(Model model, Authentication authentication) {
		
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		String loggedUser = userPrincipal.getUsername();
		User user = userService.findByUsername(loggedUser);
		
		model.addAttribute("user", user);

		return "/user_courses";
	}
	
	@GetMapping("/teacher/edit_course/{cid}")
	public String editCoursesData(@PathVariable("cid") Long cid, Model model) {
		Course course = courseService.findCourseById(cid);
		model.addAttribute("course", course);
		return "/teacher/edit_course";
	}
	
	@PostMapping("/updateCourse/{cid}")
	public String updateCourseData(@PathVariable("cid") Long cid, 
			@Valid @ModelAttribute Course course, BindingResult bindingResult, Model model, Authentication authentication) throws IOException {

		Course c = courseService.findCourseById(cid);
		
//		courseValidation.validate(course, bindingResult);
//		if (bindingResult.hasErrors()) {
//			//model.addAttribute("course", course);
//			model.addAttribute("courseNameError", bindingResult.getFieldError("courseName"));
//			model.addAttribute("courseNameError", true);
//			return "redirect:/edit_course/"+cid;
//		}
		
		c.setCourseName(course.getCourseName());
		c.setDepartment(course.getDepartment());
		c.setDega(course.getDega());
		c.setSemester(course.getSemester());
		c.setStartTime(course.getStartTime());
		c.setEndTime(course.getEndTime());
		c.setDescription(course.getDescription());
		courseService.updateCourse(c);
		
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		String loggedUser = userPrincipal.getUsername();
		User user = userService.findByUsername(loggedUser);
		
		model.addAttribute("user", user);
		
		return "redirect:/user_courses";
	}
	
	@GetMapping("/courses")
	public String coursesPage(Model model, @RequestParam("courseName") String courseName, 
			@RequestParam("department") String department, @RequestParam("dega") String dega, Authentication authentication) {
		
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		String loggedUser = userPrincipal.getUsername();
		User user = userService.findByUsername(loggedUser);
		
		List<Course> courses = courseService.searchForCourse(courseName, department, dega);
		model.addAttribute("courses", courses);
		model.addAttribute("user", user);
		return "courses";
	}
	
	@RequestMapping("/registerToCourse/{cid}")
	public String registerToCourse(@PathVariable("cid") Long cid, Model model, Authentication authentication, HttpServletRequest request) {
		Course course = courseService.findCourseById(cid);
		
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		String loggedUser = userPrincipal.getUsername();
		User user = userService.findByUsername(loggedUser);
		
		if (course != null) {
			if (!course.hasUser(user)) {
				course.getUsers().add(user);
			}
		}
		courseService.updateCourse(course);
		model.addAttribute("user", user);
		String referer = request.getHeader("Referer");
	    return "redirect:"+ referer;
	}
	
	public String findCourseProfessor(Course course) {
		String profesor = courseService.findCourseProfessor(course);
		return profesor;
	}
	
	public String findCourseProfessorEmail(Course course) {
		String email = courseService.findCourseProfessorEmail(course);
		return email;
	}
	
	@GetMapping("/delete/course/{cid}")
    public String deleteCourse(@PathVariable("cid") Long cid, Model model, Authentication authentication) throws IOException {
		
        courseService.deleteCourse(cid);

        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		String loggedUser = userPrincipal.getUsername();
		User user = userService.findByUsername(loggedUser);
		
		model.addAttribute("user", user);
        return "redirect:/user_courses";
    }
	
	@GetMapping("/all_courses")
    public String allCourses(Model model, Authentication authentication) throws IOException {
        List<Course> courses = courseService.getAllCourses();
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		String loggedUser = userPrincipal.getUsername();
		User user = userService.findByUsername(loggedUser);
		
		model.addAttribute("user", user);
		model.addAttribute("courses", courses);
        return "/all_courses";
    }
}
