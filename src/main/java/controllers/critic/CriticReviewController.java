package controllers.critic;

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

import security.Authority;
import security.LoginService;
import services.CriticService;
import services.NutritionistService;
import services.RecipeService;
import services.ReviewService;
import services.SocialActorService;
import services.UserService;

import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Category;
import domain.Critic;
import domain.Recipe;
import domain.Review;
import domain.Step;
import domain.User;

@Controller
@RequestMapping("/review")
public class CriticReviewController extends AbstractController{
	//Services----------------------------------------------------
		@Autowired
		private ReviewService reviewService;
		
		@Autowired
		private CriticService criticService;
		
		@Autowired
		private RecipeService recipeService;
	//Constructor-------------------------------------------------
	public CriticReviewController(){
		super();
	}
	
	// List -------------------------------------------------------

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		Collection<Review> reviews;

		reviews=reviewService.findAll();
			
		result= new ModelAndView("review/list");
		result.addObject("reviews",reviews);
		result.addObject("requestURI", "review/list.do");
		
		return result;
	}
	
	//Creation-------------------------
	
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create(){
		
			ModelAndView result;
			Review review;
			Critic critic =criticService.findByPrincipal();
			
			review = reviewService.create();
			review.setCritic(critic);
			result=new ModelAndView("review/edit");
			result.addObject("review",review);
			Collection<Recipe>recipes=recipeService.findAll();
			result.addObject("recipes", recipes);
			return result;
			
		}
	
	//Edition--------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int reviewId){
		
		ModelAndView result;
		Review review;
		Collection<Recipe> recipes;
		
		review = reviewService.findOne(reviewId);
		Assert.notNull(review);
		recipes = recipeService.findAll();
		if(review.getCritic()==criticService.findByPrincipal()){
			result=new ModelAndView("review/edit");
			result.addObject("review",review);
			result.addObject("recipes", recipes);
		}else{
			result = new ModelAndView("review/list");
		}
		
		return result;
		
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Review review, BindingResult binding){
		
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(review, null);
		}else{
			try{
				reviewService.save(review);
				result = list();
			}catch(Throwable oops){
				result = createEditModelAndView(review, "review.commit.error");
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Review review, BindingResult binding){
		
		ModelAndView result;
		
		try{
			reviewService.delete(review);
			result = list();
		}catch(Throwable oops){
			result = createEditModelAndView(review, "category.commit.error");
		}
	return result;	
	}
	
	// Ancillary methods ----------------------------------------------------------------
		
	protected ModelAndView createEditModelAndView(Review review, String message){
		ModelAndView result;
		result=new ModelAndView("review/edit");
		
		result.addObject("review",review);
		result.addObject("message",message);
		return result;
	}
}