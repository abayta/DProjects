package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.Event;
import domain.Route;
import forms.AdministratorForm;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AdministratorRepository administratorRepository;
	
	// Services----------------------------------------------------------------
	@Autowired
	private RouteService routeService;
	@Autowired
	private EventService eventService;

	// Constructors -----------------------------------------------------------

	public AdministratorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Administrator create() {
		Administrator result = new Administrator();
		Assert.notNull(result);

		Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		UserAccount userAccount = new UserAccount();
		userAccount.addAuthority(authority);

		result.setUserAccount(userAccount);

		return result;
	}

	public void save(Administrator administrator) {
		Assert.notNull(administrator);
		String password = administrator.getUserAccount().getPassword();
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		password = encoder.encodePassword(password, null);
		administrator.getUserAccount().setPassword(password);
		administratorRepository.save(administrator);
	}

	// Other business methods -------------------------------------------------

	public Administrator findByPrincipal() {
		Administrator result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByUserAccount(userAccount);
		Assert.notNull(result);
		return result;
	}

	public Administrator findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		Administrator result;
		result = administratorRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	// Ancillary methods -----------------------------------------------------
	
	public void calculateRating(){
		for(Authority a:LoginService.getPrincipal().getAuthorities()){
			   Assert.isTrue(a.getAuthority().equals("ADMIN"));
		}
		
		Collection<Route> routes = routeService.findAllRoutes();
		Collection<Event> events = eventService.findAllEvents();
		
		for (Event e:events){
			if(!e.getRegistrations().isEmpty()){
				Long sumRating = administratorRepository.sumaRatingEvent(e.getId());
				Long numberRating = administratorRepository.numberRatingEvent(e.getId());
				if(sumRating!=null){
					Double res = (double) sumRating/numberRating;
					eventService.saveRating(e.getId(), res);
				}
			}
		}		
		
		for(Route r:routes){
			if(!r.getAssessmentsRoutes().isEmpty()){
				Long sumRating = administratorRepository.sumaRatingRoute(r.getId());
				Long numberRating = administratorRepository.numberRatingRoute(r.getId());
				if(sumRating!=null){
					Double res = (double)sumRating/numberRating;
					routeService.saveRating(r.getId(), res);
				}
			}
		}

	}
	
	public AdministratorForm construct(){		
		AdministratorForm administratorForm = new AdministratorForm();
		Administrator administrator = create();
		
		administratorForm.setId(administrator.getId());
		administratorForm.setVersion(administrator.getVersion());
		administratorForm.setName(administrator.getName());
		administratorForm.setSurname(administrator.getName());
		administratorForm.setEmailAddress(administrator.getEmailAddress());
		administratorForm.setUsername(administrator.getUserAccount().getUsername());
		administratorForm.setPassword(administrator.getUserAccount().getPassword());
		
		return administratorForm;
	}
	
	public Administrator reconstruct(AdministratorForm administratorForm) {
		Administrator administrator = create();
		
		administrator.setId(administratorForm.getId());
		administrator.setVersion(administratorForm.getVersion());
		administrator.setName(administratorForm.getName());
		administrator.setSurname(administratorForm.getName());
		administrator.setEmailAddress(administratorForm.getEmailAddress());
		administrator.getUserAccount().setUsername(administratorForm.getUsername());
		administrator.getUserAccount().setPassword(administratorForm.getPassword());
		
		Assert.isTrue(administratorForm.getAcceptTerms());
		Assert.isTrue(administrator.getUserAccount().getPassword().equals(administratorForm.getConfirmPassword()));

		return administrator;
	}
	
}