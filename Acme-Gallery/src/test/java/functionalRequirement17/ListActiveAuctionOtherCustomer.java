package functionalRequirement17;

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

import security.LoginService;
import services.AuctionService;
import utilities.PopulateDatabase;
import domain.Auction;


@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ListActiveAuctionOtherCustomer {
	
	//List active auctions that have been created by other customers.

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
		public void listActiveAuctionOtherCustomer() {
			authenticate("customer1");
			Collection<Auction> auctions = auctionService.findActiveAuctionsOtherCustomer();
			Assert.isTrue(auctions.size()==1);
			Auction auction = auctionService.findOne(23);
			Assert.isTrue(auctions.contains(auction));
		
		}

}
