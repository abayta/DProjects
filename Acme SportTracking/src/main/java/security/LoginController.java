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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ParticipantService;

import controllers.AbstractController;
import domain.Administrator;
import domain.Participant;
import forms.AdministratorForm;
import forms.ParticipantForm;

@Controller
@RequestMapping("/security")
public class LoginController extends AbstractController {

	// Supporting services ----------------------------------------------------
	
	@Autowired
	LoginService service;
	@Autowired
	AdministratorService administratorService;
	@Autowired
	ParticipantService participantService;
	
	// Constructors -----------------------------------------------------------
	
	public LoginController() {
		super();
	}
	
	// Login ------------------------------------------------------------------

	@RequestMapping("/login")
	public ModelAndView login(
			@Valid @ModelAttribute Credentials credentials,
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

		@RequestMapping(value = "/participant/create", method = RequestMethod.GET)
		public ModelAndView createParticipant() {
			ModelAndView result;
			ParticipantForm participantForm;

			participantForm = participantService.construct();
			result = createEditModelAndView(participantForm);

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
				} catch (Throwable oops) {
					result = createEditModelAndView(administratorForm, "actor.commit.error");
				}
			}

			return result;
		}

		@RequestMapping(value = "/participant/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView saveParticipant(@Valid ParticipantForm participantForm,
				BindingResult bindingResult) {
			ModelAndView result;

			if (bindingResult.hasErrors()) {
				result = createEditModelAndView(participantForm);
			} else {
				try {
					Participant participant = participantService.reconstruct(participantForm);
					participantService.save(participant);
					result = new ModelAndView("redirect:../../");
				} catch (Throwable oops) {
					result = createEditModelAndView(participantForm, "actor.commit.error");
				}
			}

			return result;
		}

		// Ancillary Methods --------------------------------------------------------

		protected ModelAndView createEditModelAndView(ParticipantForm participantForm) {
			assert participantForm != null;
			ModelAndView result;

			result = createEditModelAndView(participantForm, null);

			return result;
		}

		protected ModelAndView createEditModelAndView(ParticipantForm participantForm, String message) {
			assert participantForm != null;
			ModelAndView result;

			result = new ModelAndView("security/registerParticipant");
			result.addObject("participantForm", participantForm);
			result.addObject("message", message);	

			return result;
		}

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

}
