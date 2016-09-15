package functionalRequirement22;

import java.util.Calendar;
import java.util.Date;

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
import domain.Doctor;
import domain.Schedule;
import domain.Schedule.DayOfWeek;

import security.LoginService;
import services.ClinicService;
import services.DoctorService;
import services.ScheduleService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class AddScheduleTest {

	// Un actor que está autentificado como paciente debe ser capaz de:
	// Ver el perfil de las clínicas

	// Service Authenticate----------------------------------------

	@Autowired
	private ClinicService clinicService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private ScheduleService scheduleService;

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
	// Añadir Schedule
	@Test
	public void checkAddSchedule() {
		authenticate("doctor1");
		Clinic clinic = clinicService.findOne(22);
		Schedule schedule = scheduleService.create();
		schedule.setDay(DayOfWeek.Monday);
		schedule.setClinic(clinic);
		Calendar start = Calendar.getInstance();
		start.set(Calendar.HOUR_OF_DAY, 15);
		start.set(Calendar.MINUTE, 00);
		start.set(Calendar.SECOND, 00);
		Calendar end = (Calendar) start.clone();
		end.add(Calendar.MINUTE, 15);
		schedule.setStartTime(start.getTime());
		schedule.setEndTime(end.getTime());

		scheduleService.save(schedule);

		Assert.isTrue(scheduleService.findAll().size() == 4);

	}

	// Negative
	// Añadir Schedule fecha de comienzo y de fin iguales
	@Test(expected = IllegalArgumentException.class)
	public void checkAddScheduleFail() {
		authenticate("doctor1");
		Clinic clinic = clinicService.findOne(22);
		Schedule schedule = scheduleService.create();
		schedule.setDay(DayOfWeek.Monday);
		schedule.setClinic(clinic);
		Date now = new Date();
		schedule.setStartTime(now);
		schedule.setEndTime(now);

		scheduleService.save(schedule);
	}

	// Negative
	// Añadir Schedule fecha de comienzo y de fin incorrectas
	@Test(expected = IllegalArgumentException.class)
	public void checkAddScheduleFailDate() {
		authenticate("doctor1");
		Clinic clinic = clinicService.findOne(22);
		Schedule schedule = scheduleService.create();
		schedule.setDay(DayOfWeek.Monday);
		schedule.setClinic(clinic);
		schedule.setStartTime(new Date());
		schedule.setEndTime(new Date(System.currentTimeMillis()+10000));

		scheduleService.save(schedule);
	}

	// Negative
	// Añadir Schedule fecha de comienzo y de fin iguales
	@Test(expected = IllegalArgumentException.class)
	public void checkAddScheduleToOtherDoctor() {
		authenticate("doctor1");
		Clinic clinic = clinicService.findOne(22);
		Doctor doctor = doctorService.findOne(21);
		Schedule schedule = scheduleService.create();
		schedule.setDay(DayOfWeek.Monday);
		schedule.setDoctor(doctor);
		schedule.setClinic(clinic);
		Date now = new Date();
		schedule.setStartTime(now);
		schedule.setEndTime(now);

		scheduleService.save(schedule);

	}

}