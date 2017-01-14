package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.SocialActor;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class SocialActorServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private SocialActorService socialActorService;
	
	//Tests ---------------------------------------
	
	@Test
	public void testCreate(){
		SocialActor socialActor;
		socialActor=socialActorService.create();
		Assert.notNull(socialActor);
	}
	
	@Test
	public void testSave(){
		SocialActor s=socialActorService.findOne(24);
		String st="Ana";
		s.setName(st);
		socialActorService.save(s);
		Assert.isTrue(s.getName().equals(st));
	}
	
	@Test
	public void testdelete(){
		SocialActor s=socialActorService.findOne(25);
		socialActorService.delete(s);
		Collection<SocialActor> socialActors=socialActorService.findAll();
		Assert.isTrue(!socialActors.contains(s));
	}
	
	@Test
	public void testFindAll(){
		Collection<SocialActor>soc;
		soc=socialActorService.findAll();
		Assert.notNull(soc);
	}
	
	@Test
	public void testFindOne(){
		SocialActor soc;
		soc=socialActorService.findOne(24);
		Assert.notNull(soc);
	}
	//Negative-----------------------------------------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void testNCreate(){
		SocialActor socialActor;
		socialActor=socialActorService.create();
		socialActor=null;
		Assert.notNull(socialActor);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNSave(){
		SocialActor s=socialActorService.findOne(24);
		String st="Ana";
		String d=s.getName();
		s.setName(st);
		socialActorService.save(s);
		Assert.isTrue(s.getName().equals(d));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNDelete(){
		SocialActor s=socialActorService.findOne(25);
		socialActorService.delete(s);
		Collection<SocialActor> socialActors=socialActorService.findAll();
		Assert.isTrue(socialActors.contains(s));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindOne(){
		SocialActor soc;
		soc=socialActorService.findOne(218);
		Assert.notNull(soc);
	}
}