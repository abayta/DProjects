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

import services.ProjectService;
import controllers.AbstractController;
import domain.Project;
import forms.ProjectForm;

@Controller
@RequestMapping("/project/user")
public class ProjectUserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ProjectService projectService;

	// Constructors -----------------------------------------------------------

	public ProjectUserController() {
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
		result.addObject("ret", 0);
		result.addObject("requestURI", "project/user/list-available.do");

		return result;
	}

	@RequestMapping(value = "/list-my-created", method = RequestMethod.GET)
	public ModelAndView listMyCreatedProjects() {
		ModelAndView result;
		Collection<Project> projects;

		projects = projectService.findAllMyCreated();
		result = new ModelAndView("project/list/my-created");
		result.addObject("projects", projects);
		result.addObject("ret", 1);
		result.addObject("requestURI", "project/user/list-my-created.do");

		return result;
	}

	@RequestMapping(value = "/list-vacancy", method = RequestMethod.GET)
	public ModelAndView listRecruited() {
		ModelAndView result;
		Collection<Project> projects;

		projects = projectService.findAllVacancyByGroup();
		result = new ModelAndView("project/list/vacancy");
		result.addObject("projects", projects);
		result.addObject("ret", 2);
		result.addObject("requestURI", "project/user/list-vacancy.do");

		return result;
	}

	@RequestMapping(value = "/list-my-joined", method = RequestMethod.GET)
	public ModelAndView listMyJoinedProjects() {
		ModelAndView result;
		Collection<Project> projects;

		projects = projectService.findAllMyJoined();
		result = new ModelAndView("project/list/my-joined");
		result.addObject("projects", projects);
		result.addObject("ret", 3);
		result.addObject("requestURI", "project/user/list-my-joined.do");

		return result;
	}

	@RequestMapping(value = "/list-my-followed", method = RequestMethod.GET)
	public ModelAndView listMyFollowedProjects() {
		ModelAndView result;
		Collection<Project> projects;

		projects = projectService.findAllMyFollowed();
		result = new ModelAndView("project/list/my-followed");
		result.addObject("projects", projects);
		result.addObject("ret", 4);
		result.addObject("requestURI", "project/user/list-my-followed.do");

		return result;
	}
	
	@RequestMapping(value = "/list-details", method = RequestMethod.GET)
	public ModelAndView listDetails(@RequestParam int projectId, String ret) {
		ModelAndView result;
		Project project;
		long participantNumber;
		Boolean isFollowed;

		project = projectService.findOne(projectId);
		participantNumber = projectService.participantsOfProject(projectId);
		isFollowed = projectService.isFollowed(projectId);
		result = new ModelAndView("project/display");
		result.addObject("project", project);
		result.addObject("participantNumber", participantNumber);
		result.addObject("isFollowed", isFollowed);
		result.addObject("iAmJoined", projectService.iAmJoined(projectId));
		result.addObject("canJoin", projectService.iCanJoin(projectId));
		result.addObject("requirements", project.getRequirements());
		result.addObject("ret", redirectCancel(ret));
		result.addObject("retNum", ret);
		result.addObject("requestURI", "project/user/list-details.do");

		return result;
	}

	// Creation ---------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Project project;

		project = projectService.create();
		result = createEditModelAndView(project);

		return result;
	}

	// Edition ----------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView save(@RequestParam int projectId) {
		ModelAndView result;
		Project project;

		project = projectService.findOne(projectId);
		result = createEditModelAndView(project);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid ProjectForm projectForm, BindingResult binding) {
		ModelAndView result;
		Project project;
		
		project = projectService.reconstruct(projectForm);
		if (binding.hasErrors()) {
			result = createEditModelAndView(project);
		} else {
			try {
				projectService.save(project);
				result = new ModelAndView("redirect:/project/user/list-my-created.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(project, "project.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid ProjectForm projectForm, BindingResult bindingResult) {
		ModelAndView result;
		Project project;
		
		project = projectService.reconstruct(projectForm);
		try {
			projectService.delete(project);
			result = new ModelAndView("redirect:list-my-created.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(project, "project.commit.error");
		}

		return result;
	}
	
	// Ancillary Methods -------------------------------------------------------

	@RequestMapping(value = "/finish", method = RequestMethod.GET)
	public ModelAndView finish(@RequestParam int projectId, String ret) {
		ModelAndView result;

		projectService.finish(projectId);
		result = new ModelAndView("redirect:/project/user/list-details.do?projectId=" + projectId
						+ "&ret=" + ret);

		return result;
	}
	
	@RequestMapping(value = "/follow", method = RequestMethod.GET)
	public ModelAndView follow(@RequestParam int projectId, String ret) {
		ModelAndView result;

		projectService.follow(projectId, true);
		result = new ModelAndView("redirect:/project/user/list-details.do?projectId=" + projectId
						+ "&ret=" + ret);

		return result;
	}

	@RequestMapping(value = "/unfollow", method = RequestMethod.GET)
	public ModelAndView unfollow(@RequestParam int projectId, String ret) {
		ModelAndView result;

		projectService.follow(projectId, false);
		result = new ModelAndView("redirect:/project/user/list-details.do?projectId=" + projectId
						+ "&ret=" + ret);

		return result;
	}
	
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
	
	protected ModelAndView createEditModelAndView(Project project) {
		assert project != null;
		
		return createEditModelAndView(project, null);
	}

	protected ModelAndView createEditModelAndView(Project project, String message) {
		assert project != null;
		ModelAndView res;

		res = new ModelAndView("project/create");
		res.addObject("project", project);
		res.addObject("message", message);
		
		return res;
	}
	
}
