package services;

import java.util.Collection;

import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Project;
import domain.Project.StateEnum;
import domain.Registration;
import domain.StatusUpdate;
import domain.User;
import forms.ProjectForm;
import repositories.ProjectRepository;
import security.Authority;

@Service
@Transactional
public class ProjectService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ProjectRepository projectRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private UserService userService;

	// Constructors -----------------------------------------------------------

	public ProjectService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Project findOne(int id) {
		Project result;
		result = projectRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public void delete(Project project) {
		checkByPrincipal(project);
		Assert.notNull(project);
		Assert.isTrue(project.getRegistrations().isEmpty());
		projectRepository.delete(project);
	}

	public void saveFollow(Project project) {
		Assert.notNull(project);
		projectRepository.save(project);
	}
	
	public Project create() {
		Project project;
		project = new Project();
		project.setCreationMoment(new Date());
		
		return project;
	}

	public void save(Project project) {
		Date now = new Date(System.currentTimeMillis() - 1);
		Assert.notNull(project);
		checkByPrincipal(project);
		if (project.getId() == 0) {
			project.setCreationMoment(now);
		}
		Assert.isTrue(project.getStartMoment().after(now)
				&& project.getEndMoment().after(project.getStartMoment()));
		Assert.isTrue(project.getRegistrations().isEmpty());
		Assert.isTrue(project.getState().equals(StateEnum.Recruiting));

		projectRepository.save(project);
	}

	// Other business methods -------------------------------------------------

	public Project reconstruct(ProjectForm projectForm) {
		Project project;
		if(projectForm.getId()==0){
			project = new Project();
			project.setCreator(userService.findByPrincipal());
			UUID uuid = UUID.randomUUID();
			String referenceNumber = uuid.toString().replace("-", "_");
			project.setReferenceNumber(referenceNumber);
			project.setState(StateEnum.Recruiting);
			
		}else{
			project = findOne(projectForm.getId());
		}
		
		project.setDescription(projectForm.getDescription());
		project.setEndMoment(projectForm.getEndMoment());
		project.setGoal(projectForm.getGoal());
		project.setId(projectForm.getId());
		project.setStartMoment(projectForm.getStartMoment());
		project.setTitle(projectForm.getTitle());
		project.setVersion(projectForm.getVersion());
		
		return project;
	}

	// Free Services. FR-1
	// List the projects managed by Acme Projects, Inc. as long as they are not
	// finished. The system must show the title of every project, its creation
	// date, and whether new participants can join them or not.
	public Collection<Project> findAllActive() {
		Collection<Project> projects;
		projects = projectRepository.findAllActive();
		return projects;
	}

	// Free Services. FR-9
	// List the projects in which he or she can participate. These projects are
	// those that have at least one vacancy for the group to which the user
	// belongs.
	public Collection<Project> findAllVacancyByGroup() {
		checkUserRole();
		Collection<Project> projects;
		Collection<Project> projectsAllJoined;
		User user = userService.findByPrincipal();
		int groupId = user.getGroup().getId();
		projects = projectRepository.findAllVacancyByGroup(groupId);
		projectsAllJoined = projectRepository.findAllJoined(user.getId());
		projects.removeAll(projectsAllJoined);
		return projects;
	}

	// Free Services. FR-11
	// List the projects in which he or she participates.
	public Collection<Project> findAllMyJoined() {
		checkUserRole();
		Collection<Project> projects;
		User user = userService.findByPrincipal();
		projects = projectRepository.findAllJoined(user.getId());
		return projects;
	}

	// Free Services. FR-14
	// List the projects in which he or she participates.
	public Collection<Project> findAllMyCreated() {
		checkUserRole();
		Collection<Project> projects;
		User user = userService.findByPrincipal();
		projects = projectRepository.findAllCreated(user.getId());
		return projects;
	}

	// Gold Services. FR-19
	// Show a stream with the status updates of the projects he or she follows.
	// For each status update it should show its moment, the amount of work
	// done,
	// its description and the project to which the status update belong.
	public Collection<Project> findAllMyFollowed() {
		checkUserRole();
		Collection<Project> projects;
		User user = userService.findByPrincipal();
		projects = user.getFollowedProjects();
		return projects;
	}
	
	// Ancillary methods ------------------------------------------------------

	public void checkUserRole() {
		Collection<Authority> authorities = 
				userService.findByPrincipal().getUserAccount().getAuthorities();
		Authority userAuthority = new Authority();
		userAuthority.setAuthority(Authority.USER);
		Assert.isTrue(authorities.contains(userAuthority));
	}

	// Joined user's number of project.
	public long participantsOfProject(int projectId) {
		return projectRepository.participantsOfProject(projectId);
	}

	// Number of people that requires a project.
	public Long numberOfPeopleRequiredByProject(int projectId) {
		return projectRepository.numberOfPeopleRequiredByProject(projectId);
	}
	
	public void changeToStarted(Project project) {
		Long peopleRequired = numberOfPeopleRequiredByProject(project.getId());
		long peopleJoined = participantsOfProject(project.getId());
		if (peopleRequired == peopleJoined) {
			project.setState(StateEnum.Started);
			projectRepository.save(project);
		}
	}


	private void checkByPrincipal(Project project) {
		Assert.isTrue(userService.findByPrincipal()
				.equals(project.getCreator()));
	}

	public void updateWork(StatusUpdate statusUpdate) {
		Project project = statusUpdate.getRegistration().getProject();
		project.setTotalWork(project.getTotalWork()
				+ statusUpdate.getWorkAmount());
		projectRepository.save(project);

	}

	public void finish(int projectId) {
		Project project = findOne(projectId);
		checkByPrincipal(project);
		Assert.isTrue(project.getState().equals(StateEnum.Started));
		project.setState(StateEnum.Finished);
		projectRepository.save(project);
	}

	public void follow(int projectId, Boolean follow) {
		User user = userService.findByPrincipal();
		Project project = findOne(projectId);

		if (follow) {
			Assert.isTrue(!user.getFollowedProjects().contains(project));
			Assert.isTrue(!project.getFollowers().contains(user));
		} else {
			Assert.isTrue(user.getFollowedProjects().contains(project));
			Assert.isTrue(project.getFollowers().contains(user));
		}
		Assert.isTrue(!project.getState().equals("Finished"));
		
		Collection<Project> followedProjects = new HashSet<Project>(user.getFollowedProjects());
		Collection<User> followers = new HashSet<User>(project.getFollowers());
		
		if (follow) {
			followedProjects.add(project);
			followers.add(user);
		} else {
			followedProjects.remove(project);
			followers.remove(user);
		}
		user.setFollowedProjects(followedProjects);
		project.setFollowers(followers);
		
		saveFollow(project);
		userService.save(user);
	}
	
	// If one project is followed by user.
	public Boolean isFollowed(int projectId) {
		Boolean res;
		User user = userService.findByPrincipal();
		Project project = findOne(projectId);
		if (user.getFollowedProjects().contains(project)
				&& project.getFollowers().contains(user))
			res = true;
		else
			res = false;
		return res;
	}

	public Boolean iCanJoin(int projectId) {
		boolean res;
		Project project = findOne(projectId);
		res = findAllVacancyByGroup().contains(project);
		return res;
	}
	
	public Boolean iAmJoined(int projectId) {
		Boolean res = true;
		User user = userService.findByPrincipal();
		Registration registration = 
				registrationService.findByUserAndProject(user.getId(), projectId);
		if (registration == null)
			res = false;
		return res;
	}

	
}
