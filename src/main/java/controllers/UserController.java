package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



import domain.Nutritionist;
import domain.Recipe;
import domain.SocialActor;
import domain.User;


import security.Authority;
import services.NutritionistService;
import services.RecipeService;
import services.SocialActorService;
import services.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController{
	//Services----------------------------------------------------
	@Autowired
	private UserService userService;
	
	@Autowired 
	private RecipeService recipeService;
	
	
	@Autowired
	private NutritionistService nutritionistService;
	
	@Autowired
	private SocialActorService socialActorService;
	
	
	//Constructor-------------------------------------------------
	public UserController(){
		super();
	}
	
	//Browse------------------------------------------------------
	@RequestMapping(value="/browse", method=RequestMethod.GET)
	public ModelAndView browse(){
		ModelAndView result;
		Collection<User>users;
		Collection<Nutritionist>nutritionists;
		users=userService.findAll();
		nutritionists=nutritionistService.findAll();
		result= new ModelAndView("user/browse");
		result.addObject("users",users);
		result.addObject("nutritionists",nutritionists);
		result.addObject("requestURI", "user/browse.do");
		
		return result;
	}
	
	// SearchByKeyword -------------------------------------------------------
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public ModelAndView search(@RequestParam String key) {
			ModelAndView result;
			String requestUri="user/search.do?key="+key;
			try{
				Collection<User>users = userService.findByKey(key);
				
				result=new ModelAndView("user/browse");
				result.addObject("users", users);
				result.addObject("requestURI", requestUri);
			}catch(Throwable oops){
				result= browse();
			}
			return result;
		}
	
	@RequestMapping(value="/display", method=RequestMethod.GET)
	public ModelAndView display(@RequestParam int recipeId) {
			ModelAndView result;
			Recipe recipe=recipeService.findOne(recipeId);
			User user=recipe.getUser();
			result=new ModelAndView("user/display");
			result.addObject("user", user);
			return result;
		}
	
	@RequestMapping(value="/displayProfile", method=RequestMethod.GET)
	public ModelAndView displayProfile(@RequestParam int userId) {
			ModelAndView result;
			User user=userService.findOne(userId);
			result=new ModelAndView("user/display");
			result.addObject("user", user);
			return result;
		}
	//Follow
		@RequestMapping(value = "/userFollow", method = RequestMethod.GET)
		public ModelAndView userFollow(@RequestParam int userId) {
			ModelAndView result;
			try {
				User me=userService.findByPrincipal();
				User u= userService.findOne(userId);
				userService.followUser(me, u);
				result = browse();		
			} catch (Throwable oops) {			
				result = browse();
				result.addObject("message", "user.commit.error");			
			}
			return result;
			
		
		}
		
		@RequestMapping(value = "/followNutritionist", method = RequestMethod.GET)
		public ModelAndView followNutritionist(@RequestParam int userId) {
			ModelAndView result;
			try {
				User me=userService.findByPrincipal();
				Nutritionist n= nutritionistService.findOne(userId);
				userService.followNutritionist(me, n);
				result = browse();		
			} catch (Throwable oops) {			
				result = browse();
				result.addObject("message", "user.commit.error");			
			}
			return result;
			
		
		}
		
		@RequestMapping(value = "/nutritionistFollow", method = RequestMethod.GET)
		public ModelAndView nutritionistFollow(@RequestParam int userId) {
			ModelAndView result;
			try {
				Nutritionist me=nutritionistService.findByPrincipal();
				User u= userService.findOne(userId);
				nutritionistService.followUser(me, u);
				result = browse();	
			} catch (Throwable oops) {			
				result = browse();
				result.addObject("message", "user.commit.error");			
			}
			return result;
			
			
			
		
		}
		
		@RequestMapping(value = "/followUser", method = RequestMethod.GET)
		public ModelAndView followN(@RequestParam int userId) {
			ModelAndView result;
			try {
				Nutritionist me=nutritionistService.findByPrincipal();
				Nutritionist n= nutritionistService.findOne(userId);
				nutritionistService.followNutritionist(me, n);
				result = browse();	
			} catch (Throwable oops) {			
				result = browse();
				result.addObject("message", "user.commit.error");			
			}
			return result;
		}
		
		@RequestMapping(value="/following", method=RequestMethod.GET)
		public ModelAndView following(){
			ModelAndView result;
			
			User u=userService.findByPrincipal();
			
			
			result= new ModelAndView("user/following");
			result.addObject("users",u.getFollowing());
			result.addObject("requestURI", "user/following.do");
			
			return result;
			
		}
		@RequestMapping(value="/followingN", method=RequestMethod.GET)
		public ModelAndView followingN(){
			ModelAndView result;
			
			Nutritionist n=nutritionistService.findByPrincipal();
			
			
			result= new ModelAndView("user/following");
			result.addObject("users",n.getFollowing());
			result.addObject("requestURI", "user/following.do");
			
			return result;
		}
		@RequestMapping(value="/userUnfollow", method =RequestMethod.GET)
		public ModelAndView userUnfollow(@RequestParam int userId){
			ModelAndView result;
			try{
				User me=userService.findByPrincipal();
				SocialActor s=socialActorService.findOne(userId);
				Authority au=new Authority();
				au.setAuthority("USER");
				if(s.getUserAccount().getAuthorities().contains(au)){
					User u=userService.findOne(userId);
					userService.unfollowUser(me, u);
				}else{
					Nutritionist u=nutritionistService.findOne(userId);
					userService.unfollowNutritionist(me, u);
				}
				result = browse();	
			}catch (Throwable oops) {			
				result = browse();
				result.addObject("message", "user.commit.error");			
			}
			return result;
		}
		
		@RequestMapping(value="/nutritionistUnfollow", method =RequestMethod.GET)
		public ModelAndView nutritionistUnfollow(@RequestParam int userId){
			ModelAndView result;
			try{
				Nutritionist me=nutritionistService.findByPrincipal();
				SocialActor s=socialActorService.findOne(userId);
				Authority au=new Authority();
				au.setAuthority("USER");
				if(s.getUserAccount().getAuthorities().contains(au)){
					User u=userService.findOne(userId);
					nutritionistService.unfollowUser(me, u);
				}else{
					Nutritionist u=nutritionistService.findOne(userId);
					nutritionistService.unfollowNutritionist(me, u);
				}
				result = browse();	
			}catch (Throwable oops) {			
				result = browse();
				result.addObject("message", "user.commit.error");			
			}
			return result;
		}
		
}
