package mshsie.masterproject.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mshsie.masterproject.model.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

	Quiz findQuizById(Long id);
	
	@Query(value = "SELECT u.firstname, u.lastname, uq.student_points FROM user_quiz uq JOIN users u ON uq.user_id = u.user_id WHERE uq.quiz_id = ?1 ", nativeQuery = true)
	List<Object[]> findQuizUsersAndResults(Long id);

	@Modifying
	@Transactional
    @Query(value="DELETE FROM user_quiz where quiz_id = ?1", nativeQuery = true)
    void deleteQuizUsers(Long id);
	
	@Modifying
	@Transactional
    @Query(value="DELETE FROM quizzes where chapter_id = ?1", nativeQuery = true)
    void deleteQuiz(Long id);
}
