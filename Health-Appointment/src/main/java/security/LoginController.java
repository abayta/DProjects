package security;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import services.DoctorService;
import services.PatientService;
import services.SpecialtyService;

import controllers.AbstractController;
import domain.Doctor;
import domain.Patient;
import domain.Specialty;
import forms.DoctorForm;
import forms.PatientForm;

@Controller
@RequestMapping("/security")
public class LoginController extends AbstractController {

	// Supporting services ----------------------------------------------------

	@Autowired
	DoctorService doctorService;
	
	@Autowired
	LoginService service;
	
	@Autowired
	PatientService patientService;
	
	@Autowired
	SpecialtyService specialtyService;

	// Constructors -----------------------------------------------------------

	public LoginController() {
		super();
	}

	// Login ------------------------------------------------------------------

	@RequestMapping("/login")
	public ModelAndView login(@Valid @ModelAttribute Credentials credentials,
			BindingResult bindingResult,
			@RequestParam(required = false) boolean showError) {
		Assert.notNull(credentials);
		Assert.notNull(bindingResult);

		ModelAndView result;

		result = new ModelAndView("security/login");
		result.addObject("credentials", credentials);
		result.addObject("showError", showError);

		return result;
	}

	// LoginFailure -----------------------------------------------------------

	@RequestMapping("/loginFailure")
	public ModelAndView failure() {
		ModelAndView result;

		result = new ModelAndView("redirect:login.do?showError=true");

		return result;
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/doctor/administrator/create", method = RequestMethod.GET)
	public ModelAndView createDoctor() {
		Doctor doctor = doctorService.create();
		ModelAndView result = createEditModelAndView(doctorService.construct(doctor));

		return result;
	}
	
	@RequestMapping(value = "/patient/create", method = RequestMethod.GET)
	public ModelAndView createPatient() {
		ModelAndView result;
		PatientForm patientForm;

		patientForm = patientService.construct();
		result = createEditModelAndView(patientForm);

		return result;
	}
	
	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/doctor/administrator/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int doctorId) {
		ModelAndView result;
		Doctor doctor = doctorService.findOne(doctorId);
		DoctorForm doctorForm = doctorService.construct(doctor);
		result = createEditModelAndView(doctorForm);

		return result;
	}
	
	// Register ---------------------------------------------------------------

	@RequestMapping(value = "/doctor/administrator/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveDoctor(@Valid DoctorForm doctorForm,
			BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors()) {
			result = createEditModelAndView(doctorForm);
		} else {
			try {
				Doctor doctor = doctorService.reconstruct(doctorForm);
				doctorService.save(doctor);
				result = new ModelAndView("redirect:../../../doctor/administrator/list-available.do");
			} catch (DataIntegrityViolationException oops) {
				result = createEditModelAndView(doctorForm,
						"actor.duplicated.username");
			} catch (Throwable oops) {
				if (!doctorForm.getConfirmPassword().equals(
						doctorForm.getPassword()))
					result = createEditModelAndView(doctorForm,
							"actor.different.password");
				else if (doctorForm.getAcceptTerms() == false)
					result = createEditModelAndView(doctorForm,
							"actor.terms.notAccepted");
				else
					result = createEditModelAndView(doctorForm,
							"actor.commit.error");
			}
		}

		return result;
	}
	
	@RequestMapping(value = "/patient/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView savePatient(@Valid PatientForm patientForm,
			BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors()) {
			result = createEditModelAndView(patientForm);
		} else {
			try {
				Patient patient = patientService.reconstruct(patientForm);
				patientService.save(patient);
				result = new ModelAndView("redirect:../../");
			} catch (DataIntegrityViolationException oops) {
				result = createEditModelAndView(patientForm,
						"actor.duplicated.username");
			} catch (Throwable oops) {
				if (!patientForm.getConfirmPassword().equals(
						patientForm.getPassword()))
					result = createEditModelAndView(patientForm,
							"actor.different.password");
				else if (patientForm.getAcceptTerms() == false)
					result = createEditModelAndView(patientForm,
							"actor.terms.notAccepted");
				else
					result = createEditModelAndView(patientForm,
							"actor.commit.error");
			}
		}

		return result;
	}

	// Ancillary Methods -------------------------------------------------------

	protected ModelAndView createEditModelAndView(DoctorForm doctorForm) {
		assert doctorForm != null;
		ModelAndView result;

		result = createEditModelAndView(doctorForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(DoctorForm doctorForm,
			String message) {
		assert doctorForm != null;
		ModelAndView result;
		Collection<Specialty> specialties = specialtyService.findAll();
		
		result = new ModelAndView("security/administrator/registerDoctor");
		result.addObject("doctorForm", doctorForm);
		result.addObject("specialties", specialties);
		result.addObject("message", message);

		return result;
	}
	
	protected ModelAndView createEditModelAndView(PatientForm patientForm) {
		assert patientForm != null;
		ModelAndView result;

		result = createEditModelAndView(patientForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(PatientForm patientForm,
			String message) {
		assert patientForm != null;
		ModelAndView result;

		result = new ModelAndView("security/registerPatient");
		result.addObject("patientForm", patientForm);
		result.addObject("message", message);

		return result;
	}
	
	@InitBinder
	protected void initBinder(HttpServletRequest request,
		ServletRequestDataBinder binder) throws ServletException {
		binder.registerCustomEditor(byte[].class,
		new ByteArrayMultipartFileEditor());
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true);
        binder.registerCustomEditor(Date.class, editor);
    }

}