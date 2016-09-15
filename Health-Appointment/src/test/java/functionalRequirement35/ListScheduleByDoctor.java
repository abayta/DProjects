package functionalRequirement35;

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

import domain.Clinic;
import domain.Schedule;
import domain.Specialty;

import security.LoginService;
import services.ClinicService;
import services.ScheduleService;
import services.SpecialtyService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ListScheduleByDoctor {

	//Un actor que está autentificado como paciente debe ser capaz de:
	//Ver las especialidades disponibles, dada una clínica.
	
	// Service Authenticate----------------------------------------

	@Autowired
	private ScheduleService scheduleService;
	
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
	public void checkFindAllSpecialtiesByClinicPositive() {
		authenticate("patient1");
		Object[] visits = scheduleService.visiteDays(21);
		Collection<Schedule> schedules = (Collection<Schedule>) visits[1];
		Assert.isTrue(schedules.size()==2);
	}

}