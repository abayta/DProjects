package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.User;
import forms.UserForm;
import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class UserService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private UserRepository userRepository;

	// Supporting services ----------------------------------------------------

	// Constructor ------------------------------------------------------------
	public UserService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public User create() {
		User result = new User();
		Assert.notNull(result);

		Authority authority = new Authority();
		authority.setAuthority(Authority.USER);
		UserAccount userAccount = new UserAccount();
		userAccount.addAuthority(authority);

		result.setUserAccount(userAccount);

		return result;
	}

	public void save(User user) {
		Assert.notNull(user);
		if (user.getId() == 0) {
			String password = user.getUserAccount().getPassword();
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			password = encoder.encodePassword(password, null);
			user.getUserAccount().setPassword(password);
		}
		userRepository.save(user);
	}

	public User findByPrincipal() {
		User result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByUserAccount(userAccount);
		Assert.notNull(result);
		return result;
	}

	public User findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		return userRepository.findOneByUserAccount(userAccount.getId());
	}

	// Ancillary methods -------------------------------------------------------

	public UserForm construct() {
		UserForm userForm = new UserForm();
		User user = create();

		userForm.setId(user.getId());
		userForm.setVersion(user.getVersion());
		userForm.setName(user.getName());
		userForm.setSurname(user.getName());
		userForm.setEmailAddress(user.getEmailAddress());
		userForm.setGroup(user.getGroup());
		userForm.setUsername(user.getUserAccount().getUsername());
		userForm.setPassword(user.getUserAccount().getPassword());

		return userForm;
	}

	public User reconstruct(UserForm userForm) {
		User user = create();

		user.setId(userForm.getId());
		user.setVersion(userForm.getVersion());
		user.setName(userForm.getName());
		user.setSurname(userForm.getName());
		user.setEmailAddress(userForm.getEmailAddress());
		user.setGroup(userForm.getGroup());
		user.getUserAccount().setUsername(userForm.getUsername());
		user.getUserAccount().setPassword(userForm.getPassword());

		Assert.isTrue(userForm.getAcceptTerms());
		Assert.isTrue(user.getUserAccount().getPassword().equals(userForm.getConfirmPassword()));

		return user;
	}

}
