package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {

}
