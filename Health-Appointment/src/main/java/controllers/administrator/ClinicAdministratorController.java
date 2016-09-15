package controllers.administrator;

import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import domain.Clinic;

import services.ClinicService;

@Controller
@RequestMapping("/clinic/administrator")
public class ClinicAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ClinicService clinicService;

	// Constructors -----------------------------------------------------------

	public ClinicAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-available", method = RequestMethod.GET)
	public ModelAndView listAvailable() {
		Collection<Clinic> clinics = clinicService.findAll();

		ModelAndView result = new ModelAndView("clinic/list-available");
		result.addObject("clinics", clinics);
		result.addObject("ret", 0);
		result.addObject("requestURI", "clinic/administrator/list-available.do");

		return result;
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int clinicId, String ret) {
		Clinic clinic = clinicService.findOne(clinicId);

		ModelAndView res = new ModelAndView("clinic/details");
		res.addObject("clinic", clinic);
		res.addObject("ret", redirectCancel(ret));
		res.addObject("retNum", ret);
		res.addObject("requestURI", "clinic/administrator/details.do");

		return res;
	}
	
	// Creation ---------------------------------------------------------------

		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			Clinic clinic;

			clinic = clinicService.create();
			result = createEditModelAndView(clinic);

			return result;
		}

		// Edition ----------------------------------------------------------------

		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int clinicId) {
			ModelAndView result;
			Clinic clinic;

			clinic = clinicService.findOne(clinicId);
			result = createEditModelAndView(clinic);

			return result;
		}

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Clinic clinic, BindingResult binding) {
			ModelAndView result;

			if (binding.hasErrors()) {
				result = createEditModelAndView(clinic);
			} else {
				try {
					clinicService.save(clinic);
					result = new ModelAndView("redirect:list-available.do");
				} catch (Throwable oops) {
						result = createEditModelAndView(clinic,
								"clinic.commit.error");
				}
			}

			return result;
		}

		// Ancillary Methods -------------------------------------------------------

		protected ModelAndView createEditModelAndView(Clinic clinic) {
			assert clinic != null;

			return createEditModelAndView(clinic, null);
		}

		protected ModelAndView createEditModelAndView(Clinic clinic,
				String message) {
			assert clinic != null;
			ModelAndView res;

			res = new ModelAndView("clinic/edit");
			res.addObject("clinic", clinic);
			res.addObject("message", message);

			return res;
		}

	public String redirectCancel(String ret) {
		String res = null;

		switch (ret) {
		case "0":
			res = "clinic/administrator/list-available.do";
			break;
		default:
			res = null;
		}
		return res;
	}
	
	@InitBinder
	protected void initBinder(HttpServletRequest request,
		ServletRequestDataBinder binder) throws ServletException {
		binder.registerCustomEditor(byte[].class,
		new ByteArrayMultipartFileEditor());
	}

}
