package com.tusuperjk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "redirect:/tendero/homepage";
	}

	@GetMapping("/error")
	public String error() {
		return "error";
	}

	@GetMapping("/test")
	@ResponseBody
	public String test() {
		return "CONTROLADORES FUNCIONANDO - SPRING DETECTA LAS RUTAS";
	}
}