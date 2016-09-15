package controllers.doctor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Appointment;

import services.AppointmentService;
import services.DoctorService;

@Controller
@RequestMapping("/appointment/doctor")
public class AppointmentDoctorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private DoctorService doctorService;

	// Constructors -----------------------------------------------------------

	public AppointmentDoctorController() {
		super();
	}

	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value = "/list-available", method = RequestMethod.GET)
	public ModelAndView listAvailable() {
		int doctorId = doctorService.findByPrincipal().getId();
		Collection<Appointment> appointments = appointmentService.findAllActiveByDoctor(doctorId);

		ModelAndView result = new ModelAndView("appointment/list-available");
		result.addObject("appointments", appointments);
		result.addObject("requestURI", "appointment/doctor/list-available.do");

		return result;
	}

	@RequestMapping(value = "/list-not-available", method = RequestMethod.GET)
	public ModelAndView listNotAvailable() {
		int doctorId = doctorService.findByPrincipal().getId();
		Collection<Appointment> appointments = appointmentService.findAllNotActiveByDoctor(doctorId);

		ModelAndView result = new ModelAndView("appointment/list-not-available");
		result.addObject("appointments", appointments);
		result.addObject("requestURI", "appointment/doctor/list-not-available.do");

		return result;
	}
	
	@RequestMapping(value = "/list-available-today", method = RequestMethod.GET)
	public ModelAndView listTodayvailable() {
		int doctorId = doctorService.findByPrincipal().getId();
		Collection<Appointment> appointments = appointmentService.findAllTodayActiveByDoctor(doctorId);

		ModelAndView result = new ModelAndView("appointment/list-available-today");
		result.addObject("appointments", appointments);
		result.addObject("requestURI", "appointment/doctor/list-available-today.do");

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------

}
