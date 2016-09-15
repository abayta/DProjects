package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

	
	// Find a participant by userAccounnt id
	@Query("select p from Participant p where p.userAccount.id = ?1")
	Participant findByUserAccountId(int userAccountId);

	// ---------- Is authenticated as an administrator ----------
	
	// List the participants who have joined the events he or she has created.			(Silver Service)
	@Query("select distinct r.participant from Registration r where r.event.administrator.id = ?1")
	Collection<Participant> findJoinedEventsByAdmin(int userAccountId);
	
	// List the participants who have joined an event
	@Query("select distinct r.participant from Registration r where r.event.id = ?1")
	Collection<Participant> findJoinedEvent(int eventId);	
	
}
