/* AdministratorController.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Contest;
import domain.Cook;
import domain.Sponsor;
import domain.User;

import services.ContestService;
import services.CookService;
import services.LearningMaterialService;
import services.MasterClassService;
import services.MonthlyBillService;
import services.RecipeService;
import services.SpamWordService;
import services.SponsorService;
import services.UserService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private LearningMaterialService learningMaterialService;
	
	@Autowired
	private MasterClassService masterClassService;
	
	@Autowired
	private CookService cookService;
	
	@Autowired
	private SponsorService sponsorService;
	
	@Autowired
	private MonthlyBillService monthlyBillService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ContestService contestService;
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private SpamWordService spamWordService;
	
	// Constructors -----------------------------------------------------------
	
	public AdministratorController() {
		super();
	}
		

	//SpamWord
	@RequestMapping(value = "/registerSpamWord", method = RequestMethod.GET)
	public ModelAndView registerSpamWord(@RequestParam String spamWordKey){
		ModelAndView result;
		try {			
			spamWordService.registerSpamWord(spamWordKey);
			result = new ModelAndView("welcome/index");
			result.addObject("message", "masterClass.commit.ok");			
		} catch (Throwable oops) {			
			result = new ModelAndView("welcome/index");
			result.addObject("message", "masterClass.commit.error");			
		}
		return result;
	}
	
	
	//Listing--------------------------
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard(){
			
		ModelAndView result;
		
		//Recipe
		Collection<Double> mamRU = new ArrayList<Double>();
		Collection<User> uamr = new ArrayList<User>();
		Collection<Double> mamRQC = new ArrayList<Double>();
		Collection<Contest> cmrq = new ArrayList<Contest>();
		Collection<Double> asNSR = new ArrayList<Double>();
		Collection<Double> asNIR = new ArrayList<Double>();
		Collection<User> ubp = new ArrayList<User>();
		Collection<User> ubld = new ArrayList<User>();
		
		mamRU = userService.findMinAvgMaxRecipesPerUser();
		uamr = userService.usersMoreRecipesAuthored();
		mamRQC = contestService.findMinAvgMaxRecipesPerContest();
		cmrq = contestService.findContestMoreRecipes();
		asNSR = recipeService.findMinStandardDeviationStepsPerRecipe();
		asNIR = recipeService.findAvgSandardDeviationIngredientsPerRecipe();
		ubp = userService.usersByPopularity();
		ubld = userService.usersAvgLikesDislikes();
		
		//Sponsor
		Collection <Double> mamCS = new ArrayList<Double>();
		Collection <Double>	mamACS = new ArrayList<Double>();
		Collection <Sponsor> rcvs = new ArrayList<Sponsor>();
		Collection <Sponsor> rcmb = new ArrayList<Sponsor>();
		Collection <Double> sapmb = new ArrayList<Double>();
		Collection <Double> saumb = new ArrayList<Double>();
		Collection <Sponsor> is = new ArrayList<Sponsor>();
		Collection <Sponsor> slac = new ArrayList<Sponsor>();
		Collection <Sponsor> c90 = new ArrayList<Sponsor>();
		
		mamCS = sponsorService.findMinAvgMaxCampaignPerSponsor();
		mamACS = sponsorService.findMinAvgMaxActiveCampaignPerSponsor();
		rcvs = sponsorService.findCompanyRanking();
		rcmb = sponsorService.findCompanyRankingMonthlyBill();
		sapmb = monthlyBillService.avgStdDevPaidMonthlyBills();
		saumb = monthlyBillService.standardDeviationUnpaidMonthlyBills();
		is = sponsorService.findInactiveSponsor();
		slac = sponsorService.findCompaniesSpendLessAverage();
		c90 = sponsorService.findCompaniesSpendMoreThan90();
		
		//MasterClass
		Collection<Double> avgLM = new ArrayList<Double>();
		Collection<Double> avgMCPC = new ArrayList<Double>();
		Collection<Double> mmasMCPC = new ArrayList<Double>();
		Collection<Cook> lC = new ArrayList<Cook>();
		Integer mcP = masterClassService.masterClassesPromoted();
		
		avgLM = learningMaterialService.avgLearningMaterials();
		avgMCPC = masterClassService.avgMasterClassesPromotedPerCook();
		mmasMCPC = masterClassService.minMaxAvgMasterClasses();
		lC = cookService.listCooksByMasterClassesPromoted();
		
		result = new ModelAndView("administrator/dashboard");
		
		result.addObject("mamRU", mamRU);
		result.addObject("uamr", uamr);
		result.addObject("mamRQC", mamRQC);
		result.addObject("cmrq", cmrq);
		result.addObject("asNSR", asNSR);
		result.addObject("asNIR", asNIR);
		result.addObject("ubp", ubp);
		result.addObject("ubld", ubld);
		
		result.addObject("mamCS", mamCS);
		result.addObject("mamACS", mamACS);
		result.addObject("rcvs", rcvs);
		result.addObject("rcmb", rcmb);
		result.addObject("sapmb", sapmb);
		result.addObject("saumb", saumb);
		result.addObject("is", is);
		result.addObject("slac", slac);
		result.addObject("c90", c90);
		
		result.addObject("avgLM", avgLM);
		result.addObject("avgMCPC", avgMCPC);
		result.addObject("mmasMCPC", mmasMCPC);
		result.addObject("lC", lC);
		result.addObject("mcP", mcP);
		
		result.addObject("requestURI", "administrator/dashboard.do");
			
			
		return result;
	}
	
}