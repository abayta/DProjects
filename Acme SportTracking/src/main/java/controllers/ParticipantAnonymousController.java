/* PositionController.java
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ParticipantService;
import controllers.AbstractController;
import domain.Participant;

@Controller
@RequestMapping("/participant")
public class ParticipantAnonymousController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ParticipantService participantService;

	// Constructors -----------------------------------------------------------

	public ParticipantAnonymousController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-available", method = RequestMethod.GET)
	public ModelAndView listAvailable(@RequestParam int eventId) {
		ModelAndView result;
		Collection<Participant> participants;

		participants = participantService.findJoinedEvent(eventId);
		result = new ModelAndView("participant/list");
		result.addObject("participants", participants);
		result.addObject("eventId", eventId);
		result.addObject("requestURI", "participant/list-available.do");

		return result;
	}

}