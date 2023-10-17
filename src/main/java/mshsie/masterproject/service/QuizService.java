package mshsie.masterproject.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import mshsie.masterproject.model.Answer;
import mshsie.masterproject.model.Chapter;
import mshsie.masterproject.model.Question;
import mshsie.masterproject.model.Quiz;
import mshsie.masterproject.repository.AnswerRepository;
import mshsie.masterproject.repository.QuestionRepository;
import mshsie.masterproject.repository.QuizRepository;

@Service
public class QuizService {

	@Autowired
	private QuizRepository quizRepository;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AnswerService answerService;

	// Ruan nje quiz te ri
	public void saveQuiz(Quiz quiz) {
		quiz = quizRepository.save(quiz);
	}

	public Quiz findQuizById(Long id) {
		Quiz quiz = quizRepository.findQuizById(id);
		if(quiz != null)
			return quiz;
		else return new Quiz();
	}
	
	//Fshin quizin
	public void deleteQuiz(Long cid, Long qid) {
		quizRepository.deleteQuizUsers(qid);
		quizRepository.deleteQuiz(cid);
	}
	
	//Gjen rezultatet e perdoruesve ne nje quiz
	public Map<String, List<Integer>> findQuizUsersResults(Quiz quiz) {
		List<Object[]> results = quizRepository.findQuizUsersAndResults(quiz.getId());
		Map<String, List<Integer>> quizUsersResults = new HashMap<>();
		List<Integer> values = new ArrayList<>();
		String previousKey = "";
		for(int i = 0; i < results.size(); i++) {
			String key = (results.get(i)[0]).toString() + ' ' + (results.get(i)[1]).toString();
			if(key.equals(previousKey)) {
				values.add(Integer.parseInt(new DecimalFormat("#").format(results.get(i)[2]).toString()));
				
			} else {
				values = new ArrayList<>();
				values.add(Integer.parseInt(new DecimalFormat("#").format(results.get(i)[2]).toString()));
			}
			quizUsersResults.put(key, values);
			previousKey = key;
		}
		return quizUsersResults;
	}
	
	//Update te dhenat e quizit
	public void updateQuizData(String[] questionIds, List<String> answerIds, HttpServletRequest request) {
		for(String questionId : questionIds) {
			String question = request.getParameter("question_" + questionId);
			String exp = request.getParameter("exp_" + questionId);
			Question q = questionService.findQuestionById(Long.valueOf(questionId));
			q.setQuestion(question);
			q.setExplanation(exp);
			q.getAnswers().clear();
			Set<Answer> answers = new HashSet<>();
			int count = 0;
			Iterator<String> it = answerIds.iterator();
			while (it.hasNext()) {
				String answerId = it.next();
				count++;
				String answer = request.getParameter("answer_" + answerId);
				Answer a = answerService.findAnswerById(Long.valueOf(answerId));
				a.setAnswer(answer);
				answers.add(a);
				it.remove();
				if(count == 3)
					break;
			}

			q.getAnswers().addAll(answers);
			questionService.saveQuestion(q);
		}
	}
	
}
