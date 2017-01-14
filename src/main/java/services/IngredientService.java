package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.IngredientRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Ingredient;
import domain.Property;
import domain.Quantity;

@Service
@Transactional
public class IngredientService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private IngredientRepository ingredientRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private QuantityService quantityService;
	
	
	
	// Constructors -----------------------------------------------------------
	
	public IngredientService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Ingredient create() {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("NUTRITIONIST");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Ingredient result;
		Collection<Property> properties = new ArrayList<Property>();
		Collection<Quantity> quantities = new ArrayList<Quantity>();

		result = new Ingredient();
		result.setProperties(properties);
		result.setQuantities(quantities);

		return result;
	}

	public Collection<Ingredient> findAll() {
		Collection<Ingredient> result;

		result = ingredientRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Ingredient findOne(int ingredientId) {
		Ingredient result;

		result = ingredientRepository.findOne(ingredientId);
		Assert.notNull(result);

		return result;
	}

	public Ingredient save(Ingredient ingredient) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("NUTRITIONIST");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(ingredient);
		
		Ingredient result;

		result = ingredientRepository.save(ingredient);
		
		return result;
	}

	public void delete(Ingredient ingredient) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("NUTRITIONIST");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(ingredient);
		//Assert.isTrue(ingredient.getId() != 0);
		
		Collection<Quantity> quantities = ingredient.getQuantities();
		for(Quantity q : quantities){
			quantityService.delete(q);
		}

		ingredientRepository.delete(ingredient);
	}
	
	public void save2(Ingredient ingredient){
		ingredientRepository.save(ingredient);
	}
	
	// Other business methods -------------------------------------------------

}
