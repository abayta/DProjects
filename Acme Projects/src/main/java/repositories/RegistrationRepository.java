package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
	
	// Number of registration of a project of a group.
	@Query("select count(r) from Registration r where r.project.id = ?1 and r.user.group.id = ?2")
	Long numberRegistrationByProjectAndGroup(int projectId, int groupId);
	
	// List all the project's registrations.
	@Query("select r from Registration r where r.project.id = ?1")
	Collection<Registration> findAllByProject(int projectId);

	@Query("select r from Registration r where r.user.id = ?1 and r.project.id = ?2")
	Registration findByUserAndProject(int userId, int projectId);
	
}
