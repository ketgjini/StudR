package mshsie.masterproject.controller;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mshsie.masterproject.model.Answer;
import mshsie.masterproject.model.Chapter;
import mshsie.masterproject.model.Course;
import mshsie.masterproject.model.File;
import mshsie.masterproject.model.Question;
import mshsie.masterproject.model.Quiz;
import mshsie.masterproject.model.QuizForm;
import mshsie.masterproject.service.AnswerService;
import mshsie.masterproject.service.QuestionService;
import mshsie.masterproject.service.QuizService;

@Controller
public class QuestionController {
	
	@Autowired
	QuizService quizService;
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	AnswerService answerService;
	
	private int count = 0;
	
	private int nrQuestions = 0;
	
	@GetMapping("/passQuestionNumber/{qid}")
	public String getNumberOfQuestions(@PathVariable("qid") Long qid, @ModelAttribute("noOfQuestions") int nmbrOfQuestions) {
		nrQuestions = nmbrOfQuestions;
		
		return "redirect:/teacher/add_quiz_questions/"+qid;
	}
	
	@GetMapping("/teacher/add_quiz_questions/{qid}")
	public String addQuestions(@PathVariable("qid") Long qid, Model model) {
		count++;
		Quiz quiz = quizService.findQuizById(qid);
		Chapter chapter = quiz.getChapter();
		
		model.addAttribute("quiz", quiz);
		model.addAttribute("chapter", chapter);
		model.addAttribute("count", count);
		model.addAttribute("quizForm", new QuizForm());
		model.addAttribute("noOfQuestions", nrQuestions);
		
		if(count > nrQuestions) {
			count = 0;
			return "redirect:/chapter_details/"+chapter.getId();
		}

		return "teacher/add_quiz_questions";
	}
	
	@PostMapping("/saveQuizQuestion/{qid}")
	public String saveQuestions(@PathVariable("qid") Long qid, @Valid @ModelAttribute QuizForm quizForm, 
			Model model) throws ParseException {
		
		Quiz quiz = quizService.findQuizById(qid);
		
		Question question = new Question();
		question.setQuestion(quizForm.getQuestion());
		question.setExplanation(quizForm.getExplanation());
		question.setQuiz(quiz);
		questionService.saveQuestion(question);
		
		//Add answers
		Answer a = new Answer();
		a.setAnswer(quizForm.getOption1());
		a.setCorrect(1);
		a.setQuestion(question);
		answerService.saveAnswer(a);
		
		Answer a2 = new Answer();
		a2.setAnswer(quizForm.getOption2());
		a2.setCorrect(0);
		a2.setQuestion(question);
		answerService.saveAnswer(a2);
		
		Answer a3 = new Answer();
		a3.setAnswer(quizForm.getOption3());
		a3.setCorrect(0);
		a3.setQuestion(question);
		answerService.saveAnswer(a3);
		
		Chapter chapter = quiz.getChapter();
		
		if(count == nrQuestions) {
			count = 0;
			return "redirect:/chapter_details/"+chapter.getId();
		}
		else 
			return "redirect:/teacher/add_quiz_questions/"+qid;
	}

}
