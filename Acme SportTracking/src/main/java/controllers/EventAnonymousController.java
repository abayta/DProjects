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

import services.EventService;
import controllers.AbstractController;
import domain.Event;

@Controller
@RequestMapping("/event")
public class EventAnonymousController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private EventService eventService;

	// Constructors -----------------------------------------------------------

	public EventAnonymousController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-available", method = RequestMethod.GET)
	public ModelAndView listAvailable() {
		ModelAndView result;
		Collection<Event> events;

		events = eventService.findAllActive();
		result = new ModelAndView("event/active");
		result.addObject("events", events);
		result.addObject("requestURI", "event/list-available.do");

		return result;
	}

	@RequestMapping(value = "/list-details", method = RequestMethod.GET)
	public ModelAndView listDetails(@RequestParam int eventId) {
		ModelAndView result;
		Event event;
		long participantNumber;
		
		participantNumber = eventService.participantOfEvent(eventId);

		event = eventService.findOne(eventId);
		result = new ModelAndView("event/display");
		result.addObject("event", event);
		result.addObject("participantNumber", participantNumber);

		return result;
	}
	
	@RequestMapping(value = "/list-available-onGoing", method = RequestMethod.GET)
	public ModelAndView listAvailableOnGoing() {
		ModelAndView result;
		Collection<Event> events;

		events = eventService.findAllOnGoing();
		result = new ModelAndView("event/list/ongoing");
		result.addObject("events", events);
		result.addObject("requestURI", "event/list-available-onGoing.do");

		return result;
	}
	
	@RequestMapping(value = "/list-all-order", method = RequestMethod.GET)
	public ModelAndView listAllOrder() {
		ModelAndView result;
		Collection<Event> events;

		events = eventService.findAllOrder();
		result = new ModelAndView("event/list");
		result.addObject("events", events);
		result.addObject("requestURI", "event/list-all-order.do");

		return result;
	}

}