package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.MasterClassService;
import domain.MasterClass;

@Controller
@RequestMapping("/masterClass")
public class MasterClassController extends AbstractController{
	

	//Services-------------------------
	
	@Autowired
	private MasterClassService masterClassService;
	
	//Constructor----------------------
	
	public MasterClassController(){
		super();
	}
	
	//Listing--------------------------
	
	@RequestMapping(value = "/browse", method = RequestMethod.GET)
	public ModelAndView browse(){
		
		ModelAndView result;
		Collection<MasterClass> masterClasses;
		
		
		masterClasses = masterClassService.findAll();
		
		result = new ModelAndView("masterClass/browse");
		result.addObject("masterClasses", masterClasses);
		result.addObject("requestURI", "masterClass/browse.do");
		
		
		return result;
	}
	

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int masterClassId) {
		ModelAndView result;
		MasterClass masterClass;
		String userAccount = LoginService.getPrincipal().getUsername();
		
		masterClass = masterClassService.findOne(masterClassId);
		result = new ModelAndView("masterClass/display");
		result.addObject("masterClass", masterClass);	
		result.addObject("userAccount", userAccount);
		
		return result;
	}
	
	// Register to a class ----------------------------------------------------------------

	@RequestMapping(value = "/registerMC", method = RequestMethod.GET)
	public ModelAndView registerMC(@RequestParam int masterClassId) {
		ModelAndView result;		
				
		try {			
			masterClassService.registerMC(masterClassId);
			result = browse();
			result.addObject("message", "masterClass.commit.ok");			
		} catch (Throwable oops) {			
			result = browse();
			result.addObject("message", "masterClass.commit.error");			
		}
		
		return result;
	}
	
	@RequestMapping(value = "/unregisterMC", method = RequestMethod.GET)
	public ModelAndView unregisterMC(@RequestParam int masterClassId) {
		ModelAndView result;		
				
		try {			
			masterClassService.unregisterMC(masterClassId);
			result = browse();
			result.addObject("message", "masterClass.commit.ok");			
		} catch (Throwable oops) {			
			result = browse();
			result.addObject("message", "masterClass.commit.error");			
		}
		
		return result;
	}
	
	@RequestMapping(value = "/promotedemote", method = RequestMethod.GET)
	public ModelAndView promotedemote(@RequestParam int masterClassId) {
		ModelAndView result;		
		MasterClass mc = masterClassService.findOne(masterClassId);
		try {			
			masterClassService.promoteDemoteMasterClass(mc);
			result = browse();
			result.addObject("message", "masterClass.commit.ok");			
		} catch (Throwable oops) {			
			result = browse();
			result.addObject("message", "masterClass.commit.error");			
		}
		
		return result;
	}
	
}
