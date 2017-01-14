package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LearningMaterialRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.LearningMaterial;
import domain.Presentation;
import domain.Text;
import domain.Video;

@Service
@Transactional
public class LearningMaterialService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private LearningMaterialRepository learningMaterialRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private TextService textService;
	
	@Autowired
	private PresentationService presentationService;
	
	// Constructors -----------------------------------------------------------
	
	public LearningMaterialService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public LearningMaterial create() {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("COOK");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		LearningMaterial result;

		result = new LearningMaterial();

		return result;
	}

	public Collection<LearningMaterial> findAll() {
		Collection<LearningMaterial> result;

		result = learningMaterialRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public LearningMaterial findOne(int learningMaterialId) {
		LearningMaterial result;

		result = learningMaterialRepository.findOne(learningMaterialId);
		Assert.notNull(result);

		return result;
	}

	public LearningMaterial save(LearningMaterial learningMaterial) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("COOK");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(learningMaterial);
		
		LearningMaterial result;

		result = learningMaterialRepository.save(learningMaterial);
		
		return result;
	}

	public void delete(LearningMaterial learningMaterial) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("COOK");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(learningMaterial);
		Assert.isTrue(learningMaterial.getId() != 0);

		learningMaterialRepository.delete(learningMaterial);
	}
	
	// Other business methods -------------------------------------------------

	public Collection<Double> avgLearningMaterials(){
		Collection<Double> result;
		result = learningMaterialRepository.avgLearningMaterials();
		return result;
	}
	
	public Collection<Video> findVideos(Collection<LearningMaterial> clm){
		
		Collection<Video> videosaux = videoService.findAll();
		Collection<Video> videos = new ArrayList<Video>();
		
		for (Video v : videosaux) {
			if(clm.contains(v)){
				videos.add(v);
			}
		}
		
		return videos;
	}
	
	public Collection<Text> findTexts(Collection<LearningMaterial> clm){
		
		Collection<Text> textsaux = textService.findAll();
		Collection<Text> texts = new ArrayList<Text>();
		
		for (Text t : textsaux) {
			if(clm.contains(t)){
				texts.add(t);
			}
		}
		
		return texts;
	}

	public Collection<Presentation> findPresentations(Collection<LearningMaterial> clm){
	
		Collection<Presentation> presentationsaux = presentationService.findAll();
		Collection<Presentation> presentations = new ArrayList<Presentation>();
	
		for (Presentation p : presentationsaux) {
			if(clm.contains(p)){
				presentations.add(p);
			}
		}
	
		return presentations;
	}
	
}
