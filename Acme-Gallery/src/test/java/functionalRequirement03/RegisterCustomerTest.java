package functionalRequirement03;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.CreditCard;
import domain.Customer;
import forms.CustomerForm;

import security.LoginService;
import services.CustomerService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
// An actor who is not authenticated must be able to:
// Register to the system as a customer.

public class RegisterCustomerTest {

	// Service Authenticate----------------------------------------

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private CustomerService customerService;
	
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
	public void checkCreateSaveAndLoginCustomerPositive() {
		Customer result;
		CreditCard creditCard = new CreditCard();
		creditCard.setBrandName("Brand Name");
		creditCard.setCvv(123);
		creditCard.setExpirationMonth(04);
		creditCard.setExpirationYear(2017);
		creditCard.setHolderName("Holder Name");
		creditCard.setNumber("4716505616609967");
		CustomerForm customerForm = new CustomerForm();
		customerForm.setUsername("customer4");
		customerForm.setPassword("customer4");
		customerForm.setConfirmPassword("customer4");
		customerForm.setAcceptTerms(true);
		customerForm.setName("Name");
		customerForm.setSurname("Surname");
		customerForm.setEmailAddress("Email@email.com");
		customerForm.setCreditCard(creditCard);
		customerForm.setId(99);
		customerForm.setVersion(1);
		result = customerService.reconstruct(customerForm);
		customerService.save(result);
		authenticate("customer4");
	}
	
	// Negative Test
	// Check that an DataIntegrityViolationException is thrown when trying 
	// to save an "username" called "customer1", because it already exists in the database
	@Test(expected=DataIntegrityViolationException.class)
	public void checkCreateAndSaveCustomerNegative() {
		Customer result;
		CreditCard creditCard = new CreditCard();
		creditCard.setBrandName("Brand Name");
		creditCard.setCvv(123);
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2017);
		creditCard.setHolderName("Holder Name");
		creditCard.setNumber("4716505616609967");
		CustomerForm customerForm = new CustomerForm();
		customerForm.setUsername("customer1");
		customerForm.setPassword("customer1");
		customerForm.setConfirmPassword("customer1");
		customerForm.setAcceptTerms(true);
		customerForm.setName("Name");
		customerForm.setSurname("Surname");
		customerForm.setEmailAddress("Email@email.com");
		customerForm.setCreditCard(creditCard);
		customerForm.setId(99);
		customerForm.setVersion(1);
		result = customerService.reconstruct(customerForm);
		customerService.save(result);
	}
	
	// Negative Test
	// Check that an IllegalArgumentException is thrown when trying 
	// to save a null object "customer"
	@Test(expected=IllegalArgumentException.class)
	public void checkSaveCustomerNegative2() {
		Customer result = null;
		customerService.save(result);
	}
	
}