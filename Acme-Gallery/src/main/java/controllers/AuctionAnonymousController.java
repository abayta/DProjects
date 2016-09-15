package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Auction;

import services.AuctionService;

@Controller
@RequestMapping("/auction")
public class AuctionAnonymousController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private AuctionService auctionService;

	// Constructors -----------------------------------------------------------

	public  AuctionAnonymousController() {
		super();
	}

	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value="/list-active", method=RequestMethod.GET)
	public ModelAndView listActive(){
		ModelAndView res;
		Collection<Auction> auctions;
		
		auctions = auctionService.findActiveAuctions();
		res = new ModelAndView("auction/list-active");
		res.addObject("auctions", auctions);
		res.addObject("requestURI", "/auction/list-active.do");
		
		return res;
		
	}
	
	@RequestMapping(value="/details", method=RequestMethod.GET)
	public ModelAndView details(@RequestParam int auctionId){
		ModelAndView res;
		Auction auction;
		
		auction = auctionService.findOne(auctionId);
		res = new ModelAndView("auction/details");
		res.addObject("auction", auction);
			
		return res;
		
	}

}
