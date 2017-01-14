package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.QuantityRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Ingredient;
import domain.Quantity;
import domain.Recipe;

@Service
@Transactional
public class QuantityService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private QuantityRepository quantityRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private IngredientService ingredientService;
	
	// Constructors -----------------------------------------------------------
	
	public QuantityService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Quantity create(int recipeId) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("USER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Quantity result;
		Recipe recipe= recipeService.findOne(recipeId);

		result = new Quantity();
		result.setMeasure(0.0);
		result.setRecipe(recipe);

		return result;
	}

	public Collection<Quantity> findAll() {
		Collection<Quantity> result;

		result = quantityRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Quantity findOne(int quantityId) {
		Quantity result;

		result = quantityRepository.findOne(quantityId);
		Assert.notNull(result);

		return result;
	}

	public Quantity save(Quantity quantity) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("USER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(quantity);
		
		Quantity result;

		result = quantityRepository.save(quantity);
		
		return result;
	}

	public void delete(Quantity quantity) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("USER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(quantity);
		Assert.isTrue(quantity.getId() != 0);
		
		Collection<Quantity> quantitiesRecipe = quantity.getRecipe().getQuantities();
		Collection<Quantity> quantitiesIngredient = quantity.getIngredient().getQuantities();
		Recipe recipe = quantity.getRecipe();
		Ingredient ingredient = quantity.getIngredient();
		
		quantitiesRecipe.remove(quantity);
		recipe.setQuantities(quantitiesRecipe);
		quantitiesIngredient.remove(quantity);
		ingredient.setQuantities(quantitiesIngredient);
		recipeService.save(recipe);
		ingredientService.save2(ingredient);
		
		
		quantityRepository.delete(quantity);
	}
	
	// Other business methods -------------------------------------------------
	
}
