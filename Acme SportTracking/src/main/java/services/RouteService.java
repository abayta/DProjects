package services;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Event;
import domain.Participant;
import domain.Route;

import repositories.RouteRepository;
import security.LoginService;

@Service
@Transactional
public class RouteService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RouteRepository routeRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private ParticipantService participantService;

	// Constructors -----------------------------------------------------------

	public RouteService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Route create(Event event) {
		Route result = new Route();
		result.setEvent(event);
		return result;
	}

	public Route findOne(int id) {
		Route result;
		result = routeRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Route findOneToEdit(int id) {
		Route result;
		result = routeRepository.findOne(id);
		checkPrincipal(result);
		return result;
	}
	
	public Collection<Route> findAllRoutes(){
		return routeRepository.findAll();
	}

	public void save(Route route) {
		checkPrincipal(route);
		Assert.isTrue(route.getEvent().getRegistrations().isEmpty());
		Integer orderNumber = route.getOrderNumber();
		if(route.getId()==0)
			// Checks that orderNumber is not used by any route associated to this event. 
			Assert.isTrue(!routeRepository.findOrderNumberRoute(route.getEvent().getId()).contains(orderNumber));
		else{
			Collection<Integer> orderNumbers = routeRepository.findOrderNumberRoute(route.getEvent().getId());
			List<Integer> l = new LinkedList<Integer>() ; 
			l.addAll(orderNumbers);
			// Remove the orderNumber of the orderNumber's list associated to this event, in order to
			// the event to be edited can take the same orderNumber. 
			l.remove(l.indexOf(findOne(route.getId()).getOrderNumber())); 
			// Checks that orderNumber is not used by any route associated to this event. 
			Assert.isTrue(!l.contains(orderNumber));
		}
		routeRepository.save(route);
	}
	
	public void saveRating(int routeId, Double rating){
		Route route = routeRepository.findOne(routeId);
		route.setRating(rating);
		routeRepository.save(route);
	}

	public void delete(Route route) {
		checkPrincipal(route);
		Assert.isTrue(route.getEvent().getRegistrations().isEmpty());
		routeRepository.delete(route);
	}

	// Other business methods -------------------------------------------------

	// Assess routes that he or she has joined whose finish moment has elapsed.
	public Collection<Route> findFinishedByParticipant() {
		Collection<Route> routes;
		Participant participant = participantService.findByPrincipal();
		routes = routeRepository.findFinishedByParticipant(new Date(), participant.getId());
		return routes;
	}
	
	public Collection<Route> findAllByEvent(int eventId) {
		Collection<Route> routes;
		routes = routeRepository.findAllByEvent(eventId);
		return routes;
	}
	
	public Collection<Route> findAllByPricipal(){
		return routeRepository.findAllByPrincipal(LoginService.getPrincipal().getId());
	}
	
	// Ancillary methods ----------------------------------------------------

	public void checkPrincipal(Route route) {
		Assert.isTrue(administratorService.findByPrincipal().equals(route.getEvent().getAdministrator()));
	}

	public long numberOfRoutes() {
		// TODO Auto-generated method stub
		return routeRepository.count();
	}	
	
	public Collection<Route> findAllOrder(){
		return routeRepository.findAllOrder();
	}
	
	public Collection<Route> findAllCreatedOrder(){
		return routeRepository.findAllCreatedOrder(LoginService.getPrincipal().getId());
	}
	
	public Collection<Route> findByEventJoinedOrder(int eventId){
		return routeRepository.findByEventJoinedOrder(eventId);
	}

}
