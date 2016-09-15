package domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class MedicalReport extends DomainEntity {

	private Date creationDate;
	private String description;

	private Appointment appointment;
	private ClinicalHistory clinicalHistory;
	private Collection<Prescription> prescriptions;
	private Collection<Test> tests;

	public MedicalReport() {
		super();
		prescriptions = new HashSet<Prescription>();
		tests = new HashSet<Test>();
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull
	@Valid
	@OneToOne(optional = false)
	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public ClinicalHistory getClinicalHistory() {
		return clinicalHistory;
	}

	public void setClinicalHistory(ClinicalHistory clinicalHistory) {
		this.clinicalHistory = clinicalHistory;
	}

	@Valid
	@OneToMany(mappedBy = "medicalReport")
	public Collection<Prescription> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(Collection<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	@Valid
	@OneToMany(mappedBy = "medicalReport")
	public Collection<Test> getTests() {
		return tests;
	}

	public void setTests(Collection<Test> tests) {
		this.tests = tests;
	}

}
