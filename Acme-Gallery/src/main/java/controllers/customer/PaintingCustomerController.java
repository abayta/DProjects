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

import controllers.AbstractController;

import services.AuctionService;
import services.CommentService;
import services.GalleryService;
import services.PaintingService;
import domain.Auction;
import domain.Comment;
import domain.Gallery;
import domain.Painting;
import forms.PaintingForm;

@Controller
@RequestMapping("/painting/customer")
public class PaintingCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PaintingService paintingService;
	
	@Autowired
	private AuctionService auctionService;
	
	@Autowired
	private GalleryService galleryService;
	
	@Autowired
	private CommentService commentService;

	// Constructors -----------------------------------------------------------

	public PaintingCustomerController() {
		super();
	}

	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value = "/list-of-customer", method = RequestMethod.GET)
	public ModelAndView listByCustomer(@RequestParam(required=false) Integer customerId) {
		Collection<Painting> paintings;
		if(customerId==null){
			paintings = paintingService.findAllOfCustomer();
		}else{
			paintings = paintingService.findAllOfCustomerAnonymousRole(customerId);
		}
		

		ModelAndView result = new ModelAndView("painting/list-of-customer");
		result.addObject("paintings", paintings);
		result.addObject("requestURI", "painting/customer/list-of-customer.do");

		return result;
	}
	
	@RequestMapping(value = "/list-of-customer-without-galleries", method = RequestMethod.GET)
	public ModelAndView listWithoutGalleryOfCustomer() {
		Collection<Painting> paintings = paintingService.findAllWithoutGalleryOfCustomer();

		ModelAndView result = new ModelAndView("painting/list-of-customer-without-galleries");
		result.addObject("paintings", paintings);
		result.addObject("requestURI", "painting/customer/list-of-customer-without-galleries.do");

		return result;
	}
	
	@RequestMapping(value = "/list-of-paintings", method = RequestMethod.GET)
	public ModelAndView listByGallery(@RequestParam int galleryId) {
		Collection<Painting> paintings = paintingService.findAllOfGallery(galleryId);

		ModelAndView result = new ModelAndView("painting/list-of-paintings");
		result.addObject("paintings", paintings);
		result.addObject("displayCancel", galleryId);
		result.addObject("requestURI", "painting/customer/list-of-paintings.do");

		return result;
	}
	
	@RequestMapping(value="/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int paintingId){
		ModelAndView res;
		Painting painting;
		Collection<Comment> comments;
		Auction auction;
		boolean mine;
		
		painting = paintingService.findOne(paintingId);
		comments = commentService.findInitialComments(paintingId);
		auction = auctionService.findByPaintingFinished(paintingId);
		mine = paintingService.isMine(paintingId);
		
		
		res = new ModelAndView("painting/details");
		res.addObject("painting", painting);
		res.addObject("comments", comments);
		res.addObject("auction", auction);
		res.addObject("mine", mine);
		res.addObject("requestURI", "/painting/customer/details.do");
		
		return res;
	}
	
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Painting painting;
		
		painting = paintingService.create();
		res = createEditModelAndView(painting);
		
		return res;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int paintingId) {
		Painting painting= paintingService.findOne(paintingId);
		ModelAndView result = createEditModelAndView(painting);
		
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid PaintingForm paintingForm, BindingResult bindingResult) {
		ModelAndView result;
		
		if (bindingResult.hasErrors()) {
			result = createEditModelAndView(paintingForm);
		} else {
			Painting painting = paintingService.reconstruct(paintingForm);
			try {
				paintingService.save(painting);
				result = new ModelAndView("redirect:list-of-customer.do");
			} catch (Throwable oops) {
				if (!(painting.getAuctions().isEmpty()))
					result = createEditModelAndView(paintingForm, "painting.have.auctions");
				else
					result = createEditModelAndView(paintingForm, "painting.commit.error");
			}
		}

		return result;
	}
	
	// Ancillary Methods -------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Painting painting) {
		return createEditModelAndView(painting, null);
	}
	protected ModelAndView createEditModelAndView(PaintingForm paintingForm) {
		return createEditModelAndView(paintingForm, null);
	}

	protected ModelAndView createEditModelAndView(PaintingForm paintingForm, String message) {
		assert paintingForm != null;
		Collection<Gallery> galleries = galleryService.findAll();
		
		ModelAndView result = new ModelAndView("painting/edit");
		result.addObject("paintingForm", paintingForm);
		result.addObject("galleries", galleries);
		result.addObject("message", message);

		return result;
	}
	
	protected ModelAndView createEditModelAndView(Painting painting, String message) {
		assert painting != null;
		Collection<Gallery> galleries = galleryService.findAll();
		
		ModelAndView result = new ModelAndView("painting/edit");
		result.addObject("paintingForm", painting);
		result.addObject("galleries", galleries);
		result.addObject("message", message);

		return result;
	}
	
}
