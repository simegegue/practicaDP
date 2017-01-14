package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Campaign;

import services.CampaignService;

@Controller
@RequestMapping("/administrator/campaign")
public class AdministratorCampaignController {
	
	// Services --------------------------------------------------
	
	@Autowired
	private CampaignService campaignService;
	
	// Constructor -----------------------------------------------
	
	public AdministratorCampaignController(){
		super();
	}
	
	// Listing methods ------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listing(){
		ModelAndView result;
		Collection<Campaign> campaigns;
			
		campaigns = campaignService.findAll();
			
		result = new ModelAndView("campaign/list");
		result.addObject("campaigns", campaigns);
			
		return result;
	}
	
	@RequestMapping(value = "/starred", method = RequestMethod.GET)
	public ModelAndView starred(@RequestParam int campaignId){
		ModelAndView result;
		Campaign campaign;
		Collection<Campaign> campaigns;
		
		campaigns = campaignService.findAll();
		campaign = campaignService.findOne(campaignId);
		campaign.setStarred(true);
		campaignService.save(campaign);
			
		result = new ModelAndView("campaign/list");
		result.addObject("campaigns", campaigns);
			
		return result;
	}
	
	@RequestMapping(value = "/unstarred", method = RequestMethod.GET)
	public ModelAndView unstarred(@RequestParam int campaignId){
		ModelAndView result;
		Campaign campaign;
		Collection<Campaign> campaigns;
		
		campaigns = campaignService.findAll();
		campaign = campaignService.findOne(campaignId);
		campaign.setStarred(false);
		campaignService.save(campaign);
			
		result = new ModelAndView("campaign/list");
		result.addObject("campaigns", campaigns);
			
		return result;
	}
	
	

}
