package functionalRequirement06;


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
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.Assert;

import domain.Specialty;

import security.LoginService;
import services.SpecialtyService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class AddSpecialtyTest {

	//Un actor que está autentificado como administrador debe ser capaz de:
	//Ver las especialidades y añadir una nueva especialidad al sistema.
	
	// Service Authenticate----------------------------------------

	@Autowired
	private SpecialtyService specialtyService;
	
	@Autowired
	private LoginService loginService;

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
	public void checkAddSpecialtyPositive() {
		authenticate("admin");
		Specialty specialty = specialtyService.create();
		specialty.setTitle("Test");
		specialty.setDescription("Test");
		specialty.setId(99);
		specialty.setVersion(99);
		specialtyService.save(specialty);
		Assert.isTrue(specialtyService.findOne(32768).getTitle().equals("Test"));
	}
	
	//Negative Test
	@Test(expected = TransactionSystemException.class)
	public void checkAddSpecialtyNegative() {
		authenticate("admin");
		Specialty specialty = specialtyService.create();
		specialty.setTitle("Test");
		specialtyService.save(specialty);
	}
		

}