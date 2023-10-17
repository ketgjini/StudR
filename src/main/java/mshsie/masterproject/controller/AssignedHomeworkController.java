package mshsie.masterproject.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import mshsie.masterproject.model.AssignedHomework;
import mshsie.masterproject.model.Chapter;
import mshsie.masterproject.model.File;
import mshsie.masterproject.model.Quiz;
import mshsie.masterproject.service.AssignedHomeworkService;
import mshsie.masterproject.service.ChapterService;

@Controller
public class AssignedHomeworkController {

	@Autowired
	AssignedHomeworkService hwService;

	@Autowired
	ChapterService chapterService;

	@GetMapping("/teacher/add_homework/{cid}")
	public String addHomework(@PathVariable("cid") Long cid, Model model) {

		Chapter chapter = chapterService.findChapterById(cid);
		model.addAttribute("chapter", chapter);
		model.addAttribute("homework", new AssignedHomework());
		return "/teacher/add_homework";
	}

	@PostMapping("/assignHomework/{cid}")
	public String saveAssignedHw(@PathVariable("cid") Long cid, Model model, @RequestParam("file") MultipartFile file,
			@RequestParam("submissionTime") String submissionDate) {

		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		LocalDateTime dateTime = LocalDateTime.parse(submissionDate, formatter);

		Chapter chapter = chapterService.findChapterById(cid);
		hwService.saveAssignedHomework(file, chapter, dateTime);
		return "redirect:/chapter_details/" + chapter.getId();
	}

	@GetMapping("/delete/homework/{hid}/chapter/{cid}")
	public String deleteAssignedHw(@PathVariable("hid") Long hid, @PathVariable("cid") Long cid, Model model)
			throws IOException {

		hwService.deleteAssignedHomework(cid);
		Chapter chapter = chapterService.findChapterById(cid);
		return "redirect:/chapter_details/" + chapter.getId();
	}

	@GetMapping("download/hw/{hid}")
	public void downloadFile(@PathVariable("hid") Long hid, HttpServletResponse resp) throws IOException {

		AssignedHomework hwFile = hwService.findHwById(hid);

		byte[] byteArray = hwFile.getHwFile(); // read the byteArray

		resp.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM.getType());
		resp.setHeader("Content-Disposition", "attachment; filename=" + hwFile.getHwName());
		resp.setContentLength(byteArray.length);

		OutputStream os = resp.getOutputStream();
		try {
			os.write(byteArray, 0, byteArray.length);
		} finally {
			os.close();
		}

	}

}
