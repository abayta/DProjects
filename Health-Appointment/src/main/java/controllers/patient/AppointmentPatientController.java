package controllers.patient;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Appointment;
import domain.Clinic;

import services.AppointmentService;

import services.PatientService;

@Controller
@RequestMapping("/appointment/patient")
public class AppointmentPatientController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private PatientService patientService;

	// Constructors -----------------------------------------------------------

	public AppointmentPatientController() {
		super();
	}
	// CRUD -------------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam Integer scheduleId){
		ModelAndView res;
		Appointment appointment;
		
		appointment = appointmentService.create(scheduleId);
		res = createEditModelAndView(appointment);
		
		return res;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Appointment appointment, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(appointment);
		} else {
			try {
				appointmentService.save(appointment);
				result = new ModelAndView("redirect:list-available.do");
			} catch (Throwable oops) {
					result = createEditModelAndView(appointment,
							"clinic.commit.error");
			}
		}

		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam Integer appointmentId) {
		ModelAndView result;

		try {
			Appointment appointment = appointmentService.findOne(appointmentId);
			appointmentService.delete(appointment);
			result = new ModelAndView("redirect:list-available.do");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:list-available.do");
		}

		return result;
	}
	
	private ModelAndView createEditModelAndView(Appointment appointment){
		return createEditModelAndView(appointment, null);
	}
	
	private ModelAndView createEditModelAndView(Appointment appointment, String message) {
		ModelAndView res;
		Collection<String> freeDates = appointmentService.appointmentDates(appointment.getSchedule().getId());
		
		res = new ModelAndView("appointment/create");
		res.addObject("appointment", appointment);
		res.addObject("freeDates", freeDates);
		res.addObject("message", message);
		return res;
	}
	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-available", method = RequestMethod.GET)
	public ModelAndView listAvailable() {
		int patientId = patientService.findByPrincipal().getId();
		Collection<Appointment> appointments = appointmentService.findAllActiveByPatient(patientId);

		ModelAndView result = new ModelAndView("appointment/list-available");
		result.addObject("appointments", appointments);
		result.addObject("requestURI", "appointment/patient/list-available.do");

		return result;
	}

	@RequestMapping(value = "/list-not-available", method = RequestMethod.GET)
	public ModelAndView listNotAvailable() {
		int patientId = patientService.findByPrincipal().getId();
		Collection<Appointment> appointments = appointmentService.findAllNotActiveByPatient(patientId);

		ModelAndView result = new ModelAndView("appointment/list-not-available");
		result.addObject("appointments", appointments);
		result.addObject("requestURI", "appointment/patient/list-not-available.do");

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------

}
