package domain;

import javax.persistence.Access;

import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class AssessmentRoute extends DomainEntity {

	private Assessment assessment;
	
	private Participant participant;
	private Route route;

	public AssessmentRoute() {
		super();
	}

	@Valid
	public Assessment getAssessment() {
		return assessment;
	}

	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

}