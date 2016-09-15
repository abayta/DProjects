package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Specialty extends DomainEntity {

	private String title;
	private String description;

	private Collection<Doctor> doctors;

	public Specialty() {
		super();
		doctors = new HashSet<Doctor>();
	}

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Valid
	@OneToMany(mappedBy="specialty")
	public Collection<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(Collection<Doctor> doctors) {
		this.doctors = doctors;
	}
	
	public void addDoctor(Doctor doctor) {
		doctors.add(doctor);
		doctor.setSpecialty(this);
	}

	public void removeDoctor(Doctor doctor) {
		doctors.remove(doctor);
		doctor.setSpecialty(null);
	}

}
