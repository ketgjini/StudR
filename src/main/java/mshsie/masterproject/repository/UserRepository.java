package mshsie.masterproject.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mshsie.masterproject.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	User findByEmail(String email);
	User findUserById(Long id);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	
	@Query( "SELECT u FROM User u WHERE role = :role AND active = 1")
	List<User> findBySpecificRole(String role);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM user_course WHERE  user_id=?1 AND course_id=?2", nativeQuery = true)
	 void unsubFromCourse(Long uid, Long cid);
}
