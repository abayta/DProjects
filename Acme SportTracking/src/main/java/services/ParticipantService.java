package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.Participant;
import forms.ParticipantForm;

import repositories.ParticipantRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class ParticipantService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ParticipantRepository participantRepository;
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private AdministratorService administratorService;

	// Constructors -----------------------------------------------------------

	public ParticipantService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Participant create() {
		Participant result = new Participant();
		Assert.notNull(result);

		Authority authority = new Authority();
		authority.setAuthority(Authority.PARTICIPANT);
		UserAccount userAccount = new UserAccount();
		userAccount.addAuthority(authority);

		result.setUserAccount(userAccount);

		return result;
	}

	public void save(Participant participant) {
		Assert.notNull(participant);
		String password = participant.getUserAccount().getPassword();
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		password = encoder.encodePassword(password, null);
		participant.getUserAccount().setPassword(password);
		participantRepository.save(participant);
	}

	// Other business methods -------------------------------------------------

	public Participant findByPrincipal() {
		Participant result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByUserAccount(userAccount);
		Assert.notNull(result);
		return result;
	}

	public Participant findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		Participant result;
		result = participantRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	// Ancillary methods -----------------------------------------------------
	
	// List the participants who have joined the events he or she has created. (Silver Services)
	public Collection<Participant> findJoinedEventsByAdmin() {
		Administrator administrator = administratorService.findByPrincipal();
		Collection<Participant> participants;
		participants = participantRepository.findJoinedEventsByAdmin(administrator.getId());
		return participants;
	}
	
	public Collection<Participant> findJoinedEvent(int eventId) {
		Collection<Participant> participants;
		participants = participantRepository.findJoinedEvent(eventId);
		return participants;
	}
	
	public ParticipantForm construct(){		
		ParticipantForm participantForm = new ParticipantForm();
		Participant participant = create();
		
		participantForm.setId(participant.getId());
		participantForm.setVersion(participant.getVersion());
		participantForm.setName(participant.getName());
		participantForm.setSurname(participant.getName());
		participantForm.setEmailAddress(participant.getEmailAddress());
		participantForm.setCreditCard(participant.getCreditCard());
		participantForm.setUsername(participant.getUserAccount().getUsername());
		participantForm.setPassword(participant.getUserAccount().getPassword());
		
		return participantForm;
	}
	
	public Participant reconstruct(ParticipantForm participantForm) {
		Participant participant = create();
		
		participant.setId(participantForm.getId());
		participant.setVersion(participantForm.getVersion());
		participant.setName(participantForm.getName());
		participant.setSurname(participantForm.getName());
		participant.setEmailAddress(participantForm.getEmailAddress());
		participant.setCreditCard(participantForm.getCreditCard());		
		participant.getUserAccount().setUsername(participantForm.getUsername());
		participant.getUserAccount().setPassword(participantForm.getPassword());
		
		Assert.isTrue(participantForm.getAcceptTerms());
		Assert.isTrue(participant.getUserAccount().getPassword().equals(participantForm.getConfirmPassword()));

		return participant;
	}

}