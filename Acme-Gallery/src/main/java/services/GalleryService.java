package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Customer;
import domain.Gallery;
import repositories.GalleryRepository;

@Service
@Transactional
public class GalleryService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private GalleryRepository galleryRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private CustomerService customerService;
	
	// Constructor ------------------------------------------------------------

	public GalleryService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Collection<Gallery> findAll() {
		Collection<Gallery> galleries = galleryRepository.findAll();
		return galleries;
	}
	
	public Collection<Gallery> findAllOfCustomer(int customerId) {
		return galleryRepository.findAllOfCustomer(customerId);
	}
	
	public Gallery findOne(int id) {
		Gallery result;
		result = galleryRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}
	
	public Gallery findOneToEdit(int id) {
		Gallery result;
		result = galleryRepository.findOne(id);
		checkPrincipal(result);
		return result;
	}
	
	public Gallery create() {
		Customer customer = customerService.findByPrincipal();
		Gallery result = new Gallery();
		result.setCustomer(customer);
		return result;
	}

	public void save(Gallery gallery) {
		checkPrincipal(gallery);
		Assert.notNull(gallery);
		Assert.isTrue(gallery.getPaintings().isEmpty());
		galleryRepository.save(gallery);
	}

	public void delete(Gallery gallery) {
		checkPrincipal(gallery);
		Assert.notNull(gallery);
		Assert.isTrue(gallery.getPaintings().isEmpty());
		galleryRepository.delete(gallery);
	}

	public Collection<Object[]> findMostExpensive(){
		Collection<Object[]> galleries = galleryRepository.findMostExpensive();
		return galleries;
	}
	
	// Ancillary methods -------------------------------------------------------
		
	public void checkPrincipal(Gallery gallery) {
		Assert.isTrue(customerService.findByPrincipal().equals(
				gallery.getCustomer()));
	}
}
