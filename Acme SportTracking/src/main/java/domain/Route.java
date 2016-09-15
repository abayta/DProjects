package domain;

import java.util.Collection;

import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Route extends DomainEntity {

	private String name;
	private double length;
	private int difficulty;
	private int orderNumber;
	private Double rating;
	
	private Event event;
	private Collection<AssessmentRoute> assessmentsRoutes;

	public Route() {
		super();
		assessmentsRoutes = new HashSet<AssessmentRoute>();
	}

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Min(0)
	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	@Range(min = 0, max = 100)
	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	@Min(1)
	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
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
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@NotNull
	@OneToMany(mappedBy="route")
	public Collection<AssessmentRoute> getAssessmentsRoutes() {
		return assessmentsRoutes;
	}

	public void setAssessmentsRoutes(Collection<AssessmentRoute> assessmentsRoutes) {
		this.assessmentsRoutes = assessmentsRoutes;
	}
	
}
