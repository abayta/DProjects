package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.EventService;
import services.RouteService;

import controllers.AbstractController;

@Controller
@RequestMapping("/administrator")
public class CalculateRatingAdministratorController extends AbstractController {
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private RouteService routeService;
	@Autowired
	private EventService eventService;
	
	public CalculateRatingAdministratorController(){
		super();
	}
	
	@RequestMapping(value = "/calculate", method = RequestMethod.GET)
	public ModelAndView calculateRating(){
		ModelAndView result;
		
		administratorService.calculateRating();
		
		long events = eventService.countAllEvents();
		long routes = routeService.numberOfRoutes();
		
		result = new ModelAndView("calculateRating/list");
		result.addObject("events", events);
		result.addObject("routes", routes);
		
		return result;
		
	}

}
