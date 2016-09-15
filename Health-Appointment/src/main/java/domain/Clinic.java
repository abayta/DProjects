package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Clinic extends DomainEntity {

	private String name;
	private String address;
	private String phone;
	private String email;
	private byte[] logo;
	private boolean errorLogo;

	private Collection<Schedule> schedules;

	public Clinic() {
		super();
		schedules = new HashSet<Schedule>();
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

	@NotBlank
	@Pattern(regexp = "^[0-9]*$")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Lob
	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	@Transient
	public boolean isErrorLogo() {
		errorLogo = (getLogo() == null || getLogo().length == 0);
		return errorLogo;
	}

	public void setErrorLogo(boolean errorLogo) {
		this.errorLogo = errorLogo;
	}

	@Valid
	@OneToMany(mappedBy = "clinic")
	public Collection<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(Collection<Schedule> schedules) {
		this.schedules = schedules;
	}

}
