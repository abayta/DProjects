package functionalRequirement21;

import java.util.Collection;
import java.util.List;

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

import domain.Auction;

import security.LoginService;
import services.AuctionService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ListAuctionMoreBidsTest {

	// List auction with more bids.

	// Service Authenticate----------------------------------------

	@Autowired
	private LoginService loginService;

	@Autowired
	private AuctionService auctionService;

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
	public void checkFindAllAuctionMoreBids() {
		Collection<Auction> auctions;
		auctions = auctionService.findWithMoreBids();
		List<Auction> auctionsList = (List<Auction>) auctions;
		Assert.isTrue(auctions.size() == 2);
		Assert.isTrue(auctionsList.get(0).getId() == 21);
		Assert.isTrue(auctionsList.get(1).getId() == 22);
		
	}

}