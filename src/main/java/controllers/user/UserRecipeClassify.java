package controllers.user;

import java.util.Collection;
import java.util.HashMap;
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
import services.ContestService;
import services.QualifyService;
import services.RecipeService;
import services.UserService;
import domain.Contest;
import domain.Qualify;
import domain.Recipe;
import domain.User;

@Controller
@RequestMapping("/qualify")
public class UserRecipeClassify {
	//Services--------------------------------------------------
		
		@Autowired
		private QualifyService qualifyService;
		
		@Autowired
		private ContestService contestService;

		@Autowired
		private RecipeService recipeService;
		
		@Autowired
		private UserService userService;
		
		//Constructor-----------------------------------------------
		
		public UserRecipeClassify(){
			super();
		}
		
		// Create Recipe---------------------------------------------
		@RequestMapping(value="/create", method=RequestMethod.GET)
		public ModelAndView create(@RequestParam int recipeId) {
			ModelAndView result;
			Recipe recipe;
			
			
			Qualify qualify = qualifyService.create();
			recipe = recipeService.findOne(recipeId);
			qualify.setRecipe(recipe);
			result = createEditModelAndView(qualify, null);

			return result;
			}
			
		//Edition--------------------------
		
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int qualifyId){
			
			ModelAndView result;
			Qualify qualify;
			
			qualify = qualifyService.findOne(qualifyId);
			Assert.notNull(qualify);
			result = createEditModelAndView(qualify, null);
			
			return result;
			
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Qualify qualify, BindingResult binding){
			
			ModelAndView result;
			Recipe recipe;
			Boolean verificador = true;
			Collection<Qualify> qualifies;
			User user;
			
			qualifies = qualifyService.findAll();
			user = userService.findByPrincipal();
			recipe = qualify.getRecipe();
			
			if(binding.hasErrors() || user!=recipe.getUser()){
				result = createEditModelAndView(qualify, null);
			}else{
				try{
					for(Qualify q : qualifies){
						if(q.getRecipe()==qualify.getRecipe() && 
								q.getContest()==qualify.getContest()){
							verificador=false;
						}
					}
					
					if(verificador){
						qualify.setPosition(0);
						qualifyService.save(qualify);
						recipe.setQualified(true);
						recipeService.save(recipe);
					}
					result =new ModelAndView("recipe/listing");
					result.addObject("requestURI", "recipe/listing.do");
				}catch(Throwable oops){
					result = new ModelAndView("recipe/listing");
					result.addObject("requestURI", "recipe/listing.do");
				}
			}
			
			
			
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
		
		// Ancillary methods ----------------------------------------------------------------
			
		protected ModelAndView createEditModelAndView(Qualify qualify, String message){
			ModelAndView result;
			Collection<Contest> contests;
			
			result=new ModelAndView("qualify/create");
			contests = contestService.findContestOpened();
			
		
			result.addObject("qualify",qualify);
			result.addObject("recipe", qualify.getRecipe());
			result.addObject("message",message);
			result.addObject("contest", contests);
			return result;
		}
		
}
