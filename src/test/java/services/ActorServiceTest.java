package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class ActorServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private ActorService actorService;
	
	//Tests ---------------------------------------
	//Positive -------------------------------------
	@Test
	public void testFindAllActors(){
		Collection<Actor> a = actorService.findAll();
		Assert.notNull(a);
	}
	
	@Test
	public void testFindOne(){
		int actorId = 35;
		Actor actor = actorService.findOne(actorId);
		Assert.notNull(actor);
	}
	
	@Test
	public void testSave(){
		int actorId = 16;
		String name = "Saturno";
		
		Actor actor = actorService.findByUserAccount(actorId);
		actor.setName(name);
		actorService.save(actor);
		
		Actor actorMod = actorService.findByUserAccount(actorId);
		Assert.isTrue(actorMod.getName().equals(name));
	}
	
	@Test
	public void testDelete(){
		
		// Before deleting the actor --------------------
		Actor actor = actorService.findOne(20);
		Collection<Actor> all1 = actorService.findAll();
		Assert.isTrue(all1.contains(actor));
		
		actorService.delete(actor);
		
		// After deleting the actor ---------------------
		Collection<Actor> all2 = actorService.findAll();
		Assert.isTrue(!all2.contains(actor));
	}


	@Test
	public void testFindByprincipal(){
		super.authenticate("user1");
		
		Actor principal = actorService.findOne(24);
		
		Actor actor = actorService.findByPrincipal();
		Assert.isTrue(principal.equals(actor));
		
		super.authenticate(null);
	}
	
	@Test
	public void testFindByUserAccount(){
		Actor actor1 = actorService.findByUserAccount(2);
		Actor actor2 = actorService.findOne(18);
		
		Assert.isTrue(actor1.equals(actor2));
	}
	
	//Negative --------------------------------------------
	@Test
	public void testNFindAllActors(){
		Collection<Actor> a = actorService.findAll();
		a = null;
		Assert.isNull(a);
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value=true)
	public void testNFindOne(){
		int actorId = 8;
		Actor actor = actorService.findOne(actorId);
		Assert.notNull(actor);
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value=true)
	public void testNSave(){
		int actorId = 38;
		String name = "";
		
		Actor actor = actorService.findByUserAccount(actorId);
		actor.setName(name);
		actorService.save(actor);
		
		Actor actorMod = actorService.findByUserAccount(actorId);
		Assert.isTrue(actorMod.getName().equals(name));
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value=true)
	public void testNDelete(){
		
		// Before deleting the actor --------------------
		Actor actor = actorService.findOne(89);
		Collection<Actor> all1 = actorService.findAll();
		Assert.isTrue(all1.contains(actor));
		
		actorService.delete(actor);
		
		// After deleting the actor ---------------------
		Collection<Actor> all2 = actorService.findAll();
		Assert.isTrue(!all2.contains(actor));
	}


	@Test(expected = IllegalArgumentException.class)
	@Rollback(value=true)
	public void testNFindByprincipal(){
		super.authenticate("cook");
		
		Actor principal = actorService.findOne(23);
		
		Actor actor = actorService.findByPrincipal();
		Assert.isTrue(principal.equals(actor));
		
		super.authenticate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value=true)
	public void testNFindByUserAccount(){
		Actor actor1 = actorService.findByUserAccount(1);
		Actor actor2 = actorService.findOne(38);
		
		Assert.isTrue(actor1.equals(actor2));
	}
	
}
