package controllers.patient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ScheduleService;

import controllers.AbstractController;

@Controller
@RequestMapping("/schedule/patient")
public class SchedulePatientController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ScheduleService scheduleService;

	// Constructors -----------------------------------------------------------

	public SchedulePatientController() {
		super();
	}

	// CRUD ---------------------------------------------------------------
	
	@RequestMapping(value="/showSchedule", method = RequestMethod.GET)
	public ModelAndView showSchedule(@RequestParam Integer doctorId){
		ModelAndView res;
		Object[] o = scheduleService.visiteDays(doctorId);
		res = new ModelAndView("schedule/list");
		res.addObject("schedules",o[1]);
		res.addObject("map",o[0]);
		
		return res;
	
	}
	
	// Ancillary methods ------------------------------------------------------
}
