package mshsie.masterproject.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mshsie.masterproject.model.Quiz;
import mshsie.masterproject.model.User;
import mshsie.masterproject.model.UserQuiz;
import mshsie.masterproject.repository.UserQuizRepository;

@Service
public class UserQuizService {

	@Autowired
	private UserQuizRepository userQuizRepository;
	
	public void save(UserQuiz userQuiz) {
		userQuiz = userQuizRepository.save(userQuiz);
	}
	
	public Boolean takenMoreThanThreeOrMoreThan75(User user, Quiz quiz) {
		Boolean takenMoreThanThree = false;
		List<UserQuiz> userQuizList = userQuizRepository.findByUserAndQuizId(user.getId(), quiz.getId());
		for(UserQuiz uq : userQuizList) {
			if((userQuizList.size() == 3 && uq.getStudentPoints() <= 75) || (userQuizList.size() < 3 && uq.getStudentPoints() >= 75) || (userQuizList.size() > 3)) {
				takenMoreThanThree = true;
			} 
		}
		
		return takenMoreThanThree;
	}
	
	public Boolean takenMoreThanThree(User user, Quiz quiz) {
		Boolean takenMoreThanThree = false;
		List<UserQuiz> userQuizList = userQuizRepository.findByUserAndQuizId(user.getId(), quiz.getId());
		if(userQuizList.size() == 3) {
			takenMoreThanThree = true;
		}
		
		return takenMoreThanThree;
	}
	
	public LinkedList<UserQuiz> findUserResultsForQuiz(User user, Quiz quiz) {
		LinkedList<UserQuiz> uqList = new LinkedList<UserQuiz>();
		uqList = userQuizRepository.findUserResults(user.getId(), quiz.getId());
		return uqList;
	}
}
