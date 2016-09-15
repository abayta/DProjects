package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Schedule;
import domain.Schedule.DayOfWeek;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

	@Query("select s from Schedule s where s.day=?1 and s.doctor.id=?2 order by s.startTime")
	Collection<Schedule> findByDayAndDoctor(DayOfWeek day, Integer doctorId);
	
	@Query("select s from Schedule s where s.day=?4 and s.doctor.id=?1 and ((s.startTime>?2 and s.startTime<?3) or (s.startTime<=?2 and s.endTime>=?3) or (s.endTime>?2 and s.endTime<?3))")
	Collection<Schedule> findWithConflic(Integer doctorId,Date startMoment, Date endMoment, DayOfWeek day);

}
