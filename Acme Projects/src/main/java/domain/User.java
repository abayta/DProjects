package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	private Group group;
	private Collection<Registration> registrations;
	private Collection<Project> createdProjects;
	private Collection<Project> followedProjects;
	
	public User() {
		super();
		registrations = new HashSet<Registration>();
		createdProjects = new HashSet<Project>();
		followedProjects = new HashSet<Project>();
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@NotNull
	@OneToMany(mappedBy = "user")
	public Collection<Registration> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(Collection<Registration> registrations) {
		this.registrations = registrations;
	}

	@NotNull
	@OneToMany(mappedBy = "creator")
	public Collection<Project> getCreatedProjects() {
		return createdProjects;
	}

	public void setCreatedProjects(Collection<Project> createdProjects) {
		this.createdProjects = createdProjects;
	}

	@NotNull
	@Valid
	@ManyToMany(mappedBy = "followers")
	public Collection<Project> getFollowedProjects() {
		return followedProjects;
	}

	public void setFollowedProjects(Collection<Project> followedProjects) {
		this.followedProjects = followedProjects;
	}

}
