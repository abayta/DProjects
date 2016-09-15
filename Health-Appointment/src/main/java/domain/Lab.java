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
public class Lab extends DomainEntity {

	private String name;
	private String address;

	private Collection<LabTechnician> labTechnicians;
	private Collection<Test> tests;

	public Lab() {
		super();
		labTechnicians = new HashSet<LabTechnician>();
		tests = new HashSet<Test>();
	}

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Valid
	@OneToMany(mappedBy = "lab")
	public Collection<LabTechnician> getLabTechnicians() {
		return labTechnicians;
	}

	public void setLabTechnicians(Collection<LabTechnician> labTechnicians) {
		this.labTechnicians = labTechnicians;
	}
	
	@Valid
	@OneToMany(mappedBy = "lab")
	public Collection<Test> getTests() {
		return tests;
	}

	public void setTests(Collection<Test> tests) {
		this.tests = tests;
	}

}
