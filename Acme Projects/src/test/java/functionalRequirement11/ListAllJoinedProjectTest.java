package functionalRequirement11;

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
public class ListAllJoinedProjectTest {

	// An actor who is authenticated as a user must be able to:
	// List the projects in which he or she participates.

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
	public void checkFindAllJoinedProjectPositive() {
		authenticate("user1");
		Collection<Project> projects;
		projects = projectService.findAllMyJoined();
		Assert.isTrue(projects.size() == 2);
	}

	// Negative Test
	@Test(expected = IllegalArgumentException.class)
	public void checkFindAllJoinedProjectNegative1() {
		authenticate("admin1");
		projectService.findAllMyJoined();
	}

	// Negative Test
	@Test(expected = IllegalArgumentException.class)
	public void checkFindAllJoinedProjectNegative2() {
		authenticate("user1");
		Collection<Project> projects;
		projects = projectService.findAllMyJoined();
		Assert.isTrue(projects.size() == 1);
	}

	// Negative Test
	@Test(expected = IllegalArgumentException.class)
	public void checkFindAllJoinedProjectNegative3() {
		authenticate("user1");
		Collection<Project> projects;
		projects = projectService.findAllMyJoined();
		Assert.isTrue(projects == null);
	}

}