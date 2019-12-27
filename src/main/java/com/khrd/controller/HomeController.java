package com.khrd.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String replyTest() {
		return "replyTest";
	}
	
	@RequestMapping(value = "member", method = RequestMethod.GET)
	public String member() {
		return "member";
	}
	
	@RequestMapping(value = "doA", method = RequestMethod.GET)
	public String doA() {
		System.out.println("HomeController ::: doA");
		return "home";
	}
	
	@RequestMapping(value = "doB", method = RequestMethod.GET)
	public String doB(Model model) {
		System.out.println("HomeController ::: doB");
		model.addAttribute("result", "결과값이다 예끼 이 눔아!");
		return "home";
	}
}
