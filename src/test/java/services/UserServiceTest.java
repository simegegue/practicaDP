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
import utilities.AbstractTest;
import domain.Nutritionist;
import domain.Recipe;
import domain.SocialActor;
import domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class UserServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private UserService userService;
	
	@Autowired
	private NutritionistService nutritionistService;
	
	//Tests ---------------------------------------
	
	@Test
	public void testCreate(){
		User user;
		user=userService.create();
		user.setName("Juan");
		user.setSurname("Arroyo Sanchez");
		user.setEmail("juarsa@gmail.com");
		user.setPhone("+34(000)214S");
		Assert.notNull(user);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNCreate(){
		User user;
		user=userService.create();
		user=null;
		Assert.notNull(user);
	}
	@Test
	public void testSave(){
		User user,saved;
		Collection<User>users;
		user=userService.create();
		user.setName("Juan");
		user.setSurname("Arroyo Sanchez");
		user.setEmail("juarsa@gmail.com");
		user.setPhone("+34(000)214S");
		Assert.notNull(user);
		saved=userService.save2(user);
		users=userService.findAll();
		Assert.isTrue(users.contains(saved));
		
	}
	@Test(expected = IllegalArgumentException.class)
	public void testNSave(){
		User user,saved;
		Collection<User>users;
		user=userService.create();
		user.setName("Juan");
		user.setSurname("Arroyo Sanchez");
		user.setEmail("juarsa@gmail.com");
		user.setPhone("+34(000)214S");
		Assert.notNull(user);
		saved=userService.save2(user);
		saved=null;
		users=userService.findAll();
		Assert.isTrue(users.contains(saved));
		
	}

	
	@Test
	public void testDelete(){
		User user;
		user=userService.create();
		user.setId(201);
		user.setName("Juan");
		user.setSurname("Arroyo Sanchez");
		user.setEmail("juarsa@gmail.com");
		user.setPhone("+34(000)214S");
		Assert.notNull(user);
		userService.save(user);
		userService.delete(user);
	}
	
	@Test
	public void testFindAll(){
		Collection<User> users;
		users=userService.findAll();
		Assert.notNull(users);
	}
	
	@Test
	public void testFindOne(){
		User user;
		user=userService.findOne(24);
		Assert.notNull(user);
	}
	
	@Test
	public void testFindByPrincipal(){
		User user;
		super.authenticate("user1");
		user=userService.findByPrincipal();
		Assert.notNull(user);
	}
	
	@Test
	public void testFindByKey(){
		Collection<User> users;
		users=userService.findByKey("simon");
		Assert.notNull(users);
	}
	
	@Test
	public void testFindByUserAccount(){
		User user;
		user=userService.findByUserAccountId(7);
		Assert.notNull(user);
	}
	
	@Test
	public void testUsersMoreRecipesAuthored(){
		Collection<User>users;
		users=userService.usersMoreRecipesAuthored();
		Assert.notNull(users);
	}
	
	@Test
	public void testUsersByPopularity(){
		Collection<User>users;
		users=userService.usersByPopularity();
		Assert.notNull(users);
	}
	
	@Test
	public void testUsersAvgLikesDislikes(){
		Collection<User>users;
		users=userService.usersAvgLikesDislikes();
		Assert.notNull(users);
	}
	
	@Test
	public void testFindMinAvgMaxRecipesPerUser(){
		Collection<Double> res;
		res=userService.findMinAvgMaxRecipesPerUser();
		Assert.notNull(res);
	}
	
	@Test
	public void testFollowUser(){
		User user;
		super.authenticate("user2");
		user=userService.findByPrincipal();
		User i = userService.findOne(24);
		userService.followUser(user,i);
		Assert.notNull(i.getFollowers());
	}
	
	@Test
	public void testNFollowUserFollowing(){
		List<SocialActor>s=new ArrayList<SocialActor>();
		User user;
		super.authenticate("user2");
		user=userService.findByPrincipal();
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
		user.getFollowing().add(i);
		i.getFollowers().add(user);
		userService.followUser(user,i);
		Assert.notNull(i.getFollowers());
	}
	
	@Test
	public void testFollowNutritionist(){
		User user;
		super.authenticate("user2");
		user=userService.findByPrincipal();
		Nutritionist i= nutritionistService.findOne(35);
		nutritionistService.save(i);
		userService.followNutritionist(user,i);
		Assert.notNull(i.getFollowers());
	}
	
	@Test
	public void testNFollowNutritionist(){
		List<SocialActor>s=new ArrayList<SocialActor>();
		User user;
		super.authenticate("user2");
		user=userService.findByPrincipal();
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
		user.getFollowing().add(i);
		i.getFollowers().add(user);
		userService.followNutritionist(user,i);
		Assert.notNull(i.getFollowers());
	}
	
	@Test
	public void testUnFollowUser(){
		User user;
		super.authenticate("user2");
		user=userService.findByPrincipal();
		User i = userService.findOne(24);
		userService.followUser(user,i);
		userService.unfollowUser(user,i);
		
	}
	
	@Test
	public void testNUnFollowUser(){
		List<SocialActor>s=new ArrayList<SocialActor>();
		User user;
		super.authenticate("user2");
		user=userService.findByPrincipal();
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
		userService.unfollowUser(user,i);
		
	}
	
	@Test
	public void testUnfollowNutritionist(){
		User user;
		super.authenticate("user2");
		user=userService.findByPrincipal();
		Nutritionist i= nutritionistService.findOne(35);
		userService.followNutritionist(user,i);
		Assert.notNull(i.getFollowers());
		userService.unfollowNutritionist(user,i);
	
	}
	
	@Test
	public void testNUnfollowNutritionist(){
		List<SocialActor>s=new ArrayList<SocialActor>();
		User user;
		super.authenticate("user2");
		user=userService.findByPrincipal();
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
		userService.unfollowNutritionist(user,i);
	
	}
	@Test
	public void testfindRecipeFromFollowing(){
		User user;
		Collection<Recipe>r;
		super.authenticate("user1");
		user=userService.findByPrincipal();
		r=userService.findRecipeFromFollowing(user);
		Assert.notNull(r);
		System.out.println(r);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNfindRecipeFromFollowing(){
		User user;
		Collection<Recipe>r;
		super.authenticate("user6");
		user=userService.findByPrincipal();
		r=userService.findRecipeFromFollowing(user);
		Assert.notNull(r);
		System.out.println(r);
	}
	
}
