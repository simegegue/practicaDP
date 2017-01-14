package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;



import domain.Contest;
import domain.Qualify;
import domain.Recipe;

import domain.User;


import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class QualifyServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private QualifyService qualifyService;
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired 
	private ContestService contestService;
	
	@Autowired
	private UserService userService;
	//Tests ---------------------------------------
	//Positive ------------------------------------
	@Test
	public void testCreate(){
		Qualify qualify;
		super.authenticate("user1");
		qualify = qualifyService.create();
		super.authenticate(null);
		Assert.notNull(qualify);
	}

	@Test
	public void testSave(){
		Collection<Qualify> qualifies, qualifiesFound;
		Recipe recipe, savedR;
		User user;
		Contest contest, savedC;
		Qualify qualify, savedQ;
		Date openingTime, closingTime;
		super.authenticate("admin");
		qualifies = new HashSet<Qualify>();
		contest = contestService.create();
		contest.setQualifies(qualifies);
		contest.setTitle("Contest 1");
		openingTime = new Date();
		closingTime = new Date();
		openingTime.setTime(openingTime.getTime());
		closingTime.setTime(closingTime.getTime()+60000);
		contest.setClosingTime(closingTime);
		contest.setOpeningTime(openingTime);
		savedC = contestService.save(contest);
		super.authenticate("user1");
		user = userService.findByPrincipal();
		qualify = qualifyService.create();
		recipe = recipeService.create();
		recipe.setQualified(true);
		recipe.setUser(user);
		recipe.setTitle("Recipe 1");
		recipe.setSummary("Summary 1");
		recipe.setId(250);
		recipe.setTicker(recipeService.createTicker());
		savedR = recipeService.save(recipe);
		
		qualify.setPosition(1);
		qualify.setRecipe(savedR);
		qualifies.add(qualify);
		
		contest.setQualifies(qualifies);
		qualify.setContest(savedC);
		savedQ = qualifyService.save(qualify);
		qualifiesFound = qualifyService.findAll();
		super.authenticate(null);
		Assert.isTrue(qualifiesFound.contains(savedQ));
	}
	
	@Test
	public void testDelete(){
		Collection<Qualify> qualifies, qualifiesFound;
		Recipe recipe, savedR;
		User user;
		Contest contest, savedC;
		Qualify qualify, savedQ;
		Date openingTime, closingTime;
		super.authenticate("admin");
		qualifies = new HashSet<Qualify>();
		contest = contestService.create();
		contest.setQualifies(qualifies);
		contest.setTitle("Contest 1");
		openingTime = new Date();
		closingTime = new Date();
		openingTime.setTime(openingTime.getTime());
		closingTime.setTime(closingTime.getTime()+60000);
		contest.setClosingTime(closingTime);
		contest.setOpeningTime(openingTime);
		savedC = contestService.save(contest);
		super.authenticate("user1");
		user = userService.findByPrincipal();
		qualify = qualifyService.create();
		recipe = recipeService.create();
		recipe.setQualified(true);
		recipe.setUser(user);
		recipe.setTitle("Recipe 1");
		recipe.setSummary("Summary 1");
		recipe.setId(250);
		recipe.setTicker(recipeService.createTicker());
		savedR = recipeService.save(recipe);
		
		qualify.setPosition(1);
		qualify.setRecipe(savedR);
		qualifies.add(qualify);
		
		contest.setQualifies(qualifies);
		qualify.setContest(savedC);
		savedQ = qualifyService.save(qualify);
		qualifyService.delete(savedQ);
		qualifiesFound = qualifyService.findAll();
		super.authenticate(null);
		Assert.isTrue(!qualifiesFound.contains(savedQ));
	}
	
	@Test
	public void testFindOne(){
		Collection<Qualify> qualifies;
		Recipe recipe, savedR;
		User user;
		Contest contest, savedC;
		Qualify qualify, savedQ, qualifyFound;
		Date openingTime, closingTime;
		super.authenticate("admin");
		qualifies = new HashSet<Qualify>();
		contest = contestService.create();
		contest.setQualifies(qualifies);
		contest.setTitle("Contest 1");
		openingTime = new Date();
		closingTime = new Date();
		openingTime.setTime(openingTime.getTime());
		closingTime.setTime(closingTime.getTime()+60000);
		contest.setClosingTime(closingTime);
		contest.setOpeningTime(openingTime);
		savedC = contestService.save(contest);
		super.authenticate("user1");
		user = userService.findByPrincipal();
		qualify = qualifyService.create();
		recipe = recipeService.create();
		recipe.setQualified(true);
		recipe.setUser(user);
		recipe.setTitle("Recipe 1");
		recipe.setSummary("Summary 1");
		recipe.setId(250);
		recipe.setTicker(recipeService.createTicker());
		savedR = recipeService.save(recipe);
		
		qualify.setPosition(1);
		qualify.setRecipe(savedR);
		qualifies.add(qualify);
		
		contest.setQualifies(qualifies);
		qualify.setContest(savedC);
		savedQ = qualifyService.save(qualify);
		qualifyFound = qualifyService.findOne(savedQ.getId());
		super.authenticate(null);
		Assert.isTrue(qualifyFound.equals(savedQ));
	}
	
	
	
	@Test
	public void testFindAll(){
		Collection<Qualify> qualifies;
		qualifies = qualifyService.findAll();
		Assert.notNull(qualifies);
	}
	
	@Test
	public void testFindRecipeForContest(){
		List<Recipe> recipes;
		
		Collection<Qualify> qualifies;
		Recipe recipe, savedR;
		User user;
		Contest contest, savedC;
		Qualify qualify;
		Date openingTime, closingTime;
		super.authenticate("admin");
		qualifies = new HashSet<Qualify>();
		contest = contestService.create();
		contest.setQualifies(qualifies);
		contest.setTitle("Contest 1");
		openingTime = new Date();
		closingTime = new Date();
		openingTime.setTime(openingTime.getTime());
		closingTime.setTime(closingTime.getTime()+60000);
		contest.setClosingTime(closingTime);
		contest.setOpeningTime(openingTime);
		savedC = contestService.save(contest);
		super.authenticate("user1");
		user = userService.findByPrincipal();
		qualify = qualifyService.create();
		recipe = recipeService.create();
		recipe.setQualified(true);
		recipe.setUser(user);
		recipe.setTitle("Recipe 1");
		recipe.setSummary("Summary 1");
		recipe.setId(250);
		recipe.setTicker(recipeService.createTicker());
		savedR = recipeService.save(recipe);
		
		qualify.setPosition(1);
		qualify.setRecipe(savedR);
		qualifies.add(qualify);
		
		contest.setQualifies(qualifies);
		qualify.setContest(savedC);
		qualifyService.save(qualify);
		
		recipes = qualifyService.findRecipeForContest(savedC);
		
		super.authenticate(null);
		Assert.isTrue(recipes.contains(savedR));
		
		
		
	}

	@Test
	public void testQualifyRecipe(){
		Collection<Qualify> qualifies;
		Recipe recipe, savedR;
		User user;
		Contest contest, savedC;
		Qualify qualify, savedQ, qualifyFound;
		Date openingTime, closingTime;
		super.authenticate("admin");
		qualifies = new HashSet<Qualify>();
		contest = contestService.create();
		contest.setQualifies(qualifies);
		contest.setTitle("Contest 1");
		openingTime = new Date();
		closingTime = new Date();
		openingTime.setTime(openingTime.getTime());
		closingTime.setTime(closingTime.getTime()+60000);
		contest.setClosingTime(closingTime);
		contest.setOpeningTime(openingTime);
		savedC = contestService.save(contest);
		super.authenticate("user1");
		user = userService.findByPrincipal();
		qualify = qualifyService.create();
		recipe = recipeService.create();
		recipe.setQualified(true);
		recipe.setUser(user);
		recipe.setTitle("Recipe 1");
		recipe.setSummary("Summary 1");
		recipe.setId(250);
		recipe.setTicker(recipeService.createTicker());
		savedR = recipeService.save(recipe);
		
		qualify.setPosition(1);
		qualify.setRecipe(savedR);
		qualifies.add(qualify);
		
		contest.setQualifies(qualifies);
		qualify.setContest(savedC);
		savedQ = qualifyService.save(qualify);
		qualifyFound = qualifyService.qualifyRecipe(savedR);
		super.authenticate(null);
		Assert.isTrue(qualifyFound.equals(savedQ));
	}
	//Negative ---------------------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void testNCreate(){
		Qualify qualify;
		super.authenticate("cook1");
		qualify = qualifyService.create();
		super.authenticate(null);
		Assert.isNull(qualify);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNSave(){
		Collection<Qualify> qualifies;
		Recipe recipe, savedR;
		User user;
		Contest contest, savedC;
		Qualify qualify, savedQ;
		Date openingTime, closingTime;
		super.authenticate("user1");
		qualifies = new HashSet<Qualify>();
		contest = contestService.create();
		contest.setQualifies(qualifies);
		contest.setTitle("Contest 1");
		openingTime = new Date();
		closingTime = new Date();
		openingTime.setTime(openingTime.getTime());
		closingTime.setTime(closingTime.getTime()+60000);
		contest.setClosingTime(closingTime);
		contest.setOpeningTime(openingTime);
		savedC = contestService.save(contest);
		super.authenticate("cook1");
		user = userService.findByPrincipal();
		qualify = qualifyService.create();
		recipe = recipeService.create();
		recipe.setQualified(true);
		recipe.setUser(user);
		recipe.setTitle("Recipe 1");
		recipe.setSummary("Summary 1");
		recipe.setId(250);
		recipe.setTicker(recipeService.createTicker());
		savedR = recipeService.save(recipe);
		
		qualify.setPosition(1);
		qualify.setRecipe(savedR);
		qualifies.add(qualify);
		
		contest.setQualifies(qualifies);
		qualify.setContest(savedC);
		savedQ = qualifyService.save(qualify);
		super.authenticate(null);
		Assert.isNull(savedQ);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNDelete(){
		Collection<Qualify> qualifies, qualifiesFound;
		Recipe recipe, savedR;
		User user;
		Contest contest, savedC;
		Qualify qualify, savedQ;
		Date openingTime, closingTime;
		super.authenticate("cook1");
		qualifies = new HashSet<Qualify>();
		contest = contestService.create();
		contest.setQualifies(qualifies);
		contest.setTitle("Contest 1");
		openingTime = new Date();
		closingTime = new Date();
		openingTime.setTime(openingTime.getTime());
		closingTime.setTime(closingTime.getTime()+60000);
		contest.setClosingTime(closingTime);
		contest.setOpeningTime(openingTime);
		savedC = contestService.save(contest);
		super.authenticate("user1");
		user = userService.findByPrincipal();
		qualify = qualifyService.create();
		recipe = recipeService.create();
		recipe.setQualified(true);
		recipe.setUser(user);
		recipe.setTitle("Recipe 1");
		recipe.setSummary("Summary 1");
		recipe.setId(250);
		recipe.setTicker(recipeService.createTicker());
		savedR = recipeService.save(recipe);
		
		qualify.setPosition(1);
		qualify.setRecipe(savedR);
		qualifies.add(qualify);
		
		contest.setQualifies(qualifies);
		qualify.setContest(savedC);
		savedQ = qualifyService.save(qualify);
		qualifyService.delete(savedQ);
		qualifiesFound = qualifyService.findAll();
		super.authenticate(null);
		Assert.isTrue(!qualifiesFound.contains(savedQ));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindOne(){
		Collection<Qualify> qualifies;
		Recipe recipe, savedR;
		User user;
		Contest contest, savedC;
		Qualify qualify, savedQ, qualifyFound;
		Date openingTime, closingTime;
		super.authenticate("sponsor1");
		qualifies = new HashSet<Qualify>();
		contest = contestService.create();
		contest.setQualifies(qualifies);
		contest.setTitle("Contest 1");
		openingTime = new Date();
		closingTime = new Date();
		openingTime.setTime(openingTime.getTime());
		closingTime.setTime(closingTime.getTime()+60000);
		contest.setClosingTime(closingTime);
		contest.setOpeningTime(openingTime);
		savedC = contestService.save(contest);
		super.authenticate("user1");
		user = userService.findByPrincipal();
		qualify = qualifyService.create();
		recipe = recipeService.create();
		recipe.setQualified(true);
		recipe.setUser(user);
		recipe.setTitle("Recipe 1");
		recipe.setSummary("Summary 1");
		recipe.setId(250);
		recipe.setTicker(recipeService.createTicker());
		savedR = recipeService.save(recipe);
		
		qualify.setPosition(1);
		qualify.setRecipe(savedR);
		qualifies.add(qualify);
		
		contest.setQualifies(qualifies);
		qualify.setContest(savedC);
		savedQ = qualifyService.save(qualify);
		qualifyFound = qualifyService.findOne(savedQ.getId());
		super.authenticate(null);
		Assert.isTrue(qualifyFound.equals(savedQ));
	}
	
	@Test
	public void testNFindRecipeForContest(){
		Contest contest;
		contest = null;
		Assert.isTrue(qualifyService.findRecipeForContest(contest).isEmpty());
	}
	
	@Test
	public void testNQualifyRecipe(){
		Recipe recipe;
		recipe = null;
		Assert.isNull(qualifyService.qualifyRecipe(recipe));
	}
}