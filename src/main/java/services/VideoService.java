package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.VideoRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Video;


@Service
@Transactional
public class VideoService {
	
	// Managed repository -----------------------------------------------------

	@Autowired
	private VideoRepository videoRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	CookService cookService;
	
	@Autowired
	MasterClassService masterClassService;
	
	// Constructors -----------------------------------------------------------
	
	public VideoService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Video create() {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("COOK");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
	
		Video result;
		result = new Video();
		return result;
	}

	public Collection<Video> findAll() {
		Collection<Video> result;

		result = videoRepository.findAll();
		
		Assert.notNull(result);

		return result;
	}

	public Video findOne(int videoId) {
		Video result;

		result = videoRepository.findOne(videoId);
		Assert.notNull(result);

		return result;
	}
	
	public Video save(Video video) {
		
		Assert.notNull(video);
		Video result;
		
		result = videoRepository.save(video);
		
		return result;
	}

	public Video save2(Video video) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("COOK");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(video);
		
		Video result;

		result = videoRepository.save(video);
		
		return result;
	}

	public void delete(Video video) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("COOK");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(video);
		Assert.isTrue(video.getId() != 0);

		videoRepository.delete(video);
	}
	
	// Other business methods -------------------------------------------------

}
