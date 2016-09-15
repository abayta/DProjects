package controllers.customer;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BidService;

import controllers.AbstractController;

import domain.Bid;
import forms.BidForm;

@Controller
@RequestMapping("/bid/customer")
public class BidCustomerController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private BidService bidService;

	// Constructors -----------------------------------------------------------

	public BidCustomerController() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------------------
	
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create(@RequestParam int auctionId){
		ModelAndView res;
		Bid bid;
		try{
			bid = bidService.create(auctionId);
			res = createEditModelAndView(bid);
		}catch (Throwable oops) {
			res = new ModelAndView("redirect:../../auction/customer/details.do");
			res.addObject("auctionId", auctionId);
		}
		
		return res;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int auctionId){
		ModelAndView res;
		Bid bid;
		try{
			bid = bidService.findByCustomerAndAuction(auctionId);
			res = createEditModelAndView(bid);
		}catch (Throwable oops) {
			res = new ModelAndView("redirect:/");
		}
		
		return res;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid BidForm bidForm, BindingResult binding) {
		ModelAndView result;
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(bidForm);
		} else {
			try {
				Bid bid = bidService.reconstruct(bidForm);
				bidService.save(bid);
				result = new ModelAndView("redirect:../../auction/customer/details.do");
				result.addObject("auctionId", bid.getAuction().getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(bidForm, "bid.commit.error");
			}
		}

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid BidForm bidForm, BindingResult binding) {
		ModelAndView res;
		
		if (binding.hasErrors()) {
			res = createEditModelAndView(bidForm);
		} else {
			try {
				Bid bid = bidService.reconstruct(bidForm);
				bidService.delete(bid);
				res = new ModelAndView("redirect:../../auction/customer/details.do");
				res.addObject("auctionId", bid.getAuction().getId());
			} catch (Throwable oops) {
				res = createEditModelAndView(bidForm,"bid.commit.error");
			}
		}

		return res;
	}
	
	@RequestMapping(value="/details", method=RequestMethod.GET)
	public ModelAndView listActive(@RequestParam int bidId){
		ModelAndView res;
		Bid bid;
		
		bid = bidService.findOne(bidId);
		res = new ModelAndView("bid/details");
		res.addObject("bid", bid);
			
		return res;
	}
	
	private ModelAndView createEditModelAndView(BidForm bidForm) {
		return createEditModelAndView(bidForm, null);
	}

	private ModelAndView createEditModelAndView(BidForm bidForm, String message) {
		ModelAndView res = new ModelAndView("bid/edit");
		res.addObject("bidForm", bidForm);
		res.addObject("message", message);
		return res;
	}
	
	private ModelAndView createEditModelAndView(Bid bid) {
		ModelAndView res = new ModelAndView("bid/edit");
		res.addObject("bidForm", bid);
		return res;
	}

	
	

}
