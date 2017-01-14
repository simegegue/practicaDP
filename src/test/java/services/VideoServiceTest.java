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
import domain.Video;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class VideoServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private MasterClassService masterClassService;
	
	@Autowired
	private CookService cookService;
	
	//Tests ---------------------------------------
	
	//Positive-------------------------------------
	
	@Test
	public void testCreateVideo(){
		Video video;
		super.authenticate("cook1");
		video = videoService.create();
		super.authenticate(null);
		Assert.notNull(video);
	}
	
	@Test
	public void testSaveVideo(){
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
		
		Video video, saved;
		Collection<Video> videos = new HashSet<Video>();
		
		video = videoService.create();
		video.setMasterClass(mcS);
		video.setTitle("Video8");
		video.setAbstrac("Video numero 8");
		video.setAttachment("https://www.facebook.com/");
		video.setIdentifier("https://www.youtube.com/watch?v=bplQcvCjWio");
		Assert.notNull(video);
		
		saved = videoService.save(video);
		videos = videoService.findAll();
		Assert.isTrue(videos.contains(saved));
		super.authenticate(null);
	}	
	
	@Test
	public void testDeleteVideo(){
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
		
		Video video, saved;
		Collection<Video> videos = new HashSet<Video>();
		
		video = videoService.create();
		video.setMasterClass(mcS);
		video.setTitle("Video8");
		video.setAbstrac("Video numero 8");
		video.setAttachment("https://www.facebook.com/");
		video.setIdentifier("https://www.youtube.com/watch?v=bplQcvCjWio");
		Assert.notNull(video);
		
		saved = videoService.save(video);
		videoService.delete(saved);
		videos = videoService.findAll();
		Assert.isTrue(!videos.contains(saved));
		super.authenticate(null);
	}
	
	@Test
	public void testFindAll(){
		Collection<Video>videos;
		videos=videoService.findAll();
		Assert.notNull(videos);
	}
	
	@Test
	public void testFindOne(){
		Video video;
		video=videoService.findOne(141);
		Assert.notNull(video);
	}

	//Negative-------------------------------------
	
	//Un nutritionist no puede crear un video
	@Test(expected = IllegalArgumentException.class)
	public void testNCreateVideo(){
		Video video;
		super.authenticate("nutritionist1");
		video = videoService.create();
		super.authenticate(null);
		Assert.notNull(video);
	}
		
	//El abstract esta vacio
	@Test(expected = ConstraintViolationException.class)
	public void testNSaveVideo(){
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
		
		Video video, saved;
		Collection<Video> videos = new HashSet<Video>();
		
		video = videoService.create();
		video.setMasterClass(mcS);
		video.setTitle("Video8");
		video.setAbstrac("");
		video.setAttachment("https://www.facebook.com/");
		video.setIdentifier("https://www.youtube.com/watch?v=bplQcvCjWio");
		Assert.notNull(video);
		
		saved = videoService.save(video);
		videos = videoService.findAll();
		Assert.isTrue(videos.contains(saved));
		super.authenticate(null);
	}	
		
	//El titulo esta vacio
	@Test(expected = ConstraintViolationException.class)
	public void testNDeleteVideo(){
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
		
		Video video, saved;
		Collection<Video> videos = new HashSet<Video>();
		
		video = videoService.create();
		video.setMasterClass(mcS);
		video.setTitle("");
		video.setAbstrac("Video numero 8");
		video.setAttachment("https://www.facebook.com/");
		video.setIdentifier("https://www.youtube.com/watch?v=bplQcvCjWio");
		Assert.notNull(video);
		
		saved = videoService.save(video);
		videoService.delete(saved);
		videos = videoService.findAll();
		Assert.isTrue(!videos.contains(saved));
		super.authenticate(null);
	}
		
	//El objeto videos lo vuelvo nulo
	@Test(expected = IllegalArgumentException.class)
	public void testNFindAll(){
		Collection<Video>videos;
		videos=videoService.findAll();
		videos = null;
		Assert.notNull(videos);
	}
		
	//Fallara al buscar un ID incorrecta
	@Test(expected = IllegalArgumentException.class)
	public void testNFindOne(){
		Video video;
		video=videoService.findOne(25);
		Assert.isInstanceOf(video.getClass(), video);
	}
}