package mshsie.masterproject.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import mshsie.masterproject.model.Message;
import mshsie.masterproject.service.MessageService;

@Controller
public class MessageController {
	
	@Autowired
	MessageService messageService;
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	//Ruaj mesazhin e derguar nga perdoruesi
	@GetMapping("/contact")
	public String goToContactPage(Model model) {	
		model.addAttribute("message", new Message());
		return "contact";
	}
		
	//Ruaj mesazhin e derguar nga perdoruesi
	@PostMapping("/sendMessage")
	public String sendUserMessage(Model model, @Valid @ModelAttribute Message message) {
		messageService.saveMessage(message);
		sendEmail(message);
		return "redirect:/contact-success";
	}

	@GetMapping("/contact-success")
	public String showSuccessMessage(Model model) {	
		model.addAttribute("successMessage", true);
		model.addAttribute("message", new Message());
		return "contact";
	}
	
	void sendEmail(Message message) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setReplyTo(message.getEmail());
        msg.setTo("infostudr@gmail.com");
        msg.setSubject("Mesazh nga " + message.getName());
        msg.setText(message.getMessageBody());
        
        javaMailSender.send(msg);
    }
}
