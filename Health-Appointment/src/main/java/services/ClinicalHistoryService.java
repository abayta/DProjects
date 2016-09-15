package services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.ClinicalHistory;

import repositories.ClinicalHistoryRepository;


@Service
@Transactional
public class ClinicalHistoryService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ClinicalHistoryRepository clinicalHistoryRepository;
	
	// Supporting services ----------------------------------------------------

	
		
	// Constructor ------------------------------------------------------------

	public ClinicalHistoryService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
 
	public ClinicalHistory create() {
		ClinicalHistory result = new ClinicalHistory();
		Assert.notNull(result);
		result.setCreationDate(new Date());
		return result;
	}

	public ClinicalHistory findByAppointment(int appointmentId){
		return clinicalHistoryRepository.findByAppointment(appointmentId);
	}
	

}
