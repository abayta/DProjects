package services;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.ClinicalHistory;
import domain.MedicalReport;
import domain.Patient;
import forms.PatientForm;

import repositories.PatientRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class PatientService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private ClinicalHistoryService clinicalHistoryService;
	
	// Constructor ------------------------------------------------------------

	public PatientService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
 
	public Patient create() {
		Patient result = new Patient();
		Assert.notNull(result);

		Authority authority = new Authority();
		authority.setAuthority(Authority.PATIENT);
		UserAccount userAccount = new UserAccount();
		userAccount.addAuthority(authority);

		result.setUserAccount(userAccount);

		return result;
	}
	
	public void save(Patient patient) {
		
		long moment = System.currentTimeMillis() - 1000;
		Assert.notNull(patient);
		if (patient.getId() == 0) {
			String password = patient.getUserAccount().getPassword();
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			password = encoder.encodePassword(password, null);
			patient.getUserAccount().setPassword(password);
			ClinicalHistory clinicalHistory = clinicalHistoryService.create();
			clinicalHistory.setCreationDate(new Date(moment));
			patient.setClinicalHistory(clinicalHistory);
			clinicalHistory.setPatient(patient);
			
			byte[] image = patient.getImage();
			
			if (patient.getId() != 0 && (image.equals(null) || patient.getImage().length==0))
				patient.setImage(findOne(patient.getId()).getImage());
			
			patientRepository.save(patient);
		
		}
	
	}
	
	// Ver los pacientes que están registradas en el sistema.
	public List<Patient> findAll() {
		return patientRepository.findAll();
	}
	
	public Patient findByPrincipal() {
		Patient result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByPatientAccount(userAccount);
		Assert.notNull(result);
		return result;
	}

	public Patient findByPatientAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		return patientRepository.findOneByUserAccount(userAccount.getId());
	}
	
	public Patient findOneByMedicalReport(MedicalReport medicalReport) {
		Assert.notNull(medicalReport);
		return patientRepository.findOneByMedicalReport(medicalReport.getId());
	}

	// Ancillary methods -------------------------------------------------------

	public PatientForm construct() {
		PatientForm patientForm = new PatientForm();
		Patient patient = create();

		patientForm.setId(patient.getId());
		patientForm.setVersion(patient.getVersion());
		patientForm.setName(patient.getName());
		patientForm.setSurname(patient.getSurname());
		patientForm.setImage(patient.getImage());
		patientForm.setEmailAddress(patient.getEmailAddress());
		patientForm.setHealthInsuranceCard(patient.getHealthInsuranceCard());
		patientForm.setUsername(patient.getUserAccount().getUsername());
		patientForm.setPassword(patient.getUserAccount().getPassword());
	

		return patientForm;
	}

	public Patient reconstruct(PatientForm patientForm) {
		Patient patient = create();
		Calendar date = new GregorianCalendar();
		
		patient.setName(patientForm.getName());
		patient.setSurname(patientForm.getName());
		patient.setEmailAddress(patientForm.getEmailAddress());
		patient.setHealthInsuranceCard(patientForm.getHealthInsuranceCard());
		patient.getUserAccount().setUsername(patientForm.getUsername());
		patient.getUserAccount().setPassword(patientForm.getPassword());
		patient.setImage(patientForm.getImage());

		Assert.isTrue(patientForm.getHealthInsuranceCard().getExpirationYear()>=date.get(Calendar.YEAR));
		if (patientForm.getHealthInsuranceCard().getExpirationYear()==date.get(Calendar.YEAR))
			Assert.isTrue(patientForm.getHealthInsuranceCard().getExpirationMonth()>=date.get(Calendar.MONTH));
		Assert.isTrue(patientForm.getAcceptTerms());
		Assert.isTrue(patient.getUserAccount().getPassword()
				.equals(patientForm.getConfirmPassword()));

		return patient;
	}
	
	public Patient findOne(int patientId) {
		return patientRepository.findOne(patientId);
	}
}
