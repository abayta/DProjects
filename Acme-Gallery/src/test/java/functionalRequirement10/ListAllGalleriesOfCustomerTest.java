package functionalRequirement10;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Customer;
import domain.Gallery;

import security.LoginService;
import services.CustomerService;
import services.GalleryService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ListAllGalleriesOfCustomerTest {
	// An actor who is authenticated as a customer must be able to:
	// List galleries.

	// Service Authenticate----------------------------------------

	@Autowired
	private LoginService loginService;

	@Autowired
	private GalleryService galleryService;
	
	@Autowired
	private CustomerService customerService;

	// Authenticate-----------------------------------------------

	public void authenticate(String username) {
		UserDetails userDetails;
		TestingAuthenticationToken authenticationToken;
		SecurityContext context;

		userDetails = loginService.loadUserByUsername(username);
		authenticationToken = new TestingAuthenticationToken(userDetails, null);
		context = SecurityContextHolder.getContext();
		context.setAuthentication(authenticationToken);
	}

	@Before
	public void setUp() {
		PopulateDatabase.main(null);
	}

	// Positive Test
	@Test
	public void checkFindAllGalleriesOfCustomer() {
		authenticate("customer1");
		Customer customer = customerService.findByPrincipal();
		Collection<Gallery> galleries;
		galleries = galleryService.findAllOfCustomer(customer.getId());
		Assert.isTrue(galleries.size() == 1);
	}

}