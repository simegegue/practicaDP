package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;

import repositories.ActorRepository;
import security.LoginService;

@Service
@Transactional
public class ActorService {
	
		// Managed repository -----------------------------------------------------

		@Autowired
		private ActorRepository actorRepository;	
		
		// Supporting services ----------------------------------------------------
		
		// Constructors -----------------------------------------------------------
		
		// Simple CRUD methods ----------------------------------------------------
		
		public Collection<Actor> findAll() {
			Collection<Actor> result;
			
			result = actorRepository.findAll();
			Assert.notNull(result);
			
			return result;
		}

		public Actor findOne(int actorId) {
			Assert.isTrue(actorId != 0);
			
			Actor result;

			result = actorRepository.findOne(actorId);
			Assert.notNull(result);

			return result;
		}
		
		public void save(Actor actor) {
			Assert.notNull(actor);

			actorRepository.save(actor);
		}	
		
		public void delete(Actor actor) {
			Assert.notNull(actor);
			Assert.isTrue(actor.getId() != 0);
			Assert.isTrue(actorRepository.exists(actor.getId()));		
			
			actorRepository.delete(actor);
		}

		// Other business methods -------------------------------------------------

		public Actor findByPrincipal(){
			Actor result;
			int userAccountId;
			
			userAccountId = LoginService.getPrincipal().getId();
			result = actorRepository.findByUserAccountId(userAccountId);
			
			Assert.notNull(result);
			
			return result;
		}
		
		public Actor findByUserAccount(int id){
			Actor result;
			
			result = this.actorRepository.findByUserAccountId(id);
			Assert.notNull(result);
			
			return result;
		}
		
		public Actor findActorByUsername(String username){
			Assert.notNull(username);
			Actor actor = actorRepository.findActorByUsername(username);
			Assert.notNull(actor);
			return actor;
		}
}
