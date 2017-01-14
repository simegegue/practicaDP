package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Endorser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class EndorserServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private EndorserService endorserService;
	
	//Tests ---------------------------------------
	//Positive ------------------------------------
	@Test
	public void testCreate(){
		super.authenticate("nutritionist1");
		Endorser e;
		e=endorserService.create();
		Assert.notNull(e);
		super.authenticate(null);
	}
	
	@Test
	public void testSave(){
		Endorser e;
		super.authenticate("nutritionist1");
		e=endorserService.findOne(33);
		e.setName("Lorenzo");
		endorserService.save(e);
		Assert.isTrue(e.getName().equals("Lorenzo"));
		super.authenticate(null);
	}
	
	@Test
	public void testDelete(){
		Endorser e;
		super.authenticate("nutritionist1");
		e=endorserService.findOne(33);
		endorserService.delete(e);
		Collection<Endorser>endorsers;
		endorsers=endorserService.findAll();
		Assert.isTrue(!endorsers.contains(e));
		super.authenticate(null);
	}
	
	@Test
	public void testFindAll(){
		Collection<Endorser>e;
		e=endorserService.findAll();
		Assert.notNull(e);
	}
	
	@Test
	public void testFindOne(){
		Endorser e;
		e=endorserService.findOne(33);
		Assert.notNull(e);
	}
	//Negative -------------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void testNCreate(){
		super.authenticate("user1");
		Endorser e;
		e=endorserService.create();
		Assert.notNull(e);
		super.authenticate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNSave(){
		Endorser e;
		super.authenticate("cook");
		e=endorserService.findOne(32);
		e.setName("Lorenzo");
		endorserService.save(e);
		Assert.isTrue(e.getName().equals("Lorenzo"));
		super.authenticate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNDelete(){
		Endorser e;
		super.authenticate("nutritionist1");
		e=endorserService.findOne(96);
		endorserService.delete(e);
		Collection<Endorser>endorsers;
		endorsers=endorserService.findAll();
		Assert.isTrue(!endorsers.contains(e));
		super.authenticate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindAll(){
		Collection<Endorser>e;
		e=endorserService.findAll();
		e = null;
		Assert.notNull(e);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindOne(){
		Endorser e;
		e=endorserService.findOne(382);
		Assert.notNull(e);
	}
}
