package controllers.sponsor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.SponsorService;
import domain.Sponsor;
@Controller
@RequestMapping("/sponsor")
public class SponsorEditController extends AbstractController{
	//Services-------------------------------------------------------
	@Autowired
	private SponsorService sponsorService;
	
	//Constructor----------------------------------------------------
	
	public SponsorEditController(){
		super();
	}
	
	
	//Edit personal data---------------------------------------------
	
	@RequestMapping(value="edit", method=RequestMethod.GET)
	public ModelAndView edit(){
		
		ModelAndView result;
		Sponsor sponsor=sponsorService.create();
		sponsor=sponsorService.findByPrincipal();
		result=createEditModelAndView(sponsor);
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Sponsor sponsor, BindingResult binding){
		
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(sponsor);
		}else{
			try{
				sponsorService.save2(sponsor);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch(Throwable oops){
				result = createEditModelAndView(sponsor, "sponsor.commit.error");
			}
		}
		return result;
	}
	
	// Ancillary methods ----------------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Sponsor sponsor){
		ModelAndView result;
		result=createEditModelAndView(sponsor,null);
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Sponsor sponsor, String message){
		ModelAndView result;
			
		result=new ModelAndView("sponsor/edit");
		result.addObject("sponsor",sponsor);
		result.addObject("message",message);
		return result;
	}
}
