package controllers.doctor;

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

import domain.Appointment;
import domain.Doctor;
import domain.MedicalReport;
import domain.Patient;
import domain.Prescription;
import forms.MedicalReportForm;

import services.AppointmentService;
import services.DoctorService;
import services.MedicalReportService;
import services.PatientService;
import services.PrescriptionService;

@Controller
@RequestMapping("/medicalReport/doctor")
public class MedicalReportDoctorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private MedicalReportService medicalReportService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private PrescriptionService prescriptionService;

	// Constructors -----------------------------------------------------------

	public MedicalReportDoctorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-available-by-patient", method = RequestMethod.GET)
	public ModelAndView listAvailableByClinic(int patientId) {
		
	    Patient patient = patientService.findOne(patientId);
		Collection<MedicalReport> medicalReports = medicalReportService.findByPatient(patient.getId());

		ModelAndView result = new ModelAndView("medicalReport/list-available-by-patient");
		result.addObject("medicalReports", medicalReports);
		result.addObject("requestURI", "medicalReport/doctor/list-available-by-patient.do");

		return result;
	}
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int appointmentId) {
		ModelAndView res;
		appointmentService.checkBelongsToDoctor(appointmentId);
		MedicalReport medicalReport = medicalReportService.findByAppointment(appointmentId);
		Collection<Prescription> prescriptions = prescriptionService.findByMedicalReportViewDoctor(medicalReport.getId());

		res = new ModelAndView("medicalReport/details");
		res.addObject("medicalReport", medicalReport);
		res.addObject("prescriptions", prescriptions);
		res.addObject("requestURI", "medicalReport/doctor/details.do");
	
		return res;
	}
	
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int appointmentId) {
		MedicalReport medicalReport = medicalReportService.create(appointmentId);
		ModelAndView result = createEditModelAndView(medicalReport);
		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid MedicalReportForm medicalReportForm, BindingResult bindingResult) {
		ModelAndView result;
		Doctor doctor = doctorService.findByPrincipal();
		Collection<Appointment> appointments = appointmentService.findAllTodayActiveByDoctor(doctor.getId());

		if (bindingResult.hasErrors()) {
			result = createEditModelAndView(medicalReportForm);
		} else {
			try {
				MedicalReport medicalReport = medicalReportService.reconstruct(medicalReportForm);
				medicalReportService.save(medicalReport);
				result = new ModelAndView("redirect:../../appointment/doctor/list-available-today.do");
			} catch (Throwable oops) {
				if (appointments.contains(appointmentService.findOne(medicalReportForm.getAppointment().getId())))
					result = createEditModelAndView(medicalReportForm,
							"medicalReport.isCreated.error");
				else
					result = createEditModelAndView(medicalReportForm,
						"medicalReport.commit.error");
			}
		}

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------	

	protected ModelAndView createEditModelAndView(MedicalReportForm medicalReportForm) {
		assert medicalReportForm != null;

		return createEditModelAndView(medicalReportForm, null);
	}

	protected ModelAndView createEditModelAndView(MedicalReportForm medicalReportForm, String message) {
		assert medicalReportForm != null;
		ModelAndView res;

		res = new ModelAndView("medicalReport/edit");
		res.addObject("medicalReportForm", medicalReportForm);
		res.addObject("message", message);

		return res;
	}
	
	private ModelAndView createEditModelAndView(MedicalReport medicalReport) {
		ModelAndView res = new ModelAndView("medicalReport/edit");
		res.addObject("medicalReportForm", medicalReport);
		return res;
	}
	
}
