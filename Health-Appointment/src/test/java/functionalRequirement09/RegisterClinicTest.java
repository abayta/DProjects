package functionalRequirement09;

import java.util.Collection;
import java.util.HashSet;

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

import domain.Clinic;
import domain.Schedule;
import domain.Specialty;

import security.LoginService;
import services.ClinicService;

import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)

//Un actor que está autentificado como administrador debe ser capaz de:
//Registrar las clínicas que deben ser gestionadas por el sistema.

public class RegisterClinicTest {

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
	public void checkRegisterClinicPositive() {
		authenticate("admin");
		Collection<Schedule> schedules = new HashSet<Schedule>();
		Clinic clinic = clinicService.create();
		clinic.setAddress("Address");
		clinic.setEmail("email@email.com");
		clinic.setName("Name");
		clinic.setPhone("956321225");
		clinic.setId(0);
		clinic.setVersion(0);
		clinic.setLogo(null);
		clinic.setSchedules(schedules);
		clinicService.save(clinic);
		
		Assert.isTrue(clinicService.findOne(32768).getName().equals("Name"));
	}
	
	// Negative Test
	@Test(expected = TransactionSystemException.class)
	public void checkRegisterClinicNegative() {
		authenticate("admin");
		Clinic clinic = clinicService.create();
		clinic.setName("NameTest");
		clinicService.save(clinic);
	}
		
}