package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BannerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Banner;
import domain.Campaign;

@Service
@Transactional
public class BannerService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BannerRepository bannerRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private CampaignService campaignService;
	
	// Constructors -----------------------------------------------------------
	
	public BannerService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Banner create() {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("SPONSOR");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Banner result;

		result = new Banner();
		result.setDisplayed(0);

		return result;
	}
	
	public Banner create(Campaign campaign) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("SPONSOR");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Banner result;

		result = new Banner();
		result.setDisplayed(0);
		result.setCampaign(campaign);

		return result;
	}

	public Collection<Banner> findAll() {
		Collection<Banner> result;

		result = bannerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Banner findOne(int bannerId) {
		Banner result;

		result = bannerRepository.findOne(bannerId);
		Assert.notNull(result);

		return result;
	}

	public Banner save(Banner banner) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("SPONSOR");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(banner);
		
		Banner result;

		result = bannerRepository.save(banner);
		
		return result;
	}

	public Banner saveShowedBanner(Banner banner) {
		
		Assert.notNull(banner);
		
		Banner result;

		result = bannerRepository.save(banner);
		
		return result;
	}

	public void delete(Banner banner) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("SPONSOR");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(banner);
		Assert.isTrue(banner.getId() != 0);
		
		bannerRepository.delete(banner);
	}
	
	// Other business methods -------------------------------------------------
	
	public String showBannerNotStarred(){
		String result;
		Banner selected = null;
		Random rdn = new Random();
		List<Banner> banners = new ArrayList<Banner>();
		int count = 0;
		
		for(Campaign c : campaignService.findAll()){
			if(campaignService.isActive(c)){
				banners.addAll(c.getBanners());
			}
		}
		
		if(banners.size()>0){
			do{
				count ++;
				selected = banners.get(rdn.nextInt(banners.size()-1));
				if(count == 10){
					selected = null;
					break;
				}
			}while(selected.getDisplayed()==selected.getMaxDisplay());
		}
		
		if(selected != null){
			int showed = selected.getDisplayed()+1;
			selected.setDisplayed(showed);
			
			saveShowedBanner(selected);
			result = selected.getImage();
		}else{
			result = "No banner";
		}
		
		
		return result;
	}
	
	public String showBannerStarred(){
		String result;
		Banner selected = null;
		Random rdn = new Random();
		List<Banner> banners = new ArrayList<Banner>();
		List<Campaign> campaigns = new ArrayList<Campaign>();
		campaigns.addAll(campaignService.findCampaignStarred());
		int count = 0;
		
		for(Campaign c : campaigns){
			if(campaignService.isActive(c)){
				banners.addAll(c.getBanners());
			}
		}
		
		if(banners.size()>0){
			do{
				count ++;
				selected = banners.get(rdn.nextInt(banners.size()-1));
				if(count == 10){
					selected = null;
					break;
				}
			}while(selected.getDisplayed()==selected.getMaxDisplay());
		}
		
		if(selected != null){
			int showed = selected.getDisplayed()+1;
			selected.setDisplayed(showed);
			
			saveShowedBanner(selected);
			result = selected.getImage();
		}else{
			result = "No banner";
		}
		return result;
	}

}
