package functionalRequirement20;

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

import domain.StatusUpdate;

import security.LoginService;
import services.StatusUpdateService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ListAllStatusUpdatesOfFollowedProjectsTest {

	// An actor who is authenticated as a user must be able to:
	// Show a stream with the status updates of the projects he or she follows.
	// For each status update it should show its moment, the amount of work
	// done, its description and the project to which the status update belong.

	// Service Authenticate----------------------------------------

	@Autowired
	private LoginService loginService;

	@Autowired
	private StatusUpdateService statusUpdateService;

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
	public void checkFindAllOfAllFollowedProjectPositive() {
		authenticate("user2");
		Collection<StatusUpdate> statusUpdates;
		statusUpdates = statusUpdateService.findAllOfAllFollowedProject();
		Assert.isTrue(statusUpdates.size() == 2);
	}

	// Negative Test
	@Test(expected = IllegalArgumentException.class)
	public void checkFindAllOfAllFollowedProjectNegative1() {
		authenticate("admin1");
		statusUpdateService.findAllOfAllFollowedProject();
	}

	// Negative Test
	@Test(expected = IllegalArgumentException.class)
	public void checkFindAllOfAllFollowedProjectNegative2() {
		authenticate("user2");
		Collection<StatusUpdate> statusUpdates;
		statusUpdates = statusUpdateService.findAllOfAllFollowedProject();
		Assert.isTrue(statusUpdates.size() == 1);
	}

	// Negative Test
	@Test(expected = IllegalArgumentException.class)
	public void checkFindAllOfAllFollowedProjectNegative3() {
		authenticate("user2");
		Collection<StatusUpdate> statusUpdates;
		statusUpdates = statusUpdateService.findAllOfAllFollowedProject();
		Assert.isTrue(statusUpdates == null);
	}

}