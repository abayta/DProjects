/* EventController.java
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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.EventService;
import controllers.AbstractController;

import domain.Administrator;
import domain.Event;
import forms.EventForm;

@Controller
@RequestMapping("/event/administrator")
public class EventAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private EventService eventService;
	@Autowired
	private AdministratorService administratorService;
	// Constructors -----------------------------------------------------------

	public EventAdministratorController() {
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
		result.addObject("requestURI", "event/administrator/list-available.do");

		return result;
	}
	
	@RequestMapping(value = "/list-created", method = RequestMethod.GET)
	public ModelAndView listCreated() {
		ModelAndView result;
		Collection<Event> events;

		events = eventService.findByPrincipal();
		result = new ModelAndView("event/list");
		result.addObject("events", events);
		result.addObject("created", true);
		result.addObject("isAdministrator", true); // Checks the link "Create a new event"
		result.addObject("requestURI", "event/administrator/list-created.do");

		return result;
	}
	
	@RequestMapping(value = "/list-created-order", method = RequestMethod.GET)
	public ModelAndView listCreatedOrder() {
		ModelAndView result;
		Collection<Event> events;

		events = eventService.findAllCreatedOrder();
		result = new ModelAndView("event/list");
		result.addObject("events", events);
		result.addObject("isAdministrator", true); // Checks the link "Create a new event"
		result.addObject("requestURI", "event/administrator/list-created.do");

		return result;
	}
	
	@RequestMapping(value = "/list-available-onGoing", method = RequestMethod.GET)
	public ModelAndView listAvailableOnGoing() {
		ModelAndView result;
		Collection<Event> events;

		events = eventService.findAllOnGoing();
		result = new ModelAndView("event/list/ongoing");
		result.addObject("events", events);
		result.addObject("requestURI", "event/administrator/list-available-onGoing.do");

		return result;
	}
	
	@RequestMapping(value = "/list-created-onGoing", method = RequestMethod.GET)
	public ModelAndView listCreatedOnGoing() {
		ModelAndView result;
		Collection<Event> events;

		events = eventService.findOnGoingByPrincipal();
		result = new ModelAndView("event/list/ongoing");
		result.addObject("events", events);
		result.addObject("requestURI", "event/administrator/list-created-onGoing.do");

		return result;
	}

	@RequestMapping(value = "/list-details", method = RequestMethod.GET)
	public ModelAndView listDetails(@RequestParam int eventId) {
		ModelAndView result;
		Administrator administrator;
		Event event;
		long participantNumber;
		
		participantNumber = eventService.participantOfEvent(eventId);

		administrator = administratorService.findByPrincipal();
		event = eventService.findOne(eventId);
		result = new ModelAndView("event/display");
		result.addObject("event", event);
		result.addObject("participantNumber", participantNumber);
		if (administrator.equals(eventService.findOne(eventId).getAdministrator()))
			result.addObject("owned", true);
		else
			result.addObject("owned", false);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
	
		Event event = eventService.create();
		result = createEditModelAndView(eventService.construct(event));
		
		return result;
	}
	
	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int eventId) {
		ModelAndView result;
		Event event;
		
		event = eventService.findOneToEdit(eventId);
		
		EventForm eventForm = eventService.construct(event);
		result = createEditModelAndView(eventForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid EventForm eventForm, BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors()) {
			result = createEditModelAndView(eventForm);
		} else {
			try {
				Event event = eventService.reconstruct(eventForm);
				eventService.save(event);
				result = new ModelAndView("redirect:list-created.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(eventForm, "event.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(EventForm eventForm, BindingResult bindingResult) {
		ModelAndView result;

		try {
			Event event = eventService.reconstruct(eventForm);
			eventService.delete(event);
			result = new ModelAndView("redirect:list-created.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(eventForm, "event.commit.error");
		}

		return result;
	}
	
	// Ancillary Methods --------------------------------------------------------

	protected ModelAndView createEditModelAndView(EventForm eventForm) {
		assert eventForm != null;
		ModelAndView result;

		result = createEditModelAndView(eventForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(EventForm eventForm, String message) {
		assert eventForm != null;
		ModelAndView result;

		result = new ModelAndView("event/edit");
		result.addObject("eventForm", eventForm);
		result.addObject("message", message);

		return result;
	}

}
