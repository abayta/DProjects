package controllers.patient;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.MedicalReport;
import domain.Patient;

import services.MedicalReportService;
import services.PatientService;

@Controller
@RequestMapping("/medicalReport/patient")
public class MedicalReportPatientController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private MedicalReportService medicalReportService;
	
	@Autowired
	private PatientService patientService;

	// Constructors -----------------------------------------------------------

	public MedicalReportPatientController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-available", method = RequestMethod.GET)
	public ModelAndView listAvailableByClinic() {
		
	    Patient patient = patientService.findByPrincipal();
		Collection<MedicalReport> medicalReports = medicalReportService.findByPatient(patient.getId());

		ModelAndView result = new ModelAndView("medicalReport/list-available");
		result.addObject("medicalReports", medicalReports);
		result.addObject("requestURI", "medicalReport/patient/list-available.do");

		return result;
	}
	
	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	// Ancillary methods ------------------------------------------------------

}
