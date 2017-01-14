package controllers.sponsor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;
import services.CampaignService;

import controllers.AbstractController;
import domain.Banner;
import domain.Campaign;

@Controller
@RequestMapping("/campaign/sponsor/banner")
public class SponsorBannerController extends AbstractController{
	
	// Services ---------------------------------------------------
	
	@Autowired
	private BannerService bannerService;
	
	@Autowired
	private CampaignService campaignService;
	
	// Constructor ------------------------------------------------
	
	public SponsorBannerController(){
		super();
	}
	
	// Listing methods -------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int campaignId){
		ModelAndView result;
		Campaign campaign;
		Collection<Banner> banners;
		
		campaign = campaignService.findOne(campaignId);
		
		banners = campaign.getBanners();
		
		result = new ModelAndView("banner/list");
		result.addObject("banners", banners);
		result.addObject("campaignId", campaignId);
		
		return result;
	}
	
	// Create methods ------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int campaignId){
		ModelAndView result;
		Campaign campaign = campaignService.findOne(campaignId);
		Banner banner = bannerService.create(campaign);
		
		
		result = new ModelAndView("banner/create");
		result.addObject("banner", banner);
		
		return result;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST, params="save")
	public ModelAndView save(@ModelAttribute("banner") @Valid Banner banner, BindingResult binding) {
			
		ModelAndView result;
		if(binding.hasErrors()){
			result=createEditModelAndView(banner);
		}else{
			try{
				bannerService.save(banner);
				result = new ModelAndView("redirect:/campaign/sponsor/list.do");
			}catch (Throwable oops) {
				result = createEditModelAndView(banner,"banner.commit.error");
			}
		}
		return result;
	}
	
	// Ancillary methods -----------------------------------------------
	
	protected ModelAndView createEditModelAndView(Banner banner){
		
		ModelAndView result;
		result = createEditModelAndView(banner, null);
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Banner banner, String message){
		ModelAndView result;
		
		result = new ModelAndView("banner/create");
		result.addObject("campaign", banner);
		result.addObject("message", message);
		
		return result;
	}

}
