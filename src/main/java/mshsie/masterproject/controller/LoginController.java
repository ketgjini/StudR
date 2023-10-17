package mshsie.masterproject.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import mshsie.masterproject.model.User;
import mshsie.masterproject.service.UserService;
import mshsie.masterproject.validation.UserNotFoundException;

@Controller
public class LoginController {
	
	@Autowired
	UserService userService;

	@GetMapping(value = "/login")
	public String loginForm() {
		return "login";
	}

	//Form login me error
	@GetMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}
	
	 @GetMapping("/logout")
     public String fetchSignoutSite(HttpServletRequest request, HttpServletResponse response) {        
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         if (auth != null) {
             new SecurityContextLogoutHandler().logout(request, response, auth);
         }
           
         return "redirect:/login?logout";
     }

	@GetMapping({ "", "index","index.html" })
	public String indexPage(Model model) {
		return "index";
	}


}
