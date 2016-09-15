package functionalRequirement16;

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
import services.BidService;
import services.CustomerService;
import services.OwnershipService;
import utilities.PopulateDatabase;
import domain.Auction;
import domain.Bid;
import domain.Customer;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ChooseWinner {
	
	//When an auction finishes, list the corresponding bids and choose a winning bid.

	// Service Authenticate----------------------------------------

	@Autowired
	private LoginService loginService;
	@Autowired
	private AuctionService auctionService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private BidService bidService;
	@Autowired
	private OwnershipService ownershipService;
	
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
	public void chooseWinnerBid() {
		authenticate("customer1");
		auctionService.choose(25, 21);
		Auction auction = auctionService.findOne(21);
		Customer customer = customerService.findByPrincipal();
		Bid bid = bidService.findOne(25);
		Assert.isTrue(auction.getWinner().equals(bid));
		Assert.isTrue(auction.getCreator().equals(customer));
		Assert.notNull(ownershipService.findOneByLegalOwner(auction.getPainting().getId(), bid.getCustomer().getId()));
		
	}
	
	//Bid other Auction
	@Test(expected = IllegalArgumentException.class)
	public void chooseWinnerBidOtherAuction() {
		authenticate("customer1");
		auctionService.choose(26, 21);
	}
	
	//Auction with winner
	@Test(expected = IllegalArgumentException.class)
	public void choose2WinnerAuction() {
		authenticate("customer1");
		auctionService.choose(26, 21);
		auctionService.choose(24, 21);
	}

}
