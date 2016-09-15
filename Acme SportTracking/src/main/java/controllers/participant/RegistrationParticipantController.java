package controllers.participant;


import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EventService;
import services.RegistrationService;

import controllers.AbstractController;
import domain.Assessment;
import domain.Event;
import domain.Registration;
import forms.RegistrationForm;

@Controller
@RequestMapping("/registration/participant")
public class RegistrationParticipantController extends AbstractController{
	
	@Autowired
	private RegistrationService registrationService;
	@Autowired
	private EventService eventService;
	
	//Delete Registration
	@RequestMapping(value="/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int eventId){
		ModelAndView result;
		Registration registration = registrationService.findByEventAndParticipant(eventId);
		try{
			registrationService.delete(registration);
			result = new ModelAndView("redirect:../../event/participant/details.do?eventId="+eventId);
		}catch(Throwable oops){
			Event event = eventService.findOne(eventId);
			long participantNumber = eventService.participantOfEvent(eventId);
			Boolean isJoined = registrationService.isRegistrate(eventId);
			
			result = new ModelAndView("event/display");
			result.addObject("event", event);
			result.addObject("participantNumber", participantNumber);
			result.addObject("isJoined", isJoined);
			result.addObject("past", new Date().after(event.getFinishMoment()));
			result.addObject("message", "event.commit.error");
		}
		return result;
	}
	
	@RequestMapping(value = "/assess", method = RequestMethod.GET)
	public ModelAndView assessEdit(@RequestParam int eventId){
		ModelAndView result;
		Registration registration;
		
		registration = registrationService.findByEventAndParticipant(eventId);
		result = new ModelAndView("registration/edit");
		result.addObject("registrationForm", construct(registration));
		return result;
		
	}
	
	public RegistrationForm construct(Registration registration){
		if(registration.getAssessment()==null){
			Assessment assessment = new Assessment();
			registration.setAssessment(assessment);
		}
		RegistrationForm res = new RegistrationForm();
		res.setComments(registration.getAssessment().getComments());
		res.setRating(registration.getAssessment().getRating());
		res.setEventId(registration.getEvent().getId());
		res.setId(registration.getId());
		return res;
	}
	
	
	public Registration reconstruct(RegistrationForm registrationForm){
		Registration result = registrationService.findOne(registrationForm.getId());
		Assessment as = new Assessment();
		as.setComments(registrationForm.getComments());
		as.setRating(registrationForm.getRating());
		result.setAssessment(as);
		return result;
	}
	
	@RequestMapping(value = "/assess", method = RequestMethod.POST, params = "save")
	public ModelAndView assess(@Valid RegistrationForm registrationForm, BindingResult bindingResult){
		ModelAndView result;
		
		if (bindingResult.hasErrors()) {
			result = new ModelAndView("registration/edit");
			result.addObject("registrationFrom", registrationForm);
		}else{
			try{
				Registration registration = reconstruct(registrationForm);
				registrationService.assess(registration);
				result = new ModelAndView("redirect:../../event/participant/details.do?eventId="+registration.getEvent().getId());
			}catch(Throwable oops){
				result = new ModelAndView("registration/edit");
				result.addObject("registration", registrationForm);
				result.addObject("message", "registration.error.comit");
			}
		}
		return result;
	}
}
