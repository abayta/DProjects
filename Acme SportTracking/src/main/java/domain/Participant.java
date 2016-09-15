package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Participant extends Actor {

	private CreditCard creditCard;
	
	private Collection<Registration> registrations;
	private Collection<AssessmentRoute> assessmentsRoutes;

	public Participant() {
		super();
		registrations = new HashSet<Registration>();
		assessmentsRoutes = new HashSet<AssessmentRoute>();
	}

	@NotNull
	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@NotNull
	@OneToMany(mappedBy="participant")
	public Collection<Registration> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(Collection<Registration> registrations) {
		this.registrations = registrations;
	}
	
	@NotNull
	@OneToMany(mappedBy="participant")
	public Collection<AssessmentRoute> getAssessmentsRoutes() {
		return assessmentsRoutes;
	}

	public void setAssessmentsRoutes(Collection<AssessmentRoute> assessmentsRoutes) {
		this.assessmentsRoutes = assessmentsRoutes;
	}

}
