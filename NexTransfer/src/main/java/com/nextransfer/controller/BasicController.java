package com.nextransfer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BasicController {

	@RequestMapping("home")
	public String loadHomePage(Model m) {
		m.addAttribute("name", "Jake");
		return "home";
	}
}
