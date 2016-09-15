package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.LabTechnician;

@Repository
public interface LabTechnicianRepository extends JpaRepository<LabTechnician, Integer> {

}
