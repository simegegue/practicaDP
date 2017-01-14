package controllers.nutritionist;

import java.util.ArrayList;
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

import services.CurriculaService;
import services.EndorserService;
import services.NutritionistService;

import controllers.AbstractController;
import domain.Curricula;
import domain.Endorser;
import domain.Nutritionist;

@Controller
@RequestMapping("/nutritionist/curricula")
public class NutritionistCurriculaController extends AbstractController{
	
	@Autowired
	private CurriculaService curriculaService;
	
	@Autowired
	private NutritionistService nutritionistService;
	
	@Autowired
	private EndorserService endorserService;
	
	public NutritionistCurriculaController(){
		super();
	}
	
	
	//Listing--------------------------
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(){
		
		ModelAndView result;
		Curricula curricula;
		
		Nutritionist nutritionist = nutritionistService.findByPrincipal();
		
		curricula = curriculaService.findByPrincipal();
		
		result = new ModelAndView("curricula/display");
		result.addObject("curricula", curricula);
		result.addObject("nutritionist", nutritionist);
		result.addObject("requestURI", "nutritionist/curricula/display.do");
		
		return result;
	}
	

	//Creation-------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(){
	
		ModelAndView result;
		Curricula curricula;
		curricula = curriculaService.create();
		result = createEditModelAndView(curricula);

		return result;
		
	}
	
	//Edition--------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int curriculaId){
		
		ModelAndView result;
		Curricula curricula;
		
		curricula = curriculaService.findOne(curriculaId);
		Assert.notNull(curricula);
		result = createEditModelAndView(curricula);
		
		return result;
		
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Curricula curricula, BindingResult binding){
		
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(curricula);
		}else{
			try{
				Nutritionist nutritionist = nutritionistService.findByPrincipal();
				curricula = curriculaService.save(curricula);
				nutritionist.setCurricula(curricula);
				nutritionistService.save2(nutritionist);
				result = display();
			}catch(Throwable oops){
				result = createEditModelAndView(curricula, "curricula.commit.error");
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Curricula curricula, BindingResult binding){
		
		ModelAndView result;
		
		try{
			curriculaService.delete(curricula);
			result = display();
		}catch(Throwable oops){
			result = createEditModelAndView(curricula, "curricula.commit.error");
		}
	return result;	
	}
		
	//Ancillary Methods---------------------------
	
	protected ModelAndView createEditModelAndView(Curricula curricula){
		
		ModelAndView result;
		
		result = createEditModelAndView(curricula,null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Curricula curricula, String message){
		ModelAndView result;
		
		Collection<Endorser> endorsers = new ArrayList<Endorser>();
		
		endorsers = endorserService.findAll();
		
		result = new ModelAndView("curricula/edit");
		result.addObject("endorsers", endorsers);
		result.addObject("curricula", curricula);
		result.addObject("message", message);

		return result;
		
	}

}
