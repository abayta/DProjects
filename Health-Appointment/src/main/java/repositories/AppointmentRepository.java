package repositories;

import java.util.Date;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	// FR-23 (Doctor)
	// Ver las citas que aún no han tenido lugar, ordenadas ascendente por fecha.
	@Query("select a from Appointment a where a.schedule.doctor.id = ?1 and a.endMoment >= CURRENT_TIMESTAMP order by a.startMoment asc")
	Collection<Appointment> findAllActiveByDoctor(int doctorId);
	
	// FR-24 (Doctor)
	// Ver las citas que ya han transcurrido, ordenadas descendentemente por fecha.
	@Query("select a from Appointment a where a.schedule.doctor.id = ?1 and a.endMoment <= CURRENT_TIMESTAMP order by a.startMoment desc")
	Collection<Appointment> findAllNotActiveByDoctor(int doctorId);
	
	// (Doctor)
	// Ver las citas de hoy, ordenadas ascendente por fecha.
	@Query("select a from Appointment a where a.schedule.doctor.id = ?1 and SUBSTRING(a.startMoment, 1, 10) = SUBSTRING(current_timestamp, 1, 10) order by a.startMoment asc")
	Collection<Appointment> findAllTodayActiveByDoctor(int doctorId);

	// FR-38 (Patient)
	// Ver las citas que aún no han tenido lugar, ordenadas ascendente por fecha.
	@Query("select a from Appointment a where a.patient.id = ?1 and a.endMoment >= CURRENT_TIMESTAMP order by a.startMoment asc")
	Collection<Appointment> findAllActiveByPatient(int patientId);
	
	// FR-39 (Patient)
	// Ver las citas que ya han transcurrido, ordenadas descendentemente por fecha.
	@Query("select a from Appointment a where a.patient.id = ?1 and a.endMoment <= CURRENT_TIMESTAMP order by a.startMoment desc")
	Collection<Appointment> findAllNotActiveByPatient(int patientId);
	
	@Query("select a.startMoment from Appointment a where a.schedule.id=?1")
	Collection<Date> takenDateBySchedule(Integer scheduleId);
	
	@Query("select m.appointment from MedicalReport m where m.id = ?1")
	Appointment findByMedicalReport(int medicalReportId);

	@Query("select a from Appointment a where a.startMoment=?1 and a.schedule.id=?2")
	Collection<Appointment> savedAppointmentsByDateAndSchedule(Date startMoment, Integer scheduleId);

}
