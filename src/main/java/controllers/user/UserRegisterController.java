package controllers.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.UserService;


import controllers.AbstractController;
import domain.User;

@Controller
@RequestMapping("/user")
public class UserRegisterController extends AbstractController{
	
	//Services--------------------------------------------------
	
	@Autowired
	private UserService userService;
	
	//Constructor-----------------------------------------------
	
	public UserRegisterController(){
		super();
	}
	
	//Register--------------------------------------------------
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		User user = new User();
			
		user = userService.create();
			
		result= new ModelAndView("user/register");
		result.addObject("user",user);

		return result;
		}
		
	@RequestMapping(value="/register", method=RequestMethod.POST, params="save")
	public ModelAndView save(@ModelAttribute("user") @Valid User user, BindingResult binding) {
			
		ModelAndView result;
		if(binding.hasErrors()){
			result=createEditModelAndView(user);
		}else{
			try{
				userService.save(user);
				result = new ModelAndView("redirect:/security/login.do");
			}catch (Throwable oops) {
				result = createEditModelAndView(user,"user.commit.error");
			}
		}
		return result;
	}
		
	// Ancillary methods ----------------------------------------------------------------
		
	protected ModelAndView createEditModelAndView(User user){
		ModelAndView result;
		result=createEditModelAndView(user,null);
		return result;
	}
	
	protected ModelAndView createEditModelAndView(User user, String message){
		ModelAndView result;
			
		result=new ModelAndView("user/register");
		result.addObject("user",user);
		result.addObject("message",message);
		return result;
	}
}
