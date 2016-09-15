package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Group;
import repositories.GroupRepository;
import security.Authority;

@Service
@Transactional
public class GroupService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private GroupRepository groupRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private AdministratorService administratorService;

	// Constructors -----------------------------------------------------------

	public GroupService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Group findOne(int id) {
		Group result;
		result = groupRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Group> findAll() {
		Collection<Group> groups;
		groups = groupRepository.findAll();
		return groups;
	}

	// Free Services. FR-3, FR-7
	// List the groups managed by Acme Projects, Inc.
	public Collection<Group> findAllToAdministrator() {
		Collection<Group> groups;
		groups = groupRepository.findAllToAdministrator();
		return groups;
	}
	
	public Group create() {
		Group result = new Group();
		return result;
	}

	public void save(Group group) {
		checkAdminRole();
		Assert.notNull(group);
		Assert.isTrue(group.getUsers().isEmpty());
		groupRepository.save(group);
	}

	public void delete(Group group) {
		checkAdminRole();
		Assert.notNull(group);
		Assert.isTrue(group.getUsers().isEmpty());
		groupRepository.delete(group);
	}

	// Ancillary methods ------------------------------------------------------

	public void checkAdminRole() {
		Collection<Authority> authorities = 
				administratorService.findByPrincipal().getUserAccount().getAuthorities();
		Authority adminAuthority = new Authority();
		adminAuthority.setAuthority(Authority.ADMIN);
		Assert.isTrue(authorities.contains(adminAuthority));
	}

}
