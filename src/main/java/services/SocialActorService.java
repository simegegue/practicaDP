package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialActorRepository;
import security.LoginService;
import domain.SocialActor;

@Service
@Transactional
public class SocialActorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SocialActorRepository socialActorRepository;
	
	// Supporting services ----------------------------------------------------
	
	// Constructors -----------------------------------------------------------
	
	public SocialActorService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public SocialActor create() {
		SocialActor result;

		result = new SocialActor();

		return result;
	}

	public Collection<SocialActor> findAll() {
		Collection<SocialActor> result;

		result = socialActorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public SocialActor findOne(int socialActorId) {
		SocialActor result;

		result = socialActorRepository.findOne(socialActorId);
		Assert.notNull(result);

		return result;
	}

	public SocialActor findByPrincipal(){
		SocialActor result;
		int userAccountId;
		
		userAccountId = LoginService.getPrincipal().getId();
		result = socialActorRepository.findByUserAccountId(userAccountId);
		Assert.notNull(result);
		
		return result;
	}
	
	
	public SocialActor save(SocialActor socialActor) {
		Assert.notNull(socialActor);
		
		SocialActor result;

		result = socialActorRepository.save(socialActor);
		
		return result;
	}

	public void delete(SocialActor socialActor) {
		Assert.notNull(socialActor);
		Assert.isTrue(socialActor.getId() != 0);

		socialActorRepository.delete(socialActor);
	}
	
	// Other business methods -------------------------------------------------
	
}
