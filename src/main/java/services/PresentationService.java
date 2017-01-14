package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PresentationRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Presentation;

@Service
@Transactional
public class PresentationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PresentationRepository presentationRepository;
	
	// Supporting services ----------------------------------------------------
	
	// Constructors -----------------------------------------------------------
	
	public PresentationService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Presentation create() {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("COOK");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Presentation result;

		result = new Presentation();

		return result;
	}

	public Collection<Presentation> findAll() {
		Collection<Presentation> result;

		result = presentationRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Presentation findOne(int presentationId) {
		Presentation result;

		result = presentationRepository.findOne(presentationId);
		Assert.notNull(result);

		return result;
	}

	public Presentation save(Presentation presentation) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("COOK");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(presentation);
		
		Presentation result;

		result = presentationRepository.save(presentation);
		
		return result;
	}

	public void delete(Presentation presentation) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("COOK");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(presentation);
		Assert.isTrue(presentation.getId() != 0);

		presentationRepository.delete(presentation);
	}
	
	// Other business methods -------------------------------------------------

}
