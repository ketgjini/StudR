package mshsie.masterproject.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import mshsie.masterproject.model.Chapter;
import mshsie.masterproject.model.SubmittedHomework;
import mshsie.masterproject.model.User;
import mshsie.masterproject.repository.SubmittedHomeworkRepository;

@Service
public class SubmittedHomeworkService {

	@Autowired
	SubmittedHomeworkRepository shRepository;
	
	// Ruan dokumentin e detyres
	public SubmittedHomework saveSubmittedHomework(MultipartFile file, Chapter chapter, User user, LocalDateTime submittedDate) {
		SubmittedHomework doc = new SubmittedHomework();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		doc.setSubmittedHomeworkName(fileName);
		try {
			doc.setSubmittedHomeworkFile(file.getBytes());
			doc.setSubmittedDate(submittedDate);
			doc.setChapter(chapter);
			doc.setUser(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return shRepository.save(doc);
	}
	
	public SubmittedHomework findSubmittedHwById(Long id) {
		SubmittedHomework file = shRepository.findSubmittedHwById(id);
		if(file != null)
			return file;
		else return new SubmittedHomework();
	}
	
	public void deleteSubmittedHomework(Long sid, Long cid, Long uid) {
		shRepository.deleteSubmittedHomework(sid, cid, uid);
	}
	
	public SubmittedHomework findSubmittedHomeworkForChapter(Long cid, Long uid) {
		return shRepository.findSubmittedHomeworkByChapterAndUser(cid, uid);
	}
	
	public List<SubmittedHomework> findSubmittedHomeworkForChapterHomework(Long cid) {
		return shRepository.findSubmittedHomeworkByChapterId(cid);
	}
}
