package mshsie.masterproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import mshsie.masterproject.model.Chapter;
import mshsie.masterproject.model.Course;
import mshsie.masterproject.model.User;
import mshsie.masterproject.repository.CourseRepository;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private UserService userService;
	
	// Ruan nje kurs te ri
	public void saveCourse(Course course) {
		course = courseRepository.save(course);
	}
	
	// Update kurs ekzistues
	public void updateCourse(Course course) {
		course = courseRepository.save(course);
	}
	
	// Kontrollon nese emri i kursit ekziston
	public Boolean courseNameExists(String name) {
		Boolean exists;
		return exists = courseRepository.existsByCourseName(name);
	}
	
	//Gjen nje kurs me id
	public Course findCourseById(Long id) {
		Course course = courseRepository.findCourseById(id);
		if(course != null)
			return course;
		else return new Course();
	}
	
	//Kerkon per kurse specifike
	public List<Course> searchForCourse(String courseName, String department, String dega) {
		List<Course> courses = new ArrayList<>();
		
		if(courseName != "" && department == "" && dega == "") {
			courses = courseRepository.findCourseByCourseName(courseName);
		} 
		else if(courseName == "" && department != "" && dega == "") {
			courses = courseRepository.findCourseByDepartment(department);
		}
		else if(courseName == "" && department == "" && dega != "") {
			courses = courseRepository.findCourseByDega(dega);
		}
		else if(courseName != "" && department != "" && dega == "") {
			courses = courseRepository.searchCourseByDepartmentAndName(department, courseName);
		}
		else if(courseName != "" && department == "" && dega != "") {
			courses = courseRepository.searchCourseByNameAndDega(courseName, dega);
		}
		else if(courseName == "" && department != "" && dega != "") {
			courses = courseRepository.searchCourseByDepartmentAndDega(department, dega);
		}
		else if(courseName != "" && department != "" && dega != "") {
			courses = courseRepository.searchCourse(courseName, department, dega);
		}
		
		return courses;
	}
	
	public String findCourseProfessor(Course course) {
		List<User> users = userService.findUsersWithSpecificRole("profesor");
		String profesor = "";
		for(User u : users) {
			if(course.getUsers().contains(u)) {
				profesor = u.getFirstname() + " " + u.getLastname();
			}
		}
		return profesor;
	}
	
	public String findCourseProfessorEmail(Course course) {
		List<User> users = userService.findUsersWithSpecificRole("profesor");
		String email = "";
		for(User u : users) {
			if(course.getUsers().contains(u)) {
				email = u.getEmail();
			}
		}
		return email;
	}
	
	public void deleteCourse(Long id) {
		courseRepository.deleteById(id);
	}
	
	public List<Course> getAllCourses() {
		List<Course> courses = courseRepository.getAllCourses();
		return courses;
	}

}
