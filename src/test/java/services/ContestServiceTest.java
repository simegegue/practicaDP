package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class ContestServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private ContestService contestService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private QualifyService qualifyService;
	
	//Tests ---------------------------------------
	//Positive ------------------------------------
	@Test
	public void testCreate(){
		Contest contest;
		super.authenticate("admin");
		contest = contestService.create();
		super.authenticate(null);
		Assert.notNull(contest);
	}
	
	@Test
	public void testSave(){
		Contest contest, savedC;
		Collection<Contest> contests;
		Date openingTime, closingTime;
		Set<Qualify> qualifies = new HashSet<Qualify>();
		super.authenticate("admin");
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
		contests = contestService.findAll();
		super.authenticate(null);
		Assert.isTrue(contests.contains(savedC));
	}
	
	@Test
	public void testDelete(){
		Contest contest, savedC;
		Collection<Contest> contests;
		Date openingTime, closingTime;
		Set<Qualify> qualifies = new HashSet<Qualify>();
		super.authenticate("admin");
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
		contestService.delete(savedC);
		contests = contestService.findAll();
		super.authenticate(null);
		Assert.isTrue(!contests.contains(savedC));
	}

	@Test
	public void testFindOne(){
		Contest contest, savedC, contestFound;
		Date openingTime, closingTime;
		Set<Qualify> qualifies = new HashSet<Qualify>();
		super.authenticate("admin");
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
		contestFound = contestService.findOne(savedC.getId());
		super.authenticate(null);
		Assert.isTrue(savedC.equals(contestFound));
}

	@Test
	public void testFindAll(){
		Collection<Contest> contests;
		contests = contestService.findAll();
		Assert.notNull(contests);
	}

	@Test
	public void testFindContestRecipes(){
		Map<Contest, List<Recipe>> founds;
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
		founds = contestService.findContestRecipes();
		super.authenticate(null);
		Assert.isTrue(founds.get(savedC).contains(savedR));
	
	}

	@Test
	public void testSelectWinner(){
		Contest contest;
		Qualify qualify;
		super.authenticate("admin");
		
		contest = contestService.findOne(129);
		contestService.selectWinner(contest);
		
		qualify = qualifyService.findOne(130);
		System.out.println(qualify.getPosition());
		Assert.isTrue(qualify.getPosition()!=0);
		
		super.authenticate(null);
	}
	
	//Negative ------------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void testNCreate(){
		Contest contest;
		super.authenticate("user1");
		contest = contestService.create();
		super.authenticate(null);
		Assert.notNull(contest);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNSave(){
		Contest contest, savedC;
		Collection<Contest> contests;
		Date openingTime, closingTime;
		Set<Qualify> qualifies = new HashSet<Qualify>();
		super.authenticate("cook");
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
		contests = contestService.findAll();
		super.authenticate(null);
		Assert.isTrue(contests.contains(savedC));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNDelete(){
		Contest contest, savedC;
		Collection<Contest> contests;
		Date openingTime, closingTime;
		Set<Qualify> qualifies = new HashSet<Qualify>();
		super.authenticate("nutritionist");
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
		contestService.delete(savedC);
		contests = contestService.findAll();
		super.authenticate(null);
		Assert.isTrue(!contests.contains(savedC));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNFindOne(){
		Contest contest, savedC, contestFound;
		Date openingTime, closingTime;
		Set<Qualify> qualifies = new HashSet<Qualify>();
		super.authenticate("user1");
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
		contestFound = contestService.findOne(savedC.getId());
		super.authenticate(null);
		Assert.isTrue(savedC.equals(contestFound));
}

	@Test(expected = IllegalArgumentException.class)
	public void testNFindAll(){
		Collection<Contest> contests;
		contests = contestService.findAll();
		contests = null;
		Assert.notNull(contests);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNFindContestRecipes(){
		Map<Contest, List<Recipe>> founds;
		Collection<Qualify> qualifies;
		Recipe recipe, savedR;
		User user;
		Contest contest, savedC;
		Qualify qualify;
		Date openingTime, closingTime;
		super.authenticate("user");
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
		founds = contestService.findContestRecipes();
		super.authenticate(null);
		Assert.isTrue(founds.get(savedC).contains(savedR));
	
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNSelectWinner(){
		Contest contest;
		Qualify qualify;
		super.authenticate("administrator");

		contest = contestService.findOne(99);
		contestService.selectWinner(contest);
		
		qualify = qualifyService.findOne(124);
		System.out.println(qualify.getPosition());
		Assert.isTrue(qualify.getPosition()!=0);
		
		super.authenticate(null);
	}
}

