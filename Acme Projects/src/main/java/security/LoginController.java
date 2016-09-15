/* LoginController.java
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package security;

import java.util.Collection;

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
import services.GroupService;
import services.UserService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Group;
import domain.User;
import forms.AdministratorForm;
import forms.UserForm;

@Controller
@RequestMapping("/security")
public class LoginController extends AbstractController {

	// Supporting services ----------------------------------------------------

	@Autowired
	LoginService service;
	@Autowired
	AdministratorService administratorService;
	@Autowired
	UserService userService;
	@Autowired
	GroupService groupService;

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

	@RequestMapping(value = "/user/create", method = RequestMethod.GET)
	public ModelAndView createParticipant() {
		ModelAndView result;
		UserForm userForm;

		userForm = userService.construct();
		result = createEditModelAndView(userForm);

		return result;
	}

	// Register ---------------------------------------------------------------

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdministrator(@Valid AdministratorForm administratorForm,
			BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors()) {
			result = createEditModelAndView(administratorForm);
		} else {
			try {
				Administrator administrator = administratorService.reconstruct(administratorForm);
				administratorService.save(administrator);
				result = new ModelAndView("redirect:../../");
			} catch (DataIntegrityViolationException oops) {
				result = createEditModelAndView(administratorForm, "actor.duplicated.username");
			} catch (Throwable oops) {
				if (!administratorForm.getConfirmPassword().equals(administratorForm.getPassword()))
					result = createEditModelAndView(administratorForm, "actor.different.password");
				else if (administratorForm.getAcceptTerms() == false)
					result = createEditModelAndView(administratorForm, "actor.terms.notAccepted");
				else
					result = createEditModelAndView(administratorForm, "actor.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveUser(@Valid UserForm userForm, BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors()) {
			result = createEditModelAndView(userForm);
		} else {
			try {
				User user = userService.reconstruct(userForm);
				userService.save(user);
				result = new ModelAndView("redirect:../../");
			} catch (DataIntegrityViolationException oops) {
				result = createEditModelAndView(userForm, "actor.duplicated.username");
			} catch (Throwable oops) {
				if (!userForm.getConfirmPassword().equals(userForm.getPassword()))
					result = createEditModelAndView(userForm, "actor.different.password");
				else if (userForm.getAcceptTerms() == false)
					result = createEditModelAndView(userForm, "actor.terms.notAccepted");
				else
					result = createEditModelAndView(userForm, "actor.commit.error");
			}
		}

		return result;
	}

	// Ancillary Methods -------------------------------------------------------

	protected ModelAndView createEditModelAndView(AdministratorForm administratorForm) {
		assert administratorForm != null;
		ModelAndView result;

		result = createEditModelAndView(administratorForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(AdministratorForm administratorForm, String message) {
		assert administratorForm != null;
		ModelAndView result;

		result = new ModelAndView("security/registerAdministrator");
		result.addObject("administratorForm", administratorForm);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createEditModelAndView(UserForm userForm) {
		assert userForm != null;
		ModelAndView result;

		result = createEditModelAndView(userForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(UserForm userForm, String message) {
		assert userForm != null;
		ModelAndView result;
		Collection<Group> groups = groupService.findAllToAdministrator();

		result = new ModelAndView("security/registerUser");
		result.addObject("userForm", userForm);
		result.addObject("groups", groups);
		result.addObject("message", message);

		return result;
	}

}
