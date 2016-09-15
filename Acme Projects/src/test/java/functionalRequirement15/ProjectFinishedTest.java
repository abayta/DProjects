package functionalRequirement15;

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

import domain.Project.StateEnum;

import security.LoginService;
import services.ProjectService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProjectFinishedTest {
	
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
	public void finishProject() {
		authenticate("user1");
		projectService.finish(14);
		Assert.isTrue(projectService.findOne(14).getState().equals(StateEnum.Finished));
	}
	
	// Negative Test
		@Test(expected = IllegalArgumentException.class)
		public void finishProjectOtherUser() {
			authenticate("user2");
			projectService.finish(14);
			Assert.isTrue(projectService.findOne(14).getState().equals(StateEnum.Finished));
		}
		
		@Test(expected = IllegalArgumentException.class)
		public void finishProjectNotStarted() {
			authenticate("user2");
			projectService.finish(16);
		}

}
