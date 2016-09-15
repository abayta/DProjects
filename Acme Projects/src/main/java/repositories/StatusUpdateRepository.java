package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.StatusUpdate;

@Repository
public interface StatusUpdateRepository extends JpaRepository<StatusUpdate, Integer> {

	// Free Services. FR-8
	// List stream of status updates for project
	@Query("select s from StatusUpdate s where s.registration.project.id = ?1 order by s.moment DESC")
	Collection<StatusUpdate> findAllForProject(int projectId);
	
	// Gold Services. FR-20
	// Show a stream with the status updates of the projects he or she follows.
	// For each status update it should show its moment, the amount of work
	@Query("select s from StatusUpdate s where s.registration.project.id in (select project.id from User u INNER JOIN u.followedProjects AS project WHERE u.id = ?1) order by s.moment DESC") 
	Collection<StatusUpdate> findAllOfAllFollowedProject(int userId);
	
}
