package functionalRequirement02;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Administrator;
import domain.Group;
import domain.User;
import forms.AdministratorForm;
import forms.UserForm;

import security.LoginService;
import services.AdministratorService;
import services.GroupService;
import services.UserService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
// An actor who is not authenticated must be able to:
// Register to the system as an administrator or as a user.

public class RegisterActorTest {

	// Service Authenticate----------------------------------------

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupService groupService;
	
	@Before
	public void setUp() {
		PopulateDatabase.main(null);
	}

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
	
	// Positive Test
	@Test
	public void checkCreateSaveAndLoginAdministratorPositive() {
		Administrator result;
		AdministratorForm administratorForm = new AdministratorForm();
		administratorForm.setUsername("admin3");
		administratorForm.setPassword("admin3");
		administratorForm.setConfirmPassword("admin3");
		administratorForm.setAcceptTerms(true);
		administratorForm.setName("Name");
		administratorForm.setSurname("Surname");
		administratorForm.setEmailAddress("Email@email.com");
		administratorForm.setId(99);
		administratorForm.setVersion(1);
		result = administratorService.reconstruct(administratorForm);
		administratorService.save(result);
		authenticate("admin3");
	}
	
	// Negative Test
	// Check that an DataIntegrityViolationException is thrown when trying 
	// to save an "username" called "admin1", because it already exists in the database
	@Test(expected=DataIntegrityViolationException.class)
	public void checCreateAndSaveAdministratorNegative() {
		Administrator result;
		AdministratorForm administratorForm = new AdministratorForm();
		administratorForm.setUsername("admin1");
		administratorForm.setPassword("admin1");
		administratorForm.setConfirmPassword("admin1");
		administratorForm.setAcceptTerms(true);
		administratorForm.setName("Name");
		administratorForm.setSurname("Surname");
		administratorForm.setEmailAddress("Email@email.com");
		administratorForm.setId(99);
		administratorForm.setVersion(1);
		result = administratorService.reconstruct(administratorForm);
		administratorService.save(result);
	}
	
	// Negative Test
	// Check that an IllegalArgumentException is thrown when trying 
	// to save a null object "administrator"
	@Test(expected=IllegalArgumentException.class)
	public void checkSaveAdministratorNegative2() {
		Administrator result = null;
		administratorService.save(result);
	}
	
	// Positive Test
	@Test
	public void checkCreateSaveAndLoginUserPositive() {
		User result;
		Group group = groupService.findOne(8);
		UserForm userForm = new UserForm();
		userForm.setUsername("user4");
		userForm.setPassword("user4");
		userForm.setConfirmPassword("user4");
		userForm.setAcceptTerms(true);
		userForm.setGroup(group);
		userForm.setName("Name");
		userForm.setSurname("Surname");
		userForm.setEmailAddress("Email@email.com");
		userForm.setId(99);
		userForm.setVersion(1);
		result = userService.reconstruct(userForm);
		userService.save(result);
		authenticate("user4");
	}
		
	// Negative Test
	// Check that an DataIntegrityViolationException is thrown when trying 
	// to save an "username" called "user1", because it already exists in the database
	@Test(expected=DataIntegrityViolationException.class)
	public void checCreateAndSaveUserNegative() {
		User result;
		Group group = groupService.findOne(8);
		UserForm userForm = new UserForm();
		userForm.setUsername("user1");
		userForm.setPassword("user1");
		userForm.setConfirmPassword("user1");
		userForm.setAcceptTerms(true);
		userForm.setGroup(group);
		userForm.setName("Name");
		userForm.setSurname("Surname");
		userForm.setEmailAddress("Email@email.com");
		userForm.setId(99);
		userForm.setVersion(1);
		result = userService.reconstruct(userForm);
		userService.save(result);
	}

	// Negative Test
	// Check that an IllegalArgumentException is thrown when trying 
	// to save a null object "user"
	@Test(expected=IllegalArgumentException.class)
	public void checkSaveUserNegative2() {
		User result = null;
		userService.save(result);
	}	
		
}