package functionalRequirement10;

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

import domain.Clinic;



import security.LoginService;
import services.ClinicService;

import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EditClinicTest {

	//Un actor que está autentificado como administrador debe ser capaz de:
	//Editar la información del perfil de una clínica.
	
	// Service Authenticate----------------------------------------
	
		@Autowired
		private ClinicService clinicService;
		
		@Autowired
		private LoginService loginService;

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
	public void checkEditClinicPositive() {
		authenticate("admin");
		Clinic clinic = clinicService.findOne(22);
		clinic.setEmail("test@test.com");
		Assert.isTrue(clinicService.findOne(22).getEmail().equals("test@test.com"));
	}

	// Negative Test
	@Test(expected=IllegalArgumentException.class)
	public void checkEditItemOnSaleNegative2() {
		authenticate("doctor1");
		clinicService.save(clinicService.findOne(22));
	}

}