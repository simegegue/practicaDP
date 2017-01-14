package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RecipeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Category;
import domain.Comment;
import domain.Contest;
import domain.Qualify;
import domain.Quantity;
import domain.Recipe;
import domain.RelationDislike;
import domain.RelationLike;
import domain.Step;
import domain.User;

@Service
@Transactional
public class RecipeService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RecipeRepository recipeRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QualifyService qualifyService;
	
	@Autowired
	private ContestService contestService;
	
	
	@Autowired
	private QuantityService quantityService;
	
	
	@Autowired
	private RelationLikeService relationLikeService;
	
	@Autowired
	private RelationDislikeService relationDislikeService;
	
	// Constructors -----------------------------------------------------------
	
	public RecipeService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Recipe create() {
		
		UserAccount userAccount;
		List<RelationLike> relationLikes = new ArrayList<RelationLike>();
		List<RelationDislike> relationDislikes = new ArrayList<RelationDislike>();
		List<Comment> comments = new ArrayList<Comment>();
		List<Category> categories = new ArrayList<Category>();
		List<Step> steps = new ArrayList<Step>();
		List<Quantity> quantities = new ArrayList<Quantity>();
		Date date = new Date(System.currentTimeMillis() - 1000);
		Authority au;
		
		userAccount = LoginService.getPrincipal();
		au = new Authority();
		au.setAuthority("USER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Recipe result;
		result = new Recipe();
		
		result.setUser(userService.findByPrincipal());
		result.setTicker(createTicker());
		result.setComments(comments);
		result.setRelationDislikes(relationDislikes);
		result.setRelationLikes(relationLikes);
		result.setCategories(categories);
		result.setSteps(steps);
		result.setQuantities(quantities);
		result.setQualified(false);
		result.setAuthoredMoment(date);
		result.setUpdateMoment(date);
		
		return result;
	}

	public Collection<Recipe> findAll() {
		Collection<Recipe> result;

		result = recipeRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Recipe findOne(int recipeId) {
		Recipe result;

		result = recipeRepository.findOne(recipeId);
		Assert.notNull(result);

		return result;
	}

	public Recipe save(Recipe recipe) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		Authority au2 = new Authority();
		au.setAuthority("USER");
		au2.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au) || userAccount.getAuthorities().contains(au2));
		
		Assert.notNull(recipe);
		
		Recipe result;

		result = recipeRepository.save(recipe);
		
		return result;
	}

	public void delete(Recipe recipe) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("USER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(recipe);
		Assert.isTrue(recipe.getId() != 0);

		Collection<Qualify> qualifies = qualifyService.findAll();
		for(Qualify q : qualifies){
			if(q.getRecipe().equals(recipe)){
				qualifyService.delete(q);
			}
		}
		
		Collection<RelationLike> relationLikes = relationLikeService.findAll();
		for(RelationLike rl : relationLikes){
			if(recipe.getRelationLikes().contains(rl)){
				relationLikeService.delete(rl);
			}
		}
		
		Collection<RelationDislike> relationDislikes = relationDislikeService.findAll();
		for(RelationDislike rl : relationDislikes){
			if(recipe.getRelationDislikes().contains(rl)){
				relationDislikeService.delete(rl);
			}
		}
		
		Collection<Quantity> quantities = quantityService.findAll();
		for(Quantity q : quantities){
			if(q.getRecipe().equals(recipe)){
				quantityService.delete(q);
			}
		}
		
		
		
	/*	
		
		if(steps.isEmpty()== false){
			for(Step s: steps){
				stepService.delete(s);
			}
		}
		if(quantities.isEmpty()== false){
			for(Quantity q : quantities){
				quantityService.delete(q);
			}
		}
		if(categories.isEmpty()== false){
			for(Category c:categories){
				Collection<Recipe> recipes = c.getRecipes();
				recipes.remove(recipe);
				c.setRecipes(recipes);
				categoryService.save2(c);
			}
		}
		if(rellationsDislike.isEmpty()== false){
			for(RelationDislike rdl : rellationsDislike){
				SocialActor socialActor = rdl.getSocialActor();
				Collection<RelationDislike> dislikes = socialActor.getRelationDislikes();
				dislikes.remove(rdl);
				socialActor.setRelationDislikes(dislikes);
				socialActorService.save(socialActor);
			}
		}
		if(rellationsLikes.isEmpty()== false){
			for(RelationLike rdl : rellationsLikes){
				SocialActor socialActor = rdl.getSocialActor();
				Collection<RelationLike> likes = socialActor.getRelationLikes();
				likes.remove(rdl);
				socialActor.setRelationLikes(likes);
				socialActorService.save(socialActor);
			}
		}
	
		for(Recipe r: user.getRecipes()){
			if(!r.getTicker().equals(recipe.getTicker())){
				recipes2.add(r);
			}
		}
		if(comments.isEmpty()== false){
			for(Comment c: comments){
				commentService.delete(c);
			}
		}
		*/
		//user.setRecipes(recipes2);
		//userService.save2(user);
		recipeRepository.delete(recipe);
	}
	

	
	// Other business methods -------------------------------------------------
	
	public Recipe updateMoment(Recipe r){
		
		Date date = new Date(System.currentTimeMillis() - 1000);
		r.setUpdateMoment(date);
		return r;
	}
	
	public Collection<Double> findMinStandardDeviationStepsPerRecipe(){
		Collection<Double> result = new ArrayList<Double>();
		
		Double avg = recipeRepository.findAvgStepsPerRecipe();
		Double stdDev = recipeRepository.findStandardDeviationStepsPerRecipe();
			
		if(avg == null || avg == 0){
			result.add(0.0);
		}else{
			result.add(avg);
		}
		if(stdDev == null || stdDev == 0){
			result.add(0.0);
		}else{
			result.add(stdDev);
		}

		return result;
	}
	
	public Collection<Double> findAvgSandardDeviationIngredientsPerRecipe(){
		Collection<Double> result = new ArrayList<Double>();
		
		Double avg = recipeRepository.findAvgIngredientsPerRecipe();
		Double stdDev = recipeRepository.findSandardDeviationIngredientsPerRecipe();
			
		if(avg == null || avg == 0){
			result.add(0.0);
		}else{
			result.add(avg);
		}
		if(stdDev == null || stdDev == 0){
			result.add(0.0);
		}else{
			result.add(stdDev);
		}
		
		return result;
	}
	
	public void qualifyARecipe(Recipe recipe, Contest contest){
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("USER");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		User user = userService.findByPrincipal();
		Assert.isTrue(user.getRecipes().contains(recipe));
		Assert.isTrue(recipe.getRelationLikes().size()>=5);
		Assert.isTrue(recipe.getRelationDislikes().size()==0);
		
		Qualify qualify = qualifyService.create();
		qualify.setContest(contest);
		qualify.setRecipe(recipe);
		qualifyService.save(qualify);
		
		recipe.setQualified(true);
		save(recipe);
		
		Collection<Qualify> contestQualifies = contest.getQualifies();
		contestQualifies.add(qualify);
		contest.setQualifies(contestQualifies);
		contestService.save(contest);
	}
	
	public Collection<Recipe> findByCreator(User user){
		Collection<Recipe> result;
		result = recipeRepository.findByCreator(user);
		return result;
	}
	
	public Collection<Recipe> listByCategory(){
		
		Collection<Recipe> result = recipeRepository.listByCategory();
		return result;
	}
	
	public Collection<Recipe> findByKey(String key){
		
		Collection<Recipe> result = recipeRepository.findByKey(key);
		return result;
		
	}
	
	public String createTicker(){
		
		String result = "";
		String abc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rdn = new Random();
		
		boolean equal = false;
		
		Calendar c1 = Calendar.getInstance();
		Integer monthIn = c1.get(Calendar.MONTH) + 1;
		String month = Integer.toString(monthIn);
		String day = Integer.toString(c1.get(Calendar.DAY_OF_MONTH));
		String year = Integer.toString(c1.get(Calendar.YEAR));
		
		result = result + year.substring(2) + month + day + "-";

		
		do{
			for(int i=0; i<4; i++){
				result = result + abc.charAt(rdn.nextInt(abc.length()));
			}
			
			Assert.isTrue(result.matches("(\\d{6})[-]([a-zA-Z]{4})"));
			
			Collection<Recipe> recipes = findAll();
			for(Recipe r:recipes){
				equal = result.contentEquals(r.getTicker());
				if(equal == true){
					break;
				}
			}
		}while(equal);
		
		return result;
	}
	
	public Collection<String> findNameByCategory(Recipe r){
		Collection<Category> categories = recipeRepository.findCategoriesByRecipe(r);
		Collection<String> names = new ArrayList<String>();
		for(Category c: categories){
		names.add(c.getName());
		}
		return names;
		}
}
