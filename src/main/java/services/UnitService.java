package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.UnitRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Unit;


@Service
@Transactional
public class UnitService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private UnitRepository unitRepository;
	
	// Supporting services ----------------------------------------------------
	
	// Constructors -----------------------------------------------------------
	
	public UnitService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Unit create() {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Unit result;

		result = new Unit();

		return result;
	}

	public Collection<Unit> findAll() {
		Collection<Unit> result;

		result = unitRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Unit findOne(int unitId) {
		Unit result;

		result = unitRepository.findOne(unitId);
		Assert.notNull(result);

		return result;
	}

	public Unit save(Unit unit) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(unit);
		
		Unit result;

		result = unitRepository.save(unit);
		
		return result;
	}

	public void delete(Unit unit) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(unit);
		Assert.isTrue(unit.getId() != 0);

		unitRepository.delete(unit);
	}
	
	// Other business methods -------------------------------------------------

}
