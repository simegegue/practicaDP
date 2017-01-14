package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculaRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Curricula;
import domain.Endorser;
import domain.Nutritionist;
@Service
@Transactional
public class CurriculaService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CurriculaRepository curriculaRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private NutritionistService nutritionistService;
	
	@Autowired
	private EndorserService endorserService;
	
	// Constructors -----------------------------------------------------------
	
	public CurriculaService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Curricula create() {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("NUTRITIONIST");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Curricula result;
	
		result = new Curricula();
		
		return result;
	}

	public Collection<Curricula> findAll() {
		Collection<Curricula> result;

		result = curriculaRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Curricula findOne(int curriculaId) {
		Curricula result;

		result = curriculaRepository.findOne(curriculaId);
		Assert.notNull(result);

		return result;
	}

	public Curricula save(Curricula curricula) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("NUTRITIONIST");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(curricula);
		
		Curricula result;
		
		result = curriculaRepository.save(curricula);
		
		return result;
	}

	public void delete(Curricula curricula) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("NUTRITIONIST");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(curricula);
		Assert.isTrue(curricula.getId() != 0);
		
		Collection<Endorser> endorsers = curricula.getEndorsers();
		for(Endorser e : endorsers){
			endorserService.delete(e);
		}
		
		Nutritionist nutritionist = nutritionistService.findByPrincipal();

		nutritionist.setCurricula(null);
		
		curriculaRepository.delete(curricula);
	}
	
	// Other business methods -------------------------------------------------
	
	public Curricula findByPrincipal() {
		Curricula result;
		UserAccount userAccount;
		
		userAccount = LoginService.getPrincipal();
		result = curriculaRepository.findByUserAccount(userAccount);		
		
		return result;
	}

}
