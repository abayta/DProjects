package domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class ClinicalHistory extends DomainEntity {

	private Date creationDate;

	private Patient patient;
	private Collection<MedicalReport> medicalReports;

	public ClinicalHistory() {
		super();
		medicalReports = new HashSet<MedicalReport>();
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

	@Valid
	@NotNull
	@OneToOne(optional = false,mappedBy="clinicalHistory")
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Valid
	@OneToMany(mappedBy = "clinicalHistory")
	public Collection<MedicalReport> getMedicalReports() {
		return medicalReports;
	}

	public void setMedicalReports(Collection<MedicalReport> medicalReports) {
		this.medicalReports = medicalReports;
	}

}
