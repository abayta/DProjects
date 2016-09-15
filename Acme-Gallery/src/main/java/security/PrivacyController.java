package security;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;


import controllers.AbstractController;

@Controller
@RequestMapping("/privacy")
public class PrivacyController extends AbstractController {

	@RequestMapping(value = "/lopd-lssi")
	public ModelAndView lopdLssi() {
		ModelAndView result;
		
		result = new ModelAndView("privacy/lopd-lssi");
		
		return result;
	}
	
	@RequestMapping(value = "/cookies")
	public ModelAndView cookies() {
		ModelAndView result;
		
		result = new ModelAndView("privacy/cookies");
		
		return result;
	}
	
}
