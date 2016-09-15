package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.GalleryService;
import domain.Gallery;

@Controller
@RequestMapping("/gallery/administrator")
public class GalleryAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private GalleryService galleryService;

	// Constructors -----------------------------------------------------------

	public GalleryAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-of-customer", method = RequestMethod.GET)
	public ModelAndView listByCustomer(@RequestParam int customerId) {
		Collection<Gallery> galleries = galleryService.findAllOfCustomer(customerId);

		ModelAndView result = new ModelAndView("gallery/list-of-customer");
		result.addObject("galleries", galleries);
		result.addObject("requestURI", "gallery/administrator/list-of-customer.do");

		return result;
	}

}
