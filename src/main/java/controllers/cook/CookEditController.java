package controllers.cook;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CookService;


import controllers.AbstractController;
import domain.Cook;


@Controller
@RequestMapping(value="/cook")
public class CookEditController extends AbstractController {
	//Services-------------------------------------------------------
		@Autowired
		private CookService cookService;
		
		//Constructor----------------------------------------------------
		
		public CookEditController(){
			super();
		}
		
		
		//Edit personal data---------------------------------------------
		
		@RequestMapping(value="edit", method=RequestMethod.GET)
		public ModelAndView edit(){
			
			ModelAndView result;
			Cook cook =cookService.create();
			cook=cookService.findByPrincipal();
			result=createEditModelAndView(cook);
			
			return result;
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Cook cook , BindingResult binding){
			
			ModelAndView result;
			
			if(binding.hasErrors()){
				result = createEditModelAndView(cook);
			}else{
				try{
					cookService.save2(cook);
					result = new ModelAndView("redirect:/welcome/index.do");
				}catch(Throwable oops){
					result = createEditModelAndView(cook, "nutritionist.commit.error");
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
				
			result=new ModelAndView("cook/edit");
			result.addObject("cook",cook);
			result.addObject("message",message);
			return result;
		}
}
