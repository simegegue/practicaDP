package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Nutritionist;


import services.NutritionistService;

@Controller
@RequestMapping(value="/nutritionist")
public class NutritionistController extends AbstractController{
	//Services--------------------------------------------------
	@Autowired
	private NutritionistService nutritionistService;
	//Constructor-------------------------------------------------
	public NutritionistController(){
		super();
	}
	//Display
	@RequestMapping(value="/displayProfile", method=RequestMethod.GET)
	public ModelAndView displayProfile(@RequestParam int userId) {
			ModelAndView result;
			Nutritionist n=nutritionistService.findOne(userId);
			result=new ModelAndView("nutritionist/display");
			result.addObject("nutritionist", n);
			return result;
		}
}
