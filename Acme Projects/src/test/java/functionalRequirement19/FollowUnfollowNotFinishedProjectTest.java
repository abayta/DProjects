package functionalRequirement19;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.User;

import security.LoginService;
import services.ProjectService;
import services.UserService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FollowUnfollowNotFinishedProjectTest {

	// An actor who is authenticated as a user must be able to:
	// Show a stream with the status updates of the projects he or she follows.
	// For each status update it should show its moment, the amount of work
	// done, its description and the project to which the status update belong.

	// Service Authenticate----------------------------------------

	@Autowired
	private LoginService loginService;

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;

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

	// Follow Test
	
	// Positive Test
	@Test
	public void checkFollowPositive() {
		authenticate("user1");
		User user = userService.findByPrincipal();
		projectService.follow(14, true);
		Assert.isTrue(user.getFollowedProjects().contains(projectService.findOne(14)));
	}

	// Negative Test
	@Test(expected = IllegalArgumentException.class)
	public void checkFollowNegative1() {
		authenticate("admin1");
		User user = userService.findByPrincipal();
		projectService.follow(14, true);
		Assert.isTrue(user.getFollowedProjects().contains(projectService.findOne(14)));
	}

	// Negative Test
	@Test(expected = IllegalArgumentException.class)
	public void checkFollowNegative2() {
		authenticate("user2");
		projectService.follow(14, true);
	}
	
	// Unfollow Test

	// Positive Test
	@Test
	public void checkUnfollowPositive() {
		authenticate("user2");
		User user = userService.findByPrincipal();
		projectService.follow(14, false);
		Assert.isTrue(!user.getFollowedProjects().contains(projectService.findOne(14)));
	}

	// Negative Test
	@Test(expected = IllegalArgumentException.class)
	public void checkUnfollowNegative1() {
		authenticate("admin1");
		User user = userService.findByPrincipal();
		projectService.follow(14, false);
		Assert.isTrue(!user.getFollowedProjects().contains(projectService.findOne(14)));
	}

	// Negative Test
	@Test(expected = IllegalArgumentException.class)
	public void checkUnfollowNegative2() {
		authenticate("user1");
		projectService.follow(14, false);
	}
	
}