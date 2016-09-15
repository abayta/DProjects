package services;

import java.util.Collection;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Auction;
import domain.Bid;
import domain.Customer;
import domain.Painting;
import repositories.AuctionRepository;

@Service
@Transactional
public class AuctionService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AuctionRepository auctionRepository;

	// Services ---------------------------------------------------------------
	 @Autowired
	 private CustomerService customerService;
	 @Autowired
	 private PaintingService paintingService;
	 @Autowired
	 private BidService bidService;
	 @Autowired
	 private OwnershipService ownershipService;

	// Constructors -----------------------------------------------------------

	public AuctionService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public Auction create(int paintingId){
		Assert.isTrue(paintingService.isMine(paintingId));
		Painting painting = paintingService.findOne(paintingId);
		Auction auction = new Auction();
		auction.setCreator(customerService.findByPrincipal());
		auction.setPainting(painting);
		
		return auction;
	}
	
	public void save(Auction auction){
		Assert.isTrue(paintingService.isMine(auction.getPainting().getId()));
		Assert.notNull(auction);
		checkByPrincipal(auction);
		Assert.isTrue(auction.getBids().isEmpty());
		Assert.isTrue(auction.getEndPeriod().after(auction.getStartPeriod()));
		Assert.isTrue(auction.getEndPeriod().getTime()>auction.getStartPeriod().getTime()+8640000);
		Assert.isTrue(auction.getStartPeriod().after(new Date()));
		if(auction.getId()==0){
			Assert.isNull(auctionRepository.findByPaintingFinished(auction.getPainting().getId()));
		}
		
		auctionRepository.save(auction);
		
	}
	
	public void delete (Auction auction){
		Assert.notNull(auction);
		checkByPrincipal(auction);
		Assert.isTrue(auction.getBids().isEmpty());
		Assert.isTrue(auction.getStartPeriod().after(new Date()));
		
		auctionRepository.delete(auction);
	}
	
	public Collection<Auction> findActiveAuctionsOtherCustomer(){
		Customer customer = customerService.findByPrincipal();
		Collection<Auction> auctions = auctionRepository.findActiveAuctionsOtherCustomer(customer.getId());
		Assert.notNull(auctions);
		return auctions;
	}
	
	public Collection<Auction> findFinishedAuction(){
		Collection<Auction> res = auctionRepository.findFinishedAuction();
		return res;
	}
	
	public Auction findByPaintingFinished(int paintingId){
		return auctionRepository.findByPaintingFinished(paintingId);
	}
	
	public Collection<Auction> findActiveAuctions(){
		Collection<Auction> auctions = auctionRepository.findActiveAuctions();
		Assert.notNull(auctions);
		return auctions;
	}
	
	public Auction findOne(int auctionId){
		Auction auction = auctionRepository.findOne(auctionId);
		Assert.notNull(auction);
		return auction;
	}

	
	private void checkByPrincipal(Auction auction){
		Customer customer = customerService.findByPrincipal();
		Assert.isTrue(auction.getCreator().equals(customer));
	}

	
	public Collection<Auction> findAllWithoutActiveAuctions(int paintingId) {
		return auctionRepository.findAllWithoutActiveAuctions(paintingId);
	}

	public Collection<Auction> findWithMoreBids(){
		Collection<Auction> auctions = auctionRepository.findWithMoreBids();
		return auctions;
	}

	public Collection<Auction> findCreatedAuctions() {
		Collection<Auction> auctions = auctionRepository.findCreatedAuction(customerService.findByPrincipal().getId());
		Assert.notNull(auctions);
		return auctions;
	}

	public void choose(int bidId, int auctionId) {
		Date now = new Date(System.currentTimeMillis()-1);
		Bid bid = bidService.findOne(bidId);
		Assert.isTrue(bid.getAuction().getId()==auctionId);
		Auction auction = findOne(auctionId);
		Assert.isTrue(now.after(auction.getEndPeriod()));
		Assert.isNull(auction.getWinner());
		auction.setWinner(bid);
		auctionRepository.save(auction);
		ownershipService.saveForWinner(bid);
		
	}
}
