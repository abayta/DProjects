package controllers.participant;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RegistrationService;
import services.RouteService;

import controllers.AbstractController;
import domain.Route;

@Controller
@RequestMapping("/route/participant")
public class RouteParticipantController extends AbstractController{
	
	//Services ----------------------------------
	@Autowired
	private RouteService routeService;
	@Autowired
	private RegistrationService registrationService;
	
	public RouteParticipantController(){
		super();
	}
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int eventId){
		ModelAndView result;
		Collection<Route> routes;
		
		routes = routeService.findAllByEvent(eventId);
		result = new ModelAndView("route/list");
		result.addObject("order", true);
		result.addObject("eventId", eventId);
		result.addObject("routes", routes);
		result.addObject("requestURI", "/route/participant/list.do");
		
		return result;
	}
	
	@RequestMapping(value = "/list-all", method = RequestMethod.GET)
	public ModelAndView listAll(){
		ModelAndView result;
		Collection<Route> routes;
		
		routes = routeService.findAllByPricipal();
		result = new ModelAndView("route/list");
		result.addObject("routes", routes);
		result.addObject("requestURI", "/route/participant/list-all");
		
		return result;
	}
	
	@RequestMapping(value = "/list-order", method = RequestMethod.GET)
	public ModelAndView listByEventJoinedOrder(@RequestParam int eventId){
		ModelAndView result;
		Collection<Route> routes;
		
		routes = routeService.findByEventJoinedOrder(eventId);
		result = new ModelAndView("route/list");
		result.addObject("routes", routes);
		result.addObject("eventId", eventId);
		result.addObject("requestURI", "/route/participant/list-all.do");
		
		return result;
	}
	
	@RequestMapping(value="/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int routeId){
		ModelAndView result;
		Route route = routeService.findOne(routeId);
		Boolean isJoined = registrationService.isRegistrate(route.getEvent().getId());
		
		result = new ModelAndView("route/display");
		result.addObject("route", route);
		result.addObject("isJoined", isJoined);
		result.addObject("past", new Date().after(route.getEvent().getFinishMoment()));
		return result;
	}
}
