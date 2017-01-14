package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.SocialIdentity;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class SocialIdentityServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private SocialIdentityService socialIdentityService;
	
	//Tests ---------------------------------------
	@Test
	public void testCreate(){
		authenticate("admin");
		SocialIdentity s;
		s=socialIdentityService.create();
		Assert.notNull(s);
		authenticate(null);
	}
	
	@Test
	public void testSave(){
		SocialIdentity s;
		s=socialIdentityService.findOne(40);
		s.setNick("pakitoconk");
		socialIdentityService.save(s);
		Assert.isTrue(s.getNick().equals("pakitoconk"));
		
	}
	
	@Test
	public void testDelete(){
		SocialIdentity s;
		s=socialIdentityService.findOne(40);
		socialIdentityService.delete(s);
		Collection<SocialIdentity> soc=socialIdentityService.findAll();
		Assert.isTrue(!soc.contains(s));
	}
	
	@Test
	public void testFindAll(){
		Collection<SocialIdentity> soc=socialIdentityService.findAll();
		Assert.notNull(soc);
	}
	
	@Test
	public void testFindOne(){
		SocialIdentity s;
		s=socialIdentityService.findOne(40);
		Assert.notNull(s);
	
	}
	//Negative---------------------------------------------------------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void testNCreate(){
		SocialIdentity s;
		s=socialIdentityService.create();
		s=null;
		Assert.notNull(s);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNSave(){
		SocialIdentity s;
		s=socialIdentityService.findOne(40);
		String r=s.getNick();
		s.setNick("pakito");
		socialIdentityService.save(s);
		Assert.isTrue(s.getNick().equals(r));
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNDelete(){
		SocialIdentity s;
		s=socialIdentityService.findOne(40);
		socialIdentityService.delete(s);
		Collection<SocialIdentity> soc=socialIdentityService.findAll();
		Assert.isTrue(soc.contains(s));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindOne(){
		SocialIdentity s;
		s=socialIdentityService.findOne(228);
		Assert.notNull(s);
	
	}
	
}