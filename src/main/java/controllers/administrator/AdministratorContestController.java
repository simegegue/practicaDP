package controllers.administrator;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ContestService;
import controllers.AbstractController;
import domain.Contest;

@Controller
@RequestMapping("/administrator/contest")
public class AdministratorContestController extends AbstractController{
	
	//Services-------------------------
	
	@Autowired
	private ContestService contestService;
	
	public AdministratorContestController(){
		super();
	}
	

	//Listing--------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(){
		
		ModelAndView result;
		Collection<Contest> contests;
	
		contests = contestService.findAll();
		
		result = new ModelAndView("contest/list");
		result.addObject("contests", contests);
		result.addObject("requestURI", "administrator/contest/list.do");
		
		return result;
	}
	

	//Creation-------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(){
	
		ModelAndView result;
		Contest contest;

		contest = contestService.create();
		result = createEditModelAndView(contest);

		return result;
		
	}
	
	//Edition--------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int contestId){
		
		ModelAndView result;
		Contest contest;
		
		contest = contestService.findOne(contestId);
		Assert.notNull(contest);
		result = createEditModelAndView(contest);
		
		return result;
		
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Contest contest, BindingResult binding){
		
		ModelAndView result;
		Contest contestA;
		Date date = new Date();
		String dateF = new SimpleDateFormat("yyyy-MM-dd").format(date);
		
		if(binding.hasErrors()){
			result = createEditModelAndView(contest);
		}else{
			try{
				if(contest.getId()!=0){
					contestA = contestService.findOne(contest.getId());
					String dateO = new SimpleDateFormat("yyyy-MM-dd").format(contestA.getOpeningTime());
					String dateC = new SimpleDateFormat("yyyy-MM-dd").format(contestA.getClosingTime());
					String dateA = new SimpleDateFormat("yyyy-MM-dd").format(contestA.getClosingTime());
					String dateN = new SimpleDateFormat("yyyy-MM-dd").format(contest.getClosingTime());
					String dateNO = new SimpleDateFormat("yyyy-MM-dd").format(contest.getOpeningTime());

					if(dateO.compareTo(dateF)<0 && dateC.compareTo(dateF)>0){
						
						if(dateA.compareTo(dateN)>0 || dateN.compareTo(dateNO)<0){
							result = new ModelAndView("contest/list");
							result.addObject("contests", contestService.findAll());
							result.addObject("message", "masterClass.commit.error");
						}else{
							contestService.save(contest);
							result = list();
						}
					}else{
						if(dateN.compareTo(dateNO)<0){
							result = new ModelAndView("contest/list");
							result.addObject("contests", contestService.findAll());
							result.addObject("message", "masterClass.commit.error");
						}else{
							contestService.save(contest);
							result = list();
						}
					}
				}else{
					String dateN = new SimpleDateFormat("yyyy-MM-dd").format(contest.getClosingTime());
					String dateNO = new SimpleDateFormat("yyyy-MM-dd").format(contest.getOpeningTime());
					if(dateN.compareTo(dateNO)<0){
						result = new ModelAndView("contest/list");
						result.addObject("contests", contestService.findAll());
						result.addObject("message", "masterClass.commit.error");
					}else{
						contestService.save(contest);
						result = list();
					}
				}
				
			}catch(Throwable oops){
				result = createEditModelAndView(contest, "masterClass.commit.error");
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Contest contest, BindingResult binding){
		
		ModelAndView result;
		
		try{
			contestService.delete(contest);
			result = list();
		}catch(Throwable oops){
			result = createEditModelAndView(contest, "contest.commit.error");
		}
	return result;	
	}
		
	//Ancillary Methods---------------------------
	
	protected ModelAndView createEditModelAndView(Contest contest){
		
		ModelAndView result;
		
		result = createEditModelAndView(contest,null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Contest contest, String message){
		ModelAndView result;
		
		result = new ModelAndView("contest/edit");
		result.addObject("contest", contest);
		
		result.addObject("message", message);

		return result;
		
	}

}
