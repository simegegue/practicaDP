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
import domain.RelationLike;
import domain.User;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class RelationLikeServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private RelationLikeService relationLikeService;
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private NutritionistService nutritionistService;
	
	//Tests ---------------------------------------
	@Test
	public void testCreate(){
		RelationLike r;
		super.authenticate("user1");
		r=relationLikeService.create();
		Assert.notNull(r);
	}
	
	@Test
	public void testSave(){
		RelationLike r,saved;
		Collection<RelationLike>likes;
		super.authenticate("user1");
		r=relationLikeService.create();
		Assert.notNull(r);
		saved=relationLikeService.save(r);
		likes=relationLikeService.findAll();
		Assert.isTrue(likes.contains(saved));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDelete(){
		RelationLike r,saved;
		Collection<RelationLike>likes;
		super.authenticate("user1");
		r=relationLikeService.create();
		Assert.notNull(r);
		saved=relationLikeService.save(r);
		likes=relationLikeService.findAll();
		Assert.isTrue(likes.contains(saved));
		relationLikeService.delete(r);
		likes=relationLikeService.findAll();
		Assert.isTrue((likes.contains(saved)));
	}
	
	@Test
	public void testFindAll(){
		Collection<RelationLike>likes;
		likes=relationLikeService.findAll();
		Assert.notNull(likes);
	}
	
	@Test
	public void testFindOne(){
		RelationLike like;
		like=relationLikeService.findOne(146);
		Assert.notNull(like);
	}
	
	@Test
	public void testRecipeLikes(){
		Recipe r=recipeService.findOne(124);
		Collection<RelationLike>likes=relationLikeService.recipeLikes(r);
		Assert.notNull(likes);
	}
	
	@Test
	public void testUserGiveLike(){
		Recipe r=recipeService.findOne(124);
		User user;
		super.authenticate("user1");
		user=userService.findByPrincipal();
		Collection<RelationLike>likes=relationLikeService.giveLike(r, user);
		Assert.notNull(likes);
	}
	
	@Test
	public void testNutritionistGiveLike(){
		Recipe r=recipeService.findOne(124);
		Nutritionist nutritionist;
		super.authenticate("nutritionist1");
		nutritionist=nutritionistService.findByPrincipal();
		Collection<RelationLike>likes=relationLikeService.giveLike(r, nutritionist);
		Assert.notNull(likes);
	}
	//Negative------------------------------------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void testNCreate(){
		RelationLike r;
		super.authenticate("admin");
		r=relationLikeService.create();
		Assert.notNull(r);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNSave(){
		RelationLike r,saved;
		Collection<RelationLike>likes;
		super.authenticate("cook1");
		r=relationLikeService.create();
		Assert.notNull(r);
		saved=relationLikeService.save(r);
		likes=relationLikeService.findAll();
		Assert.isTrue(likes.contains(saved));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNDelete(){
		RelationLike r,saved;
		Collection<RelationLike>likes;
		super.authenticate("cook1");
		r=relationLikeService.create();
		Assert.notNull(r);
		saved=relationLikeService.save(r);
		likes=relationLikeService.findAll();
		Assert.isTrue(likes.contains(saved));
		relationLikeService.delete(r);
		likes=relationLikeService.findAll();
		Assert.isTrue((likes.contains(saved)));
	}
	

	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindOne(){
		RelationLike like;
		like=relationLikeService.findOne(324);
		Assert.notNull(like);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNRecipeLikes(){
		Recipe r=recipeService.findOne(118);
		Collection<RelationLike>likes=relationLikeService.recipeLikes(r);
		likes=null;
		Assert.notNull(likes);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNUserGiveLike(){
		Recipe r=recipeService.findOne(118);
		User user;
		super.authenticate("user1");
		user=userService.findByPrincipal();
		Collection<RelationLike>likes=relationLikeService.giveLike(r, user);
		likes=null;
		Assert.notNull(likes);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNegNutritionistGiveLike(){
		Recipe r=recipeService.findOne(118);
		Nutritionist nutritionist;
		super.authenticate("nutritionist1");
		nutritionist=nutritionistService.findByPrincipal();
		Collection<RelationLike>likes=relationLikeService.giveLike(r, nutritionist);
		likes=null;
		Assert.notNull(likes);
	}
}