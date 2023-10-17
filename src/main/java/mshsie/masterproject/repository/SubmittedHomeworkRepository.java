package mshsie.masterproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mshsie.masterproject.model.AssignedHomework;
import mshsie.masterproject.model.SubmittedHomework;

@Repository
public interface SubmittedHomeworkRepository extends JpaRepository<SubmittedHomework, Long> {

	SubmittedHomework findSubmittedHwById(Long id);

	@Modifying
	@Transactional
    @Query(value="DELETE FROM chapter_submitted_homework WHERE shw_id = ?1 AND chapter_id = ?2 AND user_id = ?3", nativeQuery = true)
    void deleteSubmittedHomework(Long sid, Long cid, Long uid);
	
	@Query(value="SELECT sh FROM SubmittedHomework sh WHERE sh.chapter.id = ?1 AND sh.user.id = ?2")
	SubmittedHomework findSubmittedHomeworkByChapterAndUser(Long cid, Long uid);

	@Query(value="SELECT sh FROM SubmittedHomework sh WHERE sh.chapter.id = ?1")
	List<SubmittedHomework> findSubmittedHomeworkByChapterId(Long cid);
}
