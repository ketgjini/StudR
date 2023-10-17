package mshsie.masterproject.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import mshsie.masterproject.model.User;
import mshsie.masterproject.service.UserService;
import mshsie.masterproject.validation.UserValidation;

@Controller
public class RegistrationController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidation userValidation;
	
	@Autowired
    private JavaMailSender javaMailSender;

	@GetMapping("user_registration")
	public String registration(Model model) {
		model.addAttribute("user", new User());

		return "user_registration";
	}

	@PostMapping("user_registration")
	public String registration(@Valid @ModelAttribute User user, BindingResult bindingResult) {

		userValidation.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			return "user_registration";
		}
		
		userService.saveUser(user);
		sendEmail(user.getEmail(), user.getFirstname());
		
		return "redirect:/login";
	}
	
	void sendEmail(String email, String name) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Regjistrim në StudR");
        msg.setText("Përshëndetje "+ name + "!\nRegjistrimi juaj në platformën StudR sapo ka përfunduar.\nPërmirëso Edukimin tënd sot!");
        
        javaMailSender.send(msg);
    }
}
