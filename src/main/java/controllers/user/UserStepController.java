package controllers.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.ActorService;
import services.BannerService;
import services.RecipeService;
import services.StepService;

import services.UserService;

import domain.Actor;
import domain.Recipe;
import domain.Step;
import domain.User;

@Controller
@RequestMapping("/step")
public class UserStepController {
	//Services--------------------------------------------------
	
		@Autowired
		private BannerService bannerService;
		
		@Autowired
		private RecipeService recipeService;
				
		@Autowired
		private StepService stepService;
		
		@Autowired
		private UserService userService;
		
		@Autowired
		private ActorService actorService;
		
		//Constructor-----------------------------------------------
		
		public UserStepController(){
			super();
		}
		
		// Register Step---------------------------------------------
		@RequestMapping(value="/create", method=RequestMethod.GET)
		public ModelAndView create(@RequestParam int recipeId) {
			ModelAndView result;
			
			Step step = stepService.create(recipeService.findOne(recipeId));
			result = createEditModelAndView(step, null);

			return result;
			}
			
		//Edition--------------------------
		
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int stepId){
			
			ModelAndView result;
			Step step;
			
			step = stepService.findOne(stepId);
			Assert.notNull(step);
			if(step.getRecipe().getUser()==userService.findByPrincipal()){
				result = createEditModelAndView(step, null);
			}else{
				result = new ModelAndView("recipe/listing");
			}
			
			return result;
			
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Step step, BindingResult binding){
			
			ModelAndView result;
			Recipe recipe;
			String banner;
			
			if(binding.hasErrors()){
				result = createEditModelAndView(step, null);
			}else{
				try{
					stepService.save(step);
					recipe = step.getRecipe();
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
					result = createEditModelAndView(step, "step.commit.error");
				}
			}
			return result;
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(@Valid Step step, BindingResult binding){
			
			ModelAndView result;
			Recipe recipe;
			String banner;
			
			try{
				stepService.delete(step);
				recipe = step.getRecipe();
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
				result = createEditModelAndView(step, "step.commit.error");
			}
		return result;	
		}
		// Ancillary methods ----------------------------------------------------------------
			
		protected ModelAndView createEditModelAndView(Step step, String message){
			ModelAndView result;
			result=new ModelAndView("step/register");
			
			result.addObject("step",step);
			result.addObject("message",message);
			return result;
		}
		
}
