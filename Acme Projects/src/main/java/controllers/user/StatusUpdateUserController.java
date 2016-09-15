package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.StatusUpdateService;
import controllers.AbstractController;
import domain.StatusUpdate;

@Controller
@RequestMapping("/statusUpdate/user")
public class StatusUpdateUserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private StatusUpdateService statusUpdateService;

	// Constructors -----------------------------------------------------------

	public StatusUpdateUserController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-available", method = RequestMethod.GET)
	public ModelAndView listAvailable(@RequestParam int projectId, String ret) {
		ModelAndView result;
		Collection<StatusUpdate> statusUpdates;

		statusUpdates = statusUpdateService.findAllForProject(projectId);
		result = new ModelAndView("statusUpdate/list");
		result.addObject("statusUpdates", statusUpdates);
		result.addObject("projectId", projectId);
		result.addObject("ret", redirectCancel(ret));
		result.addObject("retNum", ret);
		result.addObject("requestURI", "statusUpdate/user/list-available.do");

		return result;
	}

	@RequestMapping(value = "/list-of-followed-projects", method = RequestMethod.GET)
	public ModelAndView listOfFollowedProjects() {
		ModelAndView result;
		Collection<StatusUpdate> statusUpdates;

		statusUpdates = statusUpdateService.findAllOfAllFollowedProject();
		result = new ModelAndView("statusUpdate/list-my-followed-projects");
		result.addObject("statusUpdates", statusUpdates);
		result.addObject("requestURI", "statusUpdate/user/list-of-followed-projects.do");

		return result;
	}

	@RequestMapping(value = "/list-details", method = RequestMethod.GET)
	public ModelAndView listDetails(@RequestParam int statusUpdateId, int projectId, String ret) {
		ModelAndView result;
		StatusUpdate statusUpdate;

		statusUpdate = statusUpdateService.findOne(statusUpdateId);
		result = new ModelAndView("statusUpdate/display");
		result.addObject("statusUpdate", statusUpdate);
		result.addObject("projectId", projectId);
		result.addObject("ret", redirectCancel(ret));
		result.addObject("retNum", ret);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int projectId, String ret) {
		ModelAndView result;
		StatusUpdate statusUpdate;

		statusUpdate = statusUpdateService.create(projectId);
		result = createEditModelAndView(statusUpdate);
		result.addObject("retNum", ret);

		return result;
	}

	// Edition ----------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid StatusUpdate statusUpdate, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(statusUpdate);
		} else {
			try {
				statusUpdateService.save(statusUpdate);
				result = new ModelAndView("redirect:/project/user/list-my-created.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(statusUpdate, "project.commit.error");
			}
		}

		return result;
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
	
	protected ModelAndView createEditModelAndView(StatusUpdate statusUpdate) {
		assert statusUpdate != null;

		return createEditModelAndView(statusUpdate, null);
	}

	protected ModelAndView createEditModelAndView(StatusUpdate statusUpdate, String message) {
		assert statusUpdate != null;
		ModelAndView res;

		res = new ModelAndView("statusUpdate/edit");
		res.addObject("statusUpdate", statusUpdate);
		res.addObject("message", message);
		
		return res;
	}

}
