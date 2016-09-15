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

import services.GroupService;
import services.RequirementService;
import controllers.AbstractController;
import domain.Group;
import domain.Requirement;

@Controller
@RequestMapping("/requirement/user")
public class RequirementUserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RequirementService requirementService;
	@Autowired
	private GroupService groupService;

	// Constructors -----------------------------------------------------------

	public RequirementUserController() {
		super();
	}

	// Creation ---------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int projectId) {
		ModelAndView result;
		Requirement requirement;
		Collection<Group> groups;

		groups = groupService.findAll();
		requirement = requirementService.create(projectId);
		result = createEditModelAndView(requirement);
		result.addObject("groups", groups);

		return result;
	}

	// Edition ----------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int requirementId) {
		ModelAndView result;
		Requirement requirement;

		requirement = requirementService.findOne(requirementId);
		result = createEditModelAndView(requirement);
		result.addObject("groups", groupService.findAll());

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Requirement requirement, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(requirement);
			result.addObject("groups", groupService.findAll());
		} else {
			try {
				requirementService.save(requirement);
				result = new ModelAndView(String.format("redirect:../../project/user/list-details.do?projectId=%d&ret=1",
								requirement.getProject().getId()));
			} catch (Throwable oops) {
				result = createEditModelAndView(requirement,"project.commit.error");
				result.addObject("groups", groupService.findAll());
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid Requirement requirement, BindingResult bindingResult) {
		ModelAndView result;

		try {
			requirementService.delete(requirement);
			result = new ModelAndView(String.format("redirect:../../project/user/list-details.do?projectId=%d&ret=1",
							requirement.getProject().getId()));
		} catch (Throwable oops) {
			result = createEditModelAndView(requirement, "project.commit.error");
		}

		return result;
	}

	// Ancillary Methods -------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Requirement requirement) {
		assert requirement != null;

		return createEditModelAndView(requirement, null);
	}

	protected ModelAndView createEditModelAndView(Requirement requirement, String message) {
		assert requirement != null;
		ModelAndView res;

		res = new ModelAndView("requirement/edit");
		res.addObject("requirement", requirement);
		res.addObject("message", message);
		
		return res;
	}

}
