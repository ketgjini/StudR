package mshsie.masterproject.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import mshsie.masterproject.model.Chapter;
import mshsie.masterproject.model.File;
import mshsie.masterproject.repository.FileRepository;

@Service
public class FileService {
	
	@Autowired
	FileRepository fileRepository;
	
	// Ruan dokumentat
	public void saveFiles(MultipartFile[] files, Chapter chapter) {	
		for(MultipartFile file : files) {
			File doc = new File();
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			doc.setFileName(fileName);
			try {
				doc.setFile(file.getBytes());
				doc.setChapter(chapter);
			} catch (IOException e) {
				e.printStackTrace();
			}
		 fileRepository.save(doc);
		}
	}
		
	public File findFileById(Long id) {
		File file = fileRepository.findFileById(id);
		if(file != null)
			return file;
		else return new File();
	}
	
	public void deleteFile(Long id) {
		File file = fileRepository.findFileById(id);
		fileRepository.delete(file);
	}
}
