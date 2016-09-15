package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ProjectService;
import controllers.AbstractController;
import domain.Project;

@Controller
@RequestMapping("/project")
public class ProjectAnonymousController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ProjectService projectService;

	// Constructors -----------------------------------------------------------

	public ProjectAnonymousController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-available", method = RequestMethod.GET)
	public ModelAndView listAvailable() {
		ModelAndView result;
		Collection<Project> projects;

		projects = projectService.findAllActive();
		result = new ModelAndView("project/list");
		result.addObject("projects", projects);
		result.addObject("requestURI", "project/list-available.do");

		return result;
	}

}
