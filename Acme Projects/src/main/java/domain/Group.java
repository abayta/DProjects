package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "group_")
public class Group extends DomainEntity {

	private String name;
	private String description;
	
	private Collection<Requirement> requirements;
	private Collection<User> users;
	
	public Group() {
		super();
		requirements = new HashSet<Requirement>();
		users = new HashSet<User>();
	}

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull
	@OneToMany(mappedBy = "group")
	public Collection<Requirement> getRequirements() {
		return requirements;
	}
	
	public void setRequirements(Collection<Requirement> requirements) {
		this.requirements = requirements;
	}

	@NotNull
	@OneToMany(mappedBy = "group")
	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

}
