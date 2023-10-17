package mshsie.masterproject.controller;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import mshsie.masterproject.model.Chapter;
import mshsie.masterproject.model.Course;
import mshsie.masterproject.model.File;
import mshsie.masterproject.service.ChapterService;
import mshsie.masterproject.service.FileService;

@Controller
public class FileController {

	@Autowired
	FileService fileService;
	
	@Autowired
	ChapterService chapterService;
	
	@GetMapping("/teacher/add_files/{cid}")
	public String addFiles(@PathVariable("cid") Long cid, Model model, Authentication authentication) {
		
		Chapter chapter = chapterService.findChapterById(cid);
		model.addAttribute("chapter", chapter);
		model.addAttribute("file", new File());

		return "/teacher/add_files";
	}
	
	@PostMapping("/saveFiles/{cid}")
	public String saveFiles(@PathVariable("cid") Long cid, Model model, @RequestParam("file") MultipartFile[] file) {
		
		Chapter chapter = chapterService.findChapterById(cid);
		Course course = chapter.getCourse();
		fileService.saveFiles(file, chapter);
		
		Set<File> files = chapter.getFiles();
		int nrFiles = files.size();	
		model.addAttribute("chapter", chapter);
		model.addAttribute("course", course);
		model.addAttribute("files", files);
		model.addAttribute("numberOfFiles", nrFiles);

		return "redirect:/chapter_details/"+chapter.getId();
	}
	
    @GetMapping("/delete/file/{fid}/chapter/{cid}")
    public String deleteFile(@PathVariable("fid") Long fid, @PathVariable("cid") Long cid, Model model) throws IOException {

        fileService.deleteFile(fid);
        
        Chapter chapter = chapterService.findChapterById(cid);
		Course course = chapter.getCourse();
		
		Set<File> files = chapter.getFiles();
		int nrFiles = files.size();	
		model.addAttribute("chapter", chapter);
		model.addAttribute("course", course);
		model.addAttribute("files", files);
		model.addAttribute("numberOfFiles", nrFiles);
        
        return "redirect:/chapter_details/"+chapter.getId();
    }
	
}
