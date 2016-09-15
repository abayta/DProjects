package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Clinic;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Integer> {

}
