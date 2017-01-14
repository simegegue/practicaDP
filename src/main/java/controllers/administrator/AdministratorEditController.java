package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import controllers.AbstractController;
import domain.Administrator;


@Controller
@RequestMapping(value="/administrator")
public class AdministratorEditController extends AbstractController {
	//Services-------------------------------------------------------
		@Autowired
		private AdministratorService administratorService;
		
		//Constructor----------------------------------------------------
		
		public AdministratorEditController(){
			super();
		}
		
		
		//Edit personal data---------------------------------------------
		
		@RequestMapping(value="edit", method=RequestMethod.GET)
		public ModelAndView edit(){
			
			ModelAndView result;
			Administrator administrator =administratorService.create();
			administrator=administratorService.findByPrincipal();
			result=createEditModelAndView(administrator);
			
			return result;
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Administrator administrator , BindingResult binding){
			
			ModelAndView result;
			
			if(binding.hasErrors()){
				result = createEditModelAndView(administrator);
			}else{
				try{
					administratorService.save(administrator);
					result = new ModelAndView("redirect:/welcome/index.do");
				}catch(Throwable oops){
					result = createEditModelAndView(administrator, "administrator.commit.error");
				}
			}
			return result;
		}
		
		// Ancillary methods ----------------------------------------------------------------
		
		protected ModelAndView createEditModelAndView(Administrator administrator){
			ModelAndView result;
			result=createEditModelAndView(administrator,null);
			return result;
		}
		
		protected ModelAndView createEditModelAndView(Administrator administrator, String message){
			ModelAndView result;
				
			result=new ModelAndView("administrator/edit");
			result.addObject("administrator",administrator);
			result.addObject("message",message);
			return result;
		}
}
