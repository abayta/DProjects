package controllers.doctor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Patient;

import services.PatientService;

@Controller
@RequestMapping("/patient/doctor")
public class PatientDoctorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PatientService patientService;

	// Constructors -----------------------------------------------------------

	public PatientDoctorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-available", method = RequestMethod.GET)
	public ModelAndView listAvailable() {
	    Collection<Patient> patients = patientService.findAll();

		ModelAndView result = new ModelAndView("patient/list-available");
		result.addObject("patients", patients);
		result.addObject("requestURI", "patient/doctor/list-available.do");

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	
}
