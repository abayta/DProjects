package controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import org.springframework.web.servlet.ModelAndView;

import domain.Clinic;
import domain.Doctor;
import domain.Patient;

import services.ClinicService;
import services.DoctorService;
import services.PatientService;

@Controller
@RequestMapping("/upload")
public class UploadImageController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private ClinicService clinicService;
	
	@Autowired
	private PatientService patientService;
	
	// Showing ----------------------------------------------------------------
	
	@RequestMapping(value = "/fileDoctor")
	public ModelAndView fileDoctor(HttpServletResponse response, @RequestParam int doctorId) throws IOException {	
		Doctor doctor = doctorService.findOne(doctorId);

		response.setContentType(MediaType.IMAGE_JPEG_VALUE);		
		byte[] image = doctor.getImage();
		InputStream inputStream = new ByteArrayInputStream(image);
		IOUtils.copy(inputStream, response.getOutputStream());

		return null;
	}
	
	@RequestMapping(value = "/fileClinic")
	public ModelAndView fileClinic(HttpServletResponse response, @RequestParam int clinicId) throws IOException {	
		Clinic clinic = clinicService.findOne(clinicId);

		response.setContentType(MediaType.IMAGE_JPEG_VALUE);		
		byte[] logo = clinic.getLogo();
		InputStream inputStream = new ByteArrayInputStream(logo);
		IOUtils.copy(inputStream, response.getOutputStream());

		return null;
	}
	
	@RequestMapping(value = "/filePatient")
	public ModelAndView filePatient(HttpServletResponse response, @RequestParam int patientId) throws IOException {	
		Patient patient = patientService.findOne(patientId);

		response.setContentType(MediaType.IMAGE_JPEG_VALUE);		
		byte[] logo = patient.getImage();
		InputStream inputStream = new ByteArrayInputStream(logo);
		IOUtils.copy(inputStream, response.getOutputStream());

		return null;
	}
	
}
