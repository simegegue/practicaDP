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

import domain.Comment;
import domain.Curricula;
import domain.Message;
import domain.Nutritionist;
import domain.Recipe;
import domain.RelationDislike;
import domain.RelationLike;
import domain.SocialActor;
import domain.SocialIdentity;
import domain.User;

import repositories.NutritionistRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;


@Service
@Transactional
public class NutritionistService {

			// Managed repository -----------------------------------------------------
	
			@Autowired
			private NutritionistRepository nutritionistRepository;
			
			// Supporting services ----------------------------------------------------
			
			@Autowired
			private UserService userService;
			
			@Autowired
			private FolderService folderService;
			
			// Constructors -----------------------------------------------------------
			
			public NutritionistService(){
				super();
			}
			
			// Simple CRUD methods ----------------------------------------------------
			
			public Nutritionist create() {
				UserAccount userAccount=new UserAccount();
				List<Authority> authorities=new ArrayList<Authority>();
				Authority a = new Authority();
				a.setAuthority(Authority.NUTRITIONIST);
				authorities.add(a);
				userAccount.setAuthorities(authorities);
				Nutritionist result;

				result = new Nutritionist();
				result.setUserAccount(userAccount);
				
				
				Collection<RelationDislike> relationDislikes = new ArrayList<RelationDislike>();
				Collection<RelationLike> relationLikes = new ArrayList<RelationLike>();
				Collection<Comment> comments = new ArrayList<Comment>();
				Collection<SocialActor> following = new ArrayList<SocialActor>();
				Collection<SocialActor> followers = new ArrayList<SocialActor>();
				Collection<Message> sendedMessages = new ArrayList<Message>();
				Collection<Message> receivedMessages = new ArrayList<Message>();
				Collection<SocialIdentity> socialIdentities = new ArrayList<SocialIdentity>();
				
				result.setRelationDislikes(relationDislikes);
				result.setRelationLikes(relationLikes);
				result.setComments(comments);
				result.setFollowing(following);
				result.setFollowers(followers);
				result.setSendedMessages(sendedMessages);
				result.setReceivedMessages(receivedMessages);
				result.setSocialIdentities(socialIdentities);
				
				return result;
			}

			public Collection<Nutritionist> findAll() {
				Collection<Nutritionist> result;

				result = nutritionistRepository.findAll();
				Assert.notNull(result);

				return result;
			}

			public Nutritionist findOne(int nutritionistId) {
				Nutritionist result;

				result = nutritionistRepository.findOne(nutritionistId);
				Assert.notNull(result);

				return result;
			}
			public Nutritionist save(Nutritionist nutritionist) {
				Assert.notNull(nutritionist);
				
				String password =nutritionist.getUserAccount().getPassword();
				Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				String md5 = encoder.encodePassword(password, null);
				nutritionist.getUserAccount().setPassword(md5);
				
				Nutritionist result = nutritionistRepository.save(nutritionist);
				
				folderService.createDefaultFolders(result);
				
				return result;
			}
				

			public Nutritionist save2(Nutritionist nutritionist) {
				Nutritionist result;
				
				result = nutritionistRepository.save(nutritionist);
				
				return result;
			}

			public void delete(Nutritionist nutritionist) {
				Assert.notNull(nutritionist);
				Assert.isTrue(nutritionist.getId() != 0);

				nutritionistRepository.delete(nutritionist);
			}
			
			// Other business methods -------------------------------------------------	
			public Nutritionist findByPrincipal(){
				Nutritionist result;
				int userAccountId;
				
				userAccountId = LoginService.getPrincipal().getId();
				result = nutritionistRepository.findByUserAccountId(userAccountId);
				
				Assert.notNull(result);
				
				return result;
			}

			public Nutritionist findByUserAccountId(int id){
				Nutritionist result;
				result = nutritionistRepository.findByUserAccountId(id);
				return result;
			}
			
			public Nutritionist setCurriculaN(Curricula curricula){
				Nutritionist n = findByPrincipal();
				n.setCurricula(curricula);
				return n;
			}
			
			public void followUser(Nutritionist u,User i){
				if(!u.getFollowing().contains(i)){
					i.getFollowers().add(u);
					u.getFollowing().add(i);
					nutritionistRepository.save(u);
					userService.save2(i);
				}
			}
			
			public void followNutritionist(Nutritionist u,Nutritionist n){
				if(!u.getFollowing().contains(n) && u!=n){
					n.getFollowers().add(u);
					u.getFollowing().add(n);
					nutritionistRepository.save(u);
					nutritionistRepository.save(n);
				}
			}
			
			public void unfollowUser(Nutritionist u,User i){
				if(u.getFollowing().contains(i)){
					i.getFollowers().remove(u);
					u.getFollowing().remove(i);
					nutritionistRepository.save(u);
					userService.save2(i);
				}
			}
			
			public void unfollowNutritionist(Nutritionist u,Nutritionist n){
				if(u.getFollowing().contains(n)){
					n.getFollowers().remove(u);
					u.getFollowing().remove(n);
					nutritionistRepository.save(u);
					nutritionistRepository.save(n);
				}
			}
			public Collection<Recipe> findRecipeFromFollowing(Nutritionist n){
				Collection<SocialActor>s=n.getFollowing();
				Collection<User>users=userService.findAll();
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
