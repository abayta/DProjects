package controllers.participant;

import java.util.Collection;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AssessmentRouteService;
import services.RouteService;

import controllers.AbstractController;
import domain.AssessmentRoute;
import domain.Route;

@Controller
@RequestMapping("/assessmentRoute/participant")
public class AssessmentRouteController extends AbstractController {
	@Autowired
	private AssessmentRouteService assessmentRouteService;
	@Autowired
	private RouteService routeService;
	
	public AssessmentRouteController(){
		super();
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		Collection<AssessmentRoute> assessments = assessmentRouteService.findByPrincipal();
		
		result = new ModelAndView("assessmentRoute/list");
		result.addObject("assessmentsRoutes", assessments);
		result.addObject("requestURI", "assessmentRoute/participant/list.do");
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int routeId){
		ModelAndView result;
		Route route = routeService.findOne(routeId);
		AssessmentRoute assessmentRoute = assessmentRouteService.findByRouteAndParticipant(routeId);
		
		if (assessmentRoute == null){
			assessmentRoute = assessmentRouteService.create(route);
		}
		result = new ModelAndView("assessmentRoute/edit");
		result.addObject("assessmentRoute", assessmentRoute);
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView assess(@Valid AssessmentRoute assessmentRoute, BindingResult bindingResult){
		ModelAndView result;
		
		if (bindingResult.hasErrors()) {
			result = new ModelAndView("assessmentRoute/edit");
			result.addObject("assessmentRoute", assessmentRoute);
		}else{
			try{
				assessmentRouteService.save(assessmentRoute);
				result = new ModelAndView("redirect:../../route/participant/details.do?routeId="+assessmentRoute.getRoute().getId());
			}catch(Throwable oops){
				result = new ModelAndView("assessmentRoute/edit");
				result.addObject("assessmentRoute", assessmentRoute);
				result.addObject("message", "assessmentRoute.error.commit");
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid AssessmentRoute assessmentRoute, BindingResult bindingResult) {
		ModelAndView result;

		try {
			assessmentRouteService.delete(assessmentRoute);
			result = new ModelAndView("redirect:../../route/participant/details.do?routeId="+assessmentRoute.getRoute().getId());
		} catch (Throwable oops) {
			result = new ModelAndView("assessmentRoute/edit");
			result.addObject("assessmentRoute", assessmentRoute);
			result.addObject("message", "assessmentRoute.error.commit");
		}

		return result;
	}
}
