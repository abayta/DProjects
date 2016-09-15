package functionalRequirement03;

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

import domain.Group;

import security.LoginService;
import services.GroupService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ListGroupTest {

	// An actor who is authenticated as an administrator must be able to:
	// List the groups managed by Acme Projects, Inc.

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
	public void checkFindOnePositive() {
		// id must be 8, 9 or 10
		Group group = groupService.findOne(8);
		Assert.notNull(group);
	}

	// Negative Test
	@Test(expected = IllegalArgumentException.class)
	public void checkFindOneNegative1() {
		Group group = groupService.findOne(7);
		group.setDescription(null);
	}

	// Negative Test
	@Test(expected = IllegalArgumentException.class)
	public void checkFindOneNegative2() {
		Group group = groupService.findOne(11);
		group.setDescription(null);
	}

	// Positive Test
	@Test
	public void checkFindAllToAdministratorPositive() {
		Collection<Group> groups;
		groups = groupService.findAllToAdministrator();
		Assert.isTrue(groups.size() == 3);
	}

}