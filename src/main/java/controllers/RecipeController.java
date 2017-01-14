package controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.ActorService;
import services.BannerService;
import services.NutritionistService;
import services.RecipeService;
import services.UserService;
import domain.Actor;
import domain.Comment;
import domain.Nutritionist;
import domain.Quantity;
import domain.Recipe;
import domain.Step;
import domain.User;

@Controller
@RequestMapping("/recipe")
public class RecipeController extends AbstractController{
	
	//Services-------------------------------------------

	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private BannerService bannerService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private NutritionistService nutritionistService;
	
	//Constructor----------------------------------------
	
	public RecipeController(){
		super();
		
	}
	
	// List Recipes -----------------
	
	@RequestMapping(value="/listRecipe", method=RequestMethod.GET)
	public ModelAndView listRecipe(int userId){
		ModelAndView result;
		Collection<Recipe>recipes;
		Map<Recipe, Collection<String>> nomCategories = new HashMap<Recipe,Collection<String>>();
		
		User user = userService.findOne(userId);
		recipes=recipeService.findByCreator(user);
		
		for(Recipe r : recipes){
			nomCategories.put(r, recipeService.findNameByCategory(r));
		}
		
		result= new ModelAndView("recipe/list");
		result.addObject("nomCategories", nomCategories);
		result.addObject("recipes",recipes);
		result.addObject("requestURI", "recipe/listRecipe.do");
		
		return result;
	}
	
	//Browse---------------------------------------------
	
	@RequestMapping(value="/browse", method=RequestMethod.GET)
	public ModelAndView browse(){
		ModelAndView result;
		Collection<Recipe>recipes;
		Map<Recipe, Collection<String>> nomCategories = new HashMap<Recipe, Collection<String>>();
		
		recipes=recipeService.listByCategory();
		for(Recipe r : recipes){
			nomCategories.put(r, recipeService.findNameByCategory(r));
		}
		
		result= new ModelAndView("recipe/browse");
		result.addObject("recipes",recipes);
		result.addObject("requestURI", "recipe/browse.do");
		result.addObject("nomCategories", nomCategories);
		
		return result;
	}
	
	// SearchByKeyword -------------------------------------------------------
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public ModelAndView search(@RequestParam String key) {
			ModelAndView result;
			String requestUri="recipe/search.do?key="+key;
			try{
				Collection<Recipe>recipes = recipeService.findByKey(key);
				
				result=new ModelAndView("recipe/browse");
				result.addObject("recipes", recipes);
				result.addObject("requestURI", requestUri);
			}catch(Throwable oops){
				result= browse();
			}
			return result;
		}
	
	@RequestMapping(value="/display", method=RequestMethod.GET)
	public ModelAndView display(@RequestParam int recipeId) {
			ModelAndView result;
			String banner;
			Recipe recipe = recipeService.findOne(recipeId);
			
			Collection<Comment> comments = recipe.getComments();
			Collection<Step> steps = recipe.getSteps();
			banner = bannerService.showBannerNotStarred();
			Collection<Quantity> quantities = recipe.getQuantities();
			
			
			
			result=new ModelAndView("recipe/display");
			result.addObject("recipe", recipe);
			result.addObject("comments",comments);
			result.addObject("steps",steps);
			result.addObject("quantities", quantities);
			
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
			return result;
		}
	
	@RequestMapping(value="/stream",method=RequestMethod.GET)
	public ModelAndView stream(){
		ModelAndView result;
		Collection<Recipe>recipes;
		Map<Recipe, Collection<String>> nomCategories = new HashMap<Recipe, Collection<String>>();
		Actor a=actorService.findByPrincipal();
		Authority au=new Authority();
		au.setAuthority("USER");
		if(a.getUserAccount().getAuthorities().contains(au)){
			User us=userService.findByPrincipal();
			recipes=userService.findRecipeFromFollowing(us);
		}else{
			Nutritionist us=nutritionistService.findByPrincipal();
			recipes=nutritionistService.findRecipeFromFollowing(us);
		}
		
		for(Recipe r : recipes){
			nomCategories.put(r, recipeService.findNameByCategory(r));
		}
		
		result= new ModelAndView("recipe/browse");
		result.addObject("recipes",recipes);
		result.addObject("requestURI", "recipe/browse.do");
		result.addObject("nomCategories", nomCategories);
		
		return result;
	}
}
