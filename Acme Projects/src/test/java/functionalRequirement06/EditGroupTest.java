package functionalRequirement06;

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

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
// An actor who is authenticated as an administrator must be able to:
// Change the name or the description of a group as long as no user belongs to
// it.
@Transactional
public class EditGroupTest {

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
	public void checkSavePositive() {
		authenticate("admin1");
		Group group = groupService.findOne(10);
		group.setName("NameTest");
		group.setDescription("DescriptionTest");
		groupService.save(group);
		Assert.isTrue(group.getDescription()=="DescriptionTest" && 
				group.getName()=="NameTest");
		
	}

	// Negative Test
	@Test(expected = IllegalArgumentException.class)
	public void checkSaveNegative() {
		authenticate("admin2");
		Group group = groupService.findOne(8);
		groupService.save(group);
	}

	// Negative Test
	@Test(expected=IllegalArgumentException.class)
	public void checkSaveNegative2() {
		authenticate("user1");
		groupService.save(groupService.findOne(10));
	}

}
