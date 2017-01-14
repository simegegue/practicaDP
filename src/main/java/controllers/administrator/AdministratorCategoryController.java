package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CategoryService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Category;


@Controller
@RequestMapping(value="/administrator/category")
public class AdministratorCategoryController extends AbstractController{
	//Services----------------------------------------------------------
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private AdministratorService administratorService;
	
	//Contructor--------------------------------------------------------
	
	public AdministratorCategoryController(){
		super();
	}
	//List--------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(){
		
		ModelAndView result;
		Collection<Category> categories;
		Map<Category, Collection<String>> nomCategories = new HashMap<Category, Collection<String>>();
		categories = categoryService.findAll();
		Collection<String>names=new ArrayList<String>();
		for(Category r : categories){
			names=new ArrayList<String>();
			for(Category c:r.getSubCategories()){
				names.add(c.getName());
			}
			nomCategories.put(r,names);
		}
		result = new ModelAndView("category/list");
		
		result.addObject("categories", categories);
		result.addObject("nomCategories", nomCategories);
		result.addObject("requestURI", "administrator/category/list.do");
		
		return result;
	}
	//Creation-------------------------
	
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create(){
		
			ModelAndView result;
			Category category;
			Administrator administrator=administratorService.findByPrincipal();
			
			category = categoryService.create();
			category.setAdministrator(administrator);
			result = new ModelAndView("category/edit");
			Collection<Category>superCategory=categoryService.findAll();
			result.addObject("category", category);
			result.addObject("superCategory", superCategory);
			return result;
			
		}
		
		//Edition--------------------------
		
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int categoryId){
			
			ModelAndView result;
			Category category;
			
			category = categoryService.findOne(categoryId);
			Assert.notNull(category);
			Collection<Category>categories=categoryService.findAll();
			categories.remove(category);
			result = new ModelAndView("category/edit");
			result.addObject("category", category);
			result.addObject("superCategory", categories);
			return result;
			
		}
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Category category, BindingResult binding){
			
			ModelAndView result;
			
			if(binding.hasErrors()){
				result = createEditModelAndView(category);
			}else{
				try{
					categoryService.save(category);
					result = list();
				}catch(Throwable oops){
					result = createEditModelAndView(category, "category.commit.error");
				}
			}
			return result;
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(Category category, BindingResult binding){
			
			ModelAndView result;
			
			try{
				categoryService.delete(category);
				result = list();
			}catch(Throwable oops){
				result = createEditModelAndView(category, "category.commit.error");
			}
		return result;	
		}
			
		//Ancillary Methods---------------------------
		
		protected ModelAndView createEditModelAndView(Category category){
			
			ModelAndView result;
			
			result = createEditModelAndView(category,null);
			
			return result;
		}
		
		protected ModelAndView createEditModelAndView(Category category, String message){
			ModelAndView result;
			
			result = new ModelAndView("category/edit");
			result.addObject("category", category);
			
			result.addObject("message", message);

			return result;
			
		}
}
