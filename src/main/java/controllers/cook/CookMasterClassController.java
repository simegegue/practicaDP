package controllers.cook;

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

import services.CookService;
import services.MasterClassService;
import controllers.AbstractController;
import domain.Cook;
import domain.MasterClass;

@Controller
@RequestMapping("/cook/masterClass")
public class CookMasterClassController extends AbstractController{
	

	//Services-------------------------
	
	@Autowired
	private MasterClassService masterClassService;
	
	@Autowired
	private CookService cookService;
	
	
	//Constructor----------------------
	
	public CookMasterClassController(){
		super();
	}
	
	//Listing--------------------------
	
	@RequestMapping(value = "/browse", method = RequestMethod.GET)
	public ModelAndView browse(){
		
		ModelAndView result;
		Collection<MasterClass> masterClasses;
	
		masterClasses = masterClassService.findByPrincipal();
		
		result = new ModelAndView("masterClass/browse");
		result.addObject("masterClasses", masterClasses);
		result.addObject("requestURI", "cook/masterClass/browse.do");
		
		return result;
	}
	

	//Creation-------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(){
	
		ModelAndView result;
		MasterClass masterClass;

		masterClass = masterClassService.create();
		result = createEditModelAndView(masterClass);

		return result;
		
	}
	
	//Edition--------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int masterClassId){
		
		ModelAndView result;
		MasterClass masterClass;
		
		masterClass = masterClassService.findOne(masterClassId);
		Assert.notNull(masterClass);
		result = createEditModelAndView(masterClass);
		
		return result;
		
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid MasterClass masterClass, BindingResult binding){
		
		ModelAndView result;
		Cook cook = cookService.findByPrincipal();
		
		if(binding.hasErrors() || masterClass.getCook()!=cook){
			result = createEditModelAndView(masterClass);
		}else{
			try{
				masterClassService.save(masterClass);
				result = browse();
			}catch(Throwable oops){
				result = createEditModelAndView(masterClass, "masterClass.commit.error");
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(MasterClass masterClass, BindingResult binding){
		
		ModelAndView result;
		
		try{
			masterClassService.delete(masterClass);
			result = browse();
		}catch(Throwable oops){
			result = createEditModelAndView(masterClass, "masterClass.commit.error");
		}
	return result;	
	}
		
	//Ancillary Methods---------------------------
	
	protected ModelAndView createEditModelAndView(MasterClass masterClass){
		
		ModelAndView result;
		
		result = createEditModelAndView(masterClass,null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(MasterClass masterClass, String message){
		ModelAndView result;
		
		result = new ModelAndView("masterClass/edit");
		result.addObject("masterClass", masterClass);
		
		result.addObject("message", message);

		return result;
		
	}

}
