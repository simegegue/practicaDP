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

import domain.Cook;
import domain.MasterClass;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class MasterClassServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private MasterClassService masterClassService;
	
	@Autowired
	private CookService cookService;
	
	//Tests ---------------------------------------

	//Positive-------------------------------------
	
	@Test
	public void testCreateMasterClass(){
		MasterClass masterClass;
		super.authenticate("cook1");
		masterClass = masterClassService.create();
		super.authenticate(null);
		Assert.notNull(masterClass);
	}
	
	@Test
	public void testSaveMasterClass(){
		super.authenticate("admin");
		Cook cS;
		Cook cook = cookService.create();
		cook.setName("Paco");
		cook.setSurname("Pesao Soy");
		cook.setEmail("Paco555@gmail.com");
		Assert.notNull(cook);
		cS = cookService.save(cook);
		super.authenticate(null);
		
		super.authenticate("cook1");
		MasterClass masterClass, saved;
		Collection<MasterClass> masterClasses = new HashSet<MasterClass>();
		Collection<String> registered = new HashSet<String>();
		masterClass = masterClassService.create();
		masterClass.setCook(cS);
		masterClass.setTitle("Master Class 1");
		masterClass.setDescription("Master Class numero 1");
		masterClass.setPromoted(true);
		masterClass.setRegistered(registered);
		Assert.notNull(masterClass);
		
		saved = masterClassService.save(masterClass);
		masterClasses = masterClassService.findAll();
		Assert.isTrue(masterClasses.contains(saved));
		super.authenticate(null);
	}	
	
	@Test
	public void testDeleteMasterClass(){
				
		super.authenticate("cook1");
		MasterClass saved = masterClassService.findOne(132);
		Collection<MasterClass> masterClasses;
		masterClassService.delete(saved);
		masterClasses = masterClassService.findAll();
		Assert.isTrue(!masterClasses.contains(saved));
		super.authenticate(null);
	}
	
	@Test
	public void testFindAll(){
		Collection<MasterClass>masterClasses;
		masterClasses=masterClassService.findAll();
		Assert.notNull(masterClasses);
	}
	
	@Test
	public void testFindOne(){
		MasterClass masterClass;
		masterClass=masterClassService.findOne(132);
		Assert.notNull(masterClass);
	}
	
	@Test
	public void testMasterClassesPromoted(){
		Integer masterClasses;
		masterClasses=masterClassService.masterClassesPromoted();
		Assert.notNull(masterClasses);
	}
	
	@Test
	public void testMinMaxAvgMasterClasses(){
		Collection<Double> masterClasses;
		masterClasses = masterClassService.minMaxAvgMasterClasses();
		Assert.notNull(masterClasses);
	}
	
	@Test
	public void testAvgMasterClassesPromotedPerCook(){
		Collection<Double> masterClasses;
		masterClasses = masterClassService.avgMasterClassesPromotedPerCook();
		Assert.notNull(masterClasses);
	}
	
	@Test
	public void testAttendMasterClass(){
		MasterClass masterClass;
		Collection<String> registered = new HashSet<String>();
		super.authenticate("cook1");
		masterClass = masterClassService.create();
		masterClass.setRegistered(registered);
		masterClassService.attendMasterClass(masterClass);
		Assert.notNull(masterClass);
		super.authenticate(null);
	}
	
	@Test
	public void testPromoteDemoteMasterClass(){
		MasterClass masterClass;
		super.authenticate("cook1");
		masterClass = masterClassService.create();
		super.authenticate(null);
		super.authenticate("admin");
		masterClassService.promoteDemoteMasterClass(masterClass);
		Assert.notNull(masterClass);
		super.authenticate(null);
	}
	

	//Negative-------------------------------------
	
	//User no puede crear una master class
	@Test(expected = IllegalArgumentException.class)
	public void testNegCreateMasterClass(){
		MasterClass masterClass;
		super.authenticate("user1");
		masterClass = masterClassService.create();
		super.authenticate(null);
		Assert.notNull(masterClass);
	}
	
	//Atributo title vacio
	@Test(expected = ConstraintViolationException.class)
	public void testNegSaveMasterClass(){
		super.authenticate("admin");
		Cook cS;
		Cook cook = cookService.create();
		cook.setName("Paco");
		cook.setSurname("Pesao Soy");
		cook.setEmail("Paco555@gmail.com");
		Assert.notNull(cook);
		cS = cookService.save(cook);
		super.authenticate(null);
		
		super.authenticate("cook1");
		MasterClass masterClass, saved;
		Collection<MasterClass> masterClasses = new HashSet<MasterClass>();
		Collection<String> registered = new HashSet<String>();
		masterClass = masterClassService.create();
		masterClass.setCook(cS);
		masterClass.setTitle("");
		masterClass.setDescription("Master Class numero 1");
		masterClass.setPromoted(true);
		masterClass.setRegistered(registered);
		Assert.notNull(masterClass);
		
		saved = masterClassService.save(masterClass);
		masterClasses = masterClassService.findAll();
		Assert.isTrue(masterClasses.contains(saved));
		super.authenticate(null);
	}	
	
	//Atributo description vacio
	@Test(expected = IllegalArgumentException.class)
	public void testNegDeleteMasterClass(){
		
		
		super.authenticate("user1");
		MasterClass saved = masterClassService.findOne(132);
		Collection<MasterClass> masterClasses;
		masterClassService.delete(saved);
		masterClasses = masterClassService.findAll();
		Assert.isTrue(!masterClasses.contains(saved));
		super.authenticate(null);
	}
	
	//Variable masterclasses nula
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindAll(){
		Collection<MasterClass>masterClasses;
		masterClasses=masterClassService.findAll();
		masterClasses = null;
		Assert.notNull(masterClasses);
	}
	
	//Objeto encontrado no es el buscado
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindOne(){
		MasterClass masterClass;
		masterClass=masterClassService.findOne(25);
		Assert.notNull(masterClass);
	}
	
	//Variable masterclasses nula
	@Test(expected = IllegalArgumentException.class)
	public void testNegMasterClassesPromoted(){
		Integer masterClasses;
		masterClasses=masterClassService.masterClassesPromoted();
		masterClasses = null;
		Assert.notNull(masterClasses);
	}
	
	//Variable masterclasses nula
	@Test(expected = IllegalArgumentException.class)
	public void testNegMinMaxAvgMasterClasses(){
		Collection<Double> masterClasses;
		masterClasses = masterClassService.minMaxAvgMasterClasses();
		masterClasses = null;
		Assert.notNull(masterClasses);
	}
	
	//Variable masterclasses nula
	@Test(expected = IllegalArgumentException.class)
	public void testNegAvgMasterClassesPromotedPerCook(){
		Collection<Double> masterClasses;
		masterClasses = masterClassService.avgMasterClassesPromotedPerCook();
		masterClasses = null;
		Assert.notNull(masterClasses);
	}
	
	//Nutritionist no puede crear una master class
	@Test(expected = IllegalArgumentException.class)
	public void testNegAttendMasterClass(){
		MasterClass masterClass;
		Collection<String> registered = new HashSet<String>();
		super.authenticate("nutritionist1");
		masterClass = masterClassService.create();
		masterClass.setRegistered(registered);
		masterClassService.attendMasterClass(masterClass);
		Assert.notNull(masterClass);
		super.authenticate(null);
	}
	
	//Cook no puede hacer promote o demote de una master class
	@Test(expected = IllegalArgumentException.class)
	public void testNegPromoteDemoteMasterClass(){
		MasterClass masterClass;
		super.authenticate("cook1");
		masterClass = masterClassService.create();
		super.authenticate(null);
		super.authenticate("cook1");
		masterClassService.promoteDemoteMasterClass(masterClass);
		Assert.notNull(masterClass);
		super.authenticate(null);
	}
}