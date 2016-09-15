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
import services.EventService;
import services.RouteService;
import controllers.AbstractController;
import domain.Event;
import domain.Route;

@Controller
@RequestMapping("/route/administrator")
public class RouteAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RouteService routeService;
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private EventService eventService;

	// Constructors -----------------------------------------------------------

	public RouteAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-available", method = RequestMethod.GET)
	public ModelAndView listAvailable(@RequestParam int eventId) {
		ModelAndView result;
		Collection<Route> routes;

		routes = routeService.findAllByEvent(eventId);
		result = new ModelAndView("route/list");
		result.addObject("routes", routes);
		result.addObject("eventId", eventId);
		result.addObject("requestURI", "route/list-available.do");
		if (eventService.findOne(eventId).getAdministrator().equals(administratorService.findByPrincipal())
				&& eventService.findOne(eventId).getStartMoment().after(new Date())){
			result.addObject("isAdministrator", true); // Checks the button "Create" for routes
			result.addObject("created", true);
		}else
			result.addObject("isAdministrator", false); // Checks the button "Create" for routes
		if (eventService.findOne(eventId).getRegistrations().isEmpty())
			result.addObject("notParticipant", true); // Checks the button "Create" for routes
		else
			result.addObject("notParticipant", false); // Checks the button "Create" for routes

		return result;
	}
	
	@RequestMapping(value = "/list-created-order", method = RequestMethod.GET)
	public ModelAndView listCreated() {
		ModelAndView result;
		Collection<Route> routes;

		routes = routeService.findAllCreatedOrder();
		result = new ModelAndView("route/list");
		result.addObject("routes", routes);
		result.addObject("created", true);
		result.addObject("requestURI", "route/administrator/list-created-order.do");

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int eventId) {
		ModelAndView result;
		Event event;
		Route route;

		event = eventService.findOne(eventId);
		route = routeService.create(event);
		result = createEditModelAndView(route);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int routeId) {
		ModelAndView result;
		Route route;

		route = routeService.findOneToEdit(routeId);
		result = createEditModelAndView(route);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Route route, BindingResult bindingResult) {
		ModelAndView result;
		Event event = route.getEvent();

		if (bindingResult.hasErrors()) {
			result = createEditModelAndView(route);
		} else {
			try {
				routeService.save(route);
				result = new ModelAndView("redirect:list-available.do?eventId=" + event.getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(route, "route.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Route route, BindingResult bindingResult) {
		ModelAndView result;
		Event event = route.getEvent();

		try {
			routeService.delete(route);
			result = new ModelAndView("redirect:list-available.do?eventId="	+ event.getId());
		} catch (Throwable oops) {
			result = createEditModelAndView(route, "route.commit.error");
		}

		return result;
	}

	// Ancillary Methods --------------------------------------------------------

	protected ModelAndView createEditModelAndView(Route route) {
		assert route != null;
		ModelAndView result;

		result = createEditModelAndView(route, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Route route, String message) {
		assert route != null;
		ModelAndView result;

		result = new ModelAndView("route/edit");
		result.addObject("route", route);
		result.addObject("message", message);

		return result;
	}

}