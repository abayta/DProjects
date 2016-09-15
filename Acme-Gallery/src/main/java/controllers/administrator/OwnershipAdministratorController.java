package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.CustomerService;
import services.OwnershipService;
import domain.Customer;
import domain.Ownership;

@Controller
@RequestMapping("/ownership/administrator")
public class OwnershipAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private OwnershipService ownershipService;

	@Autowired
	private CustomerService customerService;
	
	// Constructors -----------------------------------------------------------

	public OwnershipAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-of-ownerships", method = RequestMethod.GET)
	public ModelAndView listByPainting(@RequestParam int paintingId, String ret) {
		Collection<Ownership> ownerships = ownershipService.findAllByPainting(paintingId);
		Customer customer= customerService.findCustomer(paintingId);
		ModelAndView result = new ModelAndView("ownership/list-of-ownerships");
		result.addObject("ownerships", ownerships);
		result.addObject("ret", redirectCancel(ret));
		result.addObject("customerId", customer.getId());
		result.addObject("retNum", ret);
		result.addObject("requestURI", "ownership/administrator/list-of-ownerships.do");

		return result;
	}

	// Ancillary Methods -------------------------------------------------------
	
	public String redirectCancel(String ret) {
		String res = null;
		
		switch (ret) {
		case "0":
			res = "painting/administrator/list-of-customer.do";
			break;
		case "1":
			res = "painting/administrator/list-all-paintings.do";
			break;
		default:
			res = null;
		}
		
		return res;
	}
	
}
