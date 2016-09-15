package functionalRequirement24;

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

import domain.Appointment;
import domain.Doctor;

import security.LoginService;
import services.AppointmentService;
import services.DoctorService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ListAllNotActiveAppointmentsDoctorTest {

	//Un actor que está autentificado como médico debe ser capaz de:
	//Ver las citas que ya han transcurrido, ordenadas descendentemente por fecha.
	
	// Service Authenticate----------------------------------------

	@Autowired
	private AppointmentService appointmentService;
	
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
	public void checkFindNotAllAppointmentsDoctorPositive() {
		authenticate("doctor2");
		Doctor doctor = doctorService.findOne(21);
		Collection<Appointment> appointments = appointmentService.findAllNotActiveByDoctor(doctor.getId());
		Assert.isTrue(appointments.size() == 1);
	}

}