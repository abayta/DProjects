package controllers.participant;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EventService;
import services.RegistrationService;

import controllers.AbstractController;
import domain.Event;
import domain.Registration;

@Controller
@RequestMapping("/event/participant")
public class EventParticipantController extends AbstractController{
	
	@Autowired
	private EventService eventService;
	@Autowired
	private RegistrationService registrationService;
	
	public EventParticipantController(){
		super();
	}
	
	@RequestMapping(value="/listOnGoing", method = RequestMethod.GET)
	public ModelAndView listOnGoing(){
		ModelAndView result;
		Collection<Event> events = eventService.findAllOnGoing();
		
		result = new ModelAndView("event/list/ongoing");
		result.addObject("events", events);
		result.addObject("requestURI", "event/participant/listOnGoing.do");
		
		return result;
		
	}
	
	@RequestMapping(value="/list-actives", method = RequestMethod.GET)
	public ModelAndView listActives(){
		ModelAndView result;
		Collection<Event> events = eventService.findAllActive();
		
		result = new ModelAndView("event/active");
		result.addObject("events", events);
		result.addObject("requestURI", "event/participant/list-actives.do");
		
		return result;
		
	}
	
	//List Event that he or she is joined
	@RequestMapping(value="/list-joined", method= RequestMethod.GET)
	public ModelAndView listJoined(){
		ModelAndView result;
		Collection<Event> events = eventService.findJoinedParticipant();
		
		result = new ModelAndView("event/joined");
		result.addObject("events", events);
		result.addObject("joined", true);
		result.addObject("requestURI", "event/participant/list-joined.do");
		
		return result;
	}
	
	@RequestMapping(value="/list-joined-order", method= RequestMethod.GET)
	public ModelAndView listJoinedOrder(){
		ModelAndView result;
		Collection<Event> events = eventService.findAllJoinedOrder();
		
		result = new ModelAndView("event/joined");
		result.addObject("events", events);
		
		result.addObject("requestURI", "event/participant/list-joined-order.do");
		
		return result;
	}
	
	@RequestMapping(value="/details", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int eventId){
		ModelAndView result;
		Boolean isJoined;
		Event event = eventService.findOne(eventId);
		long participantNumber;
		
		participantNumber = eventService.participantOfEvent(eventId);
		isJoined = registrationService.isRegistrate(eventId);
		
		
		result = new ModelAndView("event/display");
		result.addObject("event", event);
		result.addObject("participantNumber", participantNumber);
		result.addObject("isJoined", isJoined);
		result.addObject("past", new Date().after(event.getFinishMoment()));
		result.addObject("onGoing", new Date().after(event.getStartMoment()));
		
		return result;
		
	}
	
	//Join
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public ModelAndView join(@RequestParam int eventId){
		ModelAndView result;
		Boolean isJoined;
		Registration registration;
		
		Event event = eventService.findOne(eventId);
		try{
			registration = registrationService.create(event);
			registrationService.save(registration);
		}catch(Throwable oops){
			
		}
		long participantNumber = eventService.participantOfEvent(eventId);
		isJoined = registrationService.isRegistrate(eventId);
		
		result = new ModelAndView("event/display");
		result.addObject("event", event);
		result.addObject("participantNumber", participantNumber);
		result.addObject("isJoined", isJoined);
		
		return result;
		
	}

}
