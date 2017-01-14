package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Nutritionist;
import domain.Recipe;
import domain.RelationDislike;
import domain.User;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class RelationDislikeServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private RelationDislikeService relationDislikeService;
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private NutritionistService nutritionistService;
	
	//Tests ---------------------------------------
	@Test
	public void testCreate(){
		RelationDislike r;
		super.authenticate("user1");
		r=relationDislikeService.create();
		Assert.notNull(r);
	}
	
	@Test
	public void testSave(){
		RelationDislike r,saved;
		Collection<RelationDislike>dislikes;
		super.authenticate("user1");
		r=relationDislikeService.create();
		Assert.notNull(r);
		saved=relationDislikeService.save(r);
		dislikes=relationDislikeService.findAll();
		Assert.isTrue(dislikes.contains(saved));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDelete(){
		RelationDislike r,saved;
		Collection<RelationDislike>dislikes;
		super.authenticate("user1");
		r=relationDislikeService.create();
		Assert.notNull(r);
		saved=relationDislikeService.save(r);
		dislikes=relationDislikeService.findAll();
		Assert.isTrue(dislikes.contains(saved));
		relationDislikeService.delete(r);
		dislikes=relationDislikeService.findAll();
		Assert.isTrue((dislikes.contains(saved)));
	}
	
	@Test
	public void testFindAll(){
		Collection<RelationDislike>dislikes;
		dislikes=relationDislikeService.findAll();
		Assert.notNull(dislikes);
	}
	
	@Test
	public void testFindOne(){
		RelationDislike dislike;
		dislike=relationDislikeService.findOne(156);
		Assert.notNull(dislike);
	}
	
	@Test
	public void testUserGiveDislike(){
		Recipe r=recipeService.findOne(124);
		User user;
		super.authenticate("user1");
		user=userService.findByPrincipal();
		Collection<RelationDislike>dislikes=relationDislikeService.giveDislike(r, user);
		Assert.notNull(dislikes);
	}
	
	@Test
	public void testNutritionistGiveDislike(){
		Recipe r=recipeService.findOne(124);
		Nutritionist nutritionist;
		super.authenticate("nutritionist1");
		nutritionist=nutritionistService.findByPrincipal();
		Collection<RelationDislike>dislikes=relationDislikeService.giveDislike(r, nutritionist);
		Assert.notNull(dislikes);
	}
	//Negative----------------------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void testNCreate(){
		RelationDislike r;
		super.authenticate("cook1");
		r=relationDislikeService.create();
		Assert.notNull(r);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNSave(){
		RelationDislike r,saved;
		Collection<RelationDislike>dislikes;
		super.authenticate("cook1");
		r=relationDislikeService.create();
		Assert.notNull(r);
		saved=relationDislikeService.save(r);
		dislikes=relationDislikeService.findAll();
		Assert.isTrue(dislikes.contains(saved));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNDelete(){
		RelationDislike r,saved;
		Collection<RelationDislike>dislikes;
		super.authenticate("cook1");
		r=relationDislikeService.create();
		Assert.notNull(r);
		saved=relationDislikeService.save(r);
		dislikes=relationDislikeService.findAll();
		Assert.isTrue(dislikes.contains(saved));
		relationDislikeService.delete(r);
		dislikes=relationDislikeService.findAll();
		Assert.isTrue((dislikes.contains(saved)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindOne(){
		RelationDislike dislike;
		dislike=relationDislikeService.findOne(651);
		Assert.notNull(dislike);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNUserGiveDislike(){
		Recipe r=recipeService.findOne(118);
		User user;
		super.authenticate("user1");
		user=userService.findByPrincipal();
		Collection<RelationDislike>dislikes=relationDislikeService.giveDislike(r, user);
		dislikes=null;
		Assert.notNull(dislikes);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNegNutritionistGiveDislike(){
		Recipe r=recipeService.findOne(118);
		Nutritionist nutritionist;
		super.authenticate("nutritionist1");
		nutritionist=nutritionistService.findByPrincipal();
		Collection<RelationDislike>dislikes=relationDislikeService.giveDislike(r, nutritionist);
		dislikes=null;
		Assert.notNull(dislikes);
	}
	
}