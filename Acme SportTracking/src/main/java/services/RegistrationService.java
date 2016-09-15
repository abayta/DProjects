package services;

import java.util.Collection;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.Event;
import domain.Participant;
import domain.Registration;

import repositories.RegistrationRepository;

@Service
@Transactional
public class RegistrationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RegistrationRepository registrationRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ParticipantService participantService;
	
	@Autowired
	private AdministratorService administratorService;

	// Constructors -----------------------------------------------------------

	public RegistrationService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// Joined to an event.
	public Registration create(Event event) {
		Registration result = new Registration();
		Participant participant = participantService.findByPrincipal();
		result.setParticipant(participant);
		result.setEvent(event);
		result.setDate(new Date());
		return result;
	}
	
	public Registration findOne(int id) {
		Registration result;
		result = registrationRepository.findOne(id);
		return result;
	}
	
	public Registration findByEventAndParticipant(int eventId){
		Participant participant = participantService.findByPrincipal();
		Registration registration = registrationRepository.findByEventAndParticipant(participant.getId(), eventId);
		return registration;
	}

	public Registration findOneToEdit(int id) {
		Registration result;
		result = registrationRepository.findOne(id);
		checkPrincipalParticipant(result);
		return result;
	}
	
	// List his or her registrations (Participant).
	public Collection<Registration> findByPrincipal() {
		Participant participant = participantService.findByPrincipal();
		Collection<Registration> registrations;
		registrations = registrationRepository.findByParticipantId(participant.getId());
		return registrations;
	}

	// Consult the registrations to the events he or she has created
	// (Administrator).
	public Collection<Registration> findAllToRegistrationsByAdministratorId(int eventId) {
		Administrator administrator = administratorService.findByPrincipal();
		Collection<Registration> registrations;
		registrations = registrationRepository
				.findAllToRegistrationsByAdministratorId(administrator.getId(), eventId);
		return registrations;
	}

	public void save(Registration registration) {
		Assert.notNull(registration);
		registration.setDate(new Date(System.currentTimeMillis()-1));
		checkPrincipalParticipant(registration);
		Assert.isTrue(registration.getEvent().getStartMoment().after(new Date()));
		registrationRepository.save(registration);
	}
	//servicio para valorar 
	public void assess(Registration registration){
		Assert.notNull(registration);
		Assert.isTrue(new Date().after(registration.getEvent().getFinishMoment()));
		checkPrincipalParticipant(registration);
		registrationRepository.save(registration);
	}

	// Cancel his or her registration to an event, as long as the start moment has not
	// elapsed, yet.
	public void delete(Registration registration) {
		checkPrincipalParticipant(registration);
		Assert.isTrue(registration.getEvent().getStartMoment().after(new Date()));
		registrationRepository.delete(registration.getId());
	}

	// Other business methods -------------------------------------------------

	// Create or update the assessment of an event after the corresponding finish moment.
	public void saveAssessment(Registration registration) {
		checkPrincipalParticipant(registration);
		Assert.isTrue(registration.getEvent().getFinishMoment().before(new Date()));
		registrationRepository.save(registration);
	}
	
	//If one participant is joined to a registration
	public Boolean isRegistrate(int eventId){
		Boolean res;
		Participant participant = participantService.findByPrincipal();
		Registration registration = registrationRepository.findByEventAndParticipant(participant.getId(), eventId);
		if (registration==null)
			res = false;
		else
			res = true;
		
	return res;
	}
	
	public void checkPrincipalParticipant(Registration registration) {
		Assert.isTrue(participantService.findByPrincipal().equals(
				registration.getParticipant()));
	}

}
