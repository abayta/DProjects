package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Specialty;
import repositories.SpecialtyRepository;
import security.Authority;

@Service
@Transactional
public class SpecialtyService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SpecialtyRepository specialtyRepository;
	
	// Supporting services ----------------------------------------------------

	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private DoctorService doctorService;
		
	// Constructor ------------------------------------------------------------

	public SpecialtyService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Specialty findOne(int specialtyId) {
		return specialtyRepository.findOne(specialtyId);
	}
	
	// FR-6
	// Ver las especialidades que están registrados en el sistema.
	// FR-8
	// Registrar nuevos médicos en el sistema.
	public List<Specialty> findAll() {
		return specialtyRepository.findAll();
	}
	
	// FR-15, FR-33
	// Ver las especialidades disponibles, dada una clínica.
	public Collection<Specialty> findAllByClinic(int clinicId) {
		return specialtyRepository.findAllByClinic(clinicId);
	}
	
	// FR-6
	// Añadir una especialidad al sistema.
	public Specialty create() {
		Specialty specialty = new Specialty();
		return specialty;
	}

	public void save(Specialty specialty) {
		checkAdminRole();
		Assert.notNull(specialty);
		Assert.isTrue(doctorService.findBySpecialty(specialty.getId()).isEmpty());
		specialtyRepository.save(specialty);
	}

	// FR-6
	// Eliminar una especialidad al sistema.
	public void delete(Specialty specialty) {
		checkAdminRole();
		Assert.notNull(specialty);
		Assert.isTrue(doctorService.findBySpecialty(specialty.getId()).isEmpty());
		specialtyRepository.delete(specialty);
	}

	// Ancillary methods -------------------------------------------------------

	public void checkAdminRole() {
		Collection<Authority> authorities = 
				administratorService.findByPrincipal().getUserAccount().getAuthorities();
		Authority adminAuthority = new Authority();
		adminAuthority.setAuthority(Authority.ADMIN);
		Assert.isTrue(authorities.contains(adminAuthority));
	}
	
}
