package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Integer> {

	// ---------- Ancillary queries ----------
	
	// List his or her registrations.
	@Query("select r from Registration r where r.participant.id = ?1")
	Collection<Registration> findByParticipantId(int patricipantId);
	
	// Consult the registrations to the events he or she has created.
	@Query("select e.registrations from Event e where e.administrator.id = ?1 and e.id = ?2")
	Collection<Registration> findAllToRegistrationsByAdministratorId(int administratorId,
			int eventId);
	
	//Find the registration of a participant to an event
	@Query("select r from Registration r where r.participant.id=?1 and r.event.id=?2")
	Registration findByEventAndParticipant(int participantId, int eventId);
	
}
