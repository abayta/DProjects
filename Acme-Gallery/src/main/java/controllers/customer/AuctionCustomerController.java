package controllers.customer;

import java.util.Collection;


import java.util.Date;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuctionService;
import services.BidService;

import controllers.AbstractController;
import domain.Auction;

@Controller
@RequestMapping("/auction/customer")
public class AuctionCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AuctionService auctionService;

	@Autowired
	private BidService bidService;

	// Constructors -----------------------------------------------------------

	public AuctionCustomerController() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------------------
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create(@RequestParam int paintingId){
		ModelAndView res;
		Auction auction;
		
		auction = auctionService.create(paintingId);
		res = createEditModelAndView(auction);
		
		return res;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int auctionId){
		ModelAndView res;
		Auction auction;
		
		auction = auctionService.findOne(auctionId);
		res = createEditModelAndView(auction);
		
		return res;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
	public ModelAndView edit(@Valid Auction auction, BindingResult bindingResult){
		ModelAndView res;
		
		if(bindingResult.hasErrors()){
			res = createEditModelAndView(auction);
		}else {
			try{
				auctionService.save(auction);
				res = new ModelAndView("redirect:list-created.do");
			}catch (Throwable oops) {
				res = createEditModelAndView(auction, "auction.commit.error");
			}
		}
		return res;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid Auction auction, BindingResult binding) {
		ModelAndView res;
		
		if (binding.hasErrors()) {
			res = createEditModelAndView(auction);
		} else {
			try {
				auctionService.delete(auction);
				res = new ModelAndView("redirect:../../auction/customer/list-created.do");
			} catch (Throwable oops) {
				res = createEditModelAndView(auction,"bid.commit.error");
			}
		}

		return res;
	}
	
	@RequestMapping(value="/choose", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int bidId, int auctionId){
		ModelAndView res;
		
		try {
			auctionService.choose(bidId, auctionId);
			res = new ModelAndView("redirect:/auction/customer/details.do?auctionId="+auctionId);
		} catch (Throwable oops) {
			Auction auction = auctionService.findOne(auctionId);
			res = new ModelAndView("auction/details");
			res.addObject("auction", auction);
			res.addObject("bids", auction.getBids());
			res.addObject("end", new Date().after(auction.getEndPeriod()));
			res.addObject("message", "auction.choose.error");
		}
		return res;
	}
	
	@RequestMapping(value="/list-created", method=RequestMethod.GET)
	public ModelAndView listCreated(){
		ModelAndView res;
		Collection<Auction> auctions;
		
		auctions = auctionService.findCreatedAuctions();
		res = new ModelAndView("auction/list-created");
		res.addObject("auctions", auctions);
		res.addObject("requestURI", "/auction/customer/list-created.do");
		
		return res;
	
	}
	
	@RequestMapping(value="/list-active", method=RequestMethod.GET)
	public ModelAndView listActive(){
		ModelAndView res;
		Collection<Auction> auctions;
		
		auctions = auctionService.findActiveAuctionsOtherCustomer();
		res = new ModelAndView("auction/list-active");
		res.addObject("auctions", auctions);
		res.addObject("requestURI", "/auction/customer/list-active.do");
		
		return res;
		
	}
	
	@RequestMapping(value="/details", method=RequestMethod.GET)
	public ModelAndView details(@RequestParam int auctionId){
		ModelAndView res;
		Auction auction;
		Date now = new Date();
		
		auction = auctionService.findOne(auctionId);
		res = new ModelAndView("auction/details");
		res.addObject("auction", auction);
		res.addObject("hasBid", bidService.alreadyHasBid(auctionId));
		res.addObject("bids", bidService.findByAuction(auction.getId()));
		res.addObject("end", now.after(auction.getEndPeriod()));
		res.addObject("notStarted", auction.getStartPeriod().after(now));
		res.addObject("requestURI", "/auction/customer/details.do");
			
		return res;
		
	}
	
	private ModelAndView createEditModelAndView(Auction auction){
		return createEditModelAndView(auction, null);
	}
	
	private ModelAndView createEditModelAndView(Auction auction, String message){
		ModelAndView res;
		
		res = new ModelAndView("auction/edit");
		res.addObject("auction", auction);
		res.addObject("message", message);
		
		return res;
	}

}
