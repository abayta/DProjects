package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.CommentService;
import services.PaintingService;
import domain.Comment;
import domain.Painting;

@Controller
@RequestMapping("/painting/administrator")
public class PaintingAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PaintingService paintingService;
	@Autowired
	private CommentService commentService;

	// Constructors -----------------------------------------------------------

	public PaintingAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list-of-customer", method = RequestMethod.GET)
	public ModelAndView listOfCustomer(@RequestParam int customerId) {
		Collection<Painting> paintings = paintingService.findAllOfCustomerAnonymousRole(customerId);

		ModelAndView result = new ModelAndView("painting/list-of-customer");
		result.addObject("paintings", paintings);
		result.addObject("ret", 0);
		result.addObject("customerId", customerId);
		result.addObject("requestURI", "painting/administrator/list-of-customer.do");

		return result;
	}
	
	@RequestMapping(value = "/list-all-paintings", method = RequestMethod.GET)
	public ModelAndView listAllPaintings() {
		Collection<Painting> paintings = paintingService.findAll();

		ModelAndView result = new ModelAndView("painting/list-all-paintings");
		result.addObject("paintings", paintings);
		result.addObject("ret", 1);
		result.addObject("requestURI", "painting/administrator/list-all-paintings.do");

		return result;
	}
	
	@RequestMapping(value="/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int paintingId){
		ModelAndView res;
		Painting painting;
		Collection<Comment> comments;
		
		painting = paintingService.findOne(paintingId);
		comments = commentService.findInitialComments(paintingId);
		
		res = new ModelAndView("painting/details");
		res.addObject("requestURI", "painting/administrator/details.do");
		res.addObject("painting", painting);
		res.addObject("comments", comments);
		
		return res;
	}

}
