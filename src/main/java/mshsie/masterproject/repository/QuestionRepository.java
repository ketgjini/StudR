package mshsie.masterproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mshsie.masterproject.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

	Question findQuestionById(Long questionId);
	
	@Modifying
	@Transactional
    @Query("DELETE FROM Question q where q.quiz.id = ?1")
    void deleteQuestionsOfQuiz(Long id);

}
