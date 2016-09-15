package functionalRequirement12;

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
import services.GalleryService;
import services.PaintingService;

import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
// An actor who is authenticated as a customer must be able to:
// Move a painting from one gallery to another.
public class MovePaintingOfGalleryTest {

	// Service Authenticate----------------------------------------

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private PaintingService paintingService;
	
	@Autowired
	private GalleryService galleryService;
	
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
	public void checkMovePaintingOfGalley() {
		authenticate("customer2");
		Painting painting = paintingService.findOne(19);
		painting.setGallery(galleryService.findOne(11));
		paintingService.save(painting);
		Assert.isTrue(paintingService.findOne(19).getGallery().equals(galleryService.findOne(11)));
	}
	
	// Negative Test
	// Check that an IllegalArgumentException is thrown when trying 
	// to save an "painting" of other customer.
	@Test(expected = IllegalArgumentException.class)
	public void checkMovePaintingOfGalleryNegative() {
		authenticate("customer2");
		Painting painting = paintingService.findOne(12);
		painting.setGallery(galleryService.findOne(11));
		paintingService.save(painting);
	}
	
}

