package mshsie.masterproject.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mshsie.masterproject.model.Answer;
import mshsie.masterproject.model.Course;
import mshsie.masterproject.model.Question;
import mshsie.masterproject.repository.QuestionRepository;

@Service
public class QuestionService {
	
	@Autowired
	QuestionRepository questionRepository;
	
	// Ruan nje pyetje te re
	public void saveQuestion(Question question) {
		question = questionRepository.save(question);
	}
	
	// Ben update nje pyetje
	public void updateQuestion(Question question) {
		questionRepository.save(question);
	}

	public Boolean checkIsCorrectAnswer(Long questionId, Long selectedAnswer) {
		Boolean isCorrect = false;
		Question question = questionRepository.findQuestionById(questionId);
		Set<Answer> answers = question.getAnswers();
		for(Answer a : answers) {
			if(a.getId() == selectedAnswer && a.getCorrect() == 1) {
				isCorrect = true;
				break;
			} else {
				isCorrect = false;
			}
		}
		return isCorrect;
	}
	
	//Gjen nje pyetje me id
	public Question findQuestionById(Long id) {
		Question question = questionRepository.findQuestionById(id);
		if(question != null)
			return question;
		else return new Question();
	}

	public void deleteQuestions(Long quizId) {
		questionRepository.deleteQuestionsOfQuiz(quizId);
	}
	
}
