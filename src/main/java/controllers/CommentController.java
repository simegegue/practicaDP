package controllers;

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
import security.UserAccount;
import services.ActorService;
import services.BannerService;
import services.CommentService;
import services.NutritionistService;
import services.RecipeService;
import services.UserService;
import domain.Actor;
import domain.Comment;
import domain.Nutritionist;
import domain.Recipe;
import domain.User;


@Controller
@RequestMapping("/comment")
	public class CommentController {
		//Services--------------------------------------------------
		
			@Autowired
			private BannerService bannerService;
			
			@Autowired
			private RecipeService recipeService;
					
			@Autowired
			private CommentService commentService;
			
			@Autowired
			private UserService userService;
			
			@Autowired
			private ActorService actorService;
			
			@Autowired
			private NutritionistService nutritionistService;
			
			//Constructor-----------------------------------------------
			
			public CommentController(){
				super();
			}
			
			// Register Comment ---------------------------------------------
			@RequestMapping(value="/create", method=RequestMethod.GET)
			public ModelAndView create(@RequestParam int recipeId) {
				ModelAndView result;
				Recipe recipe;
				
				recipe = recipeService.findOne(recipeId);
				
				Comment comment = commentService.create(recipe);
				result = createEditModelAndView(comment, null);

				return result;
				}
				
			//Edition--------------------------
			
			@RequestMapping(value = "/edit", method = RequestMethod.GET)
			public ModelAndView edit(@RequestParam int commentId){
				
				ModelAndView result;
				Comment comment;
				User user;
				Nutritionist nutritionist;
				UserAccount userAccount;
				userAccount = LoginService.getPrincipal();
				Authority au = new Authority();
				au.setAuthority("USER");
				Authority au1 = new Authority();
				au1.setAuthority("NUTRITIONIST");
				
				
				comment = commentService.findOne(commentId);
				Assert.notNull(comment);
				
				if(userAccount.getAuthorities().contains(au)){
					user = userService.findByPrincipal();
					if(comment.getUser()==user){
						result = createEditModelAndView(comment,null);
					}else{
						Recipe recipe;
						String banner;
						
						recipe = comment.getRecipe();
						banner = bannerService.showBannerNotStarred();
						
						result = new ModelAndView("recipe/display");
						result.addObject("quantities", recipe.getQuantities());
						result.addObject("recipe", recipe);
						result.addObject("steps", recipe.getSteps());
						result.addObject("comments", recipe.getComments());
						try{
							if(!LoginService.getPrincipal().equals(null)){
								User user2;
								Actor actor = actorService.findByPrincipal();
								Authority au2 = new Authority();
								au.setAuthority("USER");
								if(actor.getUserAccount().getAuthorities().contains(au2)){
									user2 = userService.findByPrincipal();
									if(recipe.getUser().equals(user2)){
										result.addObject("user", user2);
									}
								}
							}
						}catch(Throwable oops){
							
						}
						if(banner != null){
							result.addObject("banner", banner);
						}
					}
				}else{
					System.out.println(nutritionistService.findByPrincipal().getName());
					nutritionist = nutritionistService.findByPrincipal();
					if(comment.getNutritionist()==nutritionist){
						result = createEditModelAndView(comment,null);
					}else{
						Recipe recipe;
						String banner;
						
						recipe = comment.getRecipe();
						banner = bannerService.showBannerNotStarred();
						
						result = new ModelAndView("recipe/display");
						result.addObject("quantities", recipe.getQuantities());
						result.addObject("recipe", recipe);
						result.addObject("steps", recipe.getSteps());
						result.addObject("comments", recipe.getComments());
						try{
							if(!LoginService.getPrincipal().equals(null)){
								User user3;
								Actor actor = actorService.findByPrincipal();
								Authority au2 = new Authority();
								au.setAuthority("USER");
								if(actor.getUserAccount().getAuthorities().contains(au2)){
									user3 = userService.findByPrincipal();
									if(recipe.getUser().equals(user3)){
										result.addObject("user", user3);
									}
								}
							}
						}catch(Throwable oops){
							
						}
						if(banner != null){
							result.addObject("banner", banner);
						}
					}
				}
				
				return result;
				
			}
			
			@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
			public ModelAndView save(@Valid Comment comment, BindingResult binding){
				
				ModelAndView result;
				Recipe recipe;
				String banner;
				
				if(binding.hasErrors()){
					result = createEditModelAndView(comment, null);
				}else{
					try{
						commentService.save(comment);
						recipe = comment.getRecipe();
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
						result = createEditModelAndView(comment, "comment.commit.error");
					}
				}
				return result;
			}
			
			// Ancillary methods ----------------------------------------------------------------
				
			protected ModelAndView createEditModelAndView(Comment comment, String message){
				ModelAndView result;
				result=new ModelAndView("comment/register");
				result.addObject("comment",comment);
				result.addObject("message",message);
				return result;
			}

}
