package security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CustomerService;

import controllers.AbstractController;
import domain.Administrator;
import domain.Customer;
import forms.AdministratorForm;
import forms.CustomerForm;

@Controller
@RequestMapping("/security")
public class LoginController extends AbstractController {

	// Supporting services ----------------------------------------------------

	@Autowired
	LoginService service;
	@Autowired
	AdministratorService administratorService;
	@Autowired
	CustomerService customerService;

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

	@RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
	public ModelAndView createAdministrator() {
		ModelAndView result;
		AdministratorForm administratorForm;

		administratorForm = administratorService.construct();
		result = createEditModelAndView(administratorForm);

		return result;
	}

	@RequestMapping(value = "/customer/create", method = RequestMethod.GET)
	public ModelAndView createCustomer() {
		ModelAndView result;
		CustomerForm customerForm;

		customerForm = customerService.construct();
		result = createEditModelAndView(customerForm);

		return result;
	}

	// Register ---------------------------------------------------------------

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdministrator(
			@Valid AdministratorForm administratorForm,
			BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors()) {
			result = createEditModelAndView(administratorForm);
		} else {
			try {
				Administrator administrator = administratorService
						.reconstruct(administratorForm);
				administratorService.save(administrator);
				result = new ModelAndView("redirect:../../");
			} catch (DataIntegrityViolationException oops) {
				result = createEditModelAndView(administratorForm,
						"actor.duplicated.username");
			} catch (Throwable oops) {
				if (!administratorForm.getConfirmPassword().equals(
						administratorForm.getPassword()))
					result = createEditModelAndView(administratorForm,
							"actor.different.password");
				else if (administratorForm.getAcceptTerms() == false)
					result = createEditModelAndView(administratorForm,
							"actor.terms.notAccepted");
				else
					result = createEditModelAndView(administratorForm,
							"actor.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/customer/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCustomer(@Valid CustomerForm customerForm,
			BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors()) {
			result = createEditModelAndView(customerForm);
		} else {
			try {
				Customer customer = customerService.reconstruct(customerForm);
				customerService.save(customer);
				result = new ModelAndView("redirect:../../");
			} catch (DataIntegrityViolationException oops) {
				result = createEditModelAndView(customerForm,
						"actor.duplicated.username");
			} catch (Throwable oops) {
				if (!customerForm.getConfirmPassword().equals(
						customerForm.getPassword()))
					result = createEditModelAndView(customerForm,
							"actor.different.password");
				else if (customerForm.getAcceptTerms() == false)
					result = createEditModelAndView(customerForm,
							"actor.terms.notAccepted");
				else
					result = createEditModelAndView(customerForm,
							"actor.commit.error");
			}
		}

		return result;
	}

	// Ancillary Methods -------------------------------------------------------

	protected ModelAndView createEditModelAndView(
			AdministratorForm administratorForm) {
		assert administratorForm != null;
		ModelAndView result;

		result = createEditModelAndView(administratorForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			AdministratorForm administratorForm, String message) {
		assert administratorForm != null;
		ModelAndView result;

		result = new ModelAndView("security/registerAdministrator");
		result.addObject("administratorForm", administratorForm);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createEditModelAndView(CustomerForm customerForm) {
		assert customerForm != null;
		ModelAndView result;

		result = createEditModelAndView(customerForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(CustomerForm customerForm,
			String message) {
		assert customerForm != null;
		ModelAndView result;

		result = new ModelAndView("security/registerCustomer");
		result.addObject("customerForm", customerForm);
		result.addObject("message", message);

		return result;
	}
}