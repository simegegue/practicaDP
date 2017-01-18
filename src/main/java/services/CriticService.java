package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;




import domain.Critic;
import domain.Message;
import domain.SocialIdentity;

import repositories.CriticRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
@Service
@Transactional
public class CriticService {
	// Managed repositories-------------------------------------------------------
	@Autowired 
	private CriticRepository criticRepository;
	
	// SupportingServices --------------------------------------------------------
	@Autowired
	private FolderService folderService;
	//Constructor ----------------------------------------------------------------
	public CriticService(){
		super();


	}
	//Simple CRUD Methods --------------------------------------------------------
	public Critic create(){
		UserAccount userAccount = new UserAccount();
		List<Authority> authorities=new ArrayList<Authority>();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		authorities.add(au);
		userAccount.setAuthorities(authorities);
		
		Critic result;
		result = new Critic();
		result.setUserAccount(userAccount);
		
		Collection<SocialIdentity> socialIdentities = new ArrayList<SocialIdentity>();
		Collection<Message> sendedMessages = new ArrayList<Message>();
		Collection<Message> receivedMessages = new ArrayList<Message>();
		
		result.setSendedMessages(sendedMessages);
		result.setReceivedMessages(receivedMessages);
		result.setSocialIdentities(socialIdentities);
		
		return result;
	}
	public Critic save(Critic critic) {
		Assert.notNull(critic);
		
		String password =critic.getUserAccount().getPassword();
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String md5 = encoder.encodePassword(password, null);
		critic.getUserAccount().setPassword(md5);
		
		Critic result = criticRepository.save(critic);
		
		folderService.createDefaultFolders(result);
		
		return result;
		
	}

	public Critic save2(Critic critic) {
		Critic result;

		result = criticRepository.save(critic);
		
		return result;
	}
	
	public void delete(Critic critic){
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(critic);
		Assert.isTrue(critic.getId() != 0);

		criticRepository.delete(critic);
	}
	
	public Critic findByPrincipal() {
		Critic result;
		UserAccount userAccount;
	
		userAccount = LoginService.getPrincipal();
		assert userAccount != null;
		result = findByUserAccount(userAccount);
		assert result != null;

		return result;
	}
	
	public Critic findByUserAccount(UserAccount userAccount) {
		assert userAccount != null;

		Critic result;

		result = criticRepository.findByUserAccountId(userAccount.getId());
		assert result != null;

		return result;
	}
}
