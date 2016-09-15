package functionalRequirement36;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
import domain.Patient;

import security.LoginService;
import services.AppointmentService;
import services.PatientService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class CheckApplyAppointment {

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
	@Autowired
	private PatientService patientService;

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
	// Pedir cita
	@Test
	public void checkApplyAppointment() throws ParseException {
		authenticate("patient1");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		List<String> dates = new ArrayList<String>(
				appointmentService.appointmentDates(24));
		Appointment appointment = appointmentService.create(24);
		appointment.setStartMoment(sdf.parse(dates.get(0)));
		appointmentService.save(appointment);

		Assert.isTrue(appointmentService.findAll().size() == 5);
	}

	// Negativo
	// Fecha Incorrecta
	@Test(expected = IllegalArgumentException.class)
	public void checkApplyAppointmentFail() throws ParseException {
		authenticate("patient1");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Appointment appointment = appointmentService.create(24);
		appointment.setStartMoment(sdf.parse("24/05/2014 07:00"));
		appointmentService.save(appointment);
	}

	// Negativo
	// Pedir cita para otro paciente
	@Test(expected = IllegalArgumentException.class)
	public void checkApplyAppointmentOtherPatient() throws ParseException {
		authenticate("patient1");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Appointment appointment = appointmentService.create(24);
		Patient patient = patientService.findByPrincipal();
		appointment.setStartMoment(sdf.parse("24/05/2014 07:00"));
		appointment.setPatient(patient);
		appointmentService.save(appointment);

	}

	// Negativo
	// Pedir cita a una fecha ocupa
	@Test(expected = IllegalArgumentException.class)
	public void checkApplyAppointmentNotFree() throws ParseException {
		authenticate("patient1");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Appointment appointment = appointmentService.create(26);
		Patient patient = patientService.findByPrincipal();
		appointment.setStartMoment(sdf.parse("26/05/2014 08:30"));
		appointment.setPatient(patient);
		appointmentService.save(appointment);

	}
}