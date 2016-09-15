package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Requirement;

@Repository
public interface RequirementRepository extends JpaRepository<Requirement, Integer> {
	
	// User numbers in a group of a project.
	@Query("select r from Requirement r where r.group.id = ?1 and r.project.id = ?2")
	Requirement findOneByGroupAndProject(int groupId, int projectId);
	
}
