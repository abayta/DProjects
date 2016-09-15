package functionalRequirement09;

import java.util.Collection;

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

import domain.Project;

import security.LoginService;
import services.ProjectService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ListAllProjectWithVacancyByGroupTest {

	// An actor who is authenticated as a user must be able to:
	// List the projects in which he or she can participate. These projects are
	// those that have at least one vacancy for the group to which the user
	// belongs.

	// Service Authenticate----------------------------------------

	@Autowired
	private LoginService loginService;

	@Autowired
	private ProjectService projectService;

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
	public void checkFindAllVacancyByGroupPositive() {
		authenticate("user1");
		Collection<Project> projects;
		projects = projectService.findAllVacancyByGroup();
		Assert.isTrue(projects.size() == 0);
	}

	// Negative Test
	@Test(expected = IllegalArgumentException.class)
	public void checkFindAllVacancyByGroupNegative1() {
		authenticate("admin1");
		projectService.findAllVacancyByGroup();
	}

	// Negative Test
	@Test(expected = IllegalArgumentException.class)
	public void checkFindAllVacancyByGroupNegative2() {
		authenticate("user1");
		Collection<Project> projects;
		projects = projectService.findAllVacancyByGroup();
		Assert.isTrue(projects.size() == 1);
	}

	// Negative Test
	@Test(expected = IllegalArgumentException.class)
	public void checkFindAllVacancyByGroupNegative3() {
		authenticate("user1");
		Collection<Project> projects;
		projects = projectService.findAllVacancyByGroup();
		Assert.isTrue(projects == null);
	}

}