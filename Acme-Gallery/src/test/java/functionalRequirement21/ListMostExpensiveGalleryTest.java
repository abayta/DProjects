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

import domain.Gallery;

import security.LoginService;
import services.GalleryService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ListMostExpensiveGalleryTest {

	// List most expensive gallery.

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
	public void checkFindAllMostExpensiveGallery() {
		Collection<Object[]> galleriesObject;
		galleriesObject = galleryService.findMostExpensive();
		List<Object[]> galleriesListObject = (List<Object[]>) galleriesObject;
		Object[] object = galleriesListObject.get(0);
		Gallery gallery = (Gallery) object[0];
		Double appraisal = (Double) object[1];
		Assert.isTrue(galleriesObject.size() == 1);
		Assert.isTrue(gallery.getId() == 11 && appraisal == 45000.0);
	}

}