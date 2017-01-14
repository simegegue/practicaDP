package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CookRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Cook;
import domain.Message;
import domain.SocialIdentity;


@Service
@Transactional
public class CookService {

		// Managed repository -----------------------------------------------------
	
		@Autowired
		private CookRepository cookRepository;
		
		// Supporting services ----------------------------------------------------
		
		@Autowired
		private FolderService folderService;
		
		// Constructors -----------------------------------------------------------
		
		public CookService(){
			super();
		}
		
		// Simple CRUD methods ----------------------------------------------------
		
		public Cook create() {
			
			UserAccount userAccount = new UserAccount();
			List<Authority> authorities=new ArrayList<Authority>();
			Authority au = new Authority();
			au.setAuthority("ADMIN");
			authorities.add(au);
			userAccount.setAuthorities(authorities);
			
			Cook result;
			result = new Cook();
			result.setUserAccount(userAccount);
			
			Collection<SocialIdentity> socialIdentities = new ArrayList<SocialIdentity>();
			Collection<Message> sendedMessages = new ArrayList<Message>();
			Collection<Message> receivedMessages = new ArrayList<Message>();
			
			result.setSendedMessages(sendedMessages);
			result.setReceivedMessages(receivedMessages);
			result.setSocialIdentities(socialIdentities);
			
			return result;
		}

		public Collection<Cook> findAll() {
			Collection<Cook> result;

			result = cookRepository.findAll();
			Assert.notNull(result);

			return result;
		}

		public Cook findOne(int cookId) {
			Cook result;

			result = cookRepository.findOne(cookId);
			Assert.notNull(result);

			return result;
		}
		
		public Cook save(Cook cook) {
			Assert.notNull(cook);
			
			String password =cook.getUserAccount().getPassword();
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			String md5 = encoder.encodePassword(password, null);
			cook.getUserAccount().setPassword(md5);
			
			Cook result = cookRepository.save(cook);
			
			folderService.createDefaultFolders(result);
			
			return result;
			
		}

		public Cook save2(Cook cook) {
			Cook result;

			result = cookRepository.save(cook);
			
			return result;
		}

		public void delete(Cook cook) {
			
			UserAccount userAccount;
			userAccount = LoginService.getPrincipal();
			Authority au = new Authority();
			au.setAuthority("ADMIN");
			Assert.isTrue(userAccount.getAuthorities().contains(au));
			
			Assert.notNull(cook);
			Assert.isTrue(cook.getId() != 0);

			cookRepository.delete(cook);
		}
		
		public Cook findByPrincipal() {
			Cook result;
			UserAccount userAccount;
		
			userAccount = LoginService.getPrincipal();
			assert userAccount != null;
			result = findByUserAccount(userAccount);
			assert result != null;

			return result;
		}
		
		public Cook findByUserAccount(UserAccount userAccount) {
			assert userAccount != null;

			Cook result;

			result = cookRepository.findByUserAccountId(userAccount.getId());
			assert result != null;

			return result;
		}
		
		// Other business methods -------------------------------------------------
	
		public Collection<Cook> listCooksByMasterClassesPromoted(){
			Collection<Cook> result;
			result = cookRepository.listCooksByMasterClassesPromoted();
			return result;
		}
		
}
