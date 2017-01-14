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
import domain.LearningMaterial;
import domain.MasterClass;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class LearningMaterialServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private LearningMaterialService learningMaterialService;
	
	@Autowired
	private MasterClassService masterClassService;
	
	@Autowired
	private CookService cookService;
	
	//Tests ---------------------------------------
	
	//Positive ------------------------------------
	
	@Test
	public void testCreateLearningMaterial(){
		LearningMaterial learningMaterial;
		super.authenticate("cook1");
		learningMaterial = learningMaterialService.create();
		super.authenticate(null);
		Assert.notNull(learningMaterial);
	}
	
	@Test
	public void testSaveLearningMaterial(){
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
		Collection<LearningMaterial> lm = new HashSet<LearningMaterial>();
		Collection<String> registered = new HashSet<String>();
		MasterClass mcS;
		MasterClass masterClass = masterClassService.create();
		masterClass.setCook(cS);
		masterClass.setTitle("Master Class 1");
		masterClass.setDescription("Master Class numero 1");
		masterClass.setPromoted(true);
		masterClass.setRegistered(registered);
		masterClass.setLearningMaterials(lm);
		mcS = masterClassService.save(masterClass);
		
		LearningMaterial learningMaterial, saved;
		Collection<LearningMaterial> learningMaterials = new HashSet<LearningMaterial>();
		
		learningMaterial = learningMaterialService.create();
		learningMaterial.setMasterClass(mcS);
		learningMaterial.setTitle("Video8");
		learningMaterial.setAbstrac("LearningMaterial numero 8");
		learningMaterial.setAttachment("https://www.facebook.com/");
		
		Assert.notNull(learningMaterial);
		
		saved = learningMaterialService.save(learningMaterial);
		learningMaterials = learningMaterialService.findAll();
		Assert.isTrue(learningMaterials.contains(saved));
		super.authenticate(null);
	}	
	
	@Test
	public void testDeleteLearningMaterial(){
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
		Collection<LearningMaterial> lm = new HashSet<LearningMaterial>();
		Collection<String> registered = new HashSet<String>();
		MasterClass mcS;
		MasterClass masterClass = masterClassService.create();
		masterClass.setCook(cS);
		masterClass.setTitle("Master Class 1");
		masterClass.setDescription("Master Class numero 1");
		masterClass.setPromoted(true);
		masterClass.setRegistered(registered);
		masterClass.setLearningMaterials(lm);
		mcS = masterClassService.save(masterClass);
		
		LearningMaterial learningMaterial, saved;
		Collection<LearningMaterial> learningMaterials = new HashSet<LearningMaterial>();
		
		learningMaterial = learningMaterialService.create();
		learningMaterial.setMasterClass(mcS);
		learningMaterial.setTitle("Video8");
		learningMaterial.setAbstrac("LearningMaterial numero 8");
		learningMaterial.setAttachment("https://www.facebook.com/");
		
		Assert.notNull(learningMaterial);
		
		saved = learningMaterialService.save(learningMaterial);
		learningMaterialService.delete(saved);
		learningMaterials = learningMaterialService.findAll();
		Assert.isTrue(!learningMaterials.contains(saved));
		super.authenticate(null);
	}
	
	@Test
	public void testFindAll(){
		Collection<LearningMaterial>learningMaterials;
		learningMaterials=learningMaterialService.findAll();
		Assert.notNull(learningMaterials);
	}
	
	@Test
	public void testFindOne(){
		LearningMaterial learningMaterial;
		learningMaterial=learningMaterialService.findOne(141);
		Assert.notNull(learningMaterial);
	}
	
	@Test
	public void testAvgLearningMaterials(){
		Collection<Double> result = new HashSet<Double>();
		learningMaterialService.avgLearningMaterials();
		Assert.notNull(result);
	}
	
	//Negative ------------------------------------
	
	//Un nutritionist no puede crear un learning material
	@Test(expected = IllegalArgumentException.class)
	public void testNegCreateLearningMaterial(){
		LearningMaterial learningMaterial;
		super.authenticate("nutritionist1");
		learningMaterial = learningMaterialService.create();
		super.authenticate(null);
		Assert.notNull(learningMaterial);
	}
	
	//Atributo titulo esta vacio
	@Test(expected = ConstraintViolationException.class)
	public void testNegSaveLearningMaterial(){
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
		Collection<LearningMaterial> lm = new HashSet<LearningMaterial>();
		Collection<String> registered = new HashSet<String>();
		MasterClass mcS;
		MasterClass masterClass = masterClassService.create();
		masterClass.setCook(cS);
		masterClass.setTitle("Master Class 1");
		masterClass.setDescription("Master Class numero 1");
		masterClass.setPromoted(true);
		masterClass.setRegistered(registered);
		masterClass.setLearningMaterials(lm);
		mcS = masterClassService.save(masterClass);
		
		LearningMaterial learningMaterial, saved;
		Collection<LearningMaterial> learningMaterials = new HashSet<LearningMaterial>();
		
		learningMaterial = learningMaterialService.create();
		learningMaterial.setMasterClass(mcS);
		learningMaterial.setTitle("");
		learningMaterial.setAbstrac("LearningMaterial numero 8");
		learningMaterial.setAttachment("https://www.facebook.com/");
		
		Assert.notNull(learningMaterial);
		
		saved = learningMaterialService.save(learningMaterial);
		learningMaterials = learningMaterialService.findAll();
		Assert.isTrue(learningMaterials.contains(saved));
		super.authenticate(null);
	}	
	
	//Atributo abstract esta vacio
	@Test(expected = ConstraintViolationException.class)
	public void testNegDeleteLearningMaterial(){
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
		Collection<LearningMaterial> lm = new HashSet<LearningMaterial>();
		Collection<String> registered = new HashSet<String>();
		MasterClass mcS;
		MasterClass masterClass = masterClassService.create();
		masterClass.setCook(cS);
		masterClass.setTitle("Master Class 1");
		masterClass.setDescription("Master Class numero 1");
		masterClass.setPromoted(true);
		masterClass.setRegistered(registered);
		masterClass.setLearningMaterials(lm);
		mcS = masterClassService.save(masterClass);
		
		LearningMaterial learningMaterial, saved;
		Collection<LearningMaterial> learningMaterials = new HashSet<LearningMaterial>();
		
		learningMaterial = learningMaterialService.create();
		learningMaterial.setMasterClass(mcS);
		learningMaterial.setTitle("Video8");
		learningMaterial.setAbstrac("");
		learningMaterial.setAttachment("https://www.facebook.com/");
		
		Assert.notNull(learningMaterial);
		
		saved = learningMaterialService.save(learningMaterial);
		learningMaterialService.delete(saved);
		learningMaterials = learningMaterialService.findAll();
		Assert.isTrue(!learningMaterials.contains(saved));
		super.authenticate(null);
	}
	
	//Variable learning materials es nula
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindAll(){
		Collection<LearningMaterial>learningMaterials;
		learningMaterials=learningMaterialService.findAll();
		learningMaterials=null;
		Assert.notNull(learningMaterials);
	}
	
	//El objeto encontrado no corresponde con el buscado
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindOne(){
		LearningMaterial learningMaterial;
		learningMaterial=learningMaterialService.findOne(25);
		Assert.notNull(learningMaterial);
	}
	
	//Variable result es nula
	@Test(expected = IllegalArgumentException.class)
	public void testNegAvgLearningMaterials(){
		Collection<Double> result = new HashSet<Double>();
		learningMaterialService.avgLearningMaterials();
		result = null;
		Assert.notNull(result);
	}
	
}