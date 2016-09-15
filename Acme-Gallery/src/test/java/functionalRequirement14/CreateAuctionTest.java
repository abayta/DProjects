package functionalRequirement14;

import java.util.Date;


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

import domain.Auction;

import security.LoginService;
import services.AuctionService;

import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)

// An actor who is authenticated as an administrator must be able to:
// Create new groups.
@Transactional
public class CreateAuctionTest {

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
	public void checkCreatePositive() {
		authenticate("customer2");
		Auction auction = auctionService.create(19);
		auction.setStartingPrice(63.36);
		auction.setStartPeriod(new Date(System.currentTimeMillis()+86400000));
		auction.setEndPeriod(new Date(System.currentTimeMillis()+172800000));
		auctionService.save(auction);
		
		Assert.notNull(auctionService.findByPaintingFinished(19));
	}
	
	//Delete auction without bid
	@Test(expected = IllegalArgumentException.class)
	public void checkDeletePositive() {
		authenticate("customer2");
		Auction auction = auctionService.findOne(23);
		auctionService.delete(auction);
		auctionService.findOne(23);		
	}
	
	// Negative Test
	//subasta para una pintura que no es de su propiedad
	@Test(expected = IllegalArgumentException.class)
	public void checkCreateNegative() {
		authenticate("customer2");
		Auction auction = auctionService.create(12);
		auction.setStartingPrice(63.36);
		auction.setStartPeriod(new Date(System.currentTimeMillis()+86400000));
		auction.setEndPeriod(new Date(System.currentTimeMillis()+259200000));
		auctionService.save(auction);
	}
	
	//Intenta crear una subasta de menos de un dia de duracion
	@Test(expected = IllegalArgumentException.class)
	public void checkCreateNegativeLessOneDay() {
		authenticate("customer2");
		Auction auction = auctionService.create(12);
		auction.setStartingPrice(63.36);
		auction.setStartPeriod(new Date(System.currentTimeMillis()+86400000));
		auction.setEndPeriod(new Date(System.currentTimeMillis()+93600000));
		auctionService.save(auction);
	}
}

