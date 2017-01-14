package controllers.socialActor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.BannerService;
import services.NutritionistService;
import services.RecipeService;
import services.RelationDislikeService;
import services.RelationLikeService;
import services.UserService;
import domain.Actor;
import domain.Recipe;
import domain.RelationDislike;
import domain.RelationLike;
import domain.SocialActor;
import domain.User;

@Controller
@RequestMapping("socialActor/dislike")
public class SocialActorDislikeController {
	//Services--------------------------------------------------
	
		@Autowired
		private NutritionistService nutritionistService;
		
		@Autowired
		private UserService userService;
	
		@Autowired
		private RecipeService recipeService;
		
		@Autowired
		private RelationDislikeService relationDislikeService;
		
		@Autowired
		private RelationLikeService relationLikeService;
		
		@Autowired
		private BannerService bannerService;
		
		@Autowired
		private ActorService actorService;
		
		//Constructor-----------------------------------------------
		
		public SocialActorDislikeController(){
			super();
		}
				
		// Like ---------------------------------------------
		@RequestMapping(value="/create", method=RequestMethod.GET)
		public ModelAndView create(@RequestParam int recipeId) {
			ModelAndView result;
			SocialActor actor;
			RelationDislike relationDislike;
			Collection<RelationLike> relationLikes;
			Recipe recipe;
			String banner;
			UserAccount userAccount;
			userAccount = LoginService.getPrincipal();
			Authority au = new Authority();
			au.setAuthority("USER");
			Authority au1 = new Authority();
			au1.setAuthority("NUTRITIONIST");
			
			recipe= recipeService.findOne(recipeId);
			
			if(userAccount.getAuthorities().contains(au)){
				actor = userService.findByPrincipal();
			}else{
				actor = nutritionistService.findByPrincipal();
			}
			relationDislike = relationDislikeService.create();
			relationDislike.setSocialActor(actor);
			relationDislike.setRecipe(recipe);

			relationDislikeService.save(relationDislike);
			
			relationLikes = relationLikeService.findAll();
			for(RelationLike rl : relationLikes){
				if(rl.getRecipe()==recipe &&
						rl.getSocialActor()==actor){
					relationLikeService.delete(rl);
				}
			}

			banner = bannerService.showBannerNotStarred();
			result = new ModelAndView("recipe/display");
			result.addObject("quantities", recipe.getQuantities());
			result.addObject("recipe", recipe);
			result.addObject("steps", recipe.getSteps());
			result.addObject("comments", recipe.getComments());
			try{
				if(!LoginService.getPrincipal().equals(null)){
					User user;
					Actor actor2 = actorService.findByPrincipal();
					Authority au2 = new Authority();
					au.setAuthority("USER");
					if(actor2.getUserAccount().getAuthorities().contains(au2)){
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
			
		// Unlike --------------------------
		@RequestMapping(value="/delete", method=RequestMethod.GET)
		public ModelAndView delete(@RequestParam int recipeId) {
			ModelAndView result;
			SocialActor actor;
			Recipe recipe;
			String banner;
			Collection<RelationDislike> relationDislikes;
			UserAccount userAccount;
			userAccount = LoginService.getPrincipal();
			Authority au = new Authority();
			au.setAuthority("USER");
			Authority au1 = new Authority();
			au1.setAuthority("NUTRITIONIST");
			
			recipe= recipeService.findOne(recipeId);
			if(userAccount.getAuthorities().contains(au)){
				actor = userService.findByPrincipal();
			}else{
				actor = nutritionistService.findByPrincipal();
			}
			relationDislikes = relationDislikeService.findAll();
			for(RelationDislike rl : relationDislikes){
				if(rl.getRecipe()==recipe &&
						rl.getSocialActor()==actor){
					relationDislikeService.delete(rl);
				}
			}
			
			banner = bannerService.showBannerNotStarred();
			result = new ModelAndView("recipe/display");
			result.addObject("quantities", recipe.getQuantities());
			result.addObject("recipe", recipe);
			result.addObject("steps", recipe.getSteps());
			result.addObject("comments", recipe.getComments());
			try{
				if(!LoginService.getPrincipal().equals(null)){
					User user;
					Actor actor2 = actorService.findByPrincipal();
					Authority au2 = new Authority();
					au.setAuthority("USER");
					if(actor2.getUserAccount().getAuthorities().contains(au2)){
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
		
}