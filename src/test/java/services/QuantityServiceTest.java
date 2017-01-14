package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Quantity;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class QuantityServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private QuantityService quantityService;
	
	//Tests ---------------------------------------
	//Positive ------------------------------------
	@Test
	public void testCreate(){
		Quantity q;
		super.authenticate("user1");
		q=quantityService.create(111);
		Assert.notNull(q);
		super.authenticate(null);
	}
	
	@Test
	public void testSave(){
		Quantity q;
		super.authenticate("user1");
		q=quantityService.findOne(175);
		q.setMeasure(50);
		quantityService.save(q);
		Assert.isTrue(q.getMeasure()==(50));
		super.authenticate(null);
	}
	
	@Test
	public void testDelete(){
		Quantity q;
		super.authenticate("user1");
		q=quantityService.findOne(175);
		quantityService.delete(q);
		Collection<Quantity>quantities;
		quantities=quantityService.findAll();
		Assert.isTrue(!quantities.contains(q));
		super.authenticate(null);
	}
	
	@Test
	public void testFindAll(){
		Collection<Quantity>q;
		q=quantityService.findAll();
		Assert.notNull(q);
	}
	
	@Test
	public void testFindOne(){
		Quantity q;
		q=quantityService.findOne(175);
		Assert.notNull(q);
	}
	
	//Negative --------------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void testNCreate(){
		Quantity q;
		super.authenticate("cook1");
		q=quantityService.create(111);
		Assert.notNull(q);
		super.authenticate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNSave(){
		Quantity q;
		super.authenticate("user1");
		q=quantityService.findOne(963);
		q.setMeasure(50);
		quantityService.save(q);
		Assert.isTrue(q.getMeasure()==(50));
		super.authenticate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNDelete(){
		Quantity q;
		super.authenticate("user1");
		q=quantityService.findOne(962);
		quantityService.delete(q);
		Collection<Quantity>quantities;
		quantities=quantityService.findAll();
		Assert.isTrue(!quantities.contains(q));
		super.authenticate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindOne(){
		Quantity q;
		q=quantityService.findOne(92);
		Assert.notNull(q);
	}



}