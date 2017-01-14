package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SpamWordRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.SpamWord;

@Service
@Transactional
public class SpamWordService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SpamWordRepository spamWordRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private AdministratorService administratorService;
	
	// Constructors -----------------------------------------------------------
	
	public SpamWordService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public SpamWord create() {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		SpamWord result;

		result = new SpamWord();
		result.setAdministrator(administratorService.findByPrincipal());
		return result;
	}

	public Collection<SpamWord> findAll() {
		Collection<SpamWord> result;

		result = spamWordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public SpamWord findOne(int spamWordId) {
		SpamWord result;

		result = spamWordRepository.findOne(spamWordId);
		Assert.notNull(result);

		return result;
	}

	public SpamWord save(SpamWord spamWord) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(spamWord);
		
		SpamWord result;

		result = spamWordRepository.save(spamWord);
		
		return result;
	}

	public void delete(SpamWord spamWord) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(spamWord);
		Assert.isTrue(spamWord.getId() != 0);

		spamWordRepository.delete(spamWord);
	}
	
	// Other business methods -------------------------------------------------
	
	public void registerSpamWord(String string){
		Collection<SpamWord> csw = findAll();
		SpamWord spamWord;
		spamWord = create();
		spamWord.setName(string);
		if(!(csw.contains(spamWord.getName()))){
			save(spamWord);
		}
		
	}
	
	public void deleteSpamWord(String string){
		Collection<SpamWord> csw = findAll();
		for(SpamWord sw : csw){
			if(sw.getName().equals(string)){
				delete(sw);
			}
		}
	}

}
