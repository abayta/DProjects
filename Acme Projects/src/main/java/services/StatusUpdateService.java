package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Project.StateEnum;
import domain.Registration;
import domain.StatusUpdate;
import domain.User;
import repositories.StatusUpdateRepository;
import security.Authority;

@Service
@Transactional
public class StatusUpdateService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private StatusUpdateRepository statusUpdateRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ProjectService projectService;

	@Autowired
	private RegistrationService registrationService;
	
	@Autowired
	private UserService userService;

	// Constructors -----------------------------------------------------------

	public StatusUpdateService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public StatusUpdate findOne(int id) {
		StatusUpdate result;
		result = statusUpdateRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	// Free Services. FR-8
	// List stream of status updates for project.
	public Collection<StatusUpdate> findAllForProject(int projectId) {
		checkUserRole();
		Collection<StatusUpdate> statusUpdates;
		statusUpdates = statusUpdateRepository.findAllForProject(projectId);
		return statusUpdates;
	}

	// Gold Services. FR-20
	// Show a stream with the status updates of the projects he or she follows.
	// For each status update it should show its moment, the amount of work
	// done, its description and the project to which the status update belong.
	public Collection<StatusUpdate> findAllOfAllFollowedProject() {
		checkUserRole();
		User user = userService.findByPrincipal();
		Collection<StatusUpdate> statusUpdates;
		statusUpdates = statusUpdateRepository.findAllOfAllFollowedProject(user.getId());
		return statusUpdates;
	}

	public StatusUpdate create(int projectId) {
		StatusUpdate statusUpdate;
		Registration registration;
		User user = userService.findByPrincipal();
		registration = registrationService.findByUserAndProject(user.getId(),projectId);
		Assert.notNull(registration);
		statusUpdate = new StatusUpdate();
		statusUpdate.setRegistration(registration);
		statusUpdate.setMoment(new Date());
		return statusUpdate;
	}

	public void save(StatusUpdate statusUpdate) {
		checkByPricipal(statusUpdate);
		Assert.isTrue(statusUpdate.getRegistration().getProject().getState()
				.equals(StateEnum.Started));
		statusUpdate.setMoment(new Date(System.currentTimeMillis() - 1));
		statusUpdateRepository.save(statusUpdate);
		registrationService.updateWork(statusUpdate);
		projectService.updateWork(statusUpdate);
	}

	// Ancillary methods ------------------------------------------------------

	public void checkByPricipal(StatusUpdate statusUpdate) {
		User user = userService.findByPrincipal();
		Assert.isTrue(statusUpdate.getRegistration().getUser().equals(user));
	}

	public void checkUserRole() {
		Collection<Authority> authorities = 
				userService.findByPrincipal().getUserAccount().getAuthorities();
		Authority userAuthority = new Authority();
		userAuthority.setAuthority(Authority.USER);
		Assert.isTrue(authorities.contains(userAuthority));
	}

}
