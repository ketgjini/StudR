package mshsie.masterproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mshsie.masterproject.model.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

	File findFileById(Long id);

}
