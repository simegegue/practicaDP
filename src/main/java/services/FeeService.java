package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Fee;

import repositories.FeeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class FeeService {
	
	// Managed repository -----------------------------------------------------
	
	@Autowired
	private FeeRepository feeRepository;
	
	// Supporting services ----------------------------------------------------
	
	// Constructors -----------------------------------------------------------
	
	public FeeService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Fee create() {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Fee result;

		result = new Fee();

		return result;
	}
	
	public Collection<Fee> findAll() {
		Collection<Fee> result;
				
		result = feeRepository.findAll();
		Assert.notNull(result);
			
		return result;
	}

	public Fee findOne(int feeId) {
		Assert.isTrue(feeId != 0);
			
		Fee result;

		result = feeRepository.findOne(feeId);
		Assert.notNull(result);

		return result;
	}
			
	public Fee save(Fee fee) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(fee);

		return feeRepository.save(fee);
	}	
			
	public void delete(Fee fee) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(fee);
		Assert.isTrue(fee.getId() != 0);
		Assert.isTrue(feeRepository.exists(fee.getId()));		
				
		feeRepository.delete(fee);
	}

	// Other business methods -------------------------------------------------

}
