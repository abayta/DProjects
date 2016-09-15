package functionalRequirement13;

import java.util.Date;


import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import services.GroupService;
import services.ProjectService;
import services.RequirementService;

import utilities.PopulateDatabase;
import domain.Group;
import domain.Project;
import domain.Requirement;
import forms.ProjectForm;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProjectTest {

	// Service Authenticate----------------------------------------

	@Autowired
	private LoginService loginService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private RequirementService requirementService;
	@Autowired
	private GroupService groupService;
	// Authenticate-----------------------------------------------
	public void authenticate(String username) {
		UserDetails userDetails;
		TestingAuthenticationToken authenticationToken;
		SecurityContext context;

		userDetails = loginService.loadUserByUsername(username);
		authenticationToken = new TestingAuthenticationToken(userDetails, null);
		context = SecurityContextHolder.getContext();
		context.setAuthentication(authenticationToken);
	}

	@Before
	public void setUp() {
		PopulateDatabase.main(null);
	}

	// Positive Test
	@Test
	public void createProject() {
		authenticate("user1");
		Project project = projectService.create();
		ProjectForm projectForm = new ProjectForm();
				
		projectForm.setTitle("TestProject");
		projectForm.setDescription("TestProject");
		projectForm.setEndMoment(new Date(System.currentTimeMillis()+259200000));
		projectForm.setStartMoment(new Date(System.currentTimeMillis()+172800000));
		projectForm.setGoal("test");
		project = projectService.reconstruct(projectForm);
		projectService.save(project);
		
		Assert.isTrue(projectService.findAllMyCreated().size()==2);
	}
	
	@Test
	public void addRequirementProject() {
		authenticate("user2");
		Requirement requirement = requirementService.create(20);
		
		Group group = groupService.findOne(9);
		requirement.setGroup(group);
		requirement.setPeopleNumber(5);
		requirementService.save(requirement);
		
		Assert.notNull(requirementService.findOneByGroupAndProject(9, 20));
	}
	
	
	
	@Test
	public void editProject() {
		authenticate("user2");
		Project project = projectService.findOne(20);
		ProjectForm projectForm = contruct(project);
				
		projectForm.setTitle("TestProjectEdited");
		project = projectService.reconstruct(projectForm);
		projectService.save(project);
		
		Assert.isTrue(projectService.findOne(20).getTitle().equals("TestProjectEdited"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void deleteProject() {
		authenticate("user2");
		Project project = projectService.findOne(20);
		projectService.delete(project);
		
		projectService.findOne(20);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void deleteProjectOtherUser() {
		authenticate("user1");
		Project project = projectService.findOne(20);
		projectService.delete(project);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void editProjectOtherUser() {
		authenticate("user1");
		Project project = projectService.findOne(16);
		ProjectForm projectForm = contruct(project);
				
		projectForm.setTitle("TestProjectEdited");
		project = projectService.reconstruct(projectForm);
		projectService.save(project);
		
		Assert.isTrue(projectService.findOne(16).getTitle().equals("TestProjectEdited"));
	}
	
	private ProjectForm contruct(Project project){
		ProjectForm res = new ProjectForm();
		res.setDescription(project.getDescription());
		res.setEndMoment(project.getEndMoment());
		res.setGoal(project.getGoal());
		res.setId(project.getId());
		res.setStartMoment(project.getStartMoment());
		res.setTitle(project.getTitle());
		res.setVersion(project.getId());
		return res;
	}
	
	

	// Negative Test
	/*@Test(expected = IllegalArgumentException.class)
	public void createStatusNoREgistration() {
		authenticate("user2");
		statusUpdateService.create(14);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createStatusFinished() {
		authenticate("user2");
		User user = userService.findByPrincipal();
		Registration registration = registrationService.findByUserAndProject(
				user.getId(), 22);
		StatusUpdate statusUpdate = statusUpdateService.create(22);
		statusUpdate.setRegistration(registration);
		statusUpdate.setWorkAmount(20.2);
		statusUpdate.setDescription("TEST");
		statusUpdateService.save(statusUpdate);
		registration = registrationService.findByUserAndProject(user.getId(),
				14);
		Assert.isTrue(registration.getStatusUpdates().size() == 3);
	}*/

}
