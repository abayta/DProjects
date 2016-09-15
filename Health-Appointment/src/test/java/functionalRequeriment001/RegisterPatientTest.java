package functionalRequeriment001;

import java.util.Date;

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

import domain.ClinicalHistory;

import domain.HealthInsuranceCard;
import domain.Patient;

import forms.PatientForm;

import security.LoginService;
import services.PatientService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class RegisterPatientTest {

	//Un actor que no está autentificado debe ser capaz de:
	//Registrarse en el sistema como paciente.
	
	// Service Authenticate----------------------------------------

	@Autowired
	private LoginService loginService;
	
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

	// Positive Test
	@Test
	public void checkCreateSaveAndLoginPatientPositive() {
		long moment = System.currentTimeMillis() - 1000;
		HealthInsuranceCard healthInsuranceCard = new HealthInsuranceCard();
		healthInsuranceCard.setBirthday(new Date(moment));
		healthInsuranceCard.setExpirationMonth(12);
		healthInsuranceCard.setExpirationYear(2020);
		healthInsuranceCard.setIdNumber("ZZZZZZ");
		healthInsuranceCard.setNumber("4541216545");
		PatientForm patientForm = new PatientForm();
		patientForm.setUsername("patient99");
		patientForm.setPassword("patient99");
		patientForm.setConfirmPassword("patient99");
		patientForm.setName("patient99");
		patientForm.setSurname("patient99");
		patientForm.setEmailAddress("email@email.com");
		patientForm.setHealthInsuranceCard(healthInsuranceCard);
		patientForm.setAcceptTerms(true);
		patientForm.setId(99);
		patientForm.setVersion(1);
		Patient result = patientService.reconstruct(patientForm);
		ClinicalHistory clinicalHistory = new ClinicalHistory();
		clinicalHistory.setPatient(result);
		result.setClinicalHistory(clinicalHistory);
		
		patientService.save(result);
		authenticate("patient99");
	}
	
	// Negative Test
	// Check that an DataIntegrityViolationException is thrown when trying 
	// to save an "username" called "patient1", because it already exists in the database.
	@Test(expected=DataIntegrityViolationException.class)
	public void checkCreateAndSavePatientNegative() {
		long moment = System.currentTimeMillis() - 1000;
		HealthInsuranceCard healthInsuranceCard = new HealthInsuranceCard();
		healthInsuranceCard.setBirthday(new Date(moment));
		healthInsuranceCard.setExpirationMonth(12);
		healthInsuranceCard.setExpirationYear(2020);
		healthInsuranceCard.setIdNumber("ZZZZZZ");
		healthInsuranceCard.setNumber("4541216545");
		PatientForm patientForm = new PatientForm();
		patientForm.setUsername("patient1");
		patientForm.setPassword("patient1");
		patientForm.setConfirmPassword("patient1");
		patientForm.setName("patient1");
		patientForm.setSurname("patient1");
		patientForm.setEmailAddress("email@email.com");
		patientForm.setHealthInsuranceCard(healthInsuranceCard);
		patientForm.setAcceptTerms(true);
		patientForm.setId(99);
		patientForm.setVersion(1);
		Patient result = patientService.reconstruct(patientForm);
		ClinicalHistory clinicalHistory = new ClinicalHistory();
		clinicalHistory.setPatient(result);
		result.setClinicalHistory(clinicalHistory);
		
		patientService.save(result);
	}
		
	// Negative Test
	// Check that an IllegalArgumentException is thrown when trying 
	// to save a null object "patient".
	@Test(expected=IllegalArgumentException.class)
	public void checkSavePatientNegative2() {
		Patient result = null;
		patientService.save(result);
	}

}
