package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Doctor;
import forms.DoctorForm;
import repositories.DoctorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class DoctorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private DoctorRepository doctorRepository;
	
	// Constructor ------------------------------------------------------------

	public DoctorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Doctor create() {
		Doctor result = new Doctor();
		Assert.notNull(result);

		Authority authority = new Authority();
		authority.setAuthority(Authority.DOCTOR);
		UserAccount userAccount = new UserAccount();
		userAccount.addAuthority(authority);

		result.setUserAccount(userAccount);

		return result;
	}
	
	public void save(Doctor doctor) {
		Assert.notNull(doctor);
		if (doctor.getId() == 0) {
			String password = doctor.getUserAccount().getPassword();
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			password = encoder.encodePassword(password, null);
			doctor.getUserAccount().setPassword(password);
		}
		
		doctorRepository.save(doctor);
	}
	
	public Doctor findByPrincipal() {
		Doctor result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByUserAccount(userAccount);
		Assert.notNull(result);
		return result;
	}
	
	public Doctor findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		Doctor result;
		result = doctorRepository.findByUserAccountId(userAccount.getId());
		return result;
	}
	
	public Doctor findOne(int doctorId) {
		return doctorRepository.findOne(doctorId);
	}
	
	// FR-2
	// Ver los médicos que están registrados en el sistema.
	public List<Doctor> findAll() {
		return doctorRepository.findAll();
	}
	
	// FR-15, FR-33
	// Ver las especialidades disponibles, dada una clínica.
	public Collection<Doctor> findBySpecialty(int specialtyId) {
		return doctorRepository.findBySpecialty(specialtyId);
	}
	
	// FR-16, FR-34
	// Ver los médicos dada una especialidad.
	public Collection<Doctor> findAllBySpecialtyAndClinic(int specialtyId, int clinicId) {
		return doctorRepository.findAllBySpecialtyAndClinic(specialtyId, clinicId);
	}

	// Ancillary methods -------------------------------------------------------

	public DoctorForm construct(Doctor doctor) {
		DoctorForm doctorForm = new DoctorForm();

		doctorForm.setId(doctor.getId());
		doctorForm.setVersion(doctor.getVersion());
		doctorForm.setName(doctor.getName());
		doctorForm.setSurname(doctor.getName());
		doctorForm.setEmailAddress(doctor.getEmailAddress());
		doctorForm.setImage(doctor.getImage());
		doctorForm.setUsername(doctor.getUserAccount().getUsername());
		doctorForm.setPassword(doctor.getUserAccount().getPassword());
		doctorForm.setSpecialty(doctor.getSpecialty());

		return doctorForm;
	}

	public Doctor reconstruct(DoctorForm doctorForm) {
		Doctor doctor;
		if(doctorForm.getId() == 0){
			doctor = create();
			doctor.setName(doctorForm.getName());
			doctor.setSurname(doctorForm.getName());
			doctor.setSpecialty(doctorForm.getSpecialty());
			doctor.setImage(doctorForm.getImage());
			doctor.getUserAccount().setUsername(doctorForm.getUsername());
			doctor.getUserAccount().setPassword(doctorForm.getPassword());
		}else
			doctor = findOne(doctorForm.getId());
		
		doctor.setEmailAddress(doctorForm.getEmailAddress());
		if (doctor.getId() != 0 && !(doctorForm.getImage().equals(null) || doctorForm.getImage().length==0))
			doctor.setImage(doctorForm.getImage());

		if(doctorForm.getId() == 0){
			Assert.isTrue(doctorForm.getAcceptTerms());
			Assert.isTrue(doctor.getUserAccount().getPassword()
				.equals(doctorForm.getConfirmPassword()));
		}
			
		return doctor;
	}

}
