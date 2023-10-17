package mshsie.masterproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mshsie.masterproject.model.AssignedHomework;

@Repository
public interface AssignedHomeworkRepository extends JpaRepository<AssignedHomework, Long> {

	AssignedHomework findHwById(Long id);

	@Modifying
	@Transactional
    @Query(value="DELETE FROM chapter_assigned_homework where chapter_id = ?1", nativeQuery = true)
    void deleteAssignedHomework(Long id);

}
