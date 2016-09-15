package functionalRequirement03;

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

import domain.Doctor;

import security.LoginService;
import services.DoctorService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ListProfileDoctorAnonymousTest {

	//Un actor que no está autentificado debe ser capaz de:
	//Ver el perfil de los médicos

	// Service Authenticate----------------------------------------

	@Autowired
	private DoctorService doctorService;

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
	public void checkProfileClinictPositive() {
		Doctor doctor = doctorService.findOne(20);
		Assert.isTrue(doctor.getName().equals("Juan"));
		Assert.isTrue(doctor.getSurname().equals("Garrido"));
		Assert.isTrue(doctor.getSpecialty().getId() == 17);
	}

}