package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Appointment;
import domain.MedicalReport;
import domain.Prescription;
import domain.Specialty;

import repositories.PrescriptionRepository;
import security.Authority;

@Service
@Transactional
public class PrescriptionService {
	
	// Managed repository -----------------------------------------------------

	@Autowired
	private PrescriptionRepository prescriptionRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private MedicalReportService medicalReportService;
	
	// Constructor ------------------------------------------------------------

	public PrescriptionService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	// FR-28
	// Ver las recetas asociadas a un informe médico.
	public Collection<Prescription> findByMedicalReportViewDoctor(int medicalReportId) {
		checkDoctorRole();
		return prescriptionRepository.findByMedicalReport(medicalReportId);
	}
	
	// FR-41
	// Ver las recetas asociadas a sus hojas de consulta y sus detalles.
	public Collection<Prescription> findByMedicalReport(int medicalReportId) {
		MedicalReport medicalReport = medicalReportService.findOne(medicalReportId);
		medicalReportService.checkPrincipal(medicalReport);
		return prescriptionRepository.findByMedicalReport(medicalReportId);
	}

	public Prescription create(int medicalReportId) {
		Prescription prescription = new Prescription();
		prescription.setMedicalReport(medicalReportService.findOne(medicalReportId));
		medicalReportService.findOne(medicalReportId).getPrescriptions().add(prescription);
		return prescription;
	}

	public void save(Prescription prescription) {
		Appointment appointment = appointmentService.findByMedicalReport(prescription.getMedicalReport().getId());
		appointmentService.checkBelongsToDoctor(appointment.getId());
		Assert.notNull(prescription);
		prescriptionRepository.save(prescription);
	}
	
	// Ancillary methods -------------------------------------------------------
		
	public void checkDoctorRole() {
		Collection<Authority> authorities = 
				doctorService.findByPrincipal().getUserAccount().getAuthorities();
		Authority doctorAuthority = new Authority();
		doctorAuthority.setAuthority(Authority.DOCTOR);
		Assert.isTrue(authorities.contains(doctorAuthority));
	}

}

