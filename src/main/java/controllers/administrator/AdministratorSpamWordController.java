package controllers.administrator;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SpamWordService;

import controllers.AbstractController;
import domain.SpamWord;

@Controller
@RequestMapping("/administrator/spamWord")
public class AdministratorSpamWordController extends AbstractController {
	
	@Autowired
	private SpamWordService spamWordService;
	
	public AdministratorSpamWordController(){
		super();
	}
	
	// Listing -----------------------------------
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		Collection<SpamWord>spamWords;
		spamWords = spamWordService.findAll();
		SpamWord spamWord = spamWordService.create();
		//pamWord.setName("");
		result= new ModelAndView("spamWord/list");
		result.addObject("spamWords",spamWords);
		result.addObject("spamWord",spamWord);
		result.addObject("requestURI", "administrator/spamWord/list.do");
		
		return result;
	}

	//SpamWord
	@RequestMapping(value = "/registerSpamWord", method = RequestMethod.GET)
	public ModelAndView registerSpamWord(@RequestParam String spamWordKey){
		ModelAndView result=list();
		Boolean ver = true;
		try {			
			
			Collection<SpamWord> spamWords = spamWordService.findAll();
			
			for(SpamWord s : spamWords){
				if(s.getName().compareTo(spamWordKey)==0){
					ver=false;
				}
			}
			
			if(!ver){
				result.addObject("message", "masterClass.commit.error");	
			}else{
				spamWordService.registerSpamWord(spamWordKey);
				result.addObject("spamWords",spamWordService.findAll());
			}
			//result.addObject("message", "masterClass.commit.ok");			
		} catch (Throwable oops) {			
			result.addObject("message", "masterClass.commit.error");			
		}
		
		return result;
	}
	
	@RequestMapping(value = "/deleteSpamWord", method = RequestMethod.GET)
	public ModelAndView deleteSpamWord(@RequestParam String spamWordKey){
		
		ModelAndView result;
		
		try{
			spamWordService.deleteSpamWord(spamWordKey);
			result = list();
		}catch(Throwable oops){
			result = list();
			result.addObject("message", "masterClass.commit.error");		
		}
	return result;	
	}
	
	

}
