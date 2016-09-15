package controllers.administrator;

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
import controllers.AbstractController;
import domain.Group;

@Controller
@RequestMapping("/group/administrator")
public class GroupAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private GroupService groupService;

	// Constructors -----------------------------------------------------------

	public GroupAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-available", method = RequestMethod.GET)
	public ModelAndView listAvailable() {
		ModelAndView result;
		Collection<Group> groups;

		groups = groupService.findAllToAdministrator();
		result = new ModelAndView("group/list");
		result.addObject("groups", groups);
		result.addObject("requestURI", "group/administrator/list-available.do");

		return result;
	}

	@RequestMapping(value = "/list-details", method = RequestMethod.GET)
	public ModelAndView listDetails(@RequestParam int groupId) {
		ModelAndView result;
		Group group;

		group = groupService.findOne(groupId);
		result = new ModelAndView("group/display");
		result.addObject("group", group);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Group group;

		group = groupService.create();
		result = createEditModelAndView(group);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int groupId) {
		ModelAndView result;
		Group group;

		group = groupService.findOne(groupId);
		result = createEditModelAndView(group);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Group group, BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors()) {
			result = createEditModelAndView(group);
		} else {
			try {
				groupService.save(group);
				result = new ModelAndView("redirect:list-available.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(group, "group.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Group group, BindingResult bindingResult) {
		ModelAndView result;

		try {
			groupService.delete(group);
			result = new ModelAndView("redirect:list-available.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(group, "group.commit.error");
		}

		return result;
	}

	// Ancillary Methods -------------------------------------------------------

	protected ModelAndView createEditModelAndView(Group group) {
		assert group != null;
		ModelAndView result;

		result = createEditModelAndView(group, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Group group, String message) {
		assert group != null;
		ModelAndView result;

		result = new ModelAndView("group/edit");
		result.addObject("group", group);
		result.addObject("message", message);

		return result;
	}

}
