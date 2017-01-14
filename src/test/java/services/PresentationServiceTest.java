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
import domain.Presentation;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class PresentationServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private PresentationService presentationService;
	
	@Autowired
	private MasterClassService masterClassService;
	
	@Autowired
	private CookService cookService;
	
	//Tests ---------------------------------------
	
	//Positive-------------------------------------
	
	@Test
	public void testCreatePresentation(){
		Presentation presentation;
		super.authenticate("cook1");
		presentation = presentationService.create();
		Assert.notNull(presentation);
		super.authenticate(null);
	}
	
	@Test
	public void testSavePresentation(){
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
		
		Presentation presentation, saved;
		Collection<Presentation> presentations = new HashSet<Presentation>();
		
		presentation = presentationService.create();
		presentation.setMasterClass(mcS);
		presentation.setTitle("Video8");
		presentation.setAbstrac("Video numero 8");
		presentation.setAttachment("https://www.facebook.com/");
		presentation.setPath("https://www.twitch.tv/directory");
		Assert.notNull(presentation);
		
		saved = presentationService.save(presentation);
		presentations = presentationService.findAll();
		Assert.isTrue(presentations.contains(saved));
		super.authenticate(null);
	}	
	
	@Test
	public void testDeletePresentation(){
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
		
		Presentation presentation, saved;
		Collection<Presentation> presentations = new HashSet<Presentation>();
		
		presentation = presentationService.create();
		presentation.setMasterClass(mcS);
		presentation.setTitle("Video8");
		presentation.setAbstrac("Video numero 8");
		presentation.setAttachment("https://www.facebook.com/");
		presentation.setPath("https://www.twitch.tv/directory");
		Assert.notNull(presentation);
		
		saved = presentationService.save(presentation);
		presentationService.delete(saved);
		presentations = presentationService.findAll();
		Assert.isTrue(!presentations.contains(saved));
		super.authenticate(null);
	}
	
	@Test
	public void testFindAll(){
		Collection<Presentation>presentations;
		presentations=presentationService.findAll();
		Assert.notNull(presentations);
	}
	
	@Test
	public void testFindOne(){
		Presentation presentation;
		presentation=presentationService.findOne(139);
		Assert.notNull(presentation);
	}
	
	//Negative-------------------------------------
	
	//Sponsor no puede crear un objeto presentation
	@Test(expected = IllegalArgumentException.class)
	public void testNCreatePresentation(){
		Presentation presentation;
		super.authenticate("sponsor1");
		presentation = presentationService.create();
		super.authenticate(null);
		Assert.notNull(presentation);
	}
	
	//Atributo title esta vacio
	@Test(expected = ConstraintViolationException.class)
	public void testNSavePresentation(){
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
		
		Presentation presentation, saved;
		Collection<Presentation> presentations = new HashSet<Presentation>();
		
		presentation = presentationService.create();
		presentation.setMasterClass(mcS);
		presentation.setTitle("");
		presentation.setAbstrac("Video numero 8");
		presentation.setAttachment("https://www.facebook.com/");
		presentation.setPath("https://www.twitch.tv/directory");
		Assert.notNull(presentation);
		
		saved = presentationService.save(presentation);
		presentations = presentationService.findAll();
		Assert.isTrue(presentations.contains(saved));
		super.authenticate(null);
	}	
	
	//Atributo path esta vacio
	@Test(expected = ConstraintViolationException.class)
	public void testNDeletePresentation(){
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
		
		Presentation presentation, saved;
		Collection<Presentation> presentations = new HashSet<Presentation>();
		
		presentation = presentationService.create();
		presentation.setMasterClass(mcS);
		presentation.setTitle("Video8");
		presentation.setAbstrac("Video numero 8");
		presentation.setAttachment("https://www.facebook.com/");
		presentation.setPath("");
		Assert.notNull(presentation);
		
		saved = presentationService.save(presentation);
		presentationService.delete(saved);
		presentations = presentationService.findAll();
		Assert.isTrue(!presentations.contains(saved));
		super.authenticate(null);
	}
	
	//El objeto presentations lo vuelvo nulo
	@Test(expected = IllegalArgumentException.class)
	public void testNFindAll(){
		Collection<Presentation>presentations;
		presentations=presentationService.findAll();
		presentations = null;
		Assert.notNull(presentations);
	}
	
	//Fallara al buscar un ID incorrecta
	@Test(expected = IllegalArgumentException.class)
	public void testNFindOne(){
		Presentation presentation;
		presentation=presentationService.findOne(25);
		Assert.isInstanceOf(presentation.getClass(), presentation);
	}
}