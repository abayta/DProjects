package domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {@Index(columnList = "state")},
	   uniqueConstraints = {@UniqueConstraint(columnNames = "referenceNumber")})
public class Project extends DomainEntity {

	private String referenceNumber;
	private String title;
	private String goal;
	private String description;
	private Date creationMoment;
	private Date startMoment;
	private Date endMoment;
	private StateEnum state;
	private double totalWork;
	
	private Collection<Requirement> requirements;
	private Collection<Registration> registrations;
	private User creator;
	private Collection<User> followers;
	
	public enum StateEnum{
		Recruiting, Started, Finished;
		
		public String getCode(){
			switch (this){
				case Recruiting: 
					return "project.state.Recruiting";
				case Started: 
					return "project.state.Started";
				default: 
					return "project.state.Finished";
			}
		}
	}
	
	public Project() {
		super();
		requirements = new HashSet<Requirement>();
		registrations = new HashSet<Registration>();
		followers = new HashSet<User>();
	}

	@NotBlank
	@Pattern(regexp = "^[A-Za-z0-9//_]+$")
	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotBlank
	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}
	
	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCreationMoment() {
		return creationMoment;
	}

	public void setCreationMoment(Date creationMoment) {
		this.creationMoment = creationMoment;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartMoment() {
		return startMoment;
	}

	public void setStartMoment(Date startMoment) {
		this.startMoment = startMoment;
	}
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndMoment() {
		return endMoment;
	}

	public void setEndMoment(Date endMoment) {
		this.endMoment = endMoment;
	}

	@NotNull
	@Valid
	@Enumerated(EnumType.STRING)
	public StateEnum getState() {
		return state;
	}

	public void setState(StateEnum state) {
		this.state = state;
	}

	@Min(0)
	public double getTotalWork() {
		return totalWork;
	}

	public void setTotalWork(double totalWork) {
		this.totalWork = totalWork;
	}

	@NotNull
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	public Collection<Requirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(Collection<Requirement> requirements) {
		this.requirements = requirements;
	}

	@NotNull
	@OneToMany(mappedBy = "project")
	public Collection<Registration> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(Collection<Registration> registrations) {
		this.registrations = registrations;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	@Valid
	@ManyToMany
	public Collection<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Collection<User> followers) {
		this.followers = followers;
	}

}
