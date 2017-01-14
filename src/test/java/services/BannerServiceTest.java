package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.validation.ConstraintViolationException;

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
public class BannerServiceTest extends AbstractTest{
	
	//Service under test --------------------------
	@Autowired
	private BannerService bannerService;
	
	@Autowired
	private CampaignService campaignService;
	
	@Autowired
	private SponsorService sponsorService;
		
	//Tests ---------------------------------------

	//Positive-------------------------------------
	
	@Test
	public void testCreateBanner(){
		Banner banner;
		super.authenticate("sponsor1");
		banner = bannerService.create();
		Assert.notNull(banner);
		super.authenticate(null);
	}
	
	@Test
	public void testSaveBanner(){
		
		super.authenticate("sponsor1");
		
		Banner banner = bannerService.findOne(197);
		banner.setMaxDisplay(50);
		bannerService.save(banner);
		Banner banner2 = bannerService.findOne(197);
		Assert.isTrue(banner2.getMaxDisplay()==50);
		
		super.authenticate(null);
	}	
	
	@Test
	public void testDeleteBanner(){
		
		super.authenticate("sponsor1");

		Banner banner = bannerService.findOne(197);
		bannerService.delete(banner);
		
		Collection<Banner> banners = bannerService.findAll();
		Assert.isTrue(!banners.contains(banner));
		super.authenticate(null);
	}
	
	
	@Test
	public void testFindAll(){
		Collection<Banner>banners;
		banners=bannerService.findAll();
		Assert.notNull(banners);
	}
	
	@Test
	public void testFindOne(){
		Banner banner;
		banner=bannerService.findOne(194);
		Assert.notNull(banner);
	}
	

	//Negative-------------------------------------
	
	//User no puede crear un banner
	@Test(expected = IllegalArgumentException.class)
	public void testNegCreateBanner(){
		Banner banner;
		super.authenticate("user1");
		banner = bannerService.create();
		Assert.notNull(banner);
		super.authenticate(null);
	}
	
	//Atributo setDisplayed negativo
	@Test(expected = ConstraintViolationException.class)
	public void testNegSaveBanner(){
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
		Banner banner, saved;
		Collection<Banner> banners;
		Campaign campaign = campaignService.create();
		Campaign cS;
		
		Date sd = new Date();
		sd.setTime(sd.getTime()-100000);
		Date ed = new Date();
		ed.setTime(ed.getTime()+10000000);
		Collection<Banner> bannersAux = new HashSet<Banner>();
		
		campaign = campaignService.create();
		campaign.setSponsor(sS);
		campaign.setStartDate(sd);
		campaign.setEndDate(ed);
		campaign.setStarred(true);
		campaign.setBanners(bannersAux);
		Assert.notNull(campaign);
		cS = campaignService.save2(campaign);
		
		banner = bannerService.create();
		banner.setMaxDisplay(5);
		banner.setDisplayed(-1);
		banner.setImage("link image");
		banner.setCampaign(cS);
		Assert.notNull(banner);
		
		saved = bannerService.save(banner);
		banners = bannerService.findAll();
		Assert.isTrue(banners.contains(saved));
		super.authenticate(null);
	}	
	
	//Atributo maxDisplay 0
	@Test(expected = ConstraintViolationException.class)
	public void testNegDeleteBanner(){
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
		Banner banner, saved;
		Collection<Banner> banners;
		Campaign campaign = campaignService.create();
		Campaign cS;
		
		Date sd = new Date();
		sd.setTime(sd.getTime()-100000);
		Date ed = new Date();
		ed.setTime(ed.getTime()+10000000);
		Collection<Banner> bannersAux = new HashSet<Banner>();
		
		campaign = campaignService.create();
		campaign.setSponsor(sS);
		campaign.setStartDate(sd);
		campaign.setEndDate(ed);
		campaign.setStarred(true);
		campaign.setBanners(bannersAux);
		Assert.notNull(campaign);
		cS = campaignService.save2(campaign);
		
		banner = bannerService.create();
		banner.setMaxDisplay(0);
		banner.setDisplayed(3);
		banner.setImage("link image");
		banner.setCampaign(cS);
		Assert.notNull(banner);
		
		saved = bannerService.save(banner);
		bannerService.delete(saved);
		banners = bannerService.findAll();
		Assert.isTrue(!banners.contains(saved));
		super.authenticate(null);
	}
	
	//Variable banners nula
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindAll(){
		Collection<Banner>banners;
		banners=bannerService.findAll();
		banners = null;
		Assert.notNull(banners);
	}
	
	//Busqueda ID incorrecta
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindOne(){
		Banner banner;
		banner=bannerService.findOne(25);
		Assert.notNull(banner);
	}

}
