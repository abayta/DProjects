package functionalRequirement06;

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

import domain.Specialty;

import security.LoginService;
import services.SpecialtyService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ListAllSpecialtiesTest {

	//Un actor que est� autentificado como administrador debe ser capaz de:
	//Ver las especialidades y a�adir una nueva especialidad al sistema.
	
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
	public void checkFindAllSpecialtiesPositive() {
		authenticate("admin");
		Collection<Specialty> specialties = specialtyService.findAll();
		Assert.isTrue(specialties.size() == 3);
	}

}