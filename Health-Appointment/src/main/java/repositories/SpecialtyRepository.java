package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Specialty;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {

	// FR-15, FR-33
	// Ver las especialidades disponibles, dada una clínica.
	@Query("select distinct c.doctor.specialty from Schedule c where c.clinic.id = ?1")
	Collection<Specialty> findAllByClinic(int clinicId);
	
}
