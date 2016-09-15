package controllers.administrator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	// Constructors -----------------------------------------------------------

	public DashboardAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/show-dashboard", method = RequestMethod.GET)
	public ModelAndView showDashboard() {
			
		ModelAndView result = new ModelAndView("dashboard/show-dashboard");
		result.addObject("requestURI", "dashboard/administrator/show-dashboard.do");

		return result;
	}

}
