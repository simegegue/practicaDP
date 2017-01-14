package services;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Cook;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class CookServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private CookService cookService;
	
	//Tests ---------------------------------------
	
	//Positive ------------------------------------
	
	@Test
	public void testCreate(){
		super.authenticate("admin2");
		Cook cook = cookService.create();
		Assert.notNull(cook);
		super.authenticate(null);
	}
	
	@Test
	public void testSave(){
		super.authenticate("admin2");
		String name = "Caballero";
		Cook cook = cookService.findOne(38);
		Assert.notNull(cook);
		cook.setName(name);
		cookService.save(cook);
		Assert.isTrue(cook.getName().equals(name));
		super.authenticate(null);
	}
	
	@Test
	public void testDelete(){
		super.authenticate("admin2");
		String name = "Caballero";
		Collection<Cook> cooks = new HashSet<Cook>();
		Cook saved;
		Cook cook = cookService.findOne(38);
		Assert.notNull(cook);
		cook.setName(name);
		saved = cookService.save(cook);
		cookService.delete(saved);
		Assert.isTrue(!cooks.contains(saved));
		Assert.isTrue(cook.getName().equals(name));
		super.authenticate(null);
	}
	
	@Test
	public void testFindAll(){
		Collection<Cook> all = cookService.findAll();
		Assert.notNull(all);
	}
	
	@Test
	public void testFindOne(){
		Cook cook = cookService.findOne(38);
		Assert.notNull(cook);
	}
	
	@Test 
	public void testListCooksByMasterClassPromoted(){
		Collection<Cook> cooks = cookService.listCooksByMasterClassesPromoted();
		Assert.notEmpty(cooks);
	}
	
	//Negative ------------------------------------
	
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value=true)
	public void testCreateNegative(){
		Cook cook;
		super.authenticate("user1");
		cook = cookService.create();
		cook = null;
		super.authenticate(null);
		Assert.notNull(cook);
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value=true)
	public void testSaveNegative(){
		super.authenticate("user1");
		
		String name = "Caballero";
		Cook cook = cookService.findOne(36);
		Assert.notNull(cook);
		cook.setName(name);
		cookService.save(cook);
		Assert.isTrue(cook.getName().equals(name));
		
		super.authenticate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value=true)
	public void testDeleteNegative(){
		super.authenticate("user1");
		String name = "Caballero";
		Collection<Cook> cooks = new HashSet<Cook>();
		Cook saved;
		Cook cook = cookService.findOne(36);
		Assert.notNull(cook);
		cook.setName(name);
		saved = cookService.save(cook);
		cookService.delete(saved);
		Assert.isTrue(!cooks.contains(saved));
		Assert.isTrue(cook.getName().equals(name));
		super.authenticate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value=true)
	public void testFindAllNegative(){
		Collection<Cook> all = cookService.findAll();
		all = null;
		Assert.notNull(all);
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value=true)
	public void testFindOneNegative(){
		Cook cook = cookService.findOne(25);
		Assert.notNull(cook);
	}
	
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value=true)
	public void testListCooksByMasterClassPromotedNegative(){
		Collection<Cook> cooks = cookService.listCooksByMasterClassesPromoted();
		cooks = null;
		Assert.notEmpty(cooks);
	}
}