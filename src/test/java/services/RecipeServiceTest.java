package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Contest;
import domain.Recipe;
import domain.User;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class RecipeServiceTest extends AbstractTest{

	//Service under test --------------------------
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
		super.authenticate("user1");
		Recipe r;
		r=recipeService.create();
		Assert.notNull(r);
		super.authenticate(null);
	}
	
	@Test
	public void testSave(){
		Recipe r,saved;
		super.authenticate("user1");
		r=recipeService.findOne(111);
		String a="Title Test";
		r.setTitle(a);
		saved=recipeService.save(r);
		Assert.isTrue(saved.getTitle().equals(a));
		super.authenticate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteNegative(){
		Recipe r,saved;
		super.authenticate("user1");
		r=recipeService.create();
		saved=recipeService.save(r);
		recipeService.delete(r);
		Collection<Recipe>recipes;
		recipes=recipeService.findAll();
		Assert.isTrue(!recipes.contains(saved));
		super.authenticate(null);
		
	}
	
	@Test
	public void testFindAll(){
		Collection<Recipe>recipes;
		recipes=recipeService.findAll();
		Assert.notNull(recipes);		
	}
	
	@Test
	public void testFindOne(){
		Recipe r;
		r=recipeService.findOne(111);
		Assert.notNull(r);
	}
	
	@Test
	public void testFindMinStandardDeviationStepsPerRecipe(){
		Collection<Double>d;
		d=recipeService.findMinStandardDeviationStepsPerRecipe();
		Assert.notNull(d);
		
	}
	
	@Test
	public void testFindAvgSandardDeviationIngredientsPerRecipe(){
		Collection<Double>d;
		d=recipeService.findAvgSandardDeviationIngredientsPerRecipe();
		Assert.notNull(d);
	}
	
	@Test
	public void testQualifyARecipe(){
		super.authenticate("user1");
		Recipe r=recipeService.findOne(111);
		Contest c=contestService.findOne(129);
		recipeService.qualifyARecipe(r, c);
		Assert.isTrue(r.getQualified()==true);
		super.authenticate(null);
	}
	
	@Test
	public void testFindByCreator(){
		User u=userService.findOne(24);
		Collection<Recipe>r;
		r=recipeService.findByCreator(u);
		Assert.notNull(r);
	}
	
	@Test
	public void testlistByCategory(){
		Collection<Recipe>r;
		r=recipeService.listByCategory();
		Assert.notNull(r);
	
	}
	
	@Test
	public void testfindByKey(){
		Collection<Recipe>r;
		r=recipeService.findByKey("recipe");
		Assert.notNull(r);
	}
	
	@Test
	public void testcreateTicker(){
		String s;
		s=recipeService.createTicker();
		System.out.println(s);
		Assert.notNull(s);
	}
	
	//Negative ------------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void testNCreate(){
		super.authenticate("cook1");
		Recipe r;
		r=recipeService.create();
		Assert.notNull(r);
		super.authenticate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNSave(){
		Recipe r,saved;
		super.authenticate("user1");
		r=recipeService.findOne(62);
		String a="Title Test";
		r.setTitle(a);
		saved=recipeService.save(r);
		Assert.isTrue(saved.getTitle().equals(a));
		super.authenticate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNDeleteNegative(){
		Recipe r,saved;
		super.authenticate("sponsor1");
		r=recipeService.create();
		saved=recipeService.save(r);
		recipeService.delete(r);
		Collection<Recipe>recipes;
		recipes=recipeService.findAll();
		Assert.isTrue(!recipes.contains(saved));
		super.authenticate(null);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindOne(){
		Recipe r;
		r=recipeService.findOne(632);
		Assert.notNull(r);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNQualifyARecipe(){
		super.authenticate("user1");
		Recipe r=recipeService.findOne(105);
		Contest c=contestService.findOne(963);
		recipeService.qualifyARecipe(r, c);
		Assert.isTrue(r.getQualified()==true);
		super.authenticate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindByCreator(){
		User u=userService.findOne(963);
		Collection<Recipe>r;
		r=recipeService.findByCreator(u);
		Assert.isNull(r);
	}
	
	
	@Test
	public void testNfindByKey(){
		Collection<Recipe>r;
		r=recipeService.findByKey("asb");
		Assert.notNull(r);
	}
	
}