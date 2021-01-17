package ir.com.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Welcome {

	@RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
	public String hello() {
		
		return "home";
	}
	
}
