package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Message;
import domain.SocialIdentity;

@Service
@Transactional
public class AdministratorService {

		// Managed repository -----------------------------------------------------
	
		@Autowired
		private AdministratorRepository administratorRepository;
		
		// Supporting services ----------------------------------------------------

		
		// Constructors -----------------------------------------------------------
		
		public AdministratorService(){
			super();
		}
		
		// Simple CRUD methods ----------------------------------------------------
		
		public Administrator create() {
			UserAccount userAccount = new UserAccount();
			List<Authority> authorities=new ArrayList<Authority>();
			Authority au = new Authority();
			au.setAuthority("ADMIN");
			authorities.add(au);
			userAccount.setAuthorities(authorities);
			
			Administrator result;
			result = new Administrator();
			result.setUserAccount(userAccount);
			
			Collection<SocialIdentity> socialIdentities = new ArrayList<SocialIdentity>();
			Collection<Message> sendedMessages = new ArrayList<Message>();
			Collection<Message> receivedMessages = new ArrayList<Message>();
			
			result.setSendedMessages(sendedMessages);
			result.setReceivedMessages(receivedMessages);
			result.setSocialIdentities(socialIdentities);
			
			return result;
		}

		public Collection<Administrator> findAll() {
			Collection<Administrator> result;

			result = administratorRepository.findAll();
			Assert.notNull(result);

			return result;
		}

		public Administrator findOne(int administratorId) {
			Administrator result;

			result = administratorRepository.findOne(administratorId);
			Assert.notNull(result);

			return result;
		}

		public Administrator save(Administrator administrator) {
			Assert.notNull(administrator);
			
			Administrator result;

			result = administratorRepository.save(administrator);
			
			return result;
		}

		public void delete(Administrator administrator) {
			Assert.notNull(administrator);
			Assert.isTrue(administrator.getId() != 0);

			administratorRepository.delete(administrator);
		}
		
		// Other business methods -------------------------------------------------
		
		public Administrator findByPrincipal() {
			Administrator result;
			UserAccount userAccount;
		
			userAccount = LoginService.getPrincipal();
			Assert.notNull(userAccount);
			result = findByUserAccountId(userAccount.getId());
			Assert.notNull(result);

			return result;
		}
		
		public Administrator findByUserAccountId(int userAccount){
			Assert.notNull(userAccount);
			Administrator result;
			
			result = administratorRepository.findByUserAccountId(userAccount);
			
			return result;
		}
		
	
}
