package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Appointment;
import domain.ClinicalHistory;
import domain.MedicalReport;
import domain.Patient;
import forms.MedicalReportForm;

import repositories.MedicalReportRepository;

@Service
@Transactional
public class MedicalReportService {
	
	// Managed repository -----------------------------------------------------
	
	@Autowired
	private MedicalReportRepository medicalReportRepository;
	
	// Supporting services ----------------------------------------------------

	@Autowired
	private ClinicalHistoryService clinicalHistoryService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private PatientService patientService;
	
	// Constructor ------------------------------------------------------------

	public MedicalReportService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	// FR-40
	// // Ver las hojas de consulta de su historial clínico.
	public Collection<MedicalReport> findByPatient(int patientId) {
		return medicalReportRepository.findByPatient(patientId);
	}
	
	public MedicalReport findByAppointment(int appointmentId){
		return medicalReportRepository.findByAppointment(appointmentId);
	}
	
	public MedicalReport findOne(int medicalReportId) {
		return medicalReportRepository.findOne(medicalReportId);
	}
	
	public MedicalReport create(int appointmentId){
		Appointment appointment = appointmentService.findOne(appointmentId);
		ClinicalHistory clinicalHistory = clinicalHistoryService.findByAppointment(appointmentId);
		MedicalReport result = new MedicalReport();
		result.setCreationDate(new Date());
		result.setAppointment(appointment);
		result.setClinicalHistory(clinicalHistory);
		
		return result;
	}
	
	public void save(MedicalReport medicalReport){
		long moment = System.currentTimeMillis() - 1000;
		Appointment appointment = appointmentService.findOne(medicalReport.getAppointment().getId());
		appointment.setMedicalReport(medicalReport);
		appointmentService.checkBelongsToDoctor(medicalReport.getAppointment().getId());
		medicalReport.setCreationDate(new Date(moment));
		medicalReportRepository.save(medicalReport);
	}

	// Ancillary methods -------------------------------------------------------
	
	public void checkPrincipal(MedicalReport medicalReport) {
		Patient patient = patientService.findOneByMedicalReport(medicalReport);
		Assert.isTrue(patientService.findByPrincipal().equals(patient));
	}
	
	public MedicalReport reconstruct(MedicalReportForm medicalReportForm) {
		Date moment = new Date(System.currentTimeMillis() - 1000);
		MedicalReport medicalReport = new MedicalReport();
		ClinicalHistory clinicalHistory = clinicalHistoryService.findByAppointment(medicalReportForm.getAppointment().getId());
		
		medicalReport.setCreationDate(moment);
		medicalReport.setDescription(medicalReportForm.getDescription());
		medicalReport.setAppointment(medicalReportForm.getAppointment());
		medicalReport.setClinicalHistory(clinicalHistory);
		
		return medicalReport;
	}
	
}

