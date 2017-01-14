package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import domain.Actor;

@Controller
@RequestMapping("/actor")
public class ActorRegisterController extends AbstractController{
	

	//Services-------------------------

	@Autowired
	private ActorService actorService;
	
	//Constructor----------------------
	
	public ActorRegisterController(){
		super();
	}
	
	//Listing--------------------------
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(){
		
		ModelAndView result;
		
		Actor actor;
		actor = actorService.findByPrincipal();
		
		result = new ModelAndView("actor/register");
		result.addObject("actor", actor);
		result.addObject("requestURI", "actor/register.do");
		
		return result;
	}

}
