package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.CustomerService;

import domain.Customer;

@Controller
@RequestMapping("/customer/administrator")
public class CustomerAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CustomerService customerService;

	// Constructors -----------------------------------------------------------

	public CustomerAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-available", method = RequestMethod.GET)
	public ModelAndView listAvailable() {
		Collection<Customer> customers = customerService.findAll();

		ModelAndView result = new ModelAndView("customer/list-available");
		result.addObject("customers", customers);
		result.addObject("requestURI", "customer/administrator/list-available.do");

		return result;
	}

}