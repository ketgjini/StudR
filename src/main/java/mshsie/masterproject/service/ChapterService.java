package mshsie.masterproject.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mshsie.masterproject.model.Chapter;
import mshsie.masterproject.model.Course;
import mshsie.masterproject.model.DisabledChapter;
import mshsie.masterproject.model.File;
import mshsie.masterproject.model.User;
import mshsie.masterproject.repository.ChapterRepository;

@Service
public class ChapterService {
	
	@Autowired
	private ChapterRepository chapterRepository;
	
	@Autowired
	private UserQuizService userQuizService;

	// Ruan nje kapitull te ri
	public void saveChapter(Chapter chapter) {
		chapter = chapterRepository.save(chapter);
	}
	
	// Update kapitull ekzistues
	public void updateChapter(Chapter chapter) {
		chapter = chapterRepository.save(chapter);
	}
	
	//Gjen nje kapitull me id
	public Chapter findChapterById(Long id) {
		Chapter chapter = chapterRepository.findChapterById(id);
		if(chapter != null)
			return chapter;
		else return new Chapter();
	}
	
	//Fshin nje kapitull
	public void deleteChapter(Long id) {
		Chapter chapter = chapterRepository.findChapterById(id);
		chapterRepository.delete(chapter);
	}
	
	public LinkedHashMap<Long, DisabledChapter> getDisabledChapters(Course course, User user) {
		LinkedHashMap<Long, DisabledChapter> chaptersMap = new LinkedHashMap<>();
		SortedSet<Chapter> chapters = course.getChapters();
		Chapter previousChapter = null;
		Boolean isEnabled = false;
		for(Chapter chapter : chapters) {
			DisabledChapter dch = new DisabledChapter();
			Long key = chapter.getId();
			if(previousChapter != null) {
				if(!chapter.equals(previousChapter)) {
					if(previousChapter.getQuiz() != null) {
						isEnabled = userQuizService.takenMoreThanThreeOrMoreThan75(user, previousChapter.getQuiz());
					} 
					dch.setChapterTitle(chapter.getTitle());
					dch.setIsEnabled(isEnabled);
				}
			} else {
				isEnabled = true;
				dch.setChapterTitle(chapter.getTitle());
				dch.setIsEnabled(isEnabled);
			}
				
			chaptersMap.put(key, dch);
			previousChapter = chapter;
			isEnabled = false;
		}

		return chaptersMap;
	}
	
	

}
