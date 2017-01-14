package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ContestRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Contest;
import domain.Qualify;
import domain.Recipe;

@Service
@Transactional
public class ContestService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ContestRepository contestRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private QualifyService qualifyService;
	
	// Constructors -----------------------------------------------------------
	
	public ContestService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Contest create() {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Contest result;

		result = new Contest();

		return result;
	}

	public Collection<Contest> findAll() {
		Collection<Contest> result;

		result = contestRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Contest findOne(int contestId) {
		Contest result;

		result = contestRepository.findOne(contestId);
		Assert.notNull(result);

		return result;
	}

	public Contest save(Contest contest) {
				
		Assert.notNull(contest);
		
		Contest result;

		result = contestRepository.save(contest);
		
		return result;
	}

	public void delete(Contest contest) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(contest);
		Assert.isTrue(contest.getId() != 0);

		contestRepository.delete(contest);
	}
	
	// Other business methods -------------------------------------------------

	public Map<Contest, List<Recipe>> findContestRecipes(){
		
		Map<Contest, List<Recipe>> result;
		List<Contest> contests;
		List<Recipe> recipes;
		
		result = new HashMap<Contest,List<Recipe>>();
		contests = contestRepository.findAll();

		for(Contest c : contests){
			recipes = qualifyService.findRecipeForContest(c);
			result.put(c, recipes);
		}
		return result;
	}
	
	public void selectWinner(Contest contest){
		List<Recipe> recipes = new ArrayList<Recipe>();
		Boolean last;
		for(Qualify q : contest.getQualifies()){
			recipes.add(q.getRecipe());
		}
		List<Recipe> winners = new ArrayList<Recipe>();
		for(Recipe r : recipes){
			last = false;
			if(winners.isEmpty()){
				winners.add(r);
			}else{
				List<Recipe> winners2 = new ArrayList<Recipe>();
				for(Recipe rw:winners){
					winners2.add(rw);
				}
				for(Recipe rc : winners2){
					if(rc.getRelationLikes().size()<r.getRelationLikes().size()){
						winners.add(winners.indexOf(rc), r);
					}else{
						if(rc.getRelationLikes().size()==r.getRelationLikes().size() && rc.getRelationDislikes().size()>rc.getRelationDislikes().size()){
							winners.add(winners.indexOf(rc), r);
						}else{
							if(rc.getRelationLikes().size()==r.getRelationLikes().size() && rc.getRelationDislikes().size()==rc.getRelationDislikes().size()){
								Random rdn1 = new Random(); /*Número aleatorio para rc*/
								Random rdn2 = new Random(); /*Número aleatorio para r*/
								if(rdn1.nextDouble()<rdn2.nextDouble()){
									winners.add(winners.indexOf(rc), r);
								}else{
									winners.add(winners.indexOf(rc)+1, r);
								}
							}else{
								last = true;
							}
						}
					}
				}
				if(last){
					winners.add(r);
				}
			}
		}
		for(Recipe r : winners){
			Qualify q = qualifyService.qualifyRecipe(r);
			q.setPosition(winners.indexOf(r)+1);
			qualifyService.save(q);
		}
	}
	
	
	public Collection<Double> findMinAvgMaxRecipesPerContest(){
		Collection<Double> result = new ArrayList<Double>();
		Integer min = contestRepository.findMinRecipesPerContest();
		Double avg = contestRepository.findAvgRecipesPerContest();
		Integer max = contestRepository.findMaxRecipesPerContest();
	
		if(min == null){
			result.add(0.0);
		}else{
			result.add(min*1.0);
		}
		if(avg == null){
			result.add(0.0);
		}else{
			result.add(avg);
		}
		if(max == null){
			result.add(0.0);
		}else{
			result.add(max*1.0);
		}
		
		return result;
		
	}
	
	public Collection<Contest> findContestMoreRecipes(){
		Collection<Contest> result;
		result = contestRepository.findContestMoreRecipes();
		return result;
	}
	
	public Collection<Recipe> contestWinners(Contest contest){
		List<Recipe> result = new ArrayList<Recipe>();
		boolean first = true;
		List<Integer> positions = new ArrayList<Integer>();
		positions.add(1);
		positions.add(2);
		positions.add(3);
		
		for(Qualify q : contest.getQualifies()){
			if(first && positions.contains(q.getPosition())){
				first = false;
				result.add(q.getRecipe());
			}else{
				if(q.getPosition()==1 && positions.contains(q.getPosition())){
					result.add(0, q.getRecipe());
				}else{
					if(q.getPosition()==2){
						result.add(1, q.getRecipe());
					}else{
						if(q.getPosition()==3 && positions.contains(q.getPosition())){
							result.add(q.getRecipe());
						}
					}
				}
			}
		}
		
		return result;
	}
	
	public Collection<Contest> findContestOpened(){
		Collection<Contest> contests;
		contests = contestRepository.findContestOpened();
		return contests;
	}
}
