package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.GalleryService;
import controllers.AbstractController;
import domain.Customer;
import domain.Gallery;

@Controller
@RequestMapping("/gallery/customer")
public class GalleryCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private GalleryService galleryService;
	
	@Autowired
	private CustomerService customerService;
	
	// Constructors -----------------------------------------------------------

	public GalleryCustomerController() {
		super();
	}
	
	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-my-galleries", method = RequestMethod.GET)
	public ModelAndView listByCustomer() {
		Customer customer = customerService.findByPrincipal();
		Collection<Gallery> galleries = galleryService.findAllOfCustomer(customer.getId());

		ModelAndView result = new ModelAndView("gallery/list-of-customer");
		result.addObject("galleries", galleries);
		result.addObject("requestURI", "gallery/customer/list-my-galleries.do");

		return result;
	}
	
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Gallery gallery;

		gallery = galleryService.create();
		result = createEditModelAndView(gallery);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView save(@RequestParam int galleryId) {
		ModelAndView result;
		Gallery gallery;

		gallery = galleryService.findOne(galleryId);
		result = createEditModelAndView(gallery);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Gallery gallery, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(gallery);
		} else {
			try {
				galleryService.save(gallery);
				result = new ModelAndView("redirect:list-my-galleries.do");
			} catch (Throwable oops) {
				if (!gallery.getPaintings().isEmpty())
					result = createEditModelAndView(gallery,
							"gallery.saveordelete.error");
				else
					result = createEditModelAndView(gallery,
							"gallery.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid Gallery gallery, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(gallery);
		} else {
			try {
				galleryService.delete(gallery);
				result = new ModelAndView("redirect:list-my-galleries.do");
			} catch (Throwable oops) {
				if (!gallery.getPaintings().isEmpty())
					result = createEditModelAndView(gallery,
							"gallery.saveordelete.error");
				else
					result = createEditModelAndView(gallery,
							"gallery.commit.error");
			}
		}

		return result;
	}
	
	@RequestMapping(value = "/list-of-customer", method = RequestMethod.GET)
	public ModelAndView listOfCustomer(@RequestParam int customerId) {
		Collection<Gallery> galleries = galleryService.findAllOfCustomer(customerId);

		ModelAndView result = new ModelAndView("gallery/list-of-customer");
		result.addObject("galleries", galleries);
		result.addObject("requestURI", "gallery/list-of-customer.do");

		return result;
	}
	
	// Ancillary Methods -------------------------------------------------------

	protected ModelAndView createEditModelAndView(Gallery gallery) {
		assert gallery != null;

		return createEditModelAndView(gallery, null);
	}

	protected ModelAndView createEditModelAndView(Gallery gallery,
			String message) {
		assert gallery != null;
		ModelAndView res;

		res = new ModelAndView("gallery/edit");
		res.addObject("gallery", gallery);
		res.addObject("message", message);

		return res;
	}

}
