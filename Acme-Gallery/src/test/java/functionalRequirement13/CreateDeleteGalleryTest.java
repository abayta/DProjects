package functionalRequirement13;

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

import domain.Gallery;

import security.LoginService;
import services.GalleryService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)

// An actor who is authenticated as an administrator must be able to:
// Create new groups.

public class CreateDeleteGalleryTest {

	// Service Authenticate----------------------------------------

	@Autowired
	private LoginService loginService;

	@Autowired
	private GalleryService galleryService;

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
		authenticate("customer1");
		Gallery gallery = galleryService.create();
		gallery.setTitle("Title");
		galleryService.save(gallery);
		Assert.isTrue(gallery.getTitle()=="Title");
	}
	
	// Positive Test
	@Test
	public void checkCreateDeleteGalleryPositive() {
		authenticate("customer1");
		Gallery gallery = galleryService.create();
		gallery.setTitle("Title");
		galleryService.delete(gallery);
	}

	// Negative Test
	@Test(expected = IllegalArgumentException.class)
	public void checkCreateDeleteGalleryNegative() {
		authenticate("admin2");
		Gallery gallery = galleryService.findOne(11);
		galleryService.delete(gallery);
	}

}
