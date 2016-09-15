package controllers.doctor;

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

import domain.Prescription;
import domain.Specialty;

import services.PrescriptionService;

@Controller
@RequestMapping("/prescription/doctor")
public class PrescriptionDoctorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PrescriptionService prescriptionService;
	
	// Constructors -----------------------------------------------------------

	public PrescriptionDoctorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-available", method = RequestMethod.GET)
	public ModelAndView listAvailableByMedicalReport(@RequestParam int medicalReportId) {
		
		Collection<Prescription> prescriptions = prescriptionService.findByMedicalReportViewDoctor(medicalReportId);

		ModelAndView result = new ModelAndView("prescription/list-available");
		result.addObject("prescriptions", prescriptions);
		result.addObject("requestURI", "prescription/doctor/list-available.do");

		return result;
	}
	
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int medicalReportId) {
		Prescription prescription = prescriptionService.create(medicalReportId);
		ModelAndView result = createEditModelAndView(prescription);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Prescription prescription,
			BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors()) {
			result = createEditModelAndView(prescription);
		} else {
			try {
				int id = prescription.getMedicalReport().getAppointment().getId();
				prescriptionService.save(prescription);
				result = new ModelAndView("redirect:../../medicalReport/doctor/details.do?appointmentId=" + id);
			} catch (Throwable oops) {
				result = createEditModelAndView(prescription,
						"prescription.commit.error");
			}
		}

		return result;
	}
		
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Prescription prescription) {
		assert prescription != null;

		return createEditModelAndView(prescription, null);
	}

	protected ModelAndView createEditModelAndView(Prescription prescription, String message) {
		assert prescription != null;
		ModelAndView res;

		res = new ModelAndView("prescription/edit");
		res.addObject("prescription",prescription);
		res.addObject("message", message);

		return res;
	}
	
}
