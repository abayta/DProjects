package domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Registration extends DomainEntity {

	private Date date;
	private double totalWork;
	
	private User user;
	private Project project;
	private Collection<StatusUpdate> statusUpdates;
	
	public Registration() {
		super();
		statusUpdates = new HashSet<StatusUpdate>();
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Min(0)
	public double getTotalWork() {
		return totalWork;
	}

	public void setTotalWork(double totalWork) {
		this.totalWork = totalWork;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@NotNull
	@OneToMany(mappedBy = "registration", cascade = CascadeType.ALL)
	public Collection<StatusUpdate> getStatusUpdates() {
		return statusUpdates;
	}

	public void setStatusUpdates(Collection<StatusUpdate> statusUpdates) {
		this.statusUpdates = statusUpdates;
	}
	
}
