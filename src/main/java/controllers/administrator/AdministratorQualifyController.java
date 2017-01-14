package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ContestService;
import services.RecipeService;
import domain.Contest;
import domain.Qualify;
import domain.Recipe;
@Controller
@RequestMapping("/contest")
public class AdministratorQualifyController {
	//Services--------------------------------------------------
		
		@Autowired
		private ContestService contestService;
		
		@Autowired
		private RecipeService recipeService;

		
		//Constructor-----------------------------------------------
		
		public AdministratorQualifyController(){
			super();
		}
		
		// SelectWinner ---------------------------------------------
		@RequestMapping(value="/selectWinners", method=RequestMethod.GET)
		public ModelAndView selectWinners(@RequestParam int contestId) {
			ModelAndView result;
			Contest contest;
			Collection<Contest> contests;
			Recipe recipe;
			
			contest = contestService.findOne(contestId);
			contestService.selectWinner(contest);
			
			contests = contestService.findAll();
			for(Qualify a : contest.getQualifies()){
				recipe = a.getRecipe();
				recipe.setQualified(false);
				recipeService.save(recipe);
			}
			
			result = new ModelAndView("contest/browse");
			result.addObject("contests", contests);

			return result;
			}
			
}
