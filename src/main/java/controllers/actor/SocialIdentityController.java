package controllers.actor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SocialIdentityService;

import controllers.AbstractController;

import domain.SocialIdentity;

@Controller
@RequestMapping(value="/socialIdentity")
public class SocialIdentityController extends AbstractController{
		//Services
		@Autowired
		private SocialIdentityService socialIdentityService;
		//Contructor
		public SocialIdentityController(){
			super();
		}
		
	
		//Listing--------------------------
		
		@RequestMapping(value = "/browse", method = RequestMethod.GET)
		public ModelAndView browse(){
			
			ModelAndView result;
			Collection<SocialIdentity> socialIdentities;
		
			socialIdentities = socialIdentityService.findByPrincipal();
			
			result = new ModelAndView("socialIdentity/browse");
			result.addObject("socialIdentities", socialIdentities);
			result.addObject("requestURI", "socialIdentity/browse.do");
			
			return result;
		}
		

		//Creation-------------------------
		
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create(){
		
			ModelAndView result;
			SocialIdentity socialIdentity;

			socialIdentity = socialIdentityService.create();
			result = createEditModelAndView(socialIdentity);

			return result;
			
		}
		
		//Edition--------------------------
		
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int socialIdentityId){
			
			ModelAndView result;
			SocialIdentity socialIdentity;
			
			socialIdentity = socialIdentityService.findOne(socialIdentityId);
			Assert.notNull(socialIdentity);
			result = createEditModelAndView(socialIdentity);
			
			return result;
			
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid SocialIdentity socialIdentity, BindingResult binding){
			
			ModelAndView result;
			
			if(binding.hasErrors()){
				result = createEditModelAndView(socialIdentity);
			}else{
				try{
					socialIdentityService.save(socialIdentity);
					result = browse();
				}catch(Throwable oops){
					result = createEditModelAndView(socialIdentity, "socialIdentity.commit.error");
				}
			}
			return result;
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(SocialIdentity socialIdentity, BindingResult binding){
			
			ModelAndView result;
			
			try{
				socialIdentityService.delete(socialIdentity);
				result = browse();
			}catch(Throwable oops){
				result = createEditModelAndView(socialIdentity, "socialIdentity.commit.error");
			}
		return result;	
		}
			
		//Ancillary Methods---------------------------
		
		protected ModelAndView createEditModelAndView(SocialIdentity socialIdentity){
			
			ModelAndView result;
			
			result = createEditModelAndView(socialIdentity,null);
			
			return result;
		}
		
		protected ModelAndView createEditModelAndView(SocialIdentity socialIdentity, String message){
			ModelAndView result;
			
			result = new ModelAndView("socialIdentity/edit");
			result.addObject("socialIdentity", socialIdentity);
			
			result.addObject("message", message);

			return result;
			
		}
}
