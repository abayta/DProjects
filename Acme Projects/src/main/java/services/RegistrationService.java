package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Project;
import domain.Project.StateEnum;
import domain.Registration;
import domain.Requirement;
import domain.StatusUpdate;
import domain.User;
import repositories.RegistrationRepository;

@Service
@Transactional
public class RegistrationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RegistrationRepository registrationRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ProjectService projectService;

	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	private UserService userService;

	// Constructor ------------------------------------------------------------

	public RegistrationService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Registration finOne(int registrationId) {
		return registrationRepository.findOne(registrationId);
	}
	
	public Registration findByUserAndProject(int userId, int projectId) {
		return registrationRepository.findByUserAndProject(userId, projectId);
	}
	
	public Collection<Registration> findAllByProject(int projectId) {
		Collection<Registration> registrations = registrationRepository.findAllByProject(projectId);

		return registrations;
	}
	
	// Method for registering a user into a project.
	public void create(int projectId) {
		Registration registration;
		User user = userService.findByPrincipal();
		long moment = System.currentTimeMillis() - 1000;
		registration = findByUserAndProject(user.getId(), projectId);
		Assert.isNull(registration);
		Requirement requirement = requirementService.findOneByGroupAndProject(user.getGroup().getId(), 
				projectId);
		Assert.isTrue(requirement.getFreePlaces() > 0);
		Project project = projectService.findOne(projectId);
		registration = new Registration();
		registration.setUser(user);
		registration.setProject(project);
		registration.setDate(new Date(moment));
		registrationRepository.save(registration);
		requirementService.saveRegister(requirement);
		projectService.changeToStarted(project);
	}

	public void delete(int projectId) {
		Registration registration;
		User user;
		Requirement requirement;
		Project project;
		project = projectService.findOne(projectId);
		Assert.isTrue(project.getState().equals(StateEnum.Recruiting));
		user = userService.findByPrincipal();
		requirement = requirementService.findOneByGroupAndProject(user.getGroup().getId(), 
				projectId);
		Assert.notNull(requirement);
		registration = findByUserAndProject(user.getId(), projectId);
		Assert.notNull(registration);
		registrationRepository.delete(registration);
		requirementService.saveUnregister(requirement);
	}

	// Ancillary methods ------------------------------------------------------

	public Long numberRegistrationByProjectAndGroup(int projectId, int groupId) {
		return registrationRepository.numberRegistrationByProjectAndGroup(projectId, groupId);
	}

	// FR-18
	// Calculate the total amount of work done by each of the participants of a
	// project.
	public void updateWork(StatusUpdate statusUpdate) {
		Registration registration = statusUpdate.getRegistration();
		registration.setTotalWork(registration.getTotalWork() + statusUpdate.getWorkAmount());
		registrationRepository.save(registration);
	}

}
