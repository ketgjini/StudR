package mshsie.masterproject.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mshsie.masterproject.model.Chapter;
import mshsie.masterproject.model.Question;
import mshsie.masterproject.model.Quiz;
import mshsie.masterproject.model.User;
import mshsie.masterproject.model.UserQuiz;
import mshsie.masterproject.service.AnswerService;
import mshsie.masterproject.service.ChapterService;
import mshsie.masterproject.service.QuestionService;
import mshsie.masterproject.service.QuizService;
import mshsie.masterproject.service.UserQuizService;
import mshsie.masterproject.service.UserService;

@Controller
public class QuizController {
	
	@Autowired
	QuizService quizService;
	
	@Autowired
	ChapterService chapterService;
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	AnswerService answerService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserQuizService userQuizService;
	
	private String result = "";
	
	private Boolean showResults = false;
	
	private Boolean noMore = false;
	
	@GetMapping("/teacher/add_quiz/{cid}")
	public String addQuiz(@PathVariable("cid") Long cid, Model model) {
		
		Chapter chapter = chapterService.findChapterById(cid);
		model.addAttribute("chapter", chapter);
		
		if(chapter.getQuiz() == null)
			model.addAttribute("quiz", new Quiz());
		else 
			model.addAttribute("quiz", chapter.getQuiz());
		
		return "/teacher/add_quiz";
	}

	@PostMapping("/saveQuiz/{cid}")
	public String saveQuiz(@PathVariable("cid") Long cid, @Valid @ModelAttribute Quiz quiz, 
			Model model, @RequestParam("questionNumber") int noOfQuestions, RedirectAttributes redirectAttributes) throws ParseException {
		
		Chapter chapter = chapterService.findChapterById(cid);
		Quiz q = chapter.getQuiz();
		Long quizId = 0L;
		if(q == null) {
			quiz.setQuizName(quiz.getQuizName());
			quiz.setGuidelines(quiz.getGuidelines());
			quiz.setChapter(chapter);
			quizService.saveQuiz(quiz);
			quizId = quiz.getId();
			model.addAttribute("quiz", quiz);
		} else {
			Quiz qu = quizService.findQuizById(chapter.getQuiz().getId());
			qu.setQuizName(quiz.getQuizName());
			qu.setGuidelines(quiz.getGuidelines());
			quizService.saveQuiz(qu);
			quizId = q.getId();
			model.addAttribute("quiz", qu);
		}
		
		redirectAttributes.addFlashAttribute("noOfQuestions", noOfQuestions);
		return "redirect:/passQuestionNumber/"+quizId;
	}
	
	@GetMapping("/student/take_quiz/{qid}")
	public String takeQuiz(@PathVariable("qid") Long qid, Model model, Authentication authentication) {
		Quiz quiz = quizService.findQuizById(qid);
		Set<Question> questions = quiz.getQuestions();
		Chapter chapter = quiz.getChapter();
		
		User user = getLoggedUser(authentication);
		Boolean moreThanThree = userQuizService.takenMoreThanThree(user, quiz);
		if(moreThanThree)
			noMore = true;
		
		model.addAttribute("result", result);
		model.addAttribute("quiz", quiz);
		model.addAttribute("questions", questions);
		model.addAttribute("chapter", chapter);
		model.addAttribute("showResults", showResults);
		model.addAttribute("noMore", noMore);
		showResults = false;
		noMore = false;
		return "/student/take_quiz";
	}
	
	@PostMapping("/checkQuizResponses/{qid}")
	public String checkQuiz(@PathVariable("qid") Long qid, HttpServletRequest request, Model model, Authentication authentication) {
		Boolean isCorrect = false;
		double score = 0;
		Quiz quiz = quizService.findQuizById(qid);
		String[] questionIds = request.getParameterValues("questionId");
		for(String questionId : questionIds) {
			Long answerId = Long.parseLong(request.getParameter("question_" + questionId));
			isCorrect = questionService.checkIsCorrectAnswer(Long.parseLong(questionId), answerId);
			if(isCorrect) 
				score++;
		}
		
		int totalQuestions = quiz.getQuestions().size();
		double scorePercentage = (score/totalQuestions) * 100;
		String userScore = new DecimalFormat("#").format(score);
		String percentage = new DecimalFormat("#").format(scorePercentage);
		
		User user = getLoggedUser(authentication);
		
		UserQuiz uq = new UserQuiz();
		uq.setQuiz(quiz);
		uq.setUser(user);
		uq.setStudentPoints(scorePercentage);
		userQuizService.save(uq);
		
		this.result = userScore + "/" + totalQuestions + " përgjigje të sakta (" + percentage + "%)";
		showResults = true;
		return "redirect:/student/take_quiz/"+qid;	
	}
	
