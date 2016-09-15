package controllers.administrator;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.AuctionService;
import services.BidService;
import services.CommentService;
import services.CustomerService;
import services.GalleryService;
import services.PaintingService;
import domain.Auction;
import domain.Bid;
import domain.Comment;
import domain.Customer;
import domain.Gallery;
import domain.Painting;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private BidService bidService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private GalleryService galleryService;
	
	@Autowired
	private AuctionService auctionService;
	
	@Autowired
	private PaintingService paintingService;
	
	@Autowired
	private CommentService commentService;

	// Constructors -----------------------------------------------------------

	public DashboardAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/show-dashboard", method = RequestMethod.GET)
	public ModelAndView showDashboard() {
		Collection<Bid> bids = bidService.findHighest();
		Collection<Customer> customers = customerService.findWithMorePaintings();
		Collection<Object[]> galleriesCollection = galleryService.findMostExpensive();
		List<Object[]> galleriesList = new LinkedList<Object[]>();
		galleriesList.addAll(galleriesCollection);
		Collection<Auction> auctions = auctionService.findWithMoreBids();
		Collection<Customer> customersComment = customerService.findWithMoreComments();
		Collection<Painting> paintingsComment = paintingService.findWithMoreComments();
		Collection<Comment> comments = commentService.findWithMoreChildrenComments();
		
		Gallery gallery = null;
		Collection<Gallery> galleries = new HashSet<Gallery>();
		for(Object[] o: galleriesList){
			 gallery =(Gallery) o[0];
			 galleries.add(gallery);
		}
		
		Double appraisal = null;
		Double[] appraisals = new Double[galleriesList.size()];
		for(Object[] o: galleriesList){
			 appraisal =(Double) o[1];
			 for(int i=0; i<galleriesList.size(); i++)
			 appraisals[i] = appraisal;
		}
			
		ModelAndView result = new ModelAndView("dashboard/show-dashboard");
		result.addObject("bids", bids);
		result.addObject("customers", customers);
		result.addObject("galleries", galleries);
		result.addObject("appraisals", appraisals);
		result.addObject("auctions", auctions);
		result.addObject("customersComment", customersComment);
		result.addObject("paintingsComment", paintingsComment);
		result.addObject("comments", comments);
		result.addObject("requestURI", "dashboard/administrator/show-dashboard.do");

		return result;
	}

}
