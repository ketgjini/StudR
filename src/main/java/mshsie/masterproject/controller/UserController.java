package mshsie.masterproject.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import mshsie.masterproject.model.User;
import mshsie.masterproject.service.PictureService;
import mshsie.masterproject.service.UserService;
import mshsie.masterproject.validation.UserValidation;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	PictureService pictureService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@GetMapping({"/user_profile" })
	public String userProfile(Model model,  Authentication authentication) throws UnsupportedEncodingException {
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		String loggedUser = userPrincipal.getUsername();
		User user = userService.findByUsername(loggedUser);
		user.setPassword(user.getPassword());
		if(user.getPicture() != null) {
			Base64 b = new Base64();
			byte[] encodeBase64 = b.encode(user.getPicture().getPicture());
		    String base64Encoded = new String(encodeBase64, "UTF-8");
		    model.addAttribute("userImage", base64Encoded);
		}
		model.addAttribute("picture", user.getPicture());
		model.addAttribute("user", user);
		return "user_profile";
	}
	
	@GetMapping("/edit_profile/{uid}")
	public String editUserData(@PathVariable("uid") Long uid, Model model) throws UnsupportedEncodingException {
		User user = userService.findUserById(uid);
		if(user.getPicture() != null) {
			Base64 b = new Base64();
			byte[] encodeBase64 = b.encode(user.getPicture().getPicture());
		    String base64Encoded = new String(encodeBase64, "UTF-8");
		    model.addAttribute("userImage", base64Encoded);
		}
		model.addAttribute("picture", user.getPicture());
		model.addAttribute("user", user);
		return "/edit_profile";
	}

	@PostMapping("/updateUser/{uid}")
	public String updateUserData(@PathVariable("uid") Long uid, 
			@Valid @ModelAttribute User user, BindingResult bindingResult, Model model, @RequestParam("profilePic") MultipartFile photo) throws IOException {

		User u = userService.findUserById(uid);
		
		if(user.getPassword() == null || user.getPassword().isEmpty())
			user.setPassword(u.getPassword());
		
		u.setFirstname(user.getFirstname());
		u.setLastname(user.getLastname());
		u.setActive(user.getActive());
		u.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		String fileName = StringUtils.cleanPath(photo.getOriginalFilename());
		if(!fileName.isEmpty())
			pictureService.savePictures(photo, u);
		userService.updateUser(u);
		
		model.addAttribute("user", u);
		return "redirect:/user_profile";
	}
	
	@GetMapping("/remove/user/{uid}/course/{cid}")
    public String deleteCourse(@PathVariable("uid") Long uid, @PathVariable("cid") Long cid, Model model, Authentication authentication) throws IOException {

        userService.unsubFromCourse(uid, cid);

        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		String loggedUser = userPrincipal.getUsername();
		User user = userService.findByUsername(loggedUser);
		
		model.addAttribute("user", user);
        return "redirect:/user_courses";
    }
	
	@GetMapping("/forgot_password")
    public String redirectToChangePassword(Model model)  {
        return "forgot_password";
    }
	
	@PostMapping("/changeUserPassword")
    public String changeUserPassword(HttpServletRequest request, Model model) {
		String path = "";
		String email = request.getParameter("email");
		String newPass = request.getParameter("password");
		String confPass = request.getParameter("confirmPassword");
		String encNewPass = bCryptPasswordEncoder.encode(newPass);
		User u = userService.findByEmail(email);
		if(u == null || u.getActive() == 0) {
			model.addAttribute("notPresent", true);
			path =  "/forgot_password";
		} else {
			if(!newPass.equals(confPass)) {
				model.addAttribute("notTheSame", true);
				path =  "/forgot_password";
			} else {
				u.setPassword(encNewPass);
				userService.updateUser(u);
				sendEmail(u.getEmail(), u.getFirstname());
				path = "redirect:/login";
			}
		}
		
		return path;
    }
	
	@GetMapping("/about")
    public String redirectToAboutUs(Model model)  {
        return "/about";
    }
	
	void sendEmail(String email, String name) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Ndryshim fjalëkalimi");
        msg.setText("Përshëndetje "+ name + "!\nFjalëkalimi juaj në platformën StudR sapo ndryshoi.");
        
        javaMailSender.send(msg);
    }

}
