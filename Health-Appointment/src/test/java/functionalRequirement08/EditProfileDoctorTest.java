package functionalRequirement08;

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

import domain.Doctor;


import security.LoginService;
import services.DoctorService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EditProfileDoctorTest {

	//Un actor que no está autentificado debe ser capaz de:
	//Registrarse en el sistema como paciente.
	
	// Service Authenticate----------------------------------------

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private DoctorService doctorService;

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
		public void checkEditDoctorPositive() {
			authenticate("admin");
			Doctor doctor = doctorService.findOne(20);
			doctor.setEmailAddress("test@test.com");
			doctor.setImage(null);
			doctorService.save(doctor);
			Assert.isTrue(doctorService.findOne(20).getEmailAddress().equals("test@test.com"));
		}

		// Negative Test
		@Test(expected=IllegalArgumentException.class)
		public void checkEditDoctorNegative2() {
			authenticate("admin");
			doctorService.save(doctorService.findOne(22));
		}

}