package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Project;
import domain.Requirement;
import domain.User;
import repositories.RequirementRepository;

@Service
@Transactional
public class RequirementService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RequirementRepository requirementRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;	
	
	// Constructor ------------------------------------------------------------
	
	public RequirementService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public Requirement findOne(Integer RequirementId) {
		Requirement requirement;
		requirement = requirementRepository.findOne(RequirementId);
		Assert.notNull(requirement);
		return requirement;
	}

	public Requirement findOneByGroupAndProject(int groupId, int projectId) {
		Requirement res = requirementRepository.findOneByGroupAndProject(groupId, projectId);
		return res;
	}
	
	public Requirement create(int projectId) {
		Requirement requirement = new Requirement();
		Project project = projectService.findOne(projectId);
		requirement.setProject(project);
		return requirement;
	}

	public void save(Requirement requirement) {
		Assert.notNull(requirement);
		Requirement old = findOneByGroupAndProject(requirement.getGroup().getId(), 
				requirement.getProject().getId());
		User user = userService.findByPrincipal();
		Assert.isTrue(requirement.getProject().getCreator().equals(user));
		Assert.isTrue(requirement.getProject().getRegistrations().isEmpty());
		if (requirement.getId() == 0)
			Assert.isNull(old);
		else
			Assert.isTrue(old.getId() == requirement.getId());
		requirement.setFreePlaces(requirement.getPeopleNumber());
		requirementRepository.save(requirement);
	}

	public void saveRegister(Requirement requirement) {
		requirement.setFreePlaces(requirement.getFreePlaces() - 1);
		requirementRepository.save(requirement);
	}

	public void saveUnregister(Requirement requirement) {
		requirement.setFreePlaces(requirement.getFreePlaces() + 1);
		requirementRepository.save(requirement);
	}

	public void delete(Requirement requirement) {
		Assert.notNull(requirement);
		Assert.isTrue(requirement.getProject().getRegistrations().isEmpty());
		requirementRepository.delete(requirement);
	}
	
}