package controllers.nutritionist;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.NutritionistService;
import controllers.AbstractController;
import domain.Nutritionist;


@Controller
@RequestMapping("/nutritionist")
public class NutritionistEditController extends AbstractController{
	//Services-------------------------------------------------------
	@Autowired
	private NutritionistService nutritionistService;
	
	//Constructor----------------------------------------------------
	
	public NutritionistEditController(){
		super();
	}
	
	
	//Edit personal data---------------------------------------------
	
	@RequestMapping(value="edit", method=RequestMethod.GET)
	public ModelAndView edit(){
		
		ModelAndView result;
		Nutritionist nutritionist=nutritionistService.create();
		nutritionist=nutritionistService.findByPrincipal();
		result=createEditModelAndView(nutritionist);
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Nutritionist nutritionist, BindingResult binding){
		
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(nutritionist);
		}else{
			try{
				nutritionistService.save2(nutritionist);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch(Throwable oops){
				result = createEditModelAndView(nutritionist, "nutritionist.commit.error");
			}
		}
		return result;
	}
	
	// Ancillary methods ----------------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Nutritionist nutritionist){
		ModelAndView result;
		result=createEditModelAndView(nutritionist,null);
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Nutritionist nutritionist, String message){
		ModelAndView result;
			
		result=new ModelAndView("nutritionist/edit");
		result.addObject("nutritionist",nutritionist);
		result.addObject("message",message);
		return result;
	}
}
