package domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Schedule extends DomainEntity {

	private DayOfWeek day;
	private Date startTime;
	private Date endTime;

	private Clinic clinic;
	private Doctor doctor;
	private Collection<Appointment> appointments;

	public Schedule() {
		super();
		appointments = new HashSet<Appointment>();
	}
	
	public enum DayOfWeek {
		Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday;
		
		public String getCode(){
			String res;
			switch (this){
			case Monday:
				res = "DayOfWeek.Monday";
				break;
			case Tuesday:
				res = "DayOfWeek.Tuesday";
				break;
			case Wednesday:
				res = "DayOfWeek.Wednesday";
				break;
			case Thursday:
				res = "DayOfWeek.Thursday";
				break;
			case Friday:
				res = "DayOfWeek.Friday";
				break;
			case Saturday:
				res = "DayOfWeek.Saturday";
				break;
			default:
				return "DayOfWeek.Sunday";
			}
			return res;
		}
	}

	@NotNull
	@Valid
	@Enumerated(EnumType.STRING)
	public DayOfWeek getDay() {
		return day;
	}

	public void setDay(DayOfWeek day) {
		this.day = day;
	}
	
	@NotNull
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@NotNull
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	@Valid
	@OneToMany(mappedBy = "schedule")
	public Collection<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Collection<Appointment> appointments) {
		this.appointments = appointments;
	}

}
