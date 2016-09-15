package functionalRequirement10;


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
import domain.Registration;
import domain.Requirement;
import domain.User;
import security.LoginService;
import services.RegistrationService;
import services.RequirementService;
import services.UserService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class JoinInProject {
	
	// Service Authenticate----------------------------------------

	@Autowired
	private LoginService loginService;
	@Autowired
	private UserService userService;
	@Autowired
	private RegistrationService registrationService;
	@Autowired
	private RequirementService requirementService;

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
	public void join() {
		authenticate("user3");
		User user = userService.findByPrincipal();
		Requirement requirement = requirementService.findOneByGroupAndProject(user.getGroup().getId(), 18);
		registrationService.create(18);
		Registration registration = registrationService.findByUserAndProject(user.getId(), 18);
		Requirement requirementNew = requirementService.findOneByGroupAndProject(user.getGroup().getId(), 18);
		Assert.isTrue(requirement.getFreePlaces()==requirementNew.getFreePlaces()+1);
		Assert.notNull(registration);
	}
		

	@Test
	public void unregister() {
		authenticate("user2");
		User user = userService.findByPrincipal();
		registrationService.delete(16);
		Registration registration = registrationService.findByUserAndProject(user.getId(), 16);
		Assert.isNull(registration);
	}
		
	// Negative Test
	@Test(expected = IllegalArgumentException.class)
	public void join2() {
		authenticate("user2");
		registrationService.create(18);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void joinFinished() {
		authenticate("user2");
		registrationService.create(18);
	}
}
