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

import services.RouteService;
import controllers.AbstractController;
import domain.Route;

@Controller
@RequestMapping("/route")
public class RouteAnonymousController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RouteService routeService;

	// Constructors -----------------------------------------------------------

	public RouteAnonymousController() {
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

		return result;
	}
	@RequestMapping(value = "/list-all-order", method = RequestMethod.GET)
	public ModelAndView listAvailable() {
		ModelAndView result;
		Collection<Route> routes;

		routes = routeService.findAllOrder();
		result = new ModelAndView("route/list");
		result.addObject("routes", routes);
		result.addObject("requestURI", "route/list-all-order.do");

		return result;
	}

}