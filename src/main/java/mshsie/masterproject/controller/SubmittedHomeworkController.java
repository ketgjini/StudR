package mshsie.masterproject.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
import mshsie.masterproject.model.SubmittedHomework;
import mshsie.masterproject.model.User;
import mshsie.masterproject.service.ChapterService;
import mshsie.masterproject.service.SubmittedHomeworkService;
import mshsie.masterproject.service.UserService;

@Controller
public class SubmittedHomeworkController {

	@Autowired
	SubmittedHomeworkService shwService;

	@Autowired
	ChapterService chapterService;

	@Autowired
	UserService userService;

	@GetMapping("/student/submit_homework/{cid}")
	public String submitHomework(@PathVariable("cid") Long cid, Model model) {

		Chapter chapter = chapterService.findChapterById(cid);
		model.addAttribute("chapter", chapter);
		model.addAttribute("submittedHomework", new SubmittedHomework());
		return "/student/submit_homework";
	}

	@PostMapping("/submitHomework/{cid}")
	public String saveSubmittedHw(@PathVariable("cid") Long cid, Model model, @RequestParam("file") MultipartFile file,
			Authentication authentication) {

		LocalDateTime dateTime = LocalDateTime.now();
		User user = getLoggedUser(authentication);
		Chapter chapter = chapterService.findChapterById(cid);
		shwService.saveSubmittedHomework(file, chapter, user, dateTime);
		return "redirect:/chapter_details/" + chapter.getId();
	}

	@GetMapping("/delete/submission/{hid}/chapter/{cid}")
	public String deleteSubmittedHw(@PathVariable("hid") Long hid, @PathVariable("cid") Long cid, Model model,
			Authentication authentication) throws IOException {
		User user = getLoggedUser(authentication);
		shwService.deleteSubmittedHomework(hid, cid, user.getId());
		Chapter chapter = chapterService.findChapterById(cid);
		return "redirect:/chapter_details/" + chapter.getId();
	}

	@GetMapping("download/shw/{shid}")
	public void downloadFile(@PathVariable("shid") Long shid, HttpServletResponse resp) throws IOException {

		SubmittedHomework shwFile = shwService.findSubmittedHwById(shid);

		byte[] byteArray = shwFile.getSubmittedHomeworkFile(); // read the byteArray

		resp.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM.getType());
		resp.setHeader("Content-Disposition", "attachment; filename=" + shwFile.getSubmittedHomeworkName());
		resp.setContentLength(byteArray.length);

		OutputStream os = resp.getOutputStream();
		try {
			os.write(byteArray, 0, byteArray.length);
		} finally {
			os.close();
		}

	}

	@GetMapping("/teacher/students_homework/{cid}")
	public String showAllQuizResults(@PathVariable("cid") Long cid, Model model, Authentication authentication) {
		Chapter chapter = chapterService.findChapterById(cid);
		List<SubmittedHomework> submittedHomework = shwService.findSubmittedHomeworkForChapterHomework(cid);
		AssignedHomework assignedHomework = chapter.getTeacherHomework();
		ZonedDateTime zonedDateTime = ZonedDateTime.of(assignedHomework.getSubmissionDate(), ZoneId.of("Europe/Helsinki"));
		String submissionDate = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(zonedDateTime);
		
		if (!submittedHomework.isEmpty()) {
			model.addAttribute("submittedHomework", submittedHomework);
		} else {
			model.addAttribute("noSubmissions", true);
		}
		
		model.addAttribute("submissionDate", submissionDate);
		model.addAttribute("chapter", chapter);
		return "/teacher/students_homework";
	}

	public User getLoggedUser(Authentication authentication) {
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		String loggedUser = userPrincipal.getUsername();
		User user = userService.findByUsername(loggedUser);

		return user;
	}
}
