package mshsie.masterproject.controller;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

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

import mshsie.masterproject.model.AssignedHomework;
import mshsie.masterproject.model.Chapter;
import mshsie.masterproject.model.Course;
import mshsie.masterproject.model.DisabledChapter;
import mshsie.masterproject.model.File;
import mshsie.masterproject.model.Question;
import mshsie.masterproject.model.Quiz;
import mshsie.masterproject.model.SubmittedHomework;
import mshsie.masterproject.model.User;
import mshsie.masterproject.model.UserQuiz;
import mshsie.masterproject.service.AnswerService;
import mshsie.masterproject.service.ChapterService;
import mshsie.masterproject.service.CourseService;
import mshsie.masterproject.service.QuestionService;
import mshsie.masterproject.service.QuizService;
import mshsie.masterproject.service.SubmittedHomeworkService;
import mshsie.masterproject.service.UserQuizService;
import mshsie.masterproject.service.UserService;

@Controller
public class ChapterController {
	
	@Autowired
	ChapterService chapterService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	UserQuizService userQuizService;
	
	@Autowired
	QuizService quizService;
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	AnswerService answerService;
	
	@Autowired
	SubmittedHomeworkService shwService;
	
	private long diffInDays;
	private long diffInHours;
	
	@GetMapping("/course_chapters/{cid}")
	public String displayCourseChapters(@PathVariable("cid") Long cid, Model model, Authentication authentication) {		
		Course course = courseService.findCourseById(cid);
		User user = getLoggedUser(authentication);
		if(user.getRole().equals("student")) {
			LinkedHashMap<Long, DisabledChapter> chaptersMap = new LinkedHashMap<>();
			chaptersMap = chapterService.getDisabledChapters(course, user);
			model.addAttribute("chaptersMap", chaptersMap);
		}
		model.addAttribute("user", user);
		model.addAttribute("course", course);
		
		return "course_chapters";
	}
	
	@GetMapping("/teacher/add_chapter/{cid}")
	public String addChapter(@PathVariable("cid") Long cid, Model model) {
		
		Course course = courseService.findCourseById(cid);
		model.addAttribute("course", course);
		model.addAttribute("chapter", new Chapter());

		return "teacher/add_chapter";
	}
	
	@PostMapping("/saveChapter/{cid}")
	public String saveChapter(@PathVariable("cid") Long cid, @Valid @ModelAttribute Chapter chapter, 
			Model model) throws ParseException {
		
		Course course = courseService.findCourseById(cid);
		
		chapter.setCourse(course);
		chapterService.saveChapter(chapter);
		
		model.addAttribute("course", course);
		String path = "redirect:/course_chapters/" + cid;
		return path;
	}
	
	@GetMapping("teacher/edit_chapter/{cid}")
	public String editChapterData(@PathVariable("cid") Long cid, Model model) {
		Chapter chapter = chapterService.findChapterById(cid);
		Course course = chapter.getCourse();
		model.addAttribute("chapter", chapter);
		model.addAttribute("course", course);
		
		return "teacher/edit_chapter";
	}
	
	@PostMapping("/updateChapter/{cid}")
	public String updateChapterData(@PathVariable("cid") Long cid, 
			@Valid @ModelAttribute Chapter chapter, Model model) throws IOException {

		Chapter c = chapterService.findChapterById(cid);
		c.setTitle(chapter.getTitle());
		c.setSummary(chapter.getSummary());
		chapterService.updateChapter(c);
		
		Long courseId = c.getCourse().getId();
		
		return "redirect:/course_chapters/" + courseId;
	}
	
