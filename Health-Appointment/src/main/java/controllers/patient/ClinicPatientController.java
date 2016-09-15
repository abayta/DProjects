package controllers.patient;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Clinic;

import services.ClinicService;

@Controller
@RequestMapping("/clinic/patient")
public class ClinicPatientController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ClinicService clinicService;

	// Constructors -----------------------------------------------------------

	public ClinicPatientController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-available", method = RequestMethod.GET)
	public ModelAndView listAvailable() {
		Collection<Clinic> clinics = clinicService.findAll();

		ModelAndView result = new ModelAndView("clinic/list-available");
		result.addObject("clinics", clinics);
		result.addObject("ret", 0);
		result.addObject("requestURI", "clinic/patient/list-available.do");

		return result;
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int clinicId, String ret) {
		Clinic clinic = clinicService.findOne(clinicId);

		ModelAndView res = new ModelAndView("clinic/details");
		res.addObject("clinic", clinic);
		res.addObject("ret", redirectCancel(ret));
		res.addObject("retNum", ret);
		res.addObject("requestURI", "clinic/patient/details.do");

		return res;
	}
	
	// Ancillary methods ------------------------------------------------------

	public String redirectCancel(String ret) {
		String res = null;

		switch (ret) {
		case "0":
			res = "clinic/patient/list-available.do";
			break;
		default:
			res = null;
		}
		return res;
	}

}
