package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.StepRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Recipe;
import domain.Step;


@Service
@Transactional
public class StepService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private StepRepository stepRepository;
	
	// Supporting services ----------------------------------------------------
	
	// Constructors -----------------------------------------------------------
	
	public StepService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Step create(Recipe r) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("USER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Step result;

		
		result = new Step();
		result.setRecipe(r);
		
		return result;
	}

	public Collection<Step> findAll() {
		Collection<Step> result;

		result = stepRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Step findOne(int stepId) {
		Step result;

		result = stepRepository.findOne(stepId);
		Assert.notNull(result);

		return result;
	}

	public Step save(Step step) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("USER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(step);
		
		Step result;

		result = stepRepository.save(step);
		
		return result;
	}

	public void delete(Step step) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("USER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(step);
		Assert.isTrue(step.getId() != 0);

		stepRepository.delete(step);
	}
	
	// Other business methods -------------------------------------------------

}
