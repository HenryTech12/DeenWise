package com.deenwise.demo.controller;
import com.deenwise.demo.dto.*;
import com.deenwise.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController
{
	
	@Autowired
	private UserService userService;

	@GetMapping("/home")
	public String home() {
		return "index";
	}
	@PostMapping("/user/login")
	public String login(Model model) {
	    model.addAttribute("user",new UserDTO());
		return "login";
	}
	@GetMapping("/login")
	public String loginPage(Model model, boolean error) {
		model.addAttribute("user", new UserDTO());
		model.addAttribute("error",error);
		return "login";
	}
	@GetMapping("/aboutus")
	public String aboutUs() {
		return "aboutus";
	}

	@GetMapping("/signup")
	public String registerPage(Model model) {
		model.addAttribute("user", new UserDTO());
		return "signup";
	}
	
	@PostMapping("/register/add")
	public String register(@ModelAttribute UserDTO user, Model model) {
		userService.addUser(user);
		model.addAttribute("user",user);
		return "login";
	}
	
	@GetMapping("/reset")
	public String resetPage(Model model) {
		model.addAttribute("user",new UserDTO());
		return "fpassword";
	}

	@GetMapping("/courses")
	public String courseHistory() {
		return "courses";
	}

	@GetMapping("/pricing")
	public String getPricing() {
		return "pricing";
	}

	@GetMapping("/password/reset")
	public String changePassword(@ModelAttribute UserDTO user, Model model, String newPassword) {
		userService.updatePassword(user, newPassword);
		model.addAttribute("user",user);
		return "login";
	}
}
