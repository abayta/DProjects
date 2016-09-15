package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Lab;

@Repository
public interface LabRepository extends JpaRepository<Lab, Integer> {

}
