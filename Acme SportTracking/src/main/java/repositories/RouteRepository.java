package repositories;

import java.util.Collection;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
	
	// ---------- Is authenticated as a participant ----------
	
	// Assess routes that he or she has joined whose finish moment has elapsed.	 		(Silver Service)
	@Query("select r.event.routes from Registration r where r.event.finishMoment <= ?1 and r.participant.id = ?2")
	Collection<Route> findFinishedByParticipant(Date currentMoment, int participantId);

	
	// ---------- Ancillary queries ----------

	// Obtain the orderNumber collection that belong to the routes of an event.
	@Query("select orderNumber from Route r where r.event.id = ?1")
	Collection<Integer> findOrderNumberRoute(int eventId);
	
	// Obtain the route collection that belong to an event.
	@Query("select r from Route r where r.event.id = ?1")
	Collection<Route> findAllByEvent(int eventId);
	
	//Find all routes that a participant is joined
	@Query("select r.event.routes from Registration r where r.participant.userAccount.id = ?1")
	Collection<Route> findAllByPrincipal(int userAccountId);
	
	//find all routes order by rating
	@Query("select r from Route r order by r.rating DESC")
	Collection<Route> findAllOrder();
	
	//Find all routes that a administration has created order by rating
	@Query("select r from Route r where r.event.administrator.userAccount.id = ?1 order by r.rating DESC")
	Collection<Route> findAllCreatedOrder(int userAccountId);

	//Find all event's route order by rating
	@Query("select r from Route r where r.event.id=?1 order by r.rating DESC")
	public Collection<Route> findByEventJoinedOrder(int id);
	
}
