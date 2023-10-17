package mshsie.masterproject.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import mshsie.masterproject.model.AssignedHomework;
import mshsie.masterproject.model.Chapter;
import mshsie.masterproject.repository.AssignedHomeworkRepository;

@Service
public class AssignedHomeworkService {

	@Autowired
	AssignedHomeworkRepository ahRepository;
	
	// Ruan dokumentin e detyres
	public AssignedHomework saveAssignedHomework(MultipartFile file, Chapter chapter, LocalDateTime submissionDate) {
		AssignedHomework doc = new AssignedHomework();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		doc.setHwName(fileName);
		try {
			doc.setHwFile(file.getBytes());
			doc.setSubmissionDate(submissionDate);
			doc.setChapter(chapter);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ahRepository.save(doc);
	}
	
	public AssignedHomework findHwById(Long id) {
		AssignedHomework file = ahRepository.findHwById(id);
		if(file != null)
			return file;
		else return new AssignedHomework();
	}
	
	public void deleteAssignedHomework(Long id) {
		ahRepository.deleteAssignedHomework(id);
	}
}