	@GetMapping("/chapter_details/{cid}")
	public String displayChapterDetails(@PathVariable("cid") Long cid, Model model, Authentication authentication) {
		Chapter chapter = chapterService.findChapterById(cid);
		User user = getLoggedUser(authentication);
		Course course = chapter.getCourse();
		Set<File> files = chapter.getFiles();
		Quiz quiz = chapter.getQuiz();
		int nrFiles = files.size();
		Boolean showAnswers = false;
		AssignedHomework teacherHomework = chapter.getTeacherHomework();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	    
	    if(teacherHomework != null) {
		    String subDate = teacherHomework.getSubmissionDate().format(formatter);
		    String remainingTime = calculateRemainingTime(teacherHomework.getSubmissionDate());
		    model.addAttribute("submissionDate", subDate);
		    model.addAttribute("remainingTime", remainingTime);
		    model.addAttribute("diffInDays", diffInDays);
		    model.addAttribute("diffInHours", diffInHours);
	    }
	    
	    SubmittedHomework studentSubmission = shwService.findSubmittedHomeworkForChapter(cid, user.getId());
	    if(studentSubmission != null) {
		    String submittedDate = studentSubmission.getSubmittedDate().format(formatter);
		    model.addAttribute("submittedDate", submittedDate);
	    }
	    
		if(user.getRole().equals("student")) {
			if(quiz != null) {
				showAnswers = userQuizService.takenMoreThanThree(user, quiz);
			}
			model.addAttribute("showAnswers", showAnswers);
		}
		
		model.addAttribute("chapter", chapter);
		model.addAttribute("course", course);
		model.addAttribute("files", files);
		model.addAttribute("numberOfFiles", nrFiles);
		model.addAttribute("quiz", quiz);
		model.addAttribute("teacherHomework", teacherHomework);
		model.addAttribute("studentSubmission", studentSubmission);
		return "/chapter_details";
	}
	
//	@GetMapping("/return/chapter_details/{cid}")
//	public String backToChapterDetails(@PathVariable("cid") Long cid, Model model, Authentication authentication) {
//		Chapter chapter = chapterService.findChapterById(cid);
//		User user = getLoggedUser(authentication);
//		Course course = chapter.getCourse();
//		Set<File> files = chapter.getFiles();
//		Quiz quiz = chapter.getQuiz();
//		Set<Question> questions = quiz.getQuestions();
//		AssignedHomework teacherHomework = chapter.getTeacherHomework();
//	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
//	    
//	    if(teacherHomework != null) {
//		    String subDate = teacherHomework.getSubmissionDate().format(formatter);
//		    String remainingTime = calculateRemainingTime(teacherHomework.getSubmissionDate());
//		    model.addAttribute("submissionDate", subDate);
//		    model.addAttribute("remainingTime", remainingTime);
//	    }
//	    
//	    SubmittedHomework studentSubmission = shwService.findSubmittedHomeworkForChapter(cid, user.getId());
//	    if(studentSubmission != null) {
//		    String submittedDate = studentSubmission.getSubmittedDate().format(formatter);
//		    model.addAttribute("submittedDate", submittedDate);
//	    }
//	    
//		if(!questions.isEmpty() && questions != null) {
//			for(Question q : questions) {
//				answerService.deleteAnswers(q.getId());
//			}
//			questionService.deleteQuestions(quiz.getId());
//		}
//		quizService.deleteQuiz(cid, quiz.getId());
//		int nrFiles = files.size();
//		Boolean showAnswers = false;
//		if(user.getRole().equals("student")) {
//			if(quiz != null) {
//				showAnswers = userQuizService.takenMoreThanThree(user, quiz);
//			}
//			model.addAttribute("showAnswers", showAnswers);
//		}
//		
//		model.addAttribute("chapter", chapter);
//		model.addAttribute("course", course);
//		model.addAttribute("files", files);
//		model.addAttribute("numberOfFiles", nrFiles);
//		model.addAttribute("quiz", quiz);
//		model.addAttribute("teacherHomework", teacherHomework);
//		model.addAttribute("studentSubmission", studentSubmission);
//		return "redirect:/chapter_details/" + chapter.getId();
//	}
	
	@GetMapping("/delete/chapter/{chid}/course/{coid}")
    public String deleteChapter(@PathVariable("chid") Long chid, @PathVariable("coid") Long coid, Model model) throws IOException {

        chapterService.deleteChapter(chid);;
        
		Course course = courseService.findCourseById(coid);
		Long courseId = course.getId();
		
        return "redirect:/course_chapters/" + courseId;
    }
	
	public User getLoggedUser(Authentication authentication) {
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		String loggedUser = userPrincipal.getUsername();
		User user = userService.findByUsername(loggedUser);
		
		return user;
	}
	
	public String calculateRemainingTime(LocalDateTime subDate) {
		String remainingTime = "";
		LocalDateTime now = LocalDateTime.now();
	    diffInHours = ChronoUnit.HOURS.between(now, subDate);
	    diffInDays = ChronoUnit.DAYS.between(now, subDate);
	    
	    if(diffInDays >= 0 && diffInHours > 0) {
	    	remainingTime = "Koha e mbetur për dorëzim: " + String.valueOf(diffInDays) + " Ditë (" + String.valueOf(diffInHours) + " Orë)";
	    } else {
	    	remainingTime = "Koha për dorëzimin e detyrës ka mbaruar.";
	    }
	    			 
		return remainingTime;
	}
	
}
