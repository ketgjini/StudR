package mshsie.masterproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import mshsie.masterproject.model.User;
import mshsie.masterproject.service.PictureService;
import mshsie.masterproject.service.UserService;

@Controller
public class PictureController {

	@Autowired
	PictureService pictureService;
	
	@Autowired
	UserService userService;
	
	 @GetMapping("/delete/user/{uid}")
	 public String deletePicture(@PathVariable("uid") Long uid, Model model) {
	 	pictureService.deletePicture(uid);
        User user = userService.findUserById(uid);
        
		model.addAttribute("user", user);
        return "redirect:/user_profile";
	 }
	 
}
