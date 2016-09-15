package functionalRequirement05;

import java.util.Collection;

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

import domain.Group;

import security.LoginService;
import services.GroupService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)

// An actor who is authenticated as an administrator must be able to:
// Remove a group as long as no user belongs to it.
@Transactional
public class RemoveGroupTest {

	// Service Authenticate----------------------------------------

	@Autowired
	private LoginService loginService;

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
	public void checkRemovePositive() {
		authenticate("admin2");
		Collection<Group> groups = groupService.findAll();
		Group group = groupService.findOne(10);
		groupService.delete(group);
		Collection<Group> auxGroups = groupService.findAll();
		Assert.isTrue(groups.size()==auxGroups.size()+1);
	}
	
	// Negative Test
	@Test(expected=IllegalArgumentException.class)
	public void checkRemoveNegative() {
		authenticate("admin2");
		Group group = groupService.findOne(8);
		groupService.delete(group);
	}

}
