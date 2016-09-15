package services;


import java.util.Date;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Auction;
import domain.Bid;
import domain.Customer;
import forms.BidForm;
import repositories.BidRepository;

@Service
@Transactional
public class BidService {
	
	// Managed repository -----------------------------------------------------

	@Autowired
	private BidRepository bidRepository;
	@Autowired
	private AuctionService auctionService;
	@Autowired
	private CustomerService customerService;

	// Services ---------------------------------------------------------------

	// Constructors -----------------------------------------------------------

	public BidService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Bid findOne(int bidId){
		Bid bid = bidRepository.findOne(bidId);
		Assert.notNull(bid);
		return bid;
	}
	
	public Collection<Bid> findHighest(){
		Collection<Bid> bids = bidRepository.findHighest();
		return bids;
	}
	
	public Collection<Bid> findByAuction(int auctionId){
		Collection<Bid> res = bidRepository.findByAuction(auctionId);
		Assert.notNull(res);
		return res;
	}

	public Bid create(int auctionId) {
		Auction auction;
		Bid bid;
		Date now = new Date();
		Customer customer = customerService.findByPrincipal();
		auction = auctionService.findOne(auctionId);
		Assert.isTrue(now.after(auction.getStartPeriod()) && auction.getEndPeriod().after(now));
		Assert.isTrue(!auction.getCreator().equals(customer));
		
		bid = new Bid();
		bid.setAuction(auction);
		bid.setCreationMoment(new Date());
		bid.setCustomer(customer);
		
		return bid;
	}
	
	public void save(Bid bid){
		Assert.notNull(bid);
		Customer customer = customerService.findByPrincipal();
		Date now = new Date(System.currentTimeMillis() - 1000);
		Assert.isTrue(!customer.equals(bid.getAuction().getCreator()));
		Assert.isTrue(now.after(bid.getAuction().getStartPeriod()) && bid.getAuction().getEndPeriod().after(now));
		Assert.isTrue(bid.getMoneyAmount()>=bid.getAuction().getStartingPrice());
		bidRepository.save(bid);
	}
	
	public Bid reconstruct(BidForm bidForm){
		Bid res;
		Date now = new Date(System.currentTimeMillis() - 1000);
		Customer customer = customerService.findByPrincipal();
		
		if(bidForm.getId()==0){
			res = new Bid();
			res.setCreationMoment(now);
			res.setAuction(bidForm.getAuction());
			res.setCustomer(customer);
		}else{
			res = findOne(bidForm.getId());
		}
		res.setMoneyAmount(bidForm.getMoneyAmount());
		
		return res;
	}
	
	public boolean alreadyHasBid(int auctionId){
		boolean res;
		Customer customer = customerService.findByPrincipal();
		Bid bid = bidRepository.findByCustomerAndAuction(auctionId, customer.getId());
		if (bid==null){
			res = false;
		}else{
			res = true;
		}
		return res;
	}

	public Bid findByCustomerAndAuction(int auctionId) {
		Bid res = bidRepository.findByCustomerAndAuction(auctionId, customerService.findByPrincipal().getId());
		Assert.notNull(res);
		return res;
	}

	public void delete(Bid bid) {
		Assert.notNull(bid);
		Customer customer = customerService.findByPrincipal();
		Date now = new Date(System.currentTimeMillis() - 1000);
		Assert.isTrue(!customer.equals(bid.getAuction().getCreator()));
		Assert.isTrue(now.after(bid.getAuction().getStartPeriod()) && bid.getAuction().getEndPeriod().after(now));
		
		bidRepository.delete(bid);
	}
	
}
