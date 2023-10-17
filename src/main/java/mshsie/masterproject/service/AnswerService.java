package mshsie.masterproject.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mshsie.masterproject.model.Answer;
import mshsie.masterproject.model.Question;
import mshsie.masterproject.repository.AnswerRepository;

@Service
public class AnswerService {

	@Autowired
	AnswerRepository answerRepository;
	
	// Ruan pergjigjet te re
	public void saveAnswer(Answer answer) {
		answer = answerRepository.save(answer);
	}
	
	public void deleteAnswers(Long questionId) {
		answerRepository.deleteAnswersOfQuestion(questionId);
	}
	
	//Gjen nje pergjigje me id
	public Answer findAnswerById(Long id) {
		Answer answer = answerRepository.findAnswerById(id);
		if(answer != null)
			return answer;
		else return new Answer();
	}
}
