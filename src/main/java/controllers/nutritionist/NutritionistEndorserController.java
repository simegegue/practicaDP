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

import controllers.AbstractController;
import domain.Curricula;
import domain.Endorser;

@Controller
@RequestMapping("/nutritionist/endorser")
public class NutritionistEndorserController extends AbstractController{
	
	//Services
	
	@Autowired
	private EndorserService endorserService;
	
	@Autowired
	private CurriculaService curriculaService;
	
	//Constructor
	
	public NutritionistEndorserController(){
		super();
	}
	

	//Listing--------------------------
	
		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list(){
			
			ModelAndView result;
			Collection<Endorser> endorsers;
		
			endorsers = endorserService.findByPrincipal();
			
			result = new ModelAndView("endorser/list");
			result.addObject("endorsers", endorsers);
			result.addObject("requestURI", "nutritionist/endorser/list.do");
			
			return result;
		}

		//Creation-------------------------
		
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create(){
		
			ModelAndView result;
			Endorser endorser;
			endorser = endorserService.create();
			result = createEditModelAndView(endorser);

			return result;
			
		}
		
		//Edition--------------------------
		
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int endorserId){
			
			ModelAndView result;
			Endorser endorser;
			
			endorser = endorserService.findOne(endorserId);
			Assert.notNull(endorser);
			result = createEditModelAndView(endorser);
			
			return result;
			
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Endorser endorser, BindingResult binding){
			
			ModelAndView result;
			
			if(binding.hasErrors()){
				result = createEditModelAndView(endorser);
			}else{
				try{
					
					Curricula curricula = curriculaService.findByPrincipal();
					curricula = curriculaService.save(curricula);
					endorser.setCurricula(curricula);
					endorser = endorserService.save(endorser);
					curriculaService.save(curricula);

					result = list();
				}catch(Throwable oops){
					result = createEditModelAndView(endorser, "curricula.commit.error");
				}
			}
			return result;
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(Endorser endorser, BindingResult binding){
			
			ModelAndView result;
			
			try{
				endorserService.delete(endorser);
				result = list();
			}catch(Throwable oops){
				result = createEditModelAndView(endorser, "endorser.commit.error");
			}
		return result;	
		}
			
		//Ancillary Methods---------------------------
		
		protected ModelAndView createEditModelAndView(Endorser endorser){
			
			ModelAndView result;
			
			result = createEditModelAndView(endorser,null);
			
			return result;
		}
		
		protected ModelAndView createEditModelAndView(Endorser endorser, String message){
			ModelAndView result;
			
			Collection<Curricula> curriculas = new ArrayList<Curricula>();
			Curricula curricula= curriculaService.findByPrincipal();
			curriculas.add(curricula);
			
			result = new ModelAndView("endorser/edit");
			result.addObject("curriculas", curriculas);
			result.addObject("endorser", endorser);
			result.addObject("message", message);

			return result;
			
		}
}
