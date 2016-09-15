package services;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.Event;
import domain.Participant;
import forms.EventForm;

import repositories.EventRepository;
import security.LoginService;

@Service
@Transactional
public class EventService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private EventRepository eventRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ParticipantService participantService;
	
	@Autowired
	private AdministratorService administratorService;

	// Constructors -----------------------------------------------------------

	public EventService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Event create() {
		Administrator administrator = administratorService.findByPrincipal();
		Event result = new Event();
		UUID uuid = UUID.randomUUID();
		String referenceNumber = uuid.toString().replace("-", "p");
		result.setCreationMoment(new Date());
		result.setAdministrator(administrator);
		result.setReferenceNumber(referenceNumber);
		return result;
	}

	public Event findOne(int id) {
		Event result;
		result = eventRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Event findOneToEdit(int id) {
		Event result;
		result = eventRepository.findOne(id);
		checkPrincipal(result);
		return result;
	}
	
	public Collection<Event> findAllEvents(){
		return eventRepository.findAll();
	}

	// Update the events he or she has created as long as no participant has joined them.
	public void save(Event event) {
		long moment = System.currentTimeMillis() - 1000;
		checkPrincipal(event);
		Collection<Event> eventWithoutRegistrations;
		Administrator administrator = administratorService.findByPrincipal();
		eventWithoutRegistrations = eventRepository
				.findByAdministratorIdAndNotRegistration(administrator.getId());
		if (event.getId() == 0)
			Assert.isTrue(event.getRegistrations().isEmpty());
		else
			Assert.isTrue(eventWithoutRegistrations.contains(event));
		event.setCreationMoment(new Date(moment));
		eventRepository.save(event);
	}
	
	public void saveRating(int eventId, Double rating){
		Event event = eventRepository.findOne(eventId);
		event.setRating(rating);
		eventRepository.save(event);
	}

	// Delete the events he or she has created as long as no participant has joined them.
	public void delete(Event event) {
		checkPrincipal(event);
		Collection<Event> eventWithoutRegistrations;
		Administrator administrator = administratorService.findByPrincipal();
		eventWithoutRegistrations = eventRepository
				.findByAdministratorIdAndNotRegistration(administrator.getId());
		Assert.isTrue(eventWithoutRegistrations.contains(event));
		eventRepository.delete(event);
	}

	// List the events he or she has created. (Free Service)
	public Collection<Event> findByPrincipal() {
		Administrator administrator = administratorService.findByPrincipal();
		Collection<Event> events;
		events = eventRepository.findByAdministratorId(administrator.getId());
		return events;
	}

	// Other business methods -------------------------------------------------

	// List the events managed by Acme SportTracking, Inc. as long as their			(Free Services)
	// start time has not elapsed, yet.
	public Collection<Event> findAllActive() {
		Collection<Event> events;
		events = eventRepository.findAllActive();
		return events;
	}
	
	// List the on-going events managed by Acme SportTrack, Inc, that is, 
	// the events whose start moment has elapsed but not their finish moment.
	public Collection<Event> findAllOnGoing() {
		Collection<Event> events;
		events = eventRepository.findAllOnGoing();
		return events;
	}
			
	// List the events that he or she has joined.									(Free Services)
	public Collection<Event> findJoinedParticipant() {
		Participant participant = participantService.findByPrincipal();
		Collection<Event> events;
		events = eventRepository.findJoinedParticipant(participant.getId());
		return events;
	}
	
	// List the on-going events he or she has created.								(Silver Services)
	public Collection<Event> findOnGoingByPrincipal() {
		Administrator administrator = administratorService.findByPrincipal();
		Collection<Event> events;
		events = eventRepository.findAllOnGoingCreatedByAdmin(administrator.getId());
		return events;
	}
	
	//Participant of one event
	public long participantOfEvent(int eventId){
		return eventRepository.participantOfEvent(eventId);
	}
		
	// Ancillary methods ----------------------------------------------------

	public void checkPrincipal(Event event) {
		Assert.isTrue(administratorService.findByPrincipal().equals(event.getAdministrator()));
	}
	
	public EventForm construct(Event event){		
		EventForm eventForm = new EventForm();
		
		eventForm.setId(event.getId());
		eventForm.setVersion(event.getVersion());
		eventForm.setTitle(event.getTitle());
		eventForm.setFee(event.getFee());
		eventForm.setStartMoment(event.getStartMoment());
		eventForm.setFinishMoment(event.getFinishMoment());
		eventForm.setDescription(event.getDescription());
		
		return eventForm;
	}
	
	public Event reconstruct(EventForm eventForm) {
		Event event = create();
		
		event.setId(eventForm.getId());
		event.setVersion(eventForm.getVersion());
		event.setTitle(eventForm.getTitle());
		event.setFee(eventForm.getFee());
		event.setStartMoment(eventForm.getStartMoment());
		event.setFinishMoment(eventForm.getFinishMoment());
		event.setDescription(eventForm.getDescription());
	
		return event;
	}

	public long countAllEvents() {
		return eventRepository.count();
	}
	
	//(GOLD)
	
	public Collection<Event> findAllOrder(){
		return eventRepository.findAllOrder();
	}
	
	public Collection<Event> findAllCreatedOrder(){
		return eventRepository.findAllCreatedOrder(LoginService.getPrincipal().getId());
	}
	
	public Collection<Event> findAllJoinedOrder(){
		return eventRepository.findAllJoinedOrder(LoginService.getPrincipal().getId());
	}

}
