package repositories;

import java.util.Collection;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.AssessmentRoute;

@Repository
public interface AssessmentRouteRepository extends JpaRepository<AssessmentRoute, Integer> {

	// ---------- Is authenticated as a participant ----------
	
	// List his or her route assessments.										 		(Silver Service)
	@Query("select a from AssessmentRoute a where a.assessment != null and a.participant.id = ?1")
	Collection<AssessmentRoute> findParticipantAssessments(int participantId);

	// Find a assessmentRoute of a route and a participant
	@Query("select a from AssessmentRoute a where a.participant.id=?1 and a.route.id=?2")
	AssessmentRoute findByEventAndParticipant(int id, int routeId);
		
}
