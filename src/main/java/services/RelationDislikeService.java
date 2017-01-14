package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RelationDislikeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Recipe;
import domain.RelationDislike;
import domain.SocialActor;


@Service
@Transactional
public class RelationDislikeService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RelationDislikeRepository relationDislikeRepository;
	
	// Supporting services ----------------------------------------------------
		
	// Constructors -----------------------------------------------------------
	
	public RelationDislikeService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public RelationDislike create() {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("USER");
		Authority au1 = new Authority();
		au1.setAuthority("NUTRITIONIST");
		Assert.isTrue(userAccount.getAuthorities().contains(au) || userAccount.getAuthorities().contains(au1));
		
		RelationDislike result;

		result = new RelationDislike();

		return result;
	}

	public Collection<RelationDislike> findAll() {
		Collection<RelationDislike> result;

		result = relationDislikeRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public RelationDislike findOne(int relationDislikeId) {
		RelationDislike result;

		result = relationDislikeRepository.findOne(relationDislikeId);
		Assert.notNull(result);

		return result;
	}

	public RelationDislike save(RelationDislike relationDislike) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("USER");
		Authority au1 = new Authority();
		au1.setAuthority("NUTRITIONIST");
		Assert.isTrue(userAccount.getAuthorities().contains(au) || userAccount.getAuthorities().contains(au1));
		
		Assert.notNull(relationDislike);
		
		RelationDislike result;

		result = relationDislikeRepository.save(relationDislike);
		
		return result;
	}

	public void delete(RelationDislike relationDislike) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("USER");
		Authority au1 = new Authority();
		au1.setAuthority("NUTRITIONIST");
		Assert.isTrue(userAccount.getAuthorities().contains(au) || userAccount.getAuthorities().contains(au1));
		
		Assert.notNull(relationDislike);
		Assert.isTrue(relationDislike.getId() != 0);

		relationDislikeRepository.delete(relationDislike);
	}
	
	// Other business methods -------------------------------------------------
		
	public Collection<RelationDislike> giveDislike(Recipe r,SocialActor u){
		Collection<RelationDislike> result=r.getRelationDislikes();
		Collection<RelationDislike> dislike=u.getRelationDislikes();
		List<Recipe>recipes=new ArrayList<Recipe>();
		for(RelationDislike l:dislike){
			recipes.add(l.getRecipe());
		}
		if(!recipes.contains(r)){
			RelationDislike a=create();
			a.setRecipe(r);
			a.setSocialActor(u);
			u.getRelationDislikes().add(a);
			r.getRelationDislikes().add(a);
			result.add(a);
		}
		return result;			
	}
	/*
	public Collection<RelationDislike> giveDislike(Recipe r,Nutritionist n){
		Collection<RelationDislike> result=r.getRelationDislikes();
		Collection<RelationDislike> dislike=n.getRelationDislikes();
		List<Recipe>recipes=new ArrayList<Recipe>();
		for(RelationDislike l:dislike){
			recipes.add(l.getRecipe());
		}
		if(!recipes.contains(r)){
			RelationDislike a=create();
			a.setRecipe(r);
			a.setSocialActor(n);
			n.getRelationDislikes().add(a);
			r.getRelationDislikes().add(a);
			result.add(a);
		}
		return result;			
	}
		*/
}
