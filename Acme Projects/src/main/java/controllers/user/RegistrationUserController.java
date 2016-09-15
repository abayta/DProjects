package controllers.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RegistrationService;
import controllers.AbstractController;
import domain.Registration;

@Controller
@RequestMapping("/registration/user")
public class RegistrationUserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RegistrationService registrationService;

	// Constructors -----------------------------------------------------------

	public RegistrationUserController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-by-project", method = RequestMethod.GET)
	public ModelAndView listAvailable(@RequestParam int projectId, String ret) {
		ModelAndView result;
		Collection<Registration> registrations;

		registrations = registrationService.findAllByProject(projectId);
		result = new ModelAndView("registration/list");
		result.addObject("registrations", registrations);
		result.addObject("projectId", projectId);
		result.addObject("ret", redirectCancel(ret));
		result.addObject("requestURI", "registrations/user/list-by-project.do");

		return result;
	}

	// Creation ---------------------------------------------------------------
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public ModelAndView join(@RequestParam int projectId, String ret) {
		ModelAndView res;

		try {
			registrationService.create(projectId);
			res = new ModelAndView("redirect:/project/user/list-details.do?projectId=" + projectId 
					+ "&ret=" + ret);
		} catch (Exception e) {
			res = new ModelAndView("redirect:/project/user/list-details.do?projectId=" + projectId 
					+ "&ret=" + ret);
		}
		
		return res;
	}

	// Edition ----------------------------------------------------------------
	
	@RequestMapping(value = "/leave", method = RequestMethod.GET)
	public ModelAndView leave(@RequestParam int projectId, String ret) {
		ModelAndView res;

		try {
			registrationService.delete(projectId);
			res = new ModelAndView("redirect:/project/user/list-details.do?projectId=" + projectId 
					+ "&ret=" + ret);
		} catch (Exception e) {
			res = new ModelAndView("redirect:/project/user/list-details.do?projectId="	+ projectId 
					+ "&ret=" + ret);
		}
		
		return res;
	}

	// Ancillary Methods -------------------------------------------------------
	
	public String redirectCancel(String ret) {
		String res = null;
		
		switch (ret) {
		case "0":
			res = "project/user/list-available.do";
			break;
		case "1":
			res = "project/user/list-my-created.do";
			break;
		case "2":
			res = "project/user/list-vacancy.do";
			break;
		case "3":
			res = "project/user/list-my-joined.do";
			break;
		case "4":
			res = "project/user/list-my-followed.do";
			break;
		default:
			res = null;
		}
		
		return res;
	}
	
}
