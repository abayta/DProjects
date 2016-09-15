package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Doctor extends Actor {

	private Double rating;

	private Specialty specialty;
	private Collection<Schedule> schedules;
	private Collection<Comment> comments;

	public Doctor() {
		super();
		schedules = new HashSet<Schedule>();
		comments = new HashSet<Comment>();
	}

	@Range(min = 0, max = 10)
	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	@Valid
	@OneToMany(mappedBy = "doctor")
	public Collection<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(Collection<Schedule> schedules) {
		this.schedules = schedules;
	}

	@Valid
	@OneToMany(mappedBy = "doctor")
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

}
