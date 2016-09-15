package functionalRequirement12;

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
import services.RegistrationService;
import services.StatusUpdateService;
import services.UserService;
import utilities.PopulateDatabase;
import domain.Registration;
import domain.StatusUpdate;
import domain.User;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class createStatusUpdate {
	

	// Service Authenticate----------------------------------------

	@Autowired
	private LoginService loginService;
	@Autowired
	private UserService userService;
	@Autowired
	private RegistrationService registrationService;
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
	public void createStatus() {
		authenticate("user1");
		User user = userService.findByPrincipal();
		Registration registration = registrationService.findByUserAndProject(user.getId(), 14);
		StatusUpdate statusUpdate = statusUpdateService.create(14);
		statusUpdate.setRegistration(registration);
		statusUpdate.setWorkAmount(20.2);
		statusUpdate.setDescription("TEST");
		statusUpdateService.save(statusUpdate);
		registration = registrationService.findByUserAndProject(user.getId(), 14);
		Assert.isTrue(registration.getStatusUpdates().size()==3);
	}
		
	
	// Negative Test
	@Test(expected = IllegalArgumentException.class)
	public void createStatusNoREgistration() {
		authenticate("user2");
		statusUpdateService.create(14);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createStatusFinished() {
		authenticate("user2");
		User user = userService.findByPrincipal();
		Registration registration = registrationService.findByUserAndProject(user.getId(), 22);
		StatusUpdate statusUpdate = statusUpdateService.create(22);
		statusUpdate.setRegistration(registration);
		statusUpdate.setWorkAmount(20.2);
		statusUpdate.setDescription("TEST");
		statusUpdateService.save(statusUpdate);
		registration = registrationService.findByUserAndProject(user.getId(), 14);
		Assert.isTrue(registration.getStatusUpdates().size()==3);
	}

}
