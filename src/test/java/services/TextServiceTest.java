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
import domain.Text;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class TextServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private TextService textService;

	@Autowired
	private MasterClassService masterClassService;
	
	@Autowired
	private CookService cookService;
	
	//Tests ---------------------------------------
	
	//Positive-------------------------------------
	
	@Test
	public void testCreateText(){
		Text text;
		super.authenticate("cook1");
		text = textService.create();
		super.authenticate(null);
		Assert.notNull(text);
	}
	
	@Test
	public void testSaveText(){
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
		Collection<LearningMaterial> learningMaterials = new HashSet<LearningMaterial>();
		Collection<String> registered = new HashSet<String>();
		MasterClass mcS;
		MasterClass masterClass = masterClassService.create();
		masterClass.setCook(cS);
		masterClass.setTitle("Master Class 1");
		masterClass.setDescription("Master Class numero 1");
		masterClass.setPromoted(true);
		masterClass.setRegistered(registered);
		masterClass.setLearningMaterials(learningMaterials);
		mcS = masterClassService.save(masterClass);
		
		Text text, saved;
		Collection<Text> texts = new HashSet<Text>();
		
		text = textService.create();
		text.setMasterClass(mcS);
		text.setTitle("Text8");
		text.setAbstrac("Text numero 8");
		text.setAttachment("https://www.facebook.com/");
		text.setBody("Se supone que es HTML");
		Assert.notNull(text);
		
		saved = textService.save(text);
		texts = textService.findAll();
		Assert.isTrue(texts.contains(saved));
		super.authenticate(null);
	}	
	
	@Test
	public void testDeleteText(){
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
		Collection<LearningMaterial> learningMaterials = new HashSet<LearningMaterial>();
		Collection<String> registered = new HashSet<String>();
		MasterClass mcS;
		MasterClass masterClass = masterClassService.create();
		masterClass.setCook(cS);
		masterClass.setTitle("Master Class 1");
		masterClass.setDescription("Master Class numero 1");
		masterClass.setPromoted(true);
		masterClass.setRegistered(registered);
		masterClass.setLearningMaterials(learningMaterials);
		mcS = masterClassService.save(masterClass);
		
		Text text, saved;
		Collection<Text> texts = new HashSet<Text>();
		
		text = textService.create();
		text.setMasterClass(mcS);
		text.setTitle("Text8");
		text.setAbstrac("Text numero 8");
		text.setAttachment("https://www.facebook.com/");
		text.setBody("Se supone que es HTML");
		Assert.notNull(text);
		
		saved = textService.save(text);
		textService.delete(saved);
		texts = textService.findAll();
		Assert.isTrue(!texts.contains(saved));
		super.authenticate(null);
	}
	
	@Test
	public void testFindAll(){
		Collection<Text>texts;
		texts=textService.findAll();
		Assert.notNull(texts);
	}
	
	@Test
	public void testFindOne(){
		Text text;
		text=textService.findOne(137);
		Assert.notNull(text);
	}
	
	//Negative-------------------------------------
	
	//User no puede crear video
	@Test(expected = IllegalArgumentException.class)
	public void testNCreateText(){
		Text text;
		super.authenticate("user1");
		text = textService.create();
		super.authenticate(null);
		Assert.notNull(text);
	}
		
	//Atributo title esta vacio
	@Test(expected = ConstraintViolationException.class)
	public void testNSaveText(){
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
		Collection<LearningMaterial> learningMaterials = new HashSet<LearningMaterial>();
		Collection<String> registered = new HashSet<String>();
		MasterClass mcS;
		MasterClass masterClass = masterClassService.create();
		masterClass.setCook(cS);
		masterClass.setTitle("Master Class 1");
		masterClass.setDescription("Master Class numero 1");
		masterClass.setPromoted(true);
		masterClass.setRegistered(registered);
		masterClass.setLearningMaterials(learningMaterials);
		mcS = masterClassService.save(masterClass);
		
		Text text, saved;
		Collection<Text> texts = new HashSet<Text>();
		
		text = textService.create();
		text.setMasterClass(mcS);
		text.setTitle("");
		text.setAbstrac("Text numero 8");
		text.setAttachment("https://www.facebook.com/");
		text.setBody("Se supone que es HTML");
		Assert.notNull(text);
		
		saved = textService.save(text);
		texts = textService.findAll();
		Assert.isTrue(texts.contains(saved));
		super.authenticate(null);
	}	
		
	//El atributo body esta vacio
	@Test(expected = ConstraintViolationException.class)
	public void testNDeleteText(){
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
		Collection<LearningMaterial> learningMaterials = new HashSet<LearningMaterial>();
		Collection<String> registered = new HashSet<String>();
		MasterClass mcS;
		MasterClass masterClass = masterClassService.create();
		masterClass.setCook(cS);
		masterClass.setTitle("Master Class 1");
		masterClass.setDescription("Master Class numero 1");
		masterClass.setPromoted(true);
		masterClass.setRegistered(registered);
		masterClass.setLearningMaterials(learningMaterials);
		mcS = masterClassService.save(masterClass);
		
		Text text, saved;
		Collection<Text> texts = new HashSet<Text>();
		
		text = textService.create();
		text.setMasterClass(mcS);
		text.setTitle("Text8");
		text.setAbstrac("Text numero 8");
		text.setAttachment("https://www.facebook.com/");
		text.setBody("");
		Assert.notNull(text);
		
		saved = textService.save(text);
		textService.delete(saved);
		texts = textService.findAll();
		Assert.isTrue(!texts.contains(saved));
		super.authenticate(null);
	}
		
	//El objeto texts lo vuelvo nulo
	@Test(expected = IllegalArgumentException.class)
	public void testNFindAll(){
		Collection<Text>texts;
		texts=textService.findAll();
		texts = null;
		Assert.notNull(texts);
	}
		
	//Fallara al buscar un ID incorrecta
	@Test(expected = IllegalArgumentException.class)
	public void testNFindOne(){
		Text text;
		text=textService.findOne(25);
		Assert.isInstanceOf(text.getClass(), text);
	}
}