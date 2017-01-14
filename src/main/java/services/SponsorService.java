package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Campaign;
import domain.CreditCard;
import domain.Message;
import domain.MonthlyBill;
import domain.SocialIdentity;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {
	
	// Managed repository -----------------------------------------------------
	
	@Autowired
	private SponsorRepository sponsorRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private FolderService folderService;
	
	// Constructors -----------------------------------------------------------
	
	public SponsorService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Sponsor create() {
		
		UserAccount userAccount=new UserAccount();
		List<Authority> authorities=new ArrayList<Authority>();
		Authority a = new Authority();
		a.setAuthority(Authority.SPONSOR);
		authorities.add(a);
		userAccount.setAuthorities(authorities);
		Sponsor result;
		result = new Sponsor();
		result.setUserAccount(userAccount);
		CreditCard c=new CreditCard();
		result.setCreditCard(c);
		
		Collection<Message> sendedMessages = new ArrayList<Message>();
		Collection<Message> receivedMessages = new ArrayList<Message>();
		Collection<SocialIdentity> socialIdentities = new ArrayList<SocialIdentity>();
		Date cuDate=new Date();
		cuDate.setTime(cuDate.getTime()-600);
		result.setLastTimeManages(cuDate);
		result.setCampaigns(new ArrayList<Campaign>());
		result.setMonthlyBills(new ArrayList<MonthlyBill>());
		result.setSendedMessages(sendedMessages);
		result.setReceivedMessages(receivedMessages);
		result.setSocialIdentities(socialIdentities);

		
		return result;
	}

	public Collection<Sponsor> findAll() {
		Collection<Sponsor> result;

		result = sponsorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Sponsor findOne(int sponsorId) {
		Sponsor result;

		result = sponsorRepository.findOne(sponsorId);
		Assert.notNull(result);

		return result;
	}
	
	public Sponsor save(Sponsor sponsor) {
		Assert.notNull(sponsor);
		
		String password =sponsor.getUserAccount().getPassword();
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String md5 = encoder.encodePassword(password, null);
		sponsor.getUserAccount().setPassword(md5);
		
		Sponsor result = sponsorRepository.save(sponsor);
		
		folderService.createDefaultFolders(result);
		
		return result;
		
	}

	public Sponsor save2(Sponsor sponsor) {
		Sponsor result;

		result = sponsorRepository.save(sponsor);
		
		return result;
	}

	public void delete(Sponsor sponsor) {
		Assert.notNull(sponsor);
		Assert.isTrue(sponsor.getId() != 0);

		sponsorRepository.delete(sponsor);
	}
	
	// Other business methods -------------------------------------------------
	
	public Sponsor findByPrincipal(){
		Sponsor result;
		int sponsorAccountId;
		
		sponsorAccountId = LoginService.getPrincipal().getId();
		result = sponsorRepository.findByUserAccountId(sponsorAccountId);
		
	
		
		return result;
	}

	public Collection<Double> findMinAvgMaxCampaignPerSponsor(){
		Collection<Double> result = new ArrayList<Double>();
		
		Integer min = sponsorRepository.findMinCampaignPerSponsor();
		Double avg = sponsorRepository.findAvgCampaignPerSponsor();
		Integer max = sponsorRepository.findMaxCampaignPerSponsor();
			
		if(min == null || min == 0){
			result.add(0.0);
		}else{
			result.add(min*1.0);
		}
		if(avg == null || avg == 0){
			result.add(0.0);
		}else{
			result.add(avg);
		}
		if(max == null || max == 0){
			result.add(0.0);
		}else{
			result.add(max*1.0);
		}

		return result;
	}
	
	public Collection<Double> findMinAvgMaxActiveCampaignPerSponsor(){
		Collection<Double> result = new ArrayList<Double>();
		
		Integer min = sponsorRepository.findMinActiveCampaignPerSponsor();
		Double avg = sponsorRepository.findAvgActiveCampaignPerSponsor();
		Integer max = sponsorRepository.findMaxActiveCampaignPerSponsor();
			
		if(min == null || min == 0){
			result.add(0.0);
		}else{
			result.add(min*1.0);
		}
		if(avg == null || avg == 0){
			result.add(0.0);
		}else{
			result.add(avg);
		}
		if(max == null || max == 0){
			result.add(0.0);
		}else{
			result.add(max*1.0);
		}
		
		return result;
	}
	
	public Collection<Sponsor> findSponsorsByUnpaidMonthlyBills(){
		Collection<Sponsor> result;
		result = sponsorRepository.findSponsorsByUnpaidMonthlyBills();
		return result;
	}
	
	public Collection<Sponsor> findCompanyRanking(){
		Collection<Sponsor> result;
		result = sponsorRepository.findCompanyRanking();
		return result;
	}
	
	public Collection<Sponsor> findCompanyRankingMonthlyBill(){
		Collection<Sponsor> result;
		result = sponsorRepository.findCompanyRankingMonthlyBill();
		return result;
	}
	
	public Collection<Sponsor> findInactiveSponsor(){
		Collection<Sponsor> result;
		result = sponsorRepository.findInactiveSponsor();
		return result;
	}
	
	public Collection<Sponsor> findCompaniesSpendLessAverage(){
		Collection<Sponsor> result;
		result = sponsorRepository.findCompaniesSpendLessAverage();
		return result;
	}
	
	public Collection<Sponsor> findCompaniesSpendMoreThan90(){
		Collection<Sponsor> result;
		result = sponsorRepository.findCompaniesSpendMoreThan90();
		return result;
	}
	
}
