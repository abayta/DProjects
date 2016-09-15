package controllers.patient;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Specialty;

import services.SpecialtyService;

@Controller
@RequestMapping("/specialty/patient")
public class SpecialtyPatientController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private SpecialtyService specialtyService;

	// Constructors -----------------------------------------------------------

	public SpecialtyPatientController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-available-by-clinic", method = RequestMethod.GET)
	public ModelAndView listAvailableByClinic(@RequestParam int clinicId) {
		Collection<Specialty> specialties = specialtyService.findAllByClinic(clinicId);

		ModelAndView result = new ModelAndView("specialty/list-available-by-clinic");
		result.addObject("specialties", specialties);
		result.addObject("clinicId", clinicId);
		result.addObject("requestURI", "specialty/patient/list-available-by-clinic.do");

		return result;
	}
	
	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	// Ancillary methods ------------------------------------------------------

}
