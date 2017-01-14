package controllers;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ContestService;
import domain.Contest;
import domain.Qualify;
import domain.Recipe;

@Controller
@RequestMapping("/contest")
public class ContestController extends AbstractController{
	

	//Services-------------------------
	
	@Autowired
	private ContestService contestService;
	
	//Constructor----------------------
	
	public ContestController(){
		super();
	}
	
	//Listing--------------------------
	
	@RequestMapping(value = "/browse", method = RequestMethod.GET)
	public ModelAndView browse(){
		
		ModelAndView result;
		Collection<Contest> contests;
		
		contests = contestService.findAll();
		
		result = new ModelAndView("contest/browse");
		result.addObject("contests", contests);
		result.addObject("requestURI", "contest/browse.do");
		
		return result;
	}
	
	@RequestMapping(value="/listrecipes", method = RequestMethod.GET)
	public ModelAndView listRecipes(@RequestParam int contestId){
		ModelAndView result;
		Collection<Recipe> recipes = new ArrayList<Recipe>();
		Collection<Qualify> qualifies;
		
		qualifies = contestService.findOne(contestId).getQualifies();
		for(Qualify q:qualifies){
			recipes.add(q.getRecipe());
		}
		
		result = new ModelAndView("recipe/browse");
		result.addObject("recipes", recipes);
		
		return result;
	}
	
	@RequestMapping(value="/listwinners", method = RequestMethod.GET)
	public ModelAndView listWinners(@RequestParam int contestId){
		ModelAndView result;
		
		Collection<Recipe> recipes;
		Contest contest = contestService.findOne(contestId);
		recipes = contestService.contestWinners(contest);
		
		result = new ModelAndView("recipe/browse");
		result.addObject("recipes", recipes);
		
		return result;
	}

}
