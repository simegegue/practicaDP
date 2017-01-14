package controllers.sponsor;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CampaignService;

import controllers.AbstractController;
import domain.Campaign;

@Controller
@RequestMapping("campaign/sponsor")
public class SponsorCampaignController extends AbstractController{
	
	// Services -------------------------------------------------
	
	@Autowired
	private CampaignService campaignService;
	
	// Constructor ----------------------------------------------

	public SponsorCampaignController(){
		super();
	}
	
	// Listing methods ------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listing(){
		ModelAndView result;
		Collection<Campaign> campaigns;
		Date current = new Date(System.currentTimeMillis()-1);
		
		campaigns = campaignService.findCampaignSponsor();
		
		result = new ModelAndView("campaign/list");
		result.addObject("campaigns", campaigns);
		result.addObject("current", current);
		
		return result;
	}
	
	// Creating methods -----------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		
		Campaign campaign = campaignService.create();
		
		result = new ModelAndView("campaign/create");
		result.addObject("campaign", campaign);
		
		return result;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST, params="save")
	public ModelAndView save(@ModelAttribute("campaign") @Valid Campaign campaign, BindingResult binding) {
			
		ModelAndView result;
		if(binding.hasErrors()){
			result=createEditModelAndView(campaign);
		}else{
			try{
				if(campaign.getId() == 0){
					campaignService.save(campaign);
					result = new ModelAndView("redirect:/campaign/sponsor/list.do");
				}else{
					campaignService.editCampaign(campaign);
					result = new ModelAndView("redirect:/campaign/sponsor/list.do");
				}
			}catch (Throwable oops) {
				result = createEditModelAndView(campaign,"campaign.commit.error");
			}
		}
		return result;
	}
	
	// Editing methods -------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int campaignId){
		ModelAndView result;
		Campaign campaign = campaignService.findOne(campaignId);
		Assert.notNull(campaign);
		
		result = createEditModelAndView(campaign);
		
		return result;
	}

	
	// Deleting methods ------------------------------------------------
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int campaignId){
		ModelAndView result;
		Campaign campaign = campaignService.findOne(campaignId);
		
		try{
			campaignService.delete(campaign);
			result = new ModelAndView("redirect:/campaign/sponsor/list.do");
		}catch(Throwable oops){
			result = deleteModelAndView(campaign, "campaign.commit.error");
		}
		
		return result;
	}
	
	// Ancillary methods ----------------------------------------
	
	protected ModelAndView createEditModelAndView(Campaign campaign){
		
		ModelAndView result;
		result = createEditModelAndView(campaign, null);
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Campaign campaign, String message){
		ModelAndView result;
		
		result = new ModelAndView("campaign/create");
		result.addObject("campaign", campaign);
		result.addObject("message", message);
		
		return result;
	}
	
	protected ModelAndView deleteModelAndView(Campaign campaign, String message){
		ModelAndView result;
		
		Collection<Campaign> campaigns = campaignService.findCampaignSponsor();
		
		result = new ModelAndView("campaign/list");
		result.addObject("campaigns", campaigns);
		result.addObject("message", message);
		
		return result;
	}
}
