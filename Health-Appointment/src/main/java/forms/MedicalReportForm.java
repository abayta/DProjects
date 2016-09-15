package forms;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Appointment;
import domain.ClinicalHistory;
import domain.Prescription;
import domain.Test;

public class MedicalReportForm {

	private Integer id;
	private Integer version;
	private Date creationDate;
	private String description;

	private Appointment appointment;
	private ClinicalHistory clinicalHistory;
	private Collection<Prescription> prescriptions;
	private Collection<Test> tests;

	public MedicalReportForm() {
		super();
		prescriptions = new HashSet<Prescription>();
		tests = new HashSet<Test>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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
	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	@NotNull
	@Valid
	public ClinicalHistory getClinicalHistory() {
		return clinicalHistory;
	}

	public void setClinicalHistory(ClinicalHistory clinicalHistory) {
		this.clinicalHistory = clinicalHistory;
	}

	@Valid
	public Collection<Prescription> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(Collection<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	@Valid
	public Collection<Test> getTests() {
		return tests;
	}

	public void setTests(Collection<Test> tests) {
		this.tests = tests;
	}

}
