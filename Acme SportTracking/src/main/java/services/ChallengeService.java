package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Challenge;
import domain.Event;

import repositories.ChallengeRepository;

@Service
@Transactional
public class ChallengeService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ChallengeRepository challengeRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private AdministratorService administratorService;

	// Constructors -----------------------------------------------------------

	public ChallengeService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Challenge create(Event event) {
		Challenge result = new Challenge();
		result.setEvent(event);
		return result;
	}

	public Challenge findOne(int id) {
		Challenge result;
		result = challengeRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Challenge findOneToEdit(int id) {
		Challenge result;
		result = challengeRepository.findOne(id);
		checkPrincipal(result);
		return result;
	}

	// Update a challenge of his or her, as long as the start time of the associated 
	// event has not elapsed, yet.
	public void save(Challenge challenge) {
		checkPrincipal(challenge);
		Assert.isTrue(challenge.getEvent().getStartMoment().after(new Date()));
		challengeRepository.save(challenge);
	}

	// Delete a challenge of his or her, as long as the start time of the associated 
	// event has not elapsed, yet.
	public void delete(Challenge challenge) {
		checkPrincipal(challenge);
		Assert.isTrue(challenge.getEvent().getStartMoment().after(new Date()));
		challengeRepository.delete(challenge);
	}

	// List all the challenges associated to an event, and their results, if available.
	public Collection<Challenge> findAllAssociatedEvent(int eventId) {
		Collection<Challenge> challenges;
		challenges = challengeRepository.findAllAssociatedEvent(eventId);
		return challenges;
	}

	// Other business methods -------------------------------------------------
	
	// Ancillary methods ----------------------------------------------------

	public void checkPrincipal(Challenge challenge) {
		Assert.isTrue(administratorService.findByPrincipal().equals(
				challenge.getEvent().getAdministrator()));
	}

}