	@GetMapping("/student/show_answers/{qid}")
	public String showQuizAnswers(@PathVariable("qid") Long qid, Model model, Authentication authentication) {
		Quiz quiz = quizService.findQuizById(qid);
		Chapter chapter = quiz.getChapter();
		Set<Question> questions = quiz.getQuestions();
		User user = getLoggedUser(authentication);
		if(user.getRole().equals("student") && !user.getUserQuiz().isEmpty()) {
			LinkedList<UserQuiz> uq = userQuizService.findUserResultsForQuiz(user, quiz);
			model.addAttribute("userQuiz", uq);
		}
		model.addAttribute("quiz", quiz);
		model.addAttribute("questions", questions);
		model.addAttribute("chapter", chapter);
		return "/student/show_answers";
	}
	
	@GetMapping("/student/quiz_results/{qid}")
	public String showStudentQuizResults(@PathVariable("qid") Long qid, Model model, Authentication authentication) {
		Quiz quiz = quizService.findQuizById(qid);
		Chapter chapter = quiz.getChapter();
		User user = getLoggedUser(authentication);
		
		if(!user.getUserQuiz().isEmpty()) {
			LinkedList<UserQuiz> uq = userQuizService.findUserResultsForQuiz(user, quiz);
			model.addAttribute("userQuiz", uq);
		} else {
			model.addAttribute("noTries", true);
		}
		model.addAttribute("quiz", quiz);
		model.addAttribute("chapter", chapter);
		return "/student/quiz_results";
	}
	
	@GetMapping("/teacher/students_quiz_results/{qid}")
	public String showAllQuizResults(@PathVariable("qid") Long qid, Model model, Authentication authentication) {
		Quiz quiz = quizService.findQuizById(qid);
		Chapter chapter = quiz.getChapter();
		Map<String, List<Integer>> quizUsersResults = new HashMap<>();
		quizUsersResults = quizService.findQuizUsersResults(quiz);
		
		if(!quizUsersResults.isEmpty()) {
			model.addAttribute("quizUsersResults", quizUsersResults);
		} else {
			model.addAttribute("noTries", true);
		}
		model.addAttribute("quiz", quiz);
		model.addAttribute("chapter", chapter);
		return "/teacher/students_quiz_results";
	}
	
	@GetMapping("/delete/quiz/{qid}/chapter/{cid}")
    public String deleteQuiz(@PathVariable("qid") Long qid, @PathVariable("cid") Long cid, Model model) throws IOException {
		Quiz quiz = quizService.findQuizById(qid);
		Set<Question> questions = quiz.getQuestions();
		if(!questions.isEmpty() && questions != null) {
			for(Question q : questions) {
				answerService.deleteAnswers(q.getId());
			}
			questionService.deleteQuestions(qid);
		}
		quizService.deleteQuiz(cid, qid);
		Chapter chapter = chapterService.findChapterById(cid);
		Long chapterId = chapter.getId();
		
        return "redirect:/chapter_details/" + chapterId;
    }
	
	@GetMapping("/teacher/edit_quiz/{qid}")
	public String editQuiz(@PathVariable("qid") Long qid, Model model) {
		Quiz quiz = quizService.findQuizById(qid);
		Chapter chapter = quiz.getChapter();
		model.addAttribute("chapter", chapter);
		model.addAttribute("quiz", quiz);
		model.addAttribute("questions", quiz.getQuestions());
		
		if(quiz.getQuestions().isEmpty() || quiz.getQuestions().size() == 0) 
			return "redirect:/teacher/add_quiz/" + chapter.getId();

		return "/teacher/edit_quiz";
	}
	
	@PostMapping("/editQuizResponses/{qid}")
	public String updateQuiz(@PathVariable("qid") Long qid, HttpServletRequest request, Model model) {
		Quiz quiz = quizService.findQuizById(qid);
		String[] questionIds = request.getParameterValues("questionId");
		String[] answerIds = request.getParameterValues("answerId");
		List<String> answers = new LinkedList<String>(Arrays.asList(answerIds));
		quizService.updateQuizData(questionIds, answers, request);
		Chapter chapter = quiz.getChapter();
		return "redirect:/chapter_details/" + chapter.getId();
	}
	
	public User getLoggedUser(Authentication authentication) {
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		String loggedUser = userPrincipal.getUsername();
		User user = userService.findByUsername(loggedUser);
		
		return user;
	}
}
