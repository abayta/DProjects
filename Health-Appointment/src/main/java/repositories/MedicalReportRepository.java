package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MedicalReport;

@Repository
public interface MedicalReportRepository extends JpaRepository<MedicalReport, Integer> {
	
	// FR-40
	// Ver las hojas de consulta de su historial clínico.
	@Query("select c.medicalReports from ClinicalHistory c where c.patient.id = ?1")
	Collection<MedicalReport> findByPatient(int patientId);

	@Query("select m from MedicalReport m where m.appointment.id = ?1")
	MedicalReport findByAppointment(int appointmentId);
	
}
