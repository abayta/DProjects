package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

	// Buscar paciente por UserAccount
	@Query("select p from Patient p where p.userAccount.id = ?1")
	Patient findOneByUserAccount(int userAccountId);
	
	// Buscar paciente por Medical Report.
	@Query("select m.clinicalHistory.patient from MedicalReport m where m.id = ?1")
	Patient findOneByMedicalReport(int medicalReportId);
	
}
