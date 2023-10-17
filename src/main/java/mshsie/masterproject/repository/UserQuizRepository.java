package mshsie.masterproject.repository;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mshsie.masterproject.model.UserQuiz;

@Repository
public interface UserQuizRepository extends JpaRepository<UserQuiz, Long> {

	@Query(value = "SELECT * FROM user_quiz uq WHERE uq.user_id = ?1 AND uq.quiz_id = ?2 ", nativeQuery = true)
	public List<UserQuiz> findByUserAndQuizId(Long uid, Long qid);
	
	@Query(value = "SELECT * FROM user_quiz uq WHERE uq.user_id = ?1 AND uq.quiz_id = ?2 ", nativeQuery = true)
	public LinkedList<UserQuiz> findUserResults(Long uid, Long qid);

}
