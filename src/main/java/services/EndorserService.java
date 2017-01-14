package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Endorser;

@Service
@Transactional
public class EndorserService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private EndorserRepository endorserRepository;
	
	// Supporting services ----------------------------------------------------
	
	// Constructors -----------------------------------------------------------
	
	public EndorserService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Endorser create() {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("NUTRITIONIST");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Endorser result;

		result = new Endorser();

		return result;
	}

	public Collection<Endorser> findAll() {
		Collection<Endorser> result;

		result = endorserRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Endorser findOne(int endorserId) {
		Endorser result;

		result = endorserRepository.findOne(endorserId);
		Assert.notNull(result);

		return result;
	}

	public Endorser save(Endorser endorser) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("NUTRITIONIST");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(endorser);
		
		Endorser result;

		result = endorserRepository.save(endorser);
		
		return result;
	}

	public void delete(Endorser endorser) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("NUTRITIONIST");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(endorser);
		Assert.isTrue(endorser.getId() != 0);

		endorserRepository.delete(endorser);
	}

	public Collection<Endorser> findByPrincipal() {
		Collection<Endorser> result;
		UserAccount userAccount;
		
		userAccount = LoginService.getPrincipal();
		result = endorserRepository.findByUserAccount(userAccount);
		return result;
	}
	
	
	// Other business methods -------------------------------------------------

}
