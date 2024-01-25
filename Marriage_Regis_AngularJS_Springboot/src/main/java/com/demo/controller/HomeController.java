package com.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.demo.entities.CustomUserDetails;

@Controller
public class HomeController {
	
	@GetMapping("/home")
	public String showHomePage() {
		return "home";
	}

	@GetMapping("/check")
	public String show(@AuthenticationPrincipal CustomUserDetails cs) {
		String s= cs.getRole();
		if(s.equals("ROLE_ADMIN")){
			return "redirect:/welcome";
		}else if(s.equals("ROLE_NORMAL")) {
			return "redirect:/normal";
		}
		
		return "redirect:/signin";
	}
	
	
	
	@GetMapping("/index")
	public String index() {
		return "index";
	}

	@GetMapping("/persondetail")
	public String listPersonDetail() {

		return "persondetails";
	}

	@GetMapping("/previewpage")
	public String previewPage() {

		return "previewpage";
	}
	
	
	@GetMapping("/chart")
	public String chart() {

		return "chart";
	}

	@GetMapping("/signin")
	public String showLoginForm() {
		return "login";
	}

	@GetMapping("/welcome")
	public String showWelcome(@AuthenticationPrincipal CustomUserDetails cs ) {
		return "welcome";
	}
	
	@GetMapping("/normal")
	public String showWel(@AuthenticationPrincipal CustomUserDetails cs) {
		return "normal";
	}
	
}
