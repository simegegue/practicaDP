package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MonthlyBillRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Banner;
import domain.Campaign;
import domain.Fee;
import domain.MonthlyBill;
import domain.Sponsor;

@Service
@Transactional
public class MonthlyBillService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MonthlyBillRepository monthlyBillRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private FeeService feeService;
	
	@Autowired
	private SponsorService sponsorService;
	
	// Constructors -----------------------------------------------------------
	
	public MonthlyBillService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public MonthlyBill create() {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		MonthlyBill result;

		result = new MonthlyBill();

		return result;
	}

	public Collection<MonthlyBill> findAll() {
		Collection<MonthlyBill> result;

		result = monthlyBillRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public MonthlyBill findOne(int monthlyBillId) {
		MonthlyBill result;

		result = monthlyBillRepository.findOne(monthlyBillId);
		Assert.notNull(result);

		return result;
	}

	public MonthlyBill save(MonthlyBill monthlyBill) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(monthlyBill);
		
		MonthlyBill result;

		result = monthlyBillRepository.save(monthlyBill);
		
		return result;
	}

	public void delete(MonthlyBill monthlyBill) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(monthlyBill);
		Assert.isTrue(monthlyBill.getId() != 0);

		monthlyBillRepository.delete(monthlyBill);
	}
	
	// Other business methods -------------------------------------------------

	public void payMonthlyBill(MonthlyBill monthlyBill){
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("SPONSOR");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		Assert.isTrue(userAccount.getUsername().equals(monthlyBill.getSponsor().getUserAccount().getUsername()));
		
		Date payMoment = new Date(System.currentTimeMillis() - 1);
		
		monthlyBill.setPayMoment(payMoment);
		
		monthlyBillRepository.save(monthlyBill);
	}
	
	public Collection<Double> avgStdDevPaidMonthlyBills(){
		Collection<Double> result = new ArrayList<Double>();

		Double avg = monthlyBillRepository.avgPaidMonthlyBills();
		Double stdDev = monthlyBillRepository.stdDevPaidMonthlyBills();
			
		if(avg == null || avg == 0){
			result.add(0.0);
		}else{
			result.add(avg);
		}
		if(stdDev == null || stdDev == 0){
			result.add(0.0);
		}else{
			result.add(stdDev);
		}
		
		return result;
	}
	
	public Collection<Double> standardDeviationUnpaidMonthlyBills(){
		Collection<Double> result = new ArrayList<Double>();
		
		Double avg = monthlyBillRepository.avgUnpaidMonthlyBills();
		Double stdDev = monthlyBillRepository.stdDevUnpaidMonthlyBills();
			
		if(avg == null || avg == 0){
			result.add(0.0);
		}else{
			result.add(avg);
		}
		if(stdDev == null || stdDev == 0){
			result.add(0.0);
		}else{
			result.add(stdDev);
		}
	
		return result;
	}
	
	public MonthlyBill generateMonthlyBill(Campaign campaign){
		MonthlyBill monthlyBill = create();
		Fee fee = feeService.findOne(1);
		Sponsor sponsor = campaign.getSponsor();
		
		Date createMoment = new Date(System.currentTimeMillis()-100);
		monthlyBill.setCreateMoment(createMoment);
		String description = "Los conceptos de su factura son: \n";
		Collection<Banner> banners = campaign.getBanners();
		Double cost = 0.0;
		
		for(Banner b:banners){
			Double bannerCost = 0.0;
			bannerCost = bannerCost + (b.getDisplayed()*fee.getValue());
			description = description + "\n\t" + b.getImage() + " : " + bannerCost + "\n";
			cost = cost + bannerCost;
		}
		description = description + "\n\t El coste total es: " + cost;
		monthlyBill.setCost(cost);
		monthlyBill.setDescription(description);
		monthlyBill.setSponsor(sponsor);
		MonthlyBill monthlyBill2 = save(monthlyBill);
		
		Collection<MonthlyBill> monthlyBills = sponsor.getMonthlyBills();
		monthlyBills.add(monthlyBill2);
		sponsor.setMonthlyBills(monthlyBills);
		sponsorService.save2(sponsor);
		
		return monthlyBill;
	}
	
	public Collection<MonthlyBill> findAllMonthlyBillBySponsor(){
		Collection<MonthlyBill> result = new ArrayList<MonthlyBill>();
		Sponsor sponsor = sponsorService.findByPrincipal();
		
		result.addAll(sponsor.getMonthlyBills());
		
		return result;
	}
	
	public Collection<MonthlyBill> findUnpaidMonthlyBillBySponsor(){
		
		Collection<MonthlyBill> result = new ArrayList<MonthlyBill>();
		Collection<MonthlyBill> result2 = findAllMonthlyBillBySponsor();
		
		
		for(MonthlyBill mb : result2){
			if(mb.getPayMoment()==null){
				result.add(mb);
			}
		}
		
		return result;
		
	}
	
	public Collection<MonthlyBill> findUnpaidMonthlyBill(){
		
		Collection<MonthlyBill> result = new ArrayList<MonthlyBill>();
		Collection<MonthlyBill> result2 = findAll();
		Date currentMoment = new Date(System.currentTimeMillis()-1);
		
		
		for(MonthlyBill mb : result2){
			if(mb.getPayMoment()==null && currentMoment.getTime()-mb.getCreateMoment().getTime()>=TimeUnit.DAYS.toMillis(30)){
				
				result.add(mb);
			}
		}
		
		return result;
		
	}
}
