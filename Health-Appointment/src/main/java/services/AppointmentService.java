package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Appointment;
import domain.Doctor;
import domain.Patient;
import domain.Schedule;
import domain.Schedule.DayOfWeek;

import repositories.AppointmentRepository;

@Service
@Transactional
public class AppointmentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private PatientService patientService;

	// Constructor ------------------------------------------------------------

	public AppointmentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Appointment create(Integer scheduleId) {
		Schedule schedule = scheduleService.findOne(scheduleId);
		Appointment res = new Appointment();
		res.setPatient(patientService.findByPrincipal());
		res.setSchedule(schedule);
		res.setEndMoment(new Date());
		return res;
	}
	
	public void save(Appointment appointment) {
		checkByPrincipalPatient(appointment);
		
		Calendar c = Calendar.getInstance();
		c.setTime(appointment.getStartMoment());
		c.add(Calendar.MINUTE, 15);
		appointment.setEndMoment(c.getTime());
		checkCorrectDate(appointment, c);
		
		Collection<Appointment> savedAppointments = appointmentRepository.savedAppointmentsByDateAndSchedule(appointment.getStartMoment(),appointment.getSchedule().getId());
		Assert.isTrue(savedAppointments.isEmpty());
		appointmentRepository.save(appointment);
	}
	
	public void delete (Appointment appointment){
		Assert.isTrue(appointment.getStartMoment().after(new Date()));
		checkByPrincipalPatient(appointment);
		appointmentRepository.delete(appointment);
	}

	public List<String> appointmentDates(Integer scheduleId) {
		LinkedList<String> res = new LinkedList<String>();
		Schedule schedule = scheduleService.findOne(scheduleId);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Calendar start = startCalendar(schedule);
		Calendar end = endCalendar(start, schedule);
		while (end.after(start)) {
			res.addLast(sdf.format(start.getTime()));
			start.add(Calendar.MINUTE, 15);
		}
		Collection<String> rmv = takenDateBySchedule(scheduleId);
		res.removeAll(rmv);
		return res;
	}
	
	public Collection<String> takenDateBySchedule(Integer scheduleId){
		List<String> res = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Collection<Date> dates = appointmentRepository.takenDateBySchedule(scheduleId);
		for(Date d: dates){
			res.add(sdf.format(d));
		}
		return res;
	}

	public Appointment findOne(Integer appointmentId) {
		Appointment res = appointmentRepository.findOne(appointmentId);
		Assert.notNull(res);
		return res;
	}

	public Collection<Appointment> findAllActiveByDoctor(int doctorId) {
		return appointmentRepository.findAllActiveByDoctor(doctorId);
	}

	public Collection<Appointment> findAllNotActiveByDoctor(int doctorId) {
		return appointmentRepository.findAllNotActiveByDoctor(doctorId);
	}

	public Collection<Appointment> findAllTodayActiveByDoctor(int doctorId) {
		return appointmentRepository.findAllTodayActiveByDoctor(doctorId);
	}

	public Collection<Appointment> findAllActiveByPatient(int patientId) {
		return appointmentRepository.findAllActiveByPatient(patientId);
	}

	public Collection<Appointment> findAllNotActiveByPatient(int patientId) {
		return appointmentRepository.findAllNotActiveByPatient(patientId);
	}

	public Appointment findByMedicalReport(int medicalReportId){
		return appointmentRepository.findByMedicalReport(medicalReportId);
	}
	
	// Ancillary methods -------------------------------------------------------

	public void checkBelongsToDoctor(int appointmentId) {
		Doctor doctor = doctorService.findByPrincipal();
		Collection<Appointment> appointments = findAllTodayActiveByDoctor(doctor
				.getId());
		Assert.isTrue(appointments.contains(findOne(appointmentId)));
	}
	
	public void checkByPrincipalPatient(Appointment appointment){
		Patient patient = patientService.findByPrincipal();
		Assert.isTrue(patient.equals(appointment.getPatient()));
	}
	
	public void checkCorrectDate(Appointment appointment, Calendar startMoment){
		Assert.isTrue(startMoment.get(Calendar.MINUTE)==00||startMoment.get(Calendar.MINUTE)==15||
				startMoment.get(Calendar.MINUTE)==30||startMoment.get(Calendar.MINUTE)==45);
		Calendar before = Calendar.getInstance();
		before.setTime(appointment.getSchedule().getStartTime());
		before.set(Calendar.YEAR, startMoment.get(Calendar.YEAR));
		before.set(Calendar.MONTH, startMoment.get(Calendar.MONTH));
		before.set(Calendar.DAY_OF_MONTH, startMoment.get(Calendar.DAY_OF_MONTH));
		
		Calendar after = (Calendar) startMoment.clone();
		after.set(Calendar.YEAR, startMoment.get(Calendar.YEAR));
		after.set(Calendar.MONTH, startMoment.get(Calendar.MONTH));
		after.set(Calendar.DAY_OF_MONTH, startMoment.get(Calendar.DAY_OF_MONTH));
		Assert.isTrue(startMoment.after(before)||after.after(startMoment));
		Assert.isTrue(dayTonumber(appointment.getSchedule().getDay())==startMoment.get(Calendar.DAY_OF_WEEK));
	}

	public int dayTonumber(DayOfWeek day) {
		int res;
		switch (day) {
		case Sunday:
			res = 1;
			break;
		case Monday:
			res = 2;
			break;
		case Tuesday:
			res = 3;
			break;
		case Wednesday:
			res = 4;
			break;
		case Thursday:
			res = 5;
			break;
		case Friday:
			res = 6;
			break;
		default:
			res = 7;
			break;
		}
		return res;
	}

	public Calendar endCalendar(Calendar start, Schedule s) {
		Calendar res = (Calendar) start.clone();
		Calendar aux = Calendar.getInstance();
		aux.setTime(s.getEndTime());

		res.set(Calendar.HOUR_OF_DAY, aux.get(Calendar.HOUR_OF_DAY));
		res.set(Calendar.MINUTE, aux.get(Calendar.MINUTE));
		res.set(Calendar.SECOND, 0);

		return res;
	}

	public Calendar startCalendar(Schedule s) {
		Calendar date = Calendar.getInstance();
		while (date.get(Calendar.DAY_OF_WEEK) != dayTonumber(s.getDay())) {
			date.add(Calendar.DAY_OF_WEEK, 1);
		}
		Calendar aux = Calendar.getInstance();
		aux.setTime(s.getStartTime());

		date.set(Calendar.HOUR_OF_DAY, aux.get(Calendar.HOUR_OF_DAY));
		date.set(Calendar.MINUTE, aux.get(Calendar.MINUTE));
		date.set(Calendar.SECOND, 0);

		return date;
	}

	public Collection<Appointment> findAll() {
		// TODO Auto-generated method stub
		return appointmentRepository.findAll();
	}

	

}
