package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.GalleryService;
import domain.Gallery;

@Controller
@RequestMapping("/gallery")
public class GalleryAnonymousController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private GalleryService galleryService;

	// Constructors -----------------------------------------------------------

	public GalleryAnonymousController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-of-customer", method = RequestMethod.GET)
	public ModelAndView listOfCustomer(@RequestParam int customerId) {
		Collection<Gallery> galleries = galleryService.findAllOfCustomer(customerId);

		ModelAndView result = new ModelAndView("gallery/list-of-customer");
		result.addObject("galleries", galleries);
		result.addObject("requestURI", "gallery/list-of-customer.do");

		return result;
	}

}
