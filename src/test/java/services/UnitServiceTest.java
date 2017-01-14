package services;

import java.util.Collection;
import java.util.HashSet;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Unit;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class UnitServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private UnitService unitService;
	
	//Tests ---------------------------------------
	
	//Positive ------------------------------------
	
	@Test
	public void testCreate(){
		Unit unit;
		super.authenticate("admin");
		unit = unitService.create();
		super.authenticate(null);
		Assert.notNull(unit);
	}

	@Test
	public void testSave(){
		Unit unit, saved;
		Collection<Unit> units = new HashSet<Unit>();
		super.authenticate("admin");
		unit = unitService.create();
		unit.setName("unitTest");
		Assert.notNull(unit);
		
		saved = unitService.save(unit);
		units = unitService.findAll();
		Assert.isTrue(units.contains(saved));
		super.authenticate(null);
		
	}
	
	@Test
	public void testDelete(){
		Unit unit, saved;
		Collection<Unit> units = new HashSet<Unit>();
		super.authenticate("admin");
		unit = unitService.create();
		unit.setName("unitTest");
		Assert.notNull(unit);
		
		saved = unitService.save(unit);
		unitService.delete(saved);
		units = unitService.findAll();
		Assert.isTrue(!units.contains(saved));
		super.authenticate(null);
	}
	
	@Test
	public void testFindOne(){
		Unit unit;
		unit = unitService.findOne(166);
		Assert.notNull(unit);
	}
	
	@Test
	public void testFindAll(){
		Collection<Unit> units;
		units = unitService.findAll();
		Assert.notNull(units);
	}
	
	//Negative ------------------------------------
	
	//User no puede crear unit
	@Test(expected = IllegalArgumentException.class)
	public void testNegCreate(){
		Unit unit;
		super.authenticate("user1");
		unit = unitService.create();
		super.authenticate(null);
		Assert.notNull(unit);
	}

	//Sponsor no puede crear unit
	@Test(expected = IllegalArgumentException.class)
	public void testNegSave(){
		Unit unit, saved;
		Collection<Unit> units = new HashSet<Unit>();
		super.authenticate("sponsor1");
		unit = unitService.create();
		unit.setName("unitTest");
		Assert.notNull(unit);
		
		saved = unitService.save(unit);
		units = unitService.findAll();
		Assert.isTrue(units.contains(saved));
		super.authenticate(null);
		
	}
	
	//Atributo name vacio
	@Test(expected = ConstraintViolationException.class)
	public void testNegDelete(){
		Unit unit, saved;
		Collection<Unit> units = new HashSet<Unit>();
		super.authenticate("admin");
		unit = unitService.create();
		unit.setName("");
		Assert.notNull(unit);
		
		saved = unitService.save(unit);
		unitService.delete(saved);
		units = unitService.findAll();
		Assert.isTrue(!units.contains(saved));
		super.authenticate(null);
	}
	
	//Id incorrecta
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindOne(){
		Unit unit;
		unit = unitService.findOne(25);
		Assert.notNull(unit);
	}
	
	//Variable units nula
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindAll(){
		Collection<Unit> units;
		units = unitService.findAll();
		units = null;
		Assert.notNull(units);
	}
}