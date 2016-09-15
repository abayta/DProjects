package functionalRequirement32;

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

import domain.Clinic;

import security.LoginService;
import services.ClinicService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ListProfileClinicPatientTest {

	//Un actor que está autentificado como médico debe ser capaz de:
	//Ver el perfil de las clínicas

	// Service Authenticate----------------------------------------

	@Autowired
	private ClinicService clinicService;

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
		authenticate("patient1");
		Clinic clinic = clinicService.findOne(23);
		Assert.isTrue(clinic.getAddress().equals("Av. la Palmera"));
		Assert.isTrue(clinic.getEmail().equals("info@VirgenRocio.com"));
		Assert.isTrue(clinic.getName().equals("Vírgen de Rocío"));
		Assert.isTrue(clinic.getPhone().equals("954478523"));
	}


}