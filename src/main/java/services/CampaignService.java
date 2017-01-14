package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CampaignRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Banner;
import domain.Campaign;
import domain.Sponsor;

@Service
@Transactional
public class CampaignService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CampaignRepository campaignRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private BannerService bannerService;
	
	// Constructors -----------------------------------------------------------
	
	public CampaignService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Campaign create() {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("SPONSOR");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Campaign result;
		Sponsor sponsor = sponsorService.findByPrincipal();
		Collection<Banner> banners = new ArrayList<Banner>();

		result = new Campaign();
		
		result.setStarred(false);
		result.setSponsor(sponsor);
		result.setBanners(banners);

		return result;
	}

	public Collection<Campaign> findAll() {
		Collection<Campaign> result;

		result = campaignRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Campaign findOne(int campaignId) {
		Campaign result;

		result = campaignRepository.findOne(campaignId);
		Assert.notNull(result);

		return result;
	}

	public void save(Campaign campaign) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("SPONSOR");
		Authority au2 = new Authority();
		au2.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au)||userAccount.getAuthorities().contains(au2));
		
		Assert.notNull(campaign);

		save2(campaign);
	}

	public void delete(Campaign campaign) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("SPONSOR");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(campaign);
		Assert.isTrue(campaign.getId() != 0);
		Assert.isTrue(!isActive(campaign));
		
		if(campaign.getBanners().size()>0){
			for(Banner b : campaign.getBanners()){
				bannerService.delete(b);
			}
		}

		campaignRepository.delete(campaign);
	}
	
	public Campaign save2(Campaign campaign){
		return campaignRepository.save(campaign);
	}
	
	// Other business methods -------------------------------------------------
	
	public Collection<Campaign> findCampaignStarred(){
		return campaignRepository.findCampaignStarred();
	}
	
	public void editCampaign(Campaign campaign){
		Campaign campaign2 = findOne(campaign.getId());
		
		Assert.isTrue(!isActive(campaign2));
		Assert.isTrue(!isActive(campaign2));
		
		save2(campaign);
	}
	
	public Collection<Campaign> findCampaignSponsor(){
		Collection<Campaign> result;
		
		Sponsor sponsor = sponsorService.findByPrincipal();
		
		result = campaignRepository.findCampaignSponsor(sponsor);
		
		return result;
	}
	
	public boolean isActive(Campaign campaign){
		boolean result;
		Date currentDate = new Date(System.currentTimeMillis()-1);
		
		result = campaign.getStartDate().before(currentDate) && campaign.getEndDate().after(currentDate);
		
		return result;
	}
	
	public boolean isPassed(Campaign campaign){
		boolean result;
		Date currentDate = new Date(System.currentTimeMillis()-1);
		
		result = campaign.getEndDate().after(currentDate);
		
		return result;
	}
}
