package controllers.administrator;

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

import domain.Specialty;

import services.SpecialtyService;

@Controller
@RequestMapping("/specialty/administrator")
public class SpecialtyAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private SpecialtyService specialtyService;

	// Constructors -----------------------------------------------------------

	public SpecialtyAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-available", method = RequestMethod.GET)
	public ModelAndView listAvailable() {
		Collection<Specialty> specialties = specialtyService.findAll();

		ModelAndView result = new ModelAndView("specialty/list-available");
		result.addObject("specialties", specialties);
		result.addObject("requestURI", "specialty/administrator/list-available.do");

		return result;
	}

	@RequestMapping(value = "/list-available-by-clinic", method = RequestMethod.GET)
	public ModelAndView listAvailableByClinic(@RequestParam int clinicId) {
		Collection<Specialty> specialties = specialtyService.findAllByClinic(clinicId);

		ModelAndView result = new ModelAndView("specialty/list-available-by-clinic");
		result.addObject("specialties", specialties);
		result.addObject("clinicId", clinicId);
		result.addObject("requestURI", "specialty/administrator/list-available-by-clinic.do");

		return result;
	}
	
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Specialty specialty;

		specialty = specialtyService.create();
		result = createEditModelAndView(specialty);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int specialtyId) {
		ModelAndView result;
		Specialty specialty;

		specialty = specialtyService.findOne(specialtyId);
		result = createEditModelAndView(specialty);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Specialty specialty, BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors()) {
			result = createEditModelAndView(specialty);
		} else {
			try {
				specialtyService.save(specialty);
				result = new ModelAndView("redirect:list-available.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(specialty,
						"specialty.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Specialty specialty, BindingResult binding) {
		ModelAndView result;
		
		try {
			specialtyService.delete(specialty);
			result = new ModelAndView("redirect:list-available.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(specialty, "specialty.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Specialty specialty) {
		assert specialty != null;

		return createEditModelAndView(specialty, null);
	}

	protected ModelAndView createEditModelAndView(Specialty specialty, String message) {
		assert specialty != null;
		ModelAndView res;

		res = new ModelAndView("specialty/edit");
		res.addObject("specialty", specialty);
		res.addObject("message", message);

		return res;
	}

}
