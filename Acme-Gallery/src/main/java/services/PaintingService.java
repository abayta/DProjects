package services;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Auction;
import domain.Customer;
import domain.Ownership;
import domain.Painting;
import forms.PaintingForm;
import repositories.PaintingRepository;

@Service
@Transactional
public class PaintingService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PaintingRepository paintingRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private AuctionService auctionService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OwnershipService ownershipService;
	
	// Constructor ------------------------------------------------------------

	public PaintingService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public Painting create() {
		Painting result = new Painting();

		return result;
	}

	public void save(Painting painting) {
		Assert.notNull(painting);
		Ownership ownership = ownershipService.create();
		if(painting.getId()==0) {
			painting.addOwnership(ownership);
		 }else
			 checkByPrincipal(painting);		

		Collection<Auction> auctions = auctionService.findAllWithoutActiveAuctions(painting.getId());

		if(auctions!=null){
			Assert.isTrue(auctions.isEmpty());
		}
		
		paintingRepository.save(painting);
	
	}

	public Collection<Painting> findAllOfCustomer() {
		Customer customer = customerService.findByPrincipal();
		return paintingRepository.findAllOfCustomer(customer.getId());
	}
	
	public Collection<Painting> findAllOfCustomerAnonymousRole(int customerId) {
		return paintingRepository.findAllOfCustomer(customerId);
	}
	
	public Painting findOne(int paintingId){
		Painting painting = paintingRepository.findOne(paintingId);
		Assert.notNull(painting);
		return painting;
	}
	
	
	public Collection<Painting> findAllWithoutGalleryOfCustomer() {
		Customer customer = customerService.findByPrincipal();
		return paintingRepository.findAllWithoutGalleryOfCustomer(customer.getId());
	}
	
	public Collection<Painting> findAllOfGallery(int galleryId) {
		return paintingRepository.findAllOfGallery(galleryId);
	}
	
	public Collection<Painting> findAll() {
		return paintingRepository.findAll();
	}
	
	public Collection<Painting> findWithMoreComments(){
		Collection<Painting> paintings = paintingRepository.findWithMoreComments();
		return paintings;
	}

	// Ancillary methods -------------------------------------------------------
	
	public void checkByPricinpal(Painting painting) {
		Customer customer = customerService.findByPrincipal();
		Collection<Painting> paintings = paintingRepository.findAllOfCustomer(customer.getId());
		
		Assert.isTrue(paintings.contains(painting));
	}

	public Painting reconstruct(PaintingForm paintingForm) {
		Painting res;
		
		Assert.notNull(paintingForm);
		if(paintingForm.getId()==0){
			res = create();
		}else{
			res = findOne(paintingForm.getId());
		}
		res.setAppraisal(paintingForm.getAppraisal());
		res.setAuthor(paintingForm.getAuthor());
		res.setDate(paintingForm.getDate());
		res.setTitle(paintingForm.getTitle());
		
		return res;
	}

	public boolean isMine(int paintingId) {
		boolean res = true;
		Customer customer = customerService.findByPrincipal();
		Ownership ownership = ownershipService.findOneByLegalOwner(paintingId, customer.getId());
		if(ownership==null){
			res=false;
		}
		return res;
	}

	private void checkByPrincipal(Painting painting) {
		 Customer customer = customerService.findCustomer(painting.getId());
		 Assert.isTrue(customerService.findByPrincipal().equals(customer));
	}
	
}
