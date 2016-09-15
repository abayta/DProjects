package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Prescription;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {
	
	// FR-41
	// Ver las recetas asociadas a sus hojas de consulta y sus detalles.
	@Query("select p from Prescription p where p.medicalReport.id = ?1 ")
	Collection<Prescription> findByMedicalReport(int medicalReportId);
	
}
