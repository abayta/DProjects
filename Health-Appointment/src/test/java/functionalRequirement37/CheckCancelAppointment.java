package functionalRequirement37;

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

import domain.Appointment;

import security.LoginService;
import services.AppointmentService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class CheckCancelAppointment {

	// An actor who is not authenticated must be able to:
	// List the items that are available at Acme Bay, Inc., that is, the items
	// for which there's at least one unit available. For each item, the system
	// must show its reference, name, the number of units available, state,
	// price, and its seller.

	// Service Authenticate----------------------------------------

	@Autowired
	private LoginService loginService;
	@Autowired
	private AppointmentService appointmentService;

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

	// Positivo
	// Cancela una cita que todavia no ha tenido lugar
	@Test
	public void checkCancelAppointment() {
		authenticate("patient1");
		Appointment appointment = appointmentService.findOne(32);
		appointmentService.delete(appointment);

		Assert.isTrue(appointmentService.findAll().size() == 3);
	}

	// Positivo
	// Cancela una cita que todavia no ha tenido lugar
	@Test(expected=IllegalArgumentException.class)
	public void checkCancelAppointmentFail() {
		authenticate("patient1");
		Appointment appointment = appointmentService.findOne(27);
		appointmentService.delete(appointment);

		Assert.isTrue(appointmentService.findAll().size() == 3);
	}

}