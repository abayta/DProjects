package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

	// Find doctor by userAccount.id
	@Query("select d from Doctor d where d.userAccount.id = ?1")
	Doctor findByUserAccountId(int userAccountId);
	
	// FR-15, FR-33
	// Ver las especialidades disponibles, dada una clínica.
	@Query("select d from Doctor d where d.specialty.id = ?1")
	Collection<Doctor> findBySpecialty(int specialtyId);

	// FR-16, FR-34
	// Ver las especialidades disponibles, dada una clínica.
	@Query("select c.doctor from Schedule c where c.doctor.specialty.id = ?1 and c.clinic.id = ?2")
	Collection<Doctor> findAllBySpecialtyAndClinic(int specialtyId, int clinicId);
	
}
