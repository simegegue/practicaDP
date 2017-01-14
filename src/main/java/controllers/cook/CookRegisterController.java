package controllers.cook;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CookService;

import controllers.AbstractController;
import domain.Cook;

@Controller
@RequestMapping("/cook")
public class CookRegisterController extends AbstractController{
	
	//Services--------------------------------------------------
	
		@Autowired
		private CookService cookService;
		
	// Constructors -----------------------------------------------------------
		
		public CookRegisterController() {
			super();
		}
	

	//Register--------------------------------------------------
	
		@RequestMapping(value="/register", method=RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			Cook cook = new Cook();
				
			cook = cookService.create();
				
			result= new ModelAndView("cook/register");
			result.addObject("cook",cook);

			return result;
			}
			
		@RequestMapping(value="/register", method=RequestMethod.POST, params="save")
		public ModelAndView save(@ModelAttribute("cook") @Valid Cook cook, BindingResult binding) {
				
			ModelAndView result;
			if(binding.hasErrors()){
				result=createEditModelAndView(cook);
			}else{
				try{
					cookService.save(cook);
					result = new ModelAndView("redirect:/security/login.do");
				}catch (Throwable oops) {
					result = createEditModelAndView(cook,"cook.commit.error");
				}
			}
			return result;
		}
		

	// Ancillary methods ----------------------------------------------------------------
	
		protected ModelAndView createEditModelAndView(Cook cook){
			ModelAndView result;
			result=createEditModelAndView(cook,null);
			return result;
		}
			
		protected ModelAndView createEditModelAndView(Cook cook, String message){
			ModelAndView result;
				
			result=new ModelAndView("cook/register");
			result.addObject("cook",cook);
			result.addObject("message",message);
			return result;
		}	

}
