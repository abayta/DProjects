package functionalRequirement09;

import java.util.Date;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Painting;

import forms.PaintingForm;

import security.LoginService;
import services.PaintingService;

import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
// An actor who is authenticated as a customer must be able to:
// Register a painting.
public class RegisterPaintingTest {

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
	public void checkRegisterPainting() {
		authenticate("customer1");
		PaintingForm paintingForm = new PaintingForm();
		paintingForm.setTitle("painting99");
		paintingForm.setAuthor("author99");
		paintingForm.setDate(new Date(1900));
		paintingForm.setAppraisal(55900.90);
		paintingForm.setGallery(null);
		Painting result = paintingService.reconstruct(paintingForm);
		paintingService.save(result);
		Assert.isTrue(paintingService.findAllOfCustomer().size() == 2);
	}

	// Negative Test
	// Check that an IllegalArgumentException is thrown when trying 
	// to save a null object "painting"
	@Test(expected=IllegalArgumentException.class)
	public void checkRegisterPaintingNegative() {
		authenticate("customer1");
		Painting result = null;
		paintingService.save(result);
	}
	
}

