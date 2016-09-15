package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

	// ---------- Is not authenticated ----------
	
	// List the events managed by Acme SportTracking, Inc. as long as their				(Free Service)
	// start time has not elapsed, yet.
	@Query("select e from Event e where e.startMoment >= CURRENT_DATE")
	Collection<Event> findAllActive();

	// List the on-going events, that is, the events whose start moment has 			(Silver Service)
	// elapsed but not their finish moment.
	@Query("select e from Event e where e.startMoment <= CURRENT_DATE and e.finishMoment >= CURRENT_DATE")
	Collection<Event> findAllOnGoing();
	
	
	// ---------- Is authenticated as an administrator ----------
	
	// List the events he or she has created.											(Free Service)
	@Query("select e from Event e where e.administrator.id = ?1")
	Collection<Event> findByAdministratorId(int administratorId);

	// Update-Delete the events he or she has created as long as no participant has		(Free Service)
	// joined them.
	@Query("select e from Event e where e.administrator.id = ?1 and e.registrations.size = 0")
	Collection<Event> findByAdministratorIdAndNotRegistration(int administratorId);
	
	// List the on-going events he or she has created.									(Silver Service)
	@Query("select e from Event e where e.startMoment <= CURRENT_DATE and e.finishMoment >= CURRENT_DATE and e.administrator.id = ?1")
	Collection<Event> findAllOnGoingCreatedByAdmin(int administratorId);
	
	
	// ---------- Is authenticated as a participant ----------
	
	// List the events that he or she has joined.										(Free Service)
	@Query("select r.event from Registration r where r.participant.id = ?1")
	Collection<Event> findJoinedParticipant(int participantId);
	
	
	//--------------- Others--------------------
	
	//Number of participant of one Event
	@Query("select count(r) from Registration r where r.event.id=?1")
	long participantOfEvent(int eventId);
	
	//(Gold)
	//Select all events order by rating
	@Query("select e from Event e order by e.rating DESC")
	public Collection<Event> findAllOrder();
	
	//Select all one administrator's event order by rating
	@Query("select e from Event e where e.administrator.userAccount.id = ?1 order by e.rating DESC")
	public Collection<Event> findAllCreatedOrder(int userAccountId);
	
	//Select all event that a participant is joined order by rating
	@Query("select r.event from Registration r where r.participant.userAccount.id=?1 order by r.event.rating DESC")
	public Collection<Event> findAllJoinedOrder(int userAccountId);
	
	
}
