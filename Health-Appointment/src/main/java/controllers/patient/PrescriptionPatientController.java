package controllers.patient;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Prescription;

import services.PrescriptionService;

@Controller
@RequestMapping("/prescription/patient")
public class PrescriptionPatientController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PrescriptionService prescriptionService;
	
	// Constructors -----------------------------------------------------------

	public PrescriptionPatientController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-available", method = RequestMethod.GET)
	public ModelAndView listAvailableByMedicalReport(@RequestParam int medicalReportId) {
		
	   
		Collection<Prescription> prescriptions = prescriptionService.findByMedicalReport(medicalReportId);

		ModelAndView result = new ModelAndView("prescription/list-available");
		result.addObject("prescriptions", prescriptions);
		result.addObject("requestURI", "prescription/patient/list-available.do");

		return result;
	}
	
	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	// Ancillary methods ------------------------------------------------------

}
