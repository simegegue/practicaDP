package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Nutritionist;
import domain.Recipe;
import domain.SocialActor;
import domain.User;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class NutritionistServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private NutritionistService nutritionistService;
	
	@Autowired
	private UserService userService;
	
	//Tests ---------------------------------------
	//Positive ------------------------------------
	@Test
	public void testCreate(){
		Nutritionist nutritionist;
		nutritionist=nutritionistService.create();
		nutritionist.setName("Carla");
		nutritionist.setSurname("Sanchez Jimenez");
		nutritionist.setEmail("casaji@gmail.com");
		nutritionist.setPhone("+34(123)214S");
		Assert.notNull(nutritionist);
	}
	@Test
	public void testSave(){
		Nutritionist nutritionist,saved;
		Collection<Nutritionist>nutritionists;
		nutritionist=nutritionistService.create();
		nutritionist.setName("Carla");
		nutritionist.setSurname("Sanchez Jimenez");
		nutritionist.setEmail("casaji@gmail.com");
		nutritionist.setPhone("+34(123)214S");
		Assert.notNull(nutritionist);
		saved=nutritionistService.save(nutritionist);
		nutritionists=nutritionistService.findAll();
		Assert.notNull(nutritionists.contains(saved));
	}

	
	@Test
	public void testDelete(){
		Nutritionist nutritionist;
		nutritionist=nutritionistService.create();
		nutritionist.setId(322);
		nutritionist.setName("Carla");
		nutritionist.setSurname("Sanchez Jimenez");
		nutritionist.setEmail("casaji@gmail.com");
		nutritionist.setPhone("+34(123)214S");
		Assert.notNull(nutritionist);
		nutritionistService.save(nutritionist);
		nutritionistService.delete(nutritionist);
	}
	
	@Test
	public void testFindAll(){
		Collection<Nutritionist> nutritionists;
		nutritionists=nutritionistService.findAll();
		Assert.notNull(nutritionists);
	}
	
	@Test
	public void testFindOne(){
		Nutritionist nutritionist;
		nutritionist=nutritionistService.findOne(35);
		Assert.notNull(nutritionist);
	}
	
	@Test
	public void testFollowUser(){
		Nutritionist nutritionist;
		super.authenticate("nutritionist2");
		nutritionist=nutritionistService.findByPrincipal();
		User i = userService.findOne(24);;
		
		userService.save(i);
		nutritionistService.followUser(nutritionist,i);
		Assert.notNull(i.getFollowers());
	}
	
	@Test
	public void testFollowNutritionist(){
		Nutritionist nutritionist;
		super.authenticate("nutritionist2");
		nutritionist=nutritionistService.findByPrincipal();
		Nutritionist i = nutritionistService.findOne(35);
		nutritionistService.save(i);
		nutritionistService.followNutritionist(nutritionist,i);
		Assert.notNull(i.getFollowers());
	}

	@Test
	public void testUnFollowUser(){
		Nutritionist nutritionist;
		super.authenticate("nutritionist2");
		nutritionist=nutritionistService.findByPrincipal();
		User i = userService.findOne(24);
		
		nutritionistService.followUser(nutritionist,i);
		nutritionistService.unfollowUser(nutritionist,i);
		
	}
	@Test
	public void testUnfollowNutritionist(){
		Nutritionist nutritionist;
		super.authenticate("nutritionist2");
		nutritionist=nutritionistService.findByPrincipal();
		Nutritionist i = nutritionistService.findOne(35);
		
		nutritionistService.followNutritionist(nutritionist,i);
		Assert.notNull(i.getFollowers());
		nutritionistService.unfollowNutritionist(nutritionist,i);
	
	}
	@Test
	public void testfindRecipeFromFollowing(){
		Nutritionist nutritionist;
		Collection<Recipe>r;
		super.authenticate("nutritionist2");
		nutritionist=nutritionistService.findByPrincipal();
		r=nutritionistService.findRecipeFromFollowing(nutritionist);
		Assert.notNull(r);
		System.out.println(r);
	}
	
	//Negative --------------------------------------------

	@Test
	public void testNDelete(){
		Nutritionist nutritionist, saved;
		Collection<Nutritionist> nutritionists;
		nutritionist=nutritionistService.create();
		nutritionist.setId(322);
		nutritionist.setName("Carla");
		nutritionist.setSurname("Sanchez Jimenez");
		nutritionist.setEmail("casaji@gmail.com");
		nutritionist.setPhone("+34(123)214S");
		Assert.notNull(nutritionist);
		saved = nutritionistService.save(nutritionist);
		nutritionistService.delete(nutritionist);
		nutritionists = nutritionistService.findAll();
		Assert.isTrue(nutritionists.contains(saved));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindOne(){
		Nutritionist nutritionist;
		nutritionist=nutritionistService.findOne(96);
		Assert.notNull(nutritionist);
	}
	
	
	@Test
	public void testNFollowUserFollowing(){
		List<SocialActor>s=new ArrayList<SocialActor>();
		Nutritionist nutritionist;
		super.authenticate("nutritionist2");
		nutritionist=nutritionistService.findByPrincipal();
		User i;
		i=userService.create();
		i.setId(254);
		i.setName("Paco");
		i.setSurname("Garcia");
		i.setEmail("paco44@gmail.com");
		i.setPhone("+000(000)33w2");
		i.setFollowers(s);
		Assert.notNull(i);
		userService.save(i);
		nutritionist.getFollowing().add(i);
		i.getFollowers().add(nutritionist);
		nutritionistService.followUser(nutritionist,i);
		Assert.notNull(i.getFollowers());
	}
	
	@Test
	public void testNToFollowNutritionist(){
		List<SocialActor>s=new ArrayList<SocialActor>();
		Nutritionist nutritionist;
		super.authenticate("nutritionist2");
		nutritionist=nutritionistService.findByPrincipal();
		Nutritionist i;
		i=nutritionistService.create();
		i.setId(254);
		i.setName("Paco");
		i.setSurname("Garcia");
		i.setEmail("paco44@gmail.com");
		i.setPhone("+000(000)33w2");
		i.setFollowers(s);
		Assert.notNull(i);
		nutritionistService.save(i);
		nutritionist.getFollowing().add(i);
		i.getFollowers().add(nutritionist);
		nutritionistService.followNutritionist(nutritionist,i);
		Assert.notNull(i.getFollowers());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNUnFollowUser(){
		List<SocialActor>s=new ArrayList<SocialActor>();
		Nutritionist nutritionist;
		super.authenticate("nutritionis2");
		nutritionist=nutritionistService.findByPrincipal();
		User i;
		i=userService.create();
		i.setId(254);
		i.setName("Paco");
		i.setSurname("Garcia");
		i.setEmail("paco44@gmail.com");
		i.setPhone("+000(000)33w2");
		i.setFollowers(s);
		Assert.notNull(i);
		userService.save(i);
		nutritionistService.unfollowUser(nutritionist,i);
		
	}
	
	@Test
	public void testNUnfollowNutritionist(){
		List<SocialActor>s=new ArrayList<SocialActor>();
		Nutritionist nutritionist;
		super.authenticate("nutritionist2");
		nutritionist=nutritionistService.findByPrincipal();
		Nutritionist i;
		i=nutritionistService.create();
		i.setId(254);
		i.setName("Paco");
		i.setSurname("Garcia");
		i.setEmail("paco44@gmail.com");
		i.setPhone("+000(000)33w2");
		i.setFollowers(s);
		Assert.notNull(i);
		nutritionistService.save(i);
		nutritionistService.unfollowNutritionist(nutritionist,i);
	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNfindRecipeFromFollowing(){
		Nutritionist nutritionist;
		Collection<Recipe>r;
		super.authenticate("cook1");
		nutritionist=nutritionistService.findByPrincipal();
		r=nutritionistService.findRecipeFromFollowing(nutritionist);
		Assert.notNull(r);
		System.out.println(r);
	}
}