package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Contest;
import domain.Qualify;
import domain.Recipe;

import repositories.QualifyRepository;

@Service
@Transactional
public class QualifyService {
	
	// Managed repository -----------------------------------------------------
	
	@Autowired
	private QualifyRepository qualifyRepository;
	
	// Supporting services ----------------------------------------------------
	
	// Constructors -----------------------------------------------------------
	
	public QualifyService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Qualify create(){
		Qualify result;
		result = new Qualify();
		return result;
	}
	
	public Collection<Qualify> findAll(){
		Collection<Qualify> result;
		result = qualifyRepository.findAll();
		return result;
	}
	
	public Qualify findOne(int qualifyId){
		Qualify result;
		result = qualifyRepository.findOne(qualifyId);
		return result;
	}
	
	public Qualify save(Qualify qualify){
		Qualify result;
		result = qualifyRepository.save(qualify);
		return result;
	}
	
	public void delete(Qualify qualify){
		Assert.notNull(qualify);
		Assert.isTrue(qualify.getId() != 0);

		qualifyRepository.delete(qualify);
	}
	
	// Other business methods -------------------------------------------------
	
	public List<Recipe> findRecipeForContest(Contest contest){
		
		List<Recipe> recipes;
		recipes = qualifyRepository.findRecipeForContest(contest);
		return recipes;
		
	}
	
	public Qualify qualifyRecipe(Recipe recipe){
		Qualify result;
		result = qualifyRepository.qualifyRecipe(recipe);
		return result;
	}

}
