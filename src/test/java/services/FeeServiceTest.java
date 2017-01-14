package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Fee;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class FeeServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private FeeService feeService;
	
	//Tests ---------------------------------------
	//Positive ------------------------------------
	@Test
	public void testCreate(){
		Fee fee;
		super.authenticate("admin");
		fee = feeService.create();
		super.authenticate(null);
		Assert.notNull(fee);
	}
	
	@Test
	public void testSave(){
		Fee fee, saved;
		Collection<Fee> fees;
		super.authenticate("admin");
		fee = feeService.create();
		fee.setValue(50.0);
		saved = feeService.save(fee);
		fees = feeService.findAll();
		super.authenticate(null);
		Assert.isTrue(fees.contains(saved));
	}

	@Test
	public void testDelete(){
		Fee fee, saved;
		Collection<Fee> fees;
		super.authenticate("admin");
		fee = feeService.create();
		fee.setValue(50.0);
		saved = feeService.save(fee);
		feeService.delete(saved);
		fees = feeService.findAll();
		super.authenticate(null);
		Assert.isTrue(!fees.contains(saved));
	}
	
	@Test
	public void testFindOne(){
		Fee fee, saved, found;
		super.authenticate("admin");
		fee = feeService.create();
		fee.setValue(50.0);
		saved = feeService.save(fee);
		found = feeService.findOne(saved.getId());
		super.authenticate(null);
		Assert.isTrue(found.equals(saved));
	}
	
	@Test
	public void testFindAll(){
		Collection<Fee> fees;
		super.authenticate("admin");
		fees = feeService.findAll();
		super.authenticate(null);
		Assert.notNull(fees);
	}
	
	//Negative -----------------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void testNCreate(){
		Fee fee;
		super.authenticate("user1");
		fee = feeService.create();
		super.authenticate(null);
		Assert.notNull(fee);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNSave(){
		Fee fee, saved;
		Collection<Fee> fees;
		super.authenticate("user1");
		fee = feeService.create();
		fee.setValue(50.0);
		saved = feeService.save(fee);
		fees = feeService.findAll();
		super.authenticate(null);
		Assert.isTrue(fees.contains(saved));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNDelete(){
		Fee fee, saved;
		Collection<Fee> fees;
		super.authenticate("nutritionist1");
		fee = feeService.create();
		fee.setValue(50.0);
		saved = feeService.save(fee);
		feeService.delete(saved);
		fees = feeService.findAll();
		super.authenticate(null);
		Assert.isTrue(!fees.contains(saved));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindOne(){
		Fee fee, saved, found;
		super.authenticate("cook");
		fee = feeService.create();
		fee.setValue(50.0);
		saved = feeService.save(fee);
		found = feeService.findOne(saved.getId());
		super.authenticate(null);
		Assert.isTrue(found.equals(saved));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindAll(){
		Collection<Fee> fees;
		super.authenticate("cook");
		fees = feeService.findAll();
		super.authenticate(null);
		Assert.notNull(fees);
	}

}