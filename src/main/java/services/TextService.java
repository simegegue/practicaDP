package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TextRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Text;


@Service
@Transactional
public class TextService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private TextRepository textRepository;
	
	// Supporting services ----------------------------------------------------
	
	// Constructors -----------------------------------------------------------
	
	public TextService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Text create() {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("COOK");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Text result;

		result = new Text();

		return result;
	}

	public Collection<Text> findAll() {
		Collection<Text> result;

		result = textRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Text findOne(int textId) {
		Text result;

		result = textRepository.findOne(textId);
		Assert.notNull(result);

		return result;
	}

	public Text save(Text text) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("COOK");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(text);
		
		Text result;

		result = textRepository.save(text);
		
		return result;
	}

	public void delete(Text text) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("COOK");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(text);
		Assert.isTrue(text.getId() != 0);

		textRepository.delete(text);
	}
	
	// Other business methods -------------------------------------------------

}
