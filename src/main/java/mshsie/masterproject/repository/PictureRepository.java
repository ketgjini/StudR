package mshsie.masterproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mshsie.masterproject.model.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

	Picture findPictureById(Long id);
	
	@Modifying
	@Transactional
    @Query(value="DELETE FROM user_picture where user_id = ?1", nativeQuery = true)
    void deletePicture(Long id);
}
