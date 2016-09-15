package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Challenge;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Integer> {

	// ---------- Is not authenticated ----------
	
	// List all the challenges associated to an event, and their results, if 			(Silver Service)
	// available.
	@Query("select c from Challenge c where c.event.id = ?1")
	Collection<Challenge> findAllAssociatedEvent(int eventId);	
	
	
	// ---------- Is authenticated as an administrator ----------
	
	// Update-Delete a challenge of his or her, as long as the start time of the 		(Silver Service)
	// associated event has not elapsed, yet.
	@Query("select c from Challenge c where c.event.startMoment <= CURRENT_DATE and c.event.administrator.id = ?1")
	Collection<Challenge> findByAdministratorId(int administratorId);

}
