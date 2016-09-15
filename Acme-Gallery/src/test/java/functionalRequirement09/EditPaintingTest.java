package functionalRequirement09;

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

import domain.Painting;

import security.LoginService;
import services.PaintingService;

import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
// An actor who is authenticated as a customer must be able to:
// Edit a painting as long as it's not involved in an active auction.
public class EditPaintingTest {

	// Service Authenticate----------------------------------------

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private PaintingService paintingService;
	
	@Before
	public void setUp() {
		PopulateDatabase.main(null);
	}

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
	
	// Positive Test
	@Test
	public void checkEditPainting() {
		authenticate("customer1");
		Painting painting = paintingService.findOne(12);
		painting.setAuthor("author00");
		paintingService.save(painting);
		Assert.isTrue(paintingService.findOne(12).getAuthor().equals("author00"));
	}
	
	// Negative Test
	// Check that an IllegalArgumentException is thrown when trying 
	// to save an "painting" of other customer.
	@Test(expected = IllegalArgumentException.class)
	public void checkEditPaintingNegative() {
		authenticate("customer2");
		Painting painting = paintingService.findOne(12);
		painting.setAuthor("author00");
		paintingService.save(painting);
	}
	
	// Negative Test
	// Check that an IllegalArgumentException is thrown when trying 
	// to save a null object "painting"
	@Test(expected=IllegalArgumentException.class)
	public void checkEditPaintingNegative2() {
		Painting result = null;
		paintingService.save(result);
	}
	
}

