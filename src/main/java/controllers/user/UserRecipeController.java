package controllers.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.IngredientService;
import services.RecipeService;
import services.UserService;
import domain.Category;
import domain.Ingredient;
import domain.Recipe;
import domain.User;

@Controller
@RequestMapping("/recipe")
public class UserRecipeController {
	//Services--------------------------------------------------
	
		@Autowired
		private UserService userService;
		
		@Autowired
		private RecipeService recipeService;
		
		@Autowired
		private CategoryService categoryService;
		
		@Autowired
		private IngredientService ingredientService;
		
		//Constructor-----------------------------------------------
		
		public UserRecipeController(){
			super();
		}
		
		@RequestMapping(value = "/listing", method = RequestMethod.GET)
		public ModelAndView listing(){
			
			ModelAndView result;
			
			User user = userService.findByPrincipal();
			Collection<Recipe> recipes = recipeService.findByCreator(user);
			Map<Recipe, Collection<String>> nomCategories = new HashMap<Recipe,Collection<String>>();
			
			for(Recipe r : recipes){
				nomCategories.put(r, recipeService.findNameByCategory(r));
			}
			
			result =new ModelAndView("recipe/listing");
			result.addObject("recipes", recipes);
			result.addObject("nomCategories", nomCategories);
			result.addObject("requestURI", "recipe/listing.do");
			
			return result;
			
		}
		
		// Create Recipe---------------------------------------------
		@RequestMapping(value="/create", method=RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			
			Recipe recipe = recipeService.create();
			result = createEditModelAndView(recipe, null);

			return result;
			}
			
		//Edition--------------------------
		
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int recipeId){
			
			ModelAndView result;
			Recipe recipe;
			
			recipe = recipeService.findOne(recipeId);
			Assert.notNull(recipe);
			result = createEditModelAndView(recipe, null);
			
			return result;
			
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Recipe recipe, BindingResult binding){
			
			ModelAndView result;
			
			if(binding.hasErrors()){
				result = createEditModelAndView(recipe, null);
			}else{
				try{
					recipe = recipeService.updateMoment(recipe);
					recipeService.save(recipe);
					result = listing();
				}catch(Throwable oops){
					result = createEditModelAndView(recipe, "recipe.commit.error");
				}
			}
			return result;
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(Recipe recipe, BindingResult binding){
			
			ModelAndView result;
			
			try{
				if(recipe.getQualified()==true){
					result = createEditModelAndView(recipe, "recipe.commit.error");
				}else{
					recipeService.delete(recipe);
				}
				
				result = listing();
			}catch(Throwable oops){
				result = createEditModelAndView(recipe, "recipe.commit.error");
			}
		return result;	
		}
		
		// Ancillary methods ----------------------------------------------------------------
			
		protected ModelAndView createEditModelAndView(Recipe recipe, String message){
			ModelAndView result;
			List<Category> categories = new ArrayList<Category>();
			List<Ingredient> ingredients = new ArrayList<Ingredient>();
			
			result=new ModelAndView("recipe/create");
			categories.addAll(categoryService.findAll());
			ingredients.addAll(ingredientService.findAll());
			
			result.addObject("recipe",recipe);
			result.addObject("message",message);
			result.addObject("categories", categories);
			result.addObject("ingredients", ingredients);
			return result;
		}
		
		
		
}
