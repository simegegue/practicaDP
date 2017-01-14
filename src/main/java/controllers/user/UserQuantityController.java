package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Ingredient;
import domain.Quantity;
import domain.Recipe;
import domain.Unit;
import domain.User;

import security.Authority;
import security.LoginService;
import services.ActorService;
import services.BannerService;
import services.IngredientService;
import services.QuantityService;
import services.RecipeService;
import services.UnitService;
import services.UserService;

@Controller
@RequestMapping("/recipe/quantity")
public class UserQuantityController {
	
	//Services--------------------------------------------------
			
		@Autowired
		private RecipeService recipeService;
			
		@Autowired
		private IngredientService ingredientService;
			
		@Autowired
		private QuantityService quantityService;
			
		@Autowired
		private UnitService	unitService;
			
		@Autowired
		private BannerService bannerService;
		
		@Autowired
		private ActorService actorService;
		
		@Autowired
		private UserService	userService;
			
	// Constructor -------------------------------------------------
			
	public UserQuantityController(){
		super();
	}
	
	// Create ------------------------------------------------------
	
	@RequestMapping(value="/addIngredient", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int recipeId){
		ModelAndView result;
		Quantity quantity = quantityService.create(recipeId);
		Collection<Ingredient> ingredient = ingredientService.findAll();
		Collection<Unit> units = unitService.findAll();
		
		result = new ModelAndView("quantity/addIngredient");
		result.addObject("quantity", quantity);
		result.addObject("ingredients", ingredient);
		result.addObject("units", units);
		
		return result;
	}
	
	@RequestMapping(value="/addIngredient", method = RequestMethod.POST, params="save")
	public ModelAndView save(@ModelAttribute("quantity") @Valid Quantity quantity, BindingResult binding){
		
		ModelAndView result;
		String banner;
		Collection<Quantity> quantitiesRecipe = quantity.getRecipe().getQuantities();
		Collection<Quantity> quantitiesIngredient = quantity.getIngredient().getQuantities();
		Recipe recipe = quantity.getRecipe();
		Ingredient ingredient =quantity.getIngredient();
		
		if(binding.hasErrors()){
			result = createEditModelAndView(quantity, null);
		}else{
			try{
				quantityService.save(quantity);
				
				quantitiesRecipe.add(quantity);
				quantitiesIngredient.add(quantity);
				recipe.setQuantities(quantitiesRecipe);
				ingredient.setQuantities(quantitiesIngredient);
				recipeService.save(recipe);
				ingredientService.save2(ingredient);
				
				banner = bannerService.showBannerNotStarred();
				
				result = new ModelAndView("recipe/display");
				result.addObject("quantities", recipe.getQuantities());
				result.addObject("recipe", recipe);
				result.addObject("steps", recipe.getSteps());
				result.addObject("comments", recipe.getComments());
				try{
					if(!LoginService.getPrincipal().equals(null)){
						User user;
						Actor actor = actorService.findByPrincipal();
						Authority au = new Authority();
						au.setAuthority("USER");
						if(actor.getUserAccount().getAuthorities().contains(au)){
							user = userService.findByPrincipal();
							if(recipe.getUser().equals(user)){
								result.addObject("user", user);
							}
						}
					}
				}catch(Throwable oops){
					
				}
				if(banner != null){
					result.addObject("banner", banner);
				}
			}catch(Throwable oops){
				result = createEditModelAndView(quantity, "recipe.addIngredient.error");
			}
		}
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int quantityId){
		ModelAndView result;
		Quantity quantity = quantityService.findOne(quantityId);
		Assert.notNull(quantity);
		
		result = createEditModelAndView(quantity);
		
		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int quantityId){
		ModelAndView result;
		String banner;
		Quantity quantity = quantityService.findOne(quantityId);
		Recipe recipe = quantity.getRecipe();
		
		
		try{
			quantityService.delete(quantity);
			banner = bannerService.showBannerNotStarred();
			
			result = new ModelAndView("recipe/display");
			result.addObject("quantities", recipe.getQuantities());
			result.addObject("recipe", recipe);
			result.addObject("steps", recipe.getSteps());
			result.addObject("comments", recipe.getComments());
			try{
				if(!LoginService.getPrincipal().equals(null)){
					User user;
					Actor actor = actorService.findByPrincipal();
					Authority au = new Authority();
					au.setAuthority("USER");
					if(actor.getUserAccount().getAuthorities().contains(au)){
						user = userService.findByPrincipal();
						if(recipe.getUser().equals(user)){
							result.addObject("user", user);
						}
					}
				}
			}catch(Throwable oops){
				
			}
			if(banner != null){
				result.addObject("banner", banner);
			}
		}catch(Throwable oops){
			result = createEditModelAndView(quantity, "quantity.commit.error");
		}
		
		return result;
	}
	
	// Ancillary methods -------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Quantity quantity){
		
		ModelAndView result;
		result = createEditModelAndView(quantity, null);
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Quantity quantity, String message){
		ModelAndView result;
		
		result=new ModelAndView("quantity/addIngredient");
		
		Collection<Ingredient> ingredient = ingredientService.findAll();
		Collection<Unit> units = unitService.findAll();
		
		result.addObject("quantity", quantity);
		result.addObject("ingredients", ingredient);
		result.addObject("units", units);
		result.addObject("message",message);

		return result;
	}

}
