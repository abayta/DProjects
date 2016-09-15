package functionalRequirement07;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import domain.Doctor;
import domain.Specialty;

import forms.DoctorForm;

import security.LoginService;
import services.DoctorService;
import services.SpecialtyService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class RegisterDoctorTest {

	//Un actor que está autentificado como administrador debe ser capaz de:
	//Editar la información del perfil de un médico.
	
	// Service Authenticate----------------------------------------

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private DoctorService doctorService;

	@Autowired
	private SpecialtyService specialtyService;
	
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
	public void checkCreateSaveAndLoginDoctorPositive() {
		Specialty specialty = specialtyService.findOne(17);
		DoctorForm doctorForm = new DoctorForm();
		doctorForm.setUsername("doctor99");
		doctorForm.setPassword("doctor99");
		doctorForm.setConfirmPassword("doctor99");
		doctorForm.setName("doctor99");
		doctorForm.setSurname("doctor99");
		doctorForm.setEmailAddress("email@email.com");
		doctorForm.setSpecialty(specialty);
		doctorForm.setAcceptTerms(true);
		doctorForm.setId(0);
		doctorForm.setVersion(0);
		Doctor result = doctorService.reconstruct(doctorForm);
		
		
		doctorService.save(result);
		authenticate("doctor99");
	}
	
	// Negative Test
	// Check that an DataIntegrityViolationException is thrown when trying 
	// to save an "username" called "doctor1", because it already exists in the database.
	@Test(expected=DataIntegrityViolationException.class)
	public void checkCreateAndSaveDoctorNegative() {
		Specialty specialty = specialtyService.findOne(17);
		DoctorForm doctorForm = new DoctorForm();
		doctorForm.setUsername("doctor1");
		doctorForm.setPassword("doctor1");
		doctorForm.setConfirmPassword("doctor1");
		doctorForm.setName("doctor1");
		doctorForm.setSurname("doctor1");
		doctorForm.setEmailAddress("email@email.com");
		doctorForm.setSpecialty(specialty);
		doctorForm.setAcceptTerms(true);
		doctorForm.setId(0);
		doctorForm.setVersion(0);
		Doctor result = doctorService.reconstruct(doctorForm);
		
		authenticate("doctor1");
		doctorService.save(result);
	}
		
	// Negative Test
	// Check that an IllegalArgumentException is thrown when trying 
	// to save a null object "doctor".
	@Test(expected=IllegalArgumentException.class)
	public void checkSaveDoctorNegative2() {
		Doctor result = null;
		doctorService.save(result);
	}

}