package services;

import java.util.Collection;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.AssessmentRoute;
import domain.Participant;
import domain.Route;

import repositories.AssessmentRouteRepository;

@Service
@Transactional
public class AssessmentRouteService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AssessmentRouteRepository assessmentRouteRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ParticipantService participantService;
	
	// Constructors -----------------------------------------------------------

	public AssessmentRouteService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public AssessmentRoute create(Route route) {
		AssessmentRoute result = new AssessmentRoute();
		Participant participant = participantService.findByPrincipal();
		result.setParticipant(participant);
		result.setRoute(route);
		return result;
	}
	
	public AssessmentRoute findOne(int id) {
		AssessmentRoute result;
		result = assessmentRouteRepository.findOne(id);
		return result;
	}

	public AssessmentRoute findOneToEdit(int id) {
		AssessmentRoute result;
		result = assessmentRouteRepository.findOne(id);
		checkPrincipalParticipant(result);
		return result;
	}
	
	public AssessmentRoute findByRouteAndParticipant(int routeId){
		Participant participant = participantService.findByPrincipal();
		AssessmentRoute assess = assessmentRouteRepository.findByEventAndParticipant(participant.getId(), routeId);
		return assess;
	}
	
	// List his or her route assessments.
	public Collection<AssessmentRoute> findByPrincipal() {
		Participant participant = participantService.findByPrincipal();
		Collection<AssessmentRoute> assessmentsRoutes;
		assessmentsRoutes = assessmentRouteRepository.findParticipantAssessments(participant.getId());
		return assessmentsRoutes;
	}

	public void save(AssessmentRoute assessmentRoute) {
		checkPrincipalParticipant(assessmentRoute);
		Assert.isTrue(assessmentRoute.getRoute().getEvent().getFinishMoment().before(new Date()));
		assessmentRouteRepository.save(assessmentRoute);
	}
	
	// Delete a previous route assessment he or she has performed.
	public void delete(AssessmentRoute assessmentRoute) {
		checkPrincipalParticipant(assessmentRoute);
		Assert.isTrue(assessmentRoute.getRoute().getEvent().getFinishMoment().before(new Date()));
		assessmentRouteRepository.delete(assessmentRoute);
	}

	// Other business methods -------------------------------------------------
	
	public void checkPrincipalParticipant(AssessmentRoute assessmentRoute) {
		Assert.isTrue(participantService.findByPrincipal().equals(
				assessmentRoute.getParticipant()));
	}

}
