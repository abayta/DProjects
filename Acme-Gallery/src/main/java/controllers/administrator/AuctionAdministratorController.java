package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuctionService;
import services.BidService;

import controllers.AbstractController;
import domain.Auction;

@Controller
@RequestMapping("/auction/administrator")
public class AuctionAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AuctionService auctionService;
	
	@Autowired
	private BidService bidService;

	// Constructors -----------------------------------------------------------

	public AuctionAdministratorController() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------------------

	@RequestMapping("/list-finished")
	public ModelAndView listFinished(){
		ModelAndView res;
		Collection<Auction> auctions;
		
		auctions = auctionService.findFinishedAuction();
		res = new ModelAndView("auction/list-finished");
		res.addObject("requestURI", "auction/administrator/list-finished.do");
		res.addObject("auctions", auctions);
		
		return res;
	}
	
	@RequestMapping(value="/details", method=RequestMethod.GET)
	public ModelAndView details(@RequestParam int auctionId){
		ModelAndView res;
		Auction auction;
		
		auction = auctionService.findOne(auctionId);
		res = new ModelAndView("auction/details");
		res.addObject("requestURI", "auction/administrator/details.do");
		res.addObject("auction", auction);
		res.addObject("bids", bidService.findByAuction(auctionId));
			
		return res;
		
	}

}
