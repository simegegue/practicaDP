package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RelationLikeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Recipe;
import domain.RelationLike;
import domain.SocialActor;

@Service
@Transactional
public class RelationLikeService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RelationLikeRepository relationLikeRepository;
	
	// Supporting services ----------------------------------------------------
	
	// Constructors -----------------------------------------------------------
	
	public RelationLikeService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public RelationLike create() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("USER");
		Authority ao = new Authority();
		ao.setAuthority("NUTRITIONIST");
		Assert.isTrue(userAccount.getAuthorities().contains(au) || userAccount.getAuthorities().contains(ao) );
		RelationLike result;
		result = new RelationLike();
		
		return result;
	}

	public Collection<RelationLike> findAll() {
		Collection<RelationLike> result;

		result = relationLikeRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public RelationLike findOne(int relationLikeId) {
		RelationLike result;

		result = relationLikeRepository.findOne(relationLikeId);
		Assert.notNull(result);

		return result;
	}

	public RelationLike save(RelationLike relationLike) {
		Assert.notNull(relationLike);
		
		RelationLike result;

		result = relationLikeRepository.save(relationLike);
		
		return result;
	}

	public void delete(RelationLike relationLike) {
		Assert.notNull(relationLike);
		Assert.isTrue(relationLike.getId() != 0);

		relationLikeRepository.delete(relationLike);
	}
	
	// Other business methods -------------------------------------------------
	
	public Collection<RelationLike> recipeLikes(Recipe recipe){
		Collection<RelationLike> result;
		result = relationLikeRepository.recipesLikes(recipe.getId());
		return result;
	}
	
	public Collection<RelationLike> giveLike(Recipe r,SocialActor u){
		Collection<RelationLike> result=r.getRelationLikes();
		Collection<RelationLike> like=u.getRelationLikes();
		List<Recipe>recipes=new ArrayList<Recipe>();
		for(RelationLike l:like){
			recipes.add(l.getRecipe());
		}
		if(!recipes.contains(r)){
			RelationLike a=create();
			a.setRecipe(r);
			a.setSocialActor(u);
			u.getRelationLikes().add(a);
			r.getRelationLikes().add(a);
			result.add(a);
		}
		
		return result;			
	}
	/*
	public Collection<RelationLike> giveLike(Recipe r,Nutritionist n){
		Collection<RelationLike> result=r.getRelationLikes();
		Collection<RelationLike> like=n.getRelationLikes();
		List<Recipe>recipes=new ArrayList<Recipe>();
		for(RelationLike l:like){
			recipes.add(l.getRecipe());
		}
		if(!recipes.contains(r)){
			RelationLike a=create();
			a.setRecipe(r);
			a.setSocialActor(n);
			n.getRelationLikes().add(a);
			r.getRelationLikes().add(a);
			result.add(a);
		}
		
		return result;			
	}
		*/
}


