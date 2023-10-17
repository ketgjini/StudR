package mshsie.masterproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mshsie.masterproject.model.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {

	Chapter findChapterById(Long id);

}
