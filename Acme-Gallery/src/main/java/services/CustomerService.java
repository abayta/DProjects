package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Customer;
import forms.CustomerForm;
import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class CustomerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CustomerRepository customerRepository;

	// Supporting services ----------------------------------------------------

	// Constructor ------------------------------------------------------------

	public CustomerService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Customer create() {
		Customer result = new Customer();
		Assert.notNull(result);

		Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		UserAccount userAccount = new UserAccount();
		userAccount.addAuthority(authority);

		result.setUserAccount(userAccount);

		return result;
	}

	public void save(Customer customer) {
		Assert.notNull(customer);
		if (customer.getId() == 0) {
			String password = customer.getUserAccount().getPassword();
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			password = encoder.encodePassword(password, null);
			customer.getUserAccount().setPassword(password);
		}
		customerRepository.save(customer);
	}

	public Customer findByPrincipal() {
		Customer result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByCustomerAccount(userAccount);
		Assert.notNull(result);
		return result;
	}

	public Customer findByCustomerAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		return customerRepository.findOneByUserAccount(userAccount.getId());
	}

	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	public Collection<Customer> findWithMorePaintings(){
		Collection<Customer> customers = customerRepository.findWithMorePaintings();
		return customers;
	}
	
	public Collection<Customer> findWithMoreComments(){
		Collection<Customer> customers = customerRepository.findWithMoreComments();
		return customers;
	}
	
	public Customer findCustomer(int paintingId){
		Customer customer = customerRepository.findCustomer(paintingId);
		return customer;
	}
	
	// Ancillary methods -------------------------------------------------------

	public CustomerForm construct() {
		CustomerForm customerForm = new CustomerForm();
		Customer customer = create();

		customerForm.setId(customer.getId());
		customerForm.setVersion(customer.getVersion());
		customerForm.setName(customer.getName());
		customerForm.setSurname(customer.getName());
		customerForm.setEmailAddress(customer.getEmailAddress());
		customerForm.setCreditCard(customer.getCreditCard());
		customerForm.setUsername(customer.getUserAccount().getUsername());
		customerForm.setPassword(customer.getUserAccount().getPassword());

		return customerForm;
	}

	public Customer reconstruct(CustomerForm customerForm) {
		Customer customer = create();
		Calendar date = new GregorianCalendar();
		
		customer.setId(customerForm.getId());
		customer.setVersion(customerForm.getVersion());
		customer.setName(customerForm.getName());
		customer.setSurname(customerForm.getName());
		customer.setEmailAddress(customerForm.getEmailAddress());
		customer.setCreditCard(customerForm.getCreditCard());
		customer.getUserAccount().setUsername(customerForm.getUsername());
		customer.getUserAccount().setPassword(customerForm.getPassword());

		Assert.isTrue(customerForm.getCreditCard().getExpirationYear()>=date.get(Calendar.YEAR));
		if (customerForm.getCreditCard().getExpirationYear()==date.get(Calendar.YEAR))
			Assert.isTrue(customerForm.getCreditCard().getExpirationMonth()>=date.get(Calendar.MONTH));
		Assert.isTrue(customerForm.getAcceptTerms());
		Assert.isTrue(customer.getUserAccount().getPassword()
				.equals(customerForm.getConfirmPassword()));

		return customer;
	}

}
