package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ScheduleRepository;

import domain.Schedule;
import domain.Schedule.DayOfWeek;
import domain.Doctor;

@Service
@Transactional
public class ScheduleService {
	
	// Managed repository -----------------------------------------------------

	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private DoctorService doctorService;
	
	
	// Constructor ------------------------------------------------------------

	public ScheduleService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

		public Schedule create(){
		Schedule schedule = new Schedule();
		Doctor doctor = doctorService.findByPrincipal();
		schedule.setDoctor(doctor);
		
		return schedule;
	}
	
	public void save(Schedule schedule){
		
		Doctor doctor = doctorService.findByPrincipal();
		Assert.isTrue(schedule.getDoctor().equals(doctor));
		Assert.isTrue(schedule.getEndTime().after(schedule.getStartTime()));
		correctTime(schedule.getStartTime());
		correctTime(schedule.getEndTime());
		Assert.isTrue(!conflict(schedule));
		
		scheduleRepository.save(schedule);
		
	}
	
	public Collection<Schedule> findWithConflic(Integer doctorId,Date startMoment, Date endMoment, DayOfWeek day){
		Collection<Schedule> res = scheduleRepository.findWithConflic(doctorId, startMoment, endMoment, day);
		return res;
	}
	
	public Collection<Schedule> findAll(){
		return scheduleRepository.findAll();
	}
	
	public boolean conflict(Schedule schedule){
		boolean res;
		Collection<Schedule> schedules = findWithConflic(schedule.getDoctor().getId(), schedule.getStartTime(), schedule.getEndTime(), schedule.getDay());
		res = (schedules.isEmpty())?false:true;
		return res;
	}
	
	public void correctTime(Date date){
		DateFormat df = new SimpleDateFormat("mm");
		String format = df.format(date);
		Assert.isTrue(format.equals("00")||format.equals("15")||format.equals("30")||format.equals("45"));
	}
	
	public Schedule findOne(Integer calendarId){
		Schedule res = scheduleRepository.findOne(calendarId);
		Assert.notNull(res);
		return res;
	}
	
	public Collection<Schedule> findByDayAndDoctor(DayOfWeek day, Integer doctorId){
		Collection<Schedule> res = scheduleRepository.findByDayAndDoctor(day, doctorId);
		return res;
	}
	

	public Object[] visiteDays(Integer doctorId){
		Assert.notNull(doctorService.findOne(doctorId));
		Object[] objects = new Object[2];
		LinkedList<Schedule> lS = new LinkedList<Schedule>();
		Map<Schedule,Date> map = new HashMap<Schedule,Date>();
		Calendar now = Calendar.getInstance();
		Calendar ctrl = (Calendar) now.clone();
		ctrl.add(Calendar.DAY_OF_WEEK, 1);
		while(now.get(Calendar.DAY_OF_WEEK)!=ctrl.get(Calendar.DAY_OF_WEEK)){
			List<Schedule> schedules = new ArrayList<Schedule>(findByDayAndDoctor(numberDayToString(ctrl.get(Calendar.DAY_OF_WEEK)), doctorId));
			if(!schedules.isEmpty()){
				for(Schedule s:schedules){
					map.put(s, ctrl.getTime());
					lS.addLast(s);
				}
			}
			ctrl.add(Calendar.DAY_OF_MONTH, 1);
		}
		objects[0]=map;
		objects[1]=lS;

		return objects;
	}
	

/*	public Collection<String> generateSlots(Integer doctorId){

		LinkedList<String> res = new LinkedList<String>();
		Doctor doctor = doctorService.findOne(doctorId);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Calendar now = new GregorianCalendar();
		//now.add(Calendar.DAY_OF_WEEK, 1);
		DayOfWeek s = numberDay(now.get(Calendar.DAY_OF_WEEK));
		Collection<Schedule> schedules = findByDayAndDoctor(s, doctor.getId());
		for (Schedule schedule: schedules){
			Calendar c = Calendar.getInstance();
			c.setTime(now.getTime());
			c.set(Calendar.HOUR_OF_DAY, schedule.getStartTime());
			c.set(Calendar.MINUTE, 0);
			while(c.get(Calendar.HOUR_OF_DAY)<schedule.getEndTime()){
				res.addLast(sdf.format(c.getTime()));
				c.add(Calendar.MINUTE, 15);
			}
		}
		
		return res;
		
	}*/
	// Ancillary methods -----------------------------------0--------------------
	
	public DayOfWeek numberDayToString(int day){
		DayOfWeek res;
		switch (day) {
		case 1: res=DayOfWeek.Sunday; break;		
		case 2: res=DayOfWeek.Monday; break;
		case 3: res=DayOfWeek.Tuesday; break;
		case 4: res=DayOfWeek.Wednesday; break;
		case 5: res=DayOfWeek.Thursday; break;
		case 6: res=DayOfWeek.Friday; break;
		default: res=DayOfWeek.Saturday; break;
		}
		return res;
	}

}
