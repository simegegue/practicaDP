package controllers.nutritionist;

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

import services.IngredientService;
import services.PropertyService;

import controllers.AbstractController;
import domain.Ingredient;
import domain.Property;

@Controller
@RequestMapping("/nutritionist/ingredient")
public class NutritionistIngredientController extends AbstractController{
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private PropertyService propertyService;
	
	public NutritionistIngredientController(){
		super();
	}
	
	//Listing--------------------------
	
		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list(){
			
			ModelAndView result;
			Collection<Ingredient> ingredients;
		
			ingredients = ingredientService.findAll();
			
			result = new ModelAndView("ingredient/list");
			result.addObject("ingredients", ingredients);
			result.addObject("requestURI", "nutritionist/ingredient/list.do");
			
			return result;
		}
		

		//Creation-------------------------
		
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create(){
		
			ModelAndView result;
			Ingredient ingredient;

			ingredient = ingredientService.create();
			result = createEditModelAndView(ingredient);

			return result;
			
		}
		
		//Edition--------------------------
		
		@RequestMapping(value = "/editP", method = RequestMethod.GET)
		public ModelAndView editP(@RequestParam int ingredientId){
			
			ModelAndView result;
			Ingredient ingredient;
			Collection<Property> cp;
			
			ingredient = ingredientService.findOne(ingredientId);
			cp = propertyService.findAll();
			
			
			Assert.notNull(ingredient);
			result = new ModelAndView("ingredient/editP");
			result.addObject("ingredient", ingredient);
			result.addObject("properties", cp);
			return result;
			
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int ingredientId){
			
			ModelAndView result;
			Ingredient ingredient;
			
			ingredient = ingredientService.findOne(ingredientId);
			Assert.notNull(ingredient);
			result = createEditModelAndView(ingredient);
			
			return result;
			
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Ingredient ingredient, BindingResult binding){
			
			ModelAndView result;
			
			if(binding.hasErrors()){
				result = createEditModelAndView(ingredient);
			}else{
				try{
					ingredientService.save(ingredient);
					result = list();
				}catch(Throwable oops){
					result = createEditModelAndView(ingredient, "ingredient.commit.error");
				}
			}
			return result;
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(Ingredient ingredient, BindingResult binding){
			
			ModelAndView result;
			
			try{
				ingredientService.delete(ingredient);
				result = list();
			}catch(Throwable oops){
				result = createEditModelAndView(ingredient, "ingredient.commit.error");
			}
		return result;	
		}
			
		//Ancillary Methods---------------------------
		
		protected ModelAndView createEditModelAndView(Ingredient ingredient){
			
			ModelAndView result;
			
			result = createEditModelAndView(ingredient,null);
			
			return result;
		}
		
		protected ModelAndView createEditModelAndView(Ingredient ingredient, String message){
			ModelAndView result;
			
			result = new ModelAndView("ingredient/edit");
			result.addObject("ingredient", ingredient);
			
			result.addObject("message", message);

			return result;
			
		}

}
