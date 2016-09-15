package controllers;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChallengeService;
import domain.Challenge;

@Controller
@RequestMapping("/challenge")
public class ChallengeAnonymousController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private ChallengeService challengeService;
	

	// Constructors -----------------------------------------------------------

	public ChallengeAnonymousController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-available", method = RequestMethod.GET)
	public ModelAndView listAvailable(@RequestParam int eventId) {
		ModelAndView result;
		Collection<Challenge> challenges;

		challenges = challengeService.findAllAssociatedEvent(eventId);
		result = new ModelAndView("challenge/list");
		result.addObject("challenges", challenges);
		result.addObject("eventId", eventId);
		
		result.addObject("requestURI", "challenge/list-available.do");

		return result;
	}

	@RequestMapping(value = "/list-details", method = RequestMethod.GET)
	public ModelAndView listDetails(@RequestParam int challengeId) {
		ModelAndView result;
		Challenge challenge;
	
		challenge = challengeService.findOne(challengeId);
		result = new ModelAndView("challenge/display");
		result.addObject("challenge", challenge);
		
		return result;
	}
}
