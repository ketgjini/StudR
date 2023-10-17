package mshsie.masterproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import mshsie.masterproject.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

	Boolean existsByCourseName(String name);

	Course findCourseById(Long id);
	
	 @Query(value = "SELECT * FROM courses c WHERE c.course_name LIKE %?1% AND c.department LIKE %?2% "
	 		+ "AND c.dega LIKE %?3%", nativeQuery = true)
	 public List<Course> searchCourse(String courseName, String department, String dega);
	 
	 @Query(value = "SELECT * FROM courses c WHERE c.course_name LIKE %?1% AND c.department LIKE %?2% "
		 		+ "AND c.dega LIKE %?3%", nativeQuery = true)
	 public List<Course> searchCourseWithAllData(String courseName, String department, String dega);
	 
	 @Query(value = "SELECT * FROM courses c WHERE c.department LIKE %?1% "
		 		+ "AND c.dega LIKE %?2%", nativeQuery = true)
	 public List<Course> searchCourseByDepartmentAndDega(String department, String dega);
	 
	 @Query(value = "SELECT * FROM courses c WHERE c.course_name LIKE %?1% AND c.department LIKE %?2% ", nativeQuery = true)
	 public List<Course> searchCourseByDepartmentAndName(String department, String courseName);
	 
	 @Query(value = "SELECT * FROM courses c WHERE c.course_name LIKE %?1% AND c.dega LIKE %?2% ", nativeQuery = true)
	 public List<Course> searchCourseByNameAndDega(String courseName, String dega);
	 
	 public List<Course> findCourseByDepartment(String department);
	 public List<Course> findCourseByDega(String dega);
	 public List<Course> findCourseByCourseName(String courseName);

	@Query("SELECT c FROM Course c ORDER BY c.department ASC")
	List<Course> getAllCourses();

}
