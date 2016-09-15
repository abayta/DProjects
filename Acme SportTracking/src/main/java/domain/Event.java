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
import javax.persistence.Table;
import javax.persistence.Index;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
		@Index(columnList = "startMoment"), 
		@Index(columnList="finishMoment")},uniqueConstraints = { 
		@UniqueConstraint(columnNames = "referenceNumber")})


public class Event extends DomainEntity {

	private String referenceNumber;
	private String title;
	private Double fee;
	private Date creationMoment;
	private Date startMoment;
	private Date finishMoment;
	private String description;
	private Double rating;
	
	private Administrator administrator;
	private Collection<Registration> registrations;
	private Collection<Route> routes;
	private Collection<Challenge> challenges;

	public Event() {
		super();
		registrations = new HashSet<Registration>();
		routes = new HashSet<Route>();
		challenges = new HashSet<Challenge>();
	}

	@NotBlank
	@Pattern(regexp = "^[A-Za-z0-9]+$")
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

	@NotNull
	@Min(0)
	@Digits(integer = 5, fraction = 2)
	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
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
	public Date getFinishMoment() {
		return finishMoment;
	}

	public void setFinishMoment(Date finishMoment) {
		this.finishMoment = finishMoment;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Range(min = 0, max = 100)
	@Digits(integer = 3, fraction = 2)
	public Double getRating() {
	//REVISAR ESTE RATING
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Administrator getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
	}
	
	@NotNull
	@OneToMany(mappedBy = "event")
	public Collection<Registration> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(Collection<Registration> registrations) {
		this.registrations = registrations;
	}

	@NotNull
	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
	public Collection<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(Collection<Route> routes) {
		this.routes = routes;
	}

	@NotNull
	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
	public Collection<Challenge> getChallenges() {
		return challenges;
	}

	public void setChallenges(Collection<Challenge> challenges) {
		this.challenges = challenges;
	}
	
}
