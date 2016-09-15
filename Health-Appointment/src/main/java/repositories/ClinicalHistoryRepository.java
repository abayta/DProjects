package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ClinicalHistory;

@Repository
public interface ClinicalHistoryRepository extends JpaRepository<ClinicalHistory, Integer> {

	// FR-25
	// Añadir un informe médico cada vez que trate a un paciente.
	@Query("select a.patient.clinicalHistory from Appointment a where a.id = ?1")
	ClinicalHistory findByAppointment(int appointmentId);
	
}
