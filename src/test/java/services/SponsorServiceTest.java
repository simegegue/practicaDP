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

import domain.CreditCard;
import domain.Sponsor;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class SponsorServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private SponsorService sponsorService;
	
	//Tests ---------------------------------------
	
	//Positive ------------------------------------
	
	@Test
	public void testCreateSponsor(){
		Sponsor sponsor;
		sponsor = sponsorService.create();
		Assert.notNull(sponsor);
	}
	
	@Test
	public void testSaveSponsor(){
		
		Date ltm = new Date();
		ltm.setTime(ltm.getTime()-1000000);
		
		CreditCard cd = new CreditCard();
		cd.setHolderName("Paco");
		cd.setBrandName("VISA");
		cd.setNumber("4079978752719950");
		cd.setExpirationMonth(10);
		cd.setExpirationYear(2019);
		cd.setCvv(555);
		
		Sponsor sponsor, saved;
		Collection<Sponsor> sponsors = new HashSet<Sponsor>();
		
		sponsor = sponsorService.create();
		sponsor.setName("Paco");
		sponsor.setSurname("Pesao Soy");
		sponsor.setEmail("Paco555@gmail.com");
		sponsor.setCompanyName("Zapateria Barrientos");
		sponsor.setCreditCard(cd);
		sponsor.setLastTimeManages(ltm);
		Assert.notNull(sponsor);
		
		saved = sponsorService.save(sponsor);
		sponsors = sponsorService.findAll();
		Assert.isTrue(sponsors.contains(saved));
		
	}	
	
	@Test
	public void testDeleteSponsor(){
		
		Date ltm = new Date();
		ltm.setTime(ltm.getTime()-1000000);
		
		CreditCard cd = new CreditCard();
		cd.setHolderName("Paco");
		cd.setBrandName("VISA");
		cd.setNumber("4079978752719950");
		cd.setExpirationMonth(10);
		cd.setExpirationYear(2019);
		cd.setCvv(555);
		
		Sponsor sponsor, saved;
		Collection<Sponsor> sponsors = new HashSet<Sponsor>();
		
		sponsor = sponsorService.create();
		sponsor.setName("Paco");
		sponsor.setSurname("Pesao Soy");
		sponsor.setEmail("Paco555@gmail.com");
		sponsor.setCompanyName("Zapateria Barrientos");
		sponsor.setCreditCard(cd);
		sponsor.setLastTimeManages(ltm);
		Assert.notNull(sponsor);
		
		saved = sponsorService.save(sponsor);
		sponsorService.delete(saved);
		sponsors = sponsorService.findAll();
		Assert.isTrue(!sponsors.contains(saved));
	}
	
	@Test
	public void testFindAll(){
		Collection<Sponsor>sponsors;
		sponsors=sponsorService.findAll();
		Assert.notNull(sponsors);
	}
	
	@Test
	public void testFindOne(){
		Sponsor sponsor;
		sponsor=sponsorService.findOne(43);
		Assert.notNull(sponsor);
	}
	
	@Test 
	public void testFindMinAvgMaxCampaignPerSponsor(){
		Collection<Double> result = new HashSet<Double>();
		result = sponsorService.findMinAvgMaxActiveCampaignPerSponsor();
		Assert.notNull(result);
	}

	@Test 
	public void testFindMinAvgMaxActiveCampaignPerSponsor(){
		Collection<Double> result = new HashSet<Double>();
		result = sponsorService.findMinAvgMaxActiveCampaignPerSponsor();
		Assert.notNull(result);
	}

	@Test
	public void testFindSponsorsByUnpaidMonthlyBills(){
		Collection<Sponsor> result = new HashSet<Sponsor>();
		result = sponsorService.findSponsorsByUnpaidMonthlyBills();
		Assert.notNull(result);
	}

	@Test
	public void testFindCompanyRanking(){
		Collection<Sponsor> result = new HashSet<Sponsor>();
		result = sponsorService.findCompanyRanking();
		Assert.notNull(result);
	}

	@Test
	public void testFindInactiveSponsor(){
		Collection<Sponsor> result = new HashSet<Sponsor>();
		result = sponsorService.findInactiveSponsor();
		Assert.notNull(result);
	}

	@Test
	public void testFindCompaniesSpendLessAverage(){
		Collection<Sponsor> result = new HashSet<Sponsor>();
		result = sponsorService.findCompaniesSpendLessAverage();
		Assert.notNull(result);
	}

	@Test
	public void testFindCompaniesSpendMoreThan90(){
		Collection<Sponsor> result = new HashSet<Sponsor>();
		result = sponsorService.findCompaniesSpendMoreThan90();
		Assert.notNull(result);
	}
	
	//Positive ------------------------------------

	//Sponsor null
	@Test(expected = IllegalArgumentException.class)
	public void testNegCreateSponsor(){
		Sponsor sponsor;
		sponsor = sponsorService.create();
		sponsor = null;
		Assert.notNull(sponsor);
	}
	
	//Atributo company name vacio
	@Test(expected = ConstraintViolationException.class)
	public void testNegSaveSponsor(){
		
		Date ltm = new Date();
		ltm.setTime(ltm.getTime()-1000000);
		
		CreditCard cd = new CreditCard();
		cd.setHolderName("Paco");
		cd.setBrandName("VISA");
		cd.setNumber("4079978752719950");
		cd.setExpirationMonth(10);
		cd.setExpirationYear(2019);
		cd.setCvv(555);
		
		Sponsor sponsor, saved;
		Collection<Sponsor> sponsors = new HashSet<Sponsor>();
		
		sponsor = sponsorService.create();
		sponsor.setName("Paco");
		sponsor.setSurname("Pesao Soy");
		sponsor.setEmail("Paco555@gmail.com");
		sponsor.setCompanyName("");
		sponsor.setCreditCard(cd);
		sponsor.setLastTimeManages(ltm);
		Assert.notNull(sponsor);
		
		saved = sponsorService.save(sponsor);
		sponsors = sponsorService.findAll();
		Assert.isTrue(sponsors.contains(saved));
		
	}	
	
	//Atributo name vacio
	@Test(expected = ConstraintViolationException.class)
	public void testNegDeleteSponsor(){
		
		Date ltm = new Date();
		ltm.setTime(ltm.getTime()-1000000);
		
		CreditCard cd = new CreditCard();
		cd.setHolderName("Paco");
		cd.setBrandName("VISA");
		cd.setNumber("4079978752719950");
		cd.setExpirationMonth(10);
		cd.setExpirationYear(2019);
		cd.setCvv(555);
		
		Sponsor sponsor, saved;
		Collection<Sponsor> sponsors = new HashSet<Sponsor>();
		
		sponsor = sponsorService.create();
		sponsor.setName("");
		sponsor.setSurname("Pesao Soy");
		sponsor.setEmail("Paco555@gmail.com");
		sponsor.setCompanyName("Zapateria Barrientos");
		sponsor.setCreditCard(cd);
		sponsor.setLastTimeManages(ltm);
		Assert.notNull(sponsor);
		
		saved = sponsorService.save(sponsor);
		sponsorService.delete(saved);
		sponsors = sponsorService.findAll();
		Assert.isTrue(!sponsors.contains(saved));
	}
	
	//Variable sponsors nula
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindAll(){
		Collection<Sponsor>sponsors;
		sponsors=sponsorService.findAll();
		sponsors = null;
		Assert.notNull(sponsors);
	}
	
	//Id incorrecta
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindOne(){
		Sponsor sponsor;
		sponsor=sponsorService.findOne(25);
		Assert.notNull(sponsor);
	}
	
	//Variable result nula
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindMinAvgMaxCampaignPerSponsor(){
		Collection<Double> result = new HashSet<Double>();
		result = sponsorService.findMinAvgMaxActiveCampaignPerSponsor();
		result = null;
		Assert.notNull(result);
	}

	//Variable result nula
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindMinAvgMaxActiveCampaignPerSponsor(){
		Collection<Double> result = new HashSet<Double>();
		result = sponsorService.findMinAvgMaxActiveCampaignPerSponsor();
		result = null;
		Assert.notNull(result);
	}

	//Variable result nula
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindSponsorsByUnpaidMonthlyBills(){
		Collection<Sponsor> result = new HashSet<Sponsor>();
		result = sponsorService.findSponsorsByUnpaidMonthlyBills();
		result = null;
		Assert.notNull(result);
	}

	//Variable result nula
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindCompanyRanking(){
		Collection<Sponsor> result = new HashSet<Sponsor>();
		result = sponsorService.findCompanyRanking();
		result = null;
		Assert.notNull(result);
	}

	//Variable result nula
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindInactiveSponsor(){
		Collection<Sponsor> result = new HashSet<Sponsor>();
		result = sponsorService.findInactiveSponsor();
		result = null;
		Assert.notNull(result);
	}

	//Variable result nula
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindCompaniesSpendLessAverage(){
		Collection<Sponsor> result = new HashSet<Sponsor>();
		result = sponsorService.findCompaniesSpendLessAverage();
		result = null;
		Assert.notNull(result);
	}

	//Variable result nula
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindCompaniesSpendMoreThan90(){
		Collection<Sponsor> result = new HashSet<Sponsor>();
		result = sponsorService.findCompaniesSpendMoreThan90();
		result = null;
		Assert.notNull(result);
	}
}