package functionalRequirement15;

import javax.transaction.Transactional;

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

import security.LoginService;
import services.AuctionService;
import services.CustomerService;
import utilities.PopulateDatabase;
import domain.Auction;
import domain.Customer;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ListBidsAndDetailsAuction {
	
	// Give an auction that he or she's created, he or she must be able to list the bids that
	//have been made and their details.

	// Service Authenticate----------------------------------------

	@Autowired
	private LoginService loginService;
	@Autowired
	private AuctionService auctionService;
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
	public void checkFindAllGalleryOfCustomer() {
		authenticate("customer1");
		Customer customer = customerService.findByPrincipal();
		Auction auction = auctionService.findOne(21);
		Assert.isTrue(auction.getBids().size()==2);
		Assert.isTrue(auction.getCreator().equals(customer));
		
	}

}
