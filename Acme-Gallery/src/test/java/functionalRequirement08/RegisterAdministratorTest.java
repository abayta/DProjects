package functionalRequirement08;

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


import forms.AdministratorForm;


import security.LoginService;
import services.AdministratorService;

import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
// An actor who is authenticated as an administrator must be able to:
// Register a new administrator in the system.

public class RegisterAdministratorTest {

	// Service Authenticate----------------------------------------

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private AdministratorService administratorService;
	
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
	
}

