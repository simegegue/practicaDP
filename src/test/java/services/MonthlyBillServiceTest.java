package services;

import java.util.Date;
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

import domain.Campaign;
import domain.CreditCard;
import domain.MonthlyBill;
import domain.Sponsor;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class MonthlyBillServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private MonthlyBillService monthlyBillService;
	
	@Autowired
	private SponsorService sponsorService;
	
	@Autowired	
	private CampaignService campaignService;
	//Tests ---------------------------------------

	//Positive-------------------------------------
	
	@Test
	public void testCreateMonthlyBill(){
		MonthlyBill monthlyBill;
		super.authenticate("admin");
		monthlyBill = monthlyBillService.create();
		Assert.notNull(monthlyBill);
		super.authenticate(null);
		
	}
	
	@Test
	public void testSaveMonthlyBill(){
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
		
		super.authenticate("admin");
		MonthlyBill monthlyBill, saved;
		Collection<MonthlyBill> monthlyBills = new HashSet<MonthlyBill>();
		Date d=new Date();
		d.setTime(d.getTime()-60000);
		monthlyBill = monthlyBillService.create();
		monthlyBill.setCreateMoment(d);
		monthlyBill.setPayMoment(null);
		monthlyBill.setCost(50);
		monthlyBill.setDescription("Monthly bill del mes de Agosto");
		monthlyBill.setSponsor(sS);
		Assert.notNull(monthlyBill);
		
		saved = monthlyBillService.save(monthlyBill);
		monthlyBills = monthlyBillService.findAll();
		Assert.isTrue(monthlyBills.contains(saved));
		super.authenticate(null);
	}	
	
	@Test
	public void testDeleteMonthlyBill(){
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
		
		super.authenticate("admin");
		MonthlyBill monthlyBill, saved;
		Collection<MonthlyBill> monthlyBills = new HashSet<MonthlyBill>();
		Date d=new Date();
		d.setTime(d.getTime()-60000);
		monthlyBill = monthlyBillService.create();
		monthlyBill.setCreateMoment(d);
		monthlyBill.setPayMoment(null);
		monthlyBill.setCost(50);
		monthlyBill.setDescription("Monthly bill del mes de Agosto");
		monthlyBill.setSponsor(sS);
		Assert.notNull(monthlyBill);
		
		saved = monthlyBillService.save(monthlyBill);
		monthlyBillService.delete(saved);
		monthlyBills = monthlyBillService.findAll();
		Assert.isTrue(!monthlyBills.contains(saved));
		super.authenticate(null);
	}
	
	@Test
	public void testFindAll(){
		Collection<MonthlyBill>monthlyBills;
		monthlyBills=monthlyBillService.findAll();
		Assert.notNull(monthlyBills);
	}
	
	@Test
	public void testFindOne(){
		MonthlyBill monthlyBill;
		monthlyBill=monthlyBillService.findOne(187);
		Assert.notNull(monthlyBill);
	}
	
	@Test
	public void testPayMonthlyBill(){
		
		MonthlyBill monthlyBill;
		
		monthlyBill = monthlyBillService.findOne(188);
		
		super.authenticate("sponsor2");
		monthlyBillService.payMonthlyBill(monthlyBill);
		Assert.notNull(monthlyBill);
		super.authenticate(null);
	}
	
	@Test
	public void testAvgStdDevPaidMonthlyBills(){
		Collection<Double> result;
		result = monthlyBillService.avgStdDevPaidMonthlyBills();
		Assert.notNull(result);
	}
	
	@Test
	public void testStandardDeviationUnpaidMonthlyBills(){
		Collection<Double> result;
		result = monthlyBillService.standardDeviationUnpaidMonthlyBills();
		Assert.notNull(result);
	}
	
	@Test
	public void testGenerateMonthlyBill(){
		super.authenticate("admin");
		Campaign campaign = campaignService.findOne(192);
		MonthlyBill monthlyBill = monthlyBillService.generateMonthlyBill(campaign);
		Assert.notNull(monthlyBill);
		Assert.isTrue(monthlyBill.getCost()!=0.0);
		super.authenticate(null);
	}

	//Negative-------------------------------------
	
	//Sponsor no puede crear monthly bills
	@Test(expected = IllegalArgumentException.class)
	public void testNegCreateMonthlyBill(){
		MonthlyBill monthlyBill;
		super.authenticate("sponsor1");
		monthlyBill = monthlyBillService.create();
		Assert.notNull(monthlyBill);
		super.authenticate(null);
		
	}
	
	//Description vacia
	@Test(expected = ConstraintViolationException.class)
	public void testNegSaveMonthlyBill(){
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
		
		super.authenticate("admin");
		MonthlyBill monthlyBill, saved;
		Collection<MonthlyBill> monthlyBills = new HashSet<MonthlyBill>();
		Date d=new Date();
		d.setTime(d.getTime()-60000);
		monthlyBill = monthlyBillService.create();
		monthlyBill.setCreateMoment(d);
		monthlyBill.setPayMoment(null);
		monthlyBill.setCost(50);
		monthlyBill.setDescription("");
		monthlyBill.setSponsor(sS);
		Assert.notNull(monthlyBill);
		
		saved = monthlyBillService.save(monthlyBill);
		monthlyBills = monthlyBillService.findAll();
		Assert.isTrue(monthlyBills.contains(saved));
		super.authenticate(null);
	}	
	
	//Coste negativo
	@Test(expected = ConstraintViolationException.class)
	public void testNegDeleteMonthlyBill(){
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
		
		super.authenticate("admin");
		MonthlyBill monthlyBill, saved;
		Collection<MonthlyBill> monthlyBills = new HashSet<MonthlyBill>();
		Date d=new Date();
		d.setTime(d.getTime()-60000);
		monthlyBill = monthlyBillService.create();
		monthlyBill.setCreateMoment(d);
		monthlyBill.setPayMoment(null);
		monthlyBill.setCost(-1.0);
		monthlyBill.setDescription("Monthly bill del mes de Agosto");
		monthlyBill.setSponsor(sS);
		Assert.notNull(monthlyBill);
		
		saved = monthlyBillService.save(monthlyBill);
		monthlyBillService.delete(saved);
		monthlyBills = monthlyBillService.findAll();
		Assert.isTrue(!monthlyBills.contains(saved));
		super.authenticate(null);
	}
	
	//Atributo monthlyBills nulo
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindAll(){
		Collection<MonthlyBill>monthlyBills;
		monthlyBills=monthlyBillService.findAll();
		monthlyBills = null;
		Assert.notNull(monthlyBills);
	}
	
	//Id incorrecta
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindOne(){
		MonthlyBill monthlyBill;
		monthlyBill=monthlyBillService.findOne(25);
		Assert.notNull(monthlyBill);
	}
	
	//User no puede usar este metodo
	@Test(expected = IllegalArgumentException.class)
	public void testNegPayMonthlyBill(){
		
		MonthlyBill monthlyBill;
		monthlyBill = monthlyBillService.findOne(188);
		super.authenticate("user1");
		monthlyBillService.payMonthlyBill(monthlyBill);
		Assert.notNull(monthlyBill);
		super.authenticate(null);
	}
	
	//Variable result es nula
	@Test(expected = IllegalArgumentException.class)
	public void testNegAvgStdDevPaidMonthlyBills(){
		Collection<Double> result;
		result = monthlyBillService.avgStdDevPaidMonthlyBills();
		result = null;
		Assert.notNull(result);
	}
	
	//Variable result es nula
	@Test(expected = IllegalArgumentException.class)
	public void testNegStandardDeviationUnpaidMonthlyBills(){
		Collection<Double> result;
		result = monthlyBillService.standardDeviationUnpaidMonthlyBills();
		result = null;
		Assert.notNull(result);
	}
	
	//User no puede usar estos metodos
	@Test(expected = IllegalArgumentException.class)
	public void testNegGenerateMonthlyBill(){
		super.authenticate("user1");
		Campaign campaign = campaignService.findOne(182);
		MonthlyBill monthlyBill = monthlyBillService.generateMonthlyBill(campaign);
		Assert.notNull(monthlyBill);
		Assert.isTrue(monthlyBill.getCost()!=0.0);
		super.authenticate(null);
	}
	
}