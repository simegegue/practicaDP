package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Banner;
import domain.Campaign;
import domain.CreditCard;
import domain.Sponsor;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class CampaignServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private CampaignService campaignService;
	
	@Autowired
	private SponsorService sponsorService;
	
	//Tests ---------------------------------------

	//Positive-------------------------------------
	
	@Test
	public void testCreateCampaign(){
		Campaign campaign;
		super.authenticate("sponsor1");
		campaign = campaignService.create();
		Assert.notNull(campaign);
		super.authenticate(null);
	}
	
	@Test
	public void testSaveCampaign(){
		Date ltm = new Date();
		ltm.setTime(ltm.getTime()-1000000);
		
		CreditCard cd = new CreditCard();
		cd.setHolderName("Paco");
		cd.setBrandName("VISA");
		cd.setNumber("4079978752719950");
		cd.setExpirationMonth(10);
		cd.setExpirationYear(2019);
		cd.setCvv(555);
		
		Sponsor sS;
		Sponsor sponsor = sponsorService.create();
		sponsor.setName("Paco");
		sponsor.setSurname("Pesao Soy");
		sponsor.setEmail("Paco555@gmail.com");
		sponsor.setCompanyName("Zapateria Barrientos");
		sponsor.setCreditCard(cd);
		sponsor.setLastTimeManages(ltm);
		Assert.notNull(sponsor);
		sS = sponsorService.save(sponsor);
		
		
		super.authenticate("sponsor1");
		Campaign campaign, saved;
		Collection<Campaign> campaigns;
		Collection<Banner> banners = new HashSet<Banner>();
		Date sd = new Date();
		sd.setTime(sd.getTime()-100000);
		Date ed = new Date();
		ed.setTime(ed.getTime()+10000000);
		
		campaign = campaignService.create();
		campaign.setStartDate(sd);
		campaign.setEndDate(ed);
		campaign.setStarred(true);
		campaign.setSponsor(sS);
		campaign.setBanners(banners);
		Assert.notNull(campaign);
		
		saved = campaignService.save2(campaign);
		campaigns = campaignService.findAll();
		Assert.isTrue(campaigns.contains(saved));
		super.authenticate(null);
	}	
	
	@Test
	public void testDeleteCampaign(){	
		
		super.authenticate("sponsor2");
		Campaign campaign;
		campaign=campaignService.findOne(193);
		Assert.notNull(campaign);
		
		campaignService.delete(campaign);
		
		Collection<Campaign> campaigns = campaignService.findAll();
		Assert.isTrue(!campaigns.contains(campaign));
		
		super.authenticate(null);
	}
	
	@Test
	public void testFindAll(){
		Collection<Campaign>campaigns;
		campaigns=campaignService.findAll();
		Assert.notNull(campaigns);
	}
	
	@Test
	public void testFindOne(){
		Campaign campaign;
		campaign=campaignService.findOne(192);
		Assert.notNull(campaign);
	}
	
	//Negative-------------------------------------
	
	//User no puede crear una campaign
	@Test(expected = IllegalArgumentException.class)
	public void testNegCreateCampaign(){
		Campaign campaign;
		super.authenticate("user1");
		campaign = campaignService.create();
		Assert.notNull(campaign);
		super.authenticate(null);
	}
	
	//Nutritionist no puede crear campaign
	@Test(expected = IllegalArgumentException.class)
	public void testNegSaveCampaign(){
		Date ltm = new Date();
		ltm.setTime(ltm.getTime()-1000000);
		
		CreditCard cd = new CreditCard();
		cd.setHolderName("Paco");
		cd.setBrandName("VISA");
		cd.setNumber("4079978752719950");
		cd.setExpirationMonth(10);
		cd.setExpirationYear(2019);
		cd.setCvv(555);
		
		Sponsor sS;
		Sponsor sponsor = sponsorService.create();
		sponsor.setName("Paco");
		sponsor.setSurname("Pesao Soy");
		sponsor.setEmail("Paco555@gmail.com");
		sponsor.setCompanyName("Zapateria Barrientos");
		sponsor.setCreditCard(cd);
		sponsor.setLastTimeManages(ltm);
		Assert.notNull(sponsor);
		sS = sponsorService.save(sponsor);
		
		
		super.authenticate("nutritionist1");
		Campaign campaign, saved;
		Collection<Campaign> campaigns;
		Collection<Banner> banners = new HashSet<Banner>();
		Date sd = new Date();
		sd.setTime(sd.getTime()-100000);
		Date ed = new Date();
		ed.setTime(ed.getTime()+10000000);
		
		campaign = campaignService.create();
		campaign.setStartDate(sd);
		campaign.setEndDate(ed);
		campaign.setStarred(true);
		campaign.setSponsor(sS);
		campaign.setBanners(banners);
		Assert.notNull(campaign);
		
		saved = campaignService.save2(campaign);
		campaigns = campaignService.findAll();
		Assert.isTrue(campaigns.contains(saved));
		super.authenticate(null);
	}	
	
	//User no puede crear campaign
	@Test(expected = IllegalArgumentException.class)
	public void testNegDeleteCampaign(){
		Date ltm = new Date();
		ltm.setTime(ltm.getTime()-1000000);
		
		CreditCard cd = new CreditCard();
		cd.setHolderName("Paco");
		cd.setBrandName("VISA");
		cd.setNumber("4079978752719950");
		cd.setExpirationMonth(10);
		cd.setExpirationYear(2019);
		cd.setCvv(555);
		
		Sponsor sS;
		Sponsor sponsor = sponsorService.create();
		sponsor.setName("Paco");
		sponsor.setSurname("Pesao Soy");
		sponsor.setEmail("Paco555@gmail.com");
		sponsor.setCompanyName("Zapateria Barrientos");
		sponsor.setCreditCard(cd);
		sponsor.setLastTimeManages(ltm);
		Assert.notNull(sponsor);
		sS = sponsorService.save(sponsor);
		
		
		super.authenticate("user");
		Campaign campaign, saved;
		Collection<Campaign> campaigns;
		Collection<Banner> banners = new HashSet<Banner>();
		Date sd = new Date();
		sd.setTime(sd.getTime()-100000);
		Date ed = new Date();
		ed.setTime(ed.getTime()+10000000);
		
		campaign = campaignService.create();
		campaign.setStartDate(sd);
		campaign.setEndDate(ed);
		campaign.setStarred(true);
		campaign.setSponsor(sS);
		campaign.setBanners(banners);
		Assert.notNull(campaign);
		
		saved = campaignService.save2(campaign);
		campaignService.delete(saved);
		campaigns = campaignService.findAll();
		Assert.isTrue(!campaigns.contains(saved));
		super.authenticate(null);
	}
	
	//Variable campaigns nula
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindAll(){
		Collection<Campaign>campaigns;
		campaigns=campaignService.findAll();
		campaigns = null;
		Assert.notNull(campaigns);
	}
	
	//Id incorrecta
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindOne(){
		Campaign campaign;
		campaign=campaignService.findOne(25);
		Assert.notNull(campaign);
	}
}