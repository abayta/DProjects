package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.OwnershipRepository;

import domain.Bid;
import domain.Ownership;

@Service
@Transactional
public class OwnershipService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private OwnershipRepository ownershipRepository;
	
	@Autowired CustomerService customerService;

	// Supporting services ----------------------------------------------------

	// Constructor ------------------------------------------------------------

	public OwnershipService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Ownership create() {
		Ownership result = new Ownership();
		Assert.notNull(result);
		result.setLegalOwner(customerService.findByPrincipal());
		result.setStartMoment(new Date());

		return result;
	}

	public void save(Ownership ownership) {
		
		Assert.notNull(ownership);
		Assert.notNull(ownership);
		ownership.setStartMoment(new Date(System.currentTimeMillis() - 1000));

		ownershipRepository.save(ownership);
	}

	public Collection<Ownership> findAllByPainting(int paintingId) {
		return ownershipRepository.findAllByPainting(paintingId);
	}

	public void saveForWinner(Bid bid) {
		Ownership oldOwnership;
		Ownership newOwnership;
		
		oldOwnership = ownershipRepository.findByPaintingAndCustomer(bid.getAuction().getPainting().getId(),
				bid.getAuction().getCreator().getId());
		oldOwnership.setEndMoment(new Date(System.currentTimeMillis() - 1000));
		newOwnership = new Ownership();
		newOwnership.setLegalOwner(bid.getCustomer());
		newOwnership.setPainting(bid.getAuction().getPainting());
		newOwnership.setStartMoment(new Date(System.currentTimeMillis() - 1000));
		
		ownershipRepository.save(oldOwnership);
		ownershipRepository.save(newOwnership);
		
	}
	

	public Ownership findOneByLegalOwner(int paintingId, int customerId){
		Ownership ownership = ownershipRepository.findOneByLegalOwner(paintingId, customerId);
		return ownership;
	}

	// Ancillary methods -------------------------------------------------------

}
