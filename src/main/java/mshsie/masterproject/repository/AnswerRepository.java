package mshsie.masterproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mshsie.masterproject.model.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

	@Modifying
	@Transactional
    @Query("DELETE FROM Answer a where a.question.id = ?1")
    void deleteAnswersOfQuestion(Long id);

	Answer findAnswerById(Long id);
}
