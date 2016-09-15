package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Clinic;

import repositories.ClinicRepository;
import security.Authority;

@Service
@Transactional
public class ClinicService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ClinicRepository clinicRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private AdministratorService administratorService;
	
	// Constructor ------------------------------------------------------------

	public ClinicService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Clinic create() {
		Clinic result = new Clinic();
		return result;
	}

	public void save(Clinic clinic) {
		checkAdminRole();
		Assert.notNull(clinic);
		byte[] logo = clinic.getLogo();
		
		if (clinic.getId() != 0 && (logo.equals(null) || clinic.getLogo().length==0))
			clinic.setLogo(findOne(clinic.getId()).getLogo());
		
		clinicRepository.save(clinic);
	}

	public Clinic findOne(int clinicId) {
		return clinicRepository.findOne(clinicId);
	}
	
	// FR-4
	// Ver las clínicas que están registradas en el sistema.
	public List<Clinic> findAll() {
		return clinicRepository.findAll();
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
