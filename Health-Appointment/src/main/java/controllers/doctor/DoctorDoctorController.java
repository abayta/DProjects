package controllers.doctor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Doctor;

import services.DoctorService;

@Controller
@RequestMapping("/doctor/doctor")
public class DoctorDoctorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private DoctorService doctorService;

	// Constructors -----------------------------------------------------------

	public DoctorDoctorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-available", method = RequestMethod.GET)
	public ModelAndView listAvailable() {
		Collection<Doctor> doctors = doctorService.findAll();

		ModelAndView result = new ModelAndView("doctor/list-available");
		result.addObject("doctors", doctors);
		result.addObject("ret", 0);
		result.addObject("requestURI", "doctor/doctor/list-available.do");

		return result;
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int doctorId, String ret) {
		Doctor doctor = doctorService.findOne(doctorId);

		ModelAndView res = new ModelAndView("doctor/details");
		res.addObject("doctor", doctor);
		res.addObject("ret", redirectCancel(ret));
		res.addObject("retNum", ret);
		res.addObject("requestURI", "doctor/doctor/details.do");

		return res;
	}
	
	// Ancillary methods ------------------------------------------------------

	public String redirectCancel(String ret) {
		String res = null;

		switch (ret) {
		case "0":
			res = "doctor/doctor/list-available.do";
			break;
		default:
			res = null;
		}
		return res;
	}

}
