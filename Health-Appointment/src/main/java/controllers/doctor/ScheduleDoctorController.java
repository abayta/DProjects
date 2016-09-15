package controllers.doctor;

import java.util.Arrays;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ClinicService;
import services.ScheduleService;

import controllers.AbstractController;
import domain.Clinic;

import domain.Schedule;
import domain.Schedule.DayOfWeek;

@Controller
@RequestMapping("/schedule/doctor")
public class ScheduleDoctorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ClinicService clinicService;
	@Autowired
	private ScheduleService scheduleService;

	// Constructors -----------------------------------------------------------

	public ScheduleDoctorController() {
		super();
	}

	// CRUD ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Schedule schedule;

		schedule = scheduleService.create();
		result = createEditModelAndView(schedule);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Schedule schedule, BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors()) {
			result = createEditModelAndView(schedule);
		} else {
			try {
				scheduleService.save(schedule);
				result = new ModelAndView("redirect:/");
			} catch (Throwable oops) {
				result = createEditModelAndView(schedule, "schedule.commit.error");
			}
		}

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	private ModelAndView createEditModelAndView(Schedule schedule){
		return createEditModelAndView(schedule, null);
	}
	private ModelAndView createEditModelAndView(Schedule schedule, String message) {
		ModelAndView res;
		Collection<DayOfWeek> days = Arrays.asList(DayOfWeek.values());
		Collection<Clinic> clinics = clinicService.findAll();
		
		res = new ModelAndView("schedule/edit");
		res.addObject("states", days);
		res.addObject("clinics", clinics);
		res.addObject("message", message);
		res.addObject("schedule", schedule);
		
		return res;
	}
}
