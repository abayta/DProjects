package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Patient extends Actor {
	
	private Collection<Appointment> appointments;
	private ClinicalHistory clinicalHistory;
	private HealthInsuranceCard healthInsuranceCard;
	
	public Patient() {
		super();
		appointments = new HashSet<Appointment>();
	}
	
	@NotNull
	@Valid
	public HealthInsuranceCard getHealthInsuranceCard() {
		return healthInsuranceCard;
	}


	public void setHealthInsuranceCard(HealthInsuranceCard healthInsuranceCard) {
		this.healthInsuranceCard = healthInsuranceCard;
	}

	@Valid
	@OneToMany(mappedBy="patient")
	public Collection<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Collection<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional=false)
	public ClinicalHistory getClinicalHistory() {
		return clinicalHistory;
	}

	public void setClinicalHistory(ClinicalHistory clinicalHistory) {
		this.clinicalHistory = clinicalHistory;
	}
	
}
