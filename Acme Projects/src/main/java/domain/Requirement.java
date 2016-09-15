package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Requirement extends DomainEntity {

	private int peopleNumber;
	private int freePlaces;

	private Group group;
	private Project project;

	public Requirement() {
		super();
	}

	@Min(1)
	public int getPeopleNumber() {
		return peopleNumber;
	}

	public void setPeopleNumber(int peopleNumber) {
		this.peopleNumber = peopleNumber;
	}
	
	@Min(0)
	public int getFreePlaces() {
		return freePlaces;
	}

	public void setFreePlaces(int freePlaces) {
		this.freePlaces = freePlaces;
	}
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Project getProject() {
		return project;
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
	
	public void setProject(Project project) {
		this.project = project;
	}

}
