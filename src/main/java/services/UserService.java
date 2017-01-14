package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Comment;
import domain.Message;
import domain.Nutritionist;
import domain.Recipe;
import domain.RelationDislike;
import domain.RelationLike;
import domain.SocialActor;
import domain.SocialIdentity;
import domain.User;

@Service
@Transactional
public class UserService {
	
		// Managed repository -----------------------------------------------------
	
		@Autowired
		private UserRepository userRepository;
		
		// Supporting services ----------------------------------------------------
		
		@Autowired
		private FolderService folderService;
		
		@Autowired
		private NutritionistService nutritionistService;
		
		// Constructors -----------------------------------------------------------
		
		public UserService(){
			super();
		}
		
		// Simple CRUD methods ----------------------------------------------------
		
		public User create() {
			UserAccount userAccount=new UserAccount();
			List<Authority> authorities=new ArrayList<Authority>();
			Authority a = new Authority();
			a.setAuthority(Authority.USER);
			authorities.add(a);
			userAccount.addAuthority(a);
			User result = new User();
			result.setUserAccount(userAccount);
			
			Collection<RelationDislike> relationDislikes = new ArrayList<RelationDislike>();
			Collection<RelationLike> relationLikes = new ArrayList<RelationLike>();
			Collection<Comment> comments = new ArrayList<Comment>();
			Collection<Recipe> recipes = new ArrayList<Recipe>();
			Collection<SocialActor> following = new ArrayList<SocialActor>();
			Collection<SocialActor> followers = new ArrayList<SocialActor>();
			Collection<Message> sendedMessages = new ArrayList<Message>();
			Collection<Message> receivedMessages = new ArrayList<Message>();
			Collection<SocialIdentity> socialIdentities = new ArrayList<SocialIdentity>();
			
			result.setRelationDislikes(relationDislikes);
			result.setRelationLikes(relationLikes);
			result.setComments(comments);
			result.setRecipes(recipes);
			result.setFollowing(following);
			result.setFollowers(followers);
			result.setSendedMessages(sendedMessages);
			result.setReceivedMessages(receivedMessages);
			result.setSocialIdentities(socialIdentities);
			
			
			return result;
		}

		public Collection<User> findAll() {
			Collection<User> result;

			result = userRepository.findAll();
			Assert.notNull(result);

			return result;
		}

		public User findOne(int userId) {
			User result;

			result = userRepository.findOne(userId);
			Assert.notNull(result);

			return result;
		}

		public User save(User user) {
			Assert.notNull(user);
			
			String password =user.getUserAccount().getPassword();
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			String md5 = encoder.encodePassword(password, null);
			user.getUserAccount().setPassword(md5);
			
			User result = userRepository.save(user);
			
			folderService.createDefaultFolders(result);
			
			return result;
			
		}

		public void delete(User user) {
			Assert.notNull(user);
			Assert.isTrue(user.getId() != 0);

			userRepository.delete(user);
		}
		
		public User save2(User user){
			User result;
			result= userRepository.save(user);
			return result;
		}
		
		// Other business methods -------------------------------------------------
	
		public User findByPrincipal(){
			User result;
			int userAccountId;
			
			userAccountId = LoginService.getPrincipal().getId();
			result = userRepository.findByUserAccountId(userAccountId);
			
		//	Assert.notNull(result);
			
			return result;
		}
		
		public Collection<User> findByKey(String key){
			Collection<User> result;
			result = userRepository.findByKey(key);
			return result;
		}
		
		public User findByUserAccountId(int id){
			User result;
			result = userRepository.findByUserAccountId(id);
			return result;
		}
		
		public Collection<User> usersMoreRecipesAuthored(){
			Collection<User> result;
			result = userRepository.usersMoreRecipesAuthored();
			return result;
		}
		
		public Collection<User> usersByPopularity(){
			Collection<User> result;
			result = userRepository.usersByPopularity();
			return result;
		}
		
		public Collection<User> usersAvgLikesDislikes(){
			Collection<User> result;
			result = userRepository.usersAvgLikesDislikes();
			return result;
		}
		
		public Collection<Double> findMinAvgMaxRecipesPerUser(){
			Collection<Double> result = new ArrayList<Double>();
			
			Integer min = userRepository.findMinRecipesPerUser();
			Double avg = userRepository.findAvgRecipesPerUser();
			Integer max = userRepository.findMaxRecipesPerUser();
				
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
		
		public void followUser(User u,User i){
			if(!u.getFollowing().contains(i) && u!=i){
				i.getFollowers().add(u);
				u.getFollowing().add(i);
				userRepository.save(i);
				userRepository.save(u);
			}
		}
		
		
		public void followNutritionist(User u,Nutritionist n){
			if(!u.getFollowing().contains(n)){
				n.getFollowers().add(u);
				u.getFollowing().add(n);
				nutritionistService.save2(n);
				userRepository.save(u);
			}
		}
		
		public void unfollowUser(User u,User i){
			if(u.getFollowing().contains(i)){
				i.getFollowers().remove(u);
				u.getFollowing().remove(i);
				userRepository.save(i);
				userRepository.save(u);
			}
		}
		
		public void unfollowNutritionist(User u,Nutritionist n){
			if(u.getFollowing().contains(n)){
				n.getFollowers().remove(u);
				u.getFollowing().remove(n);
				nutritionistService.save2(n);
				userRepository.save(u);
			}
		}
		
		public Collection<Recipe> findRecipeFromFollowing(User us){
			Collection<SocialActor>s=us.getFollowing();
			Collection<User>users=userRepository.findAll();
			Collection<Recipe>latest= new HashSet<Recipe>();
			List<User>following=new ArrayList<User>();
			for(SocialActor soc:s){
				for(User u:users){
					if(soc.getUserAccount().getUsername().equals(u.getUserAccount().getUsername())){
						following.add(u);
					}
				}
				
			}
			for(User l:following){
				Date aux=new Date();
				Boolean esPrimero=true;
				if(!l.getRecipes().isEmpty()){
					Recipe aux2=new Recipe();
					for(Recipe rec:l.getRecipes()){
						if(esPrimero==true){
							aux=rec.getAuthoredMoment();
							aux2=rec;
							esPrimero=false;
						}else{
							if(aux.compareTo(rec.getAuthoredMoment())<1){
								aux=rec.getAuthoredMoment();
								aux2=rec;
							}
						}
					}
					latest.add(aux2);
				}
				
			}
			return latest;
		}
		
		
}
