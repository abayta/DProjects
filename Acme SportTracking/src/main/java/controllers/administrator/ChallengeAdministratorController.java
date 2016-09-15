/* ChallengeController.java
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers.administrator;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ChallengeService;
import services.EventService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Challenge;
import domain.Event;

@Controller
@RequestMapping("/challenge/administrator")
public class ChallengeAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ChallengeService challengeService;
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private EventService eventService;

	// Constructors -----------------------------------------------------------

	public ChallengeAdministratorController() {
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
		if (eventService.findOne(eventId).getAdministrator().equals(administratorService.findByPrincipal())
				&& eventService.findOne(eventId).getStartMoment().after(new Date()))
			result.addObject("isAdministrator", true); // Checks the button "Create" for challenges
		else
			result.addObject("isAdministrator", false); // Checks the button "Create" for challenges
		result.addObject("requestURI", "challenge/administrator/list-available.do");

		return result;
	}

	@RequestMapping(value = "/list-details", method = RequestMethod.GET)
	public ModelAndView listDetails(@RequestParam int challengeId) {
		ModelAndView result;
		Administrator administrator;
		Challenge challenge;

		administrator = administratorService.findByPrincipal();
		challenge = challengeService.findOne(challengeId);
		result = new ModelAndView("challenge/display");
		result.addObject("challenge", challenge);
		if (administrator.equals(challengeService.findOne(challengeId).getEvent().getAdministrator()))
			result.addObject("owned", true);
		else
			result.addObject("owned", false);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int eventId) {
		ModelAndView result;
		Event event;
		Challenge challenge;

		event = eventService.findOne(eventId);
		challenge = challengeService.create(event);
		result = createEditModelAndView(challenge);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int challengeId) {
		ModelAndView result;
		Challenge challenge;

		challenge = challengeService.findOneToEdit(challengeId);
		result = createEditModelAndView(challenge);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Challenge challenge, BindingResult bindingResult) {
		ModelAndView result;
		Event event = challenge.getEvent();
		
		if (bindingResult.hasErrors()) {
			result = createEditModelAndView(challenge);
		} else {
			try {
				challengeService.save(challenge);
				result = new ModelAndView("redirect:list-available.do?eventId=" + event.getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(challenge, "challenge.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Challenge challenge, BindingResult bindingResult) {
		ModelAndView result;
		Event event = challenge.getEvent();

		try {
			challengeService.delete(challenge);
			result = new ModelAndView("redirect:list-available.do?eventId=" + event.getId());
		} catch (Throwable oops) {
			result = createEditModelAndView(challenge, "challenge.commit.error");
		}

		return result;
	}

	// Ancillary Methods --------------------------------------------------------

	protected ModelAndView createEditModelAndView(Challenge challenge) {
		assert challenge != null;
		ModelAndView result;

		result = createEditModelAndView(challenge, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Challenge challenge, String message) {
		assert challenge != null;
		ModelAndView result;

		result = new ModelAndView("challenge/edit");
		result.addObject("challenge", challenge);
		result.addObject("message", message);

		return result;
	}

}