package functionalRequirement18;


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
import services.BidService;
import utilities.PopulateDatabase;
import domain.Bid;


@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MakeBid {
	
	//List active auctions that have been created by other customers.

		// Service Authenticate----------------------------------------

		@Autowired
		private LoginService loginService;
		@Autowired
		private BidService bidService;
	
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
		public void makeBid() {
			authenticate("customer1");
			Bid bid = bidService.create(23);
			
			bid.setMoneyAmount(44444.44);
			bidService.save(bid);
			
			Assert.isTrue(bidService.findByAuction(23).size()==1);
		}
		
		// Negative Test
		@Test(expected = IllegalArgumentException.class)
		public void makeBidMy() {
			authenticate("customer2");
			Bid bid = bidService.create(23);
			
			bid.setMoneyAmount(44444.44);
			bidService.save(bid);
			
		}
		
		@Test(expected = IllegalArgumentException.class)
		public void makeBidLess() {
			authenticate("customer1");
			Bid bid = bidService.create(23);
			
			bid.setMoneyAmount(44.44);
			bidService.save(bid);
			
		}
		
		@Test(expected = IllegalArgumentException.class)
		public void DeleteBid() {
			authenticate("customer1");
			Bid bid = bidService.create(22);
			
			bid.setMoneyAmount(44444.44);
			bidService.save(bid);
			
			Assert.isTrue(bidService.findByAuction(23).size()==1);
			
			bidService.delete(bid);
			bidService.findOne(bid.getId());
		}

}
