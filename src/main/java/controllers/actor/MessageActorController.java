package controllers.actor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import services.MessageService;

import controllers.AbstractController;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Controller
@RequestMapping("/message/actor")
public class MessageActorController extends AbstractController{
	
	// Services ---------------------------------------------
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private FolderService folderService;
	
	// Constructor ------------------------------------------------------
	
	public MessageActorController(){
		super();
	}
	
	// Listing methods --------------------------------------------------
	
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int folderId){
		ModelAndView result;
		Folder folder = folderService.findOne(folderId);
		
		result = new ModelAndView("message/actor/list");
		result.addObject("folder",folder);
		
		return result;
	}
	
	@RequestMapping(value="/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int messageId){
		ModelAndView result;
		Message message;
		
		message = messageService.findOne(messageId);
		result = new ModelAndView("message/actor/display");
		result.addObject("messageDisplay", message);
		
		return result;
	}
	
	// Creation and edition methods -------------------------------------
	
	@RequestMapping(value = "/display", method = RequestMethod.POST, params="delete")
	public ModelAndView deleteMessage(Message messageDisplay) {
		ModelAndView result;
		
			try {

				messageService.delete(messageDisplay);
				result = new ModelAndView("redirect:list.do?folderId="+messageDisplay.getFolder().getId());
			} catch (Throwable oops) {
					result = createNewModelAndView(messageDisplay,"msg.commit.error");
				
			}
	
		return result;
	}
	
	@RequestMapping(value="/send", method = RequestMethod.GET)
	public ModelAndView create(){
		
		ModelAndView result;
		
		Message message;
		message=messageService.create();
		
		result= createNewModelAndView(message);
		
		return result;
		
	}
	
	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "save")
	public ModelAndView send(@ModelAttribute("m") @Valid Message m, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createNewModelAndView(m);
		} else {
			try {				
				Folder folderToReturn = m.getFolder();
				messageService.save(m);
				result = new ModelAndView("redirect:list.do?folderId="+folderToReturn.getId());
			} catch (Throwable oops) {

				result= createNewModelAndView(m,"msg.commit.error");
				
			}
		}
		return result;
	}

	
	@RequestMapping(value="/changefolder", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int messageId){
		
		ModelAndView result;
		
		Message message;
		message=messageService.findOne(messageId);
		Assert.notNull(message);
		result = new ModelAndView("message/actor/changefolder");
		
		Actor actor = actorService.findByPrincipal();
		Collection<Folder> folders = folderService.findFoldersByActor(actor);
		folders.remove(message.getFolder());
		result.addObject("folders",folders);
		result.addObject("message", null);
		result.addObject("m", message);
		
		return result;
		
	}

	@RequestMapping(value = "/changefolder", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@ModelAttribute("m") @Valid Message m, BindingResult binding, @RequestParam int messageId) {
		ModelAndView result;
		if (binding.hasErrors()) {
			Message originalMessage = messageService.findOne(messageId); 
			result = new ModelAndView("message/actor/changefolder");
			
			Actor actor = actorService.findByPrincipal();
			Collection<Folder> folders = folderService.findFoldersByActor(actor);
			folders.remove(originalMessage.getFolder());
			result.addObject("folders",folders);
			result.addObject("message", null);
			result.addObject("m", m);
		} else {
			try {
				messageService.save(m);
				result = new ModelAndView("redirect:list.do?folderId="+m.getFolder().getId());
			} catch (Throwable oops) {
				Message originalMessage = messageService.findOne(messageId); 
				result = new ModelAndView("message/actor/changefolder");
				
				Actor actor = actorService.findByPrincipal();
				Collection<Folder> folders = folderService.findFoldersByActor(actor);
				folders.remove(originalMessage.getFolder());
				result.addObject("folders",folders);
				result.addObject("message", null);
				result.addObject("m", "msg.commit.error");
			}
		}
		return result;
	}
	


	@RequestMapping(value="/reply", method = RequestMethod.GET)
	public ModelAndView reply(@RequestParam int messageId){
		
		ModelAndView result;
		
		Message message,aux;
		aux=messageService.findOne(messageId);
		message=messageService.create();
		Assert.notNull(aux);
		
		message.setRecipient(aux.getSender());
		message.setSubject("Reply to:\""+aux.getSubject()+"\"");
		message.setBody("\n-----------------\nSender: "+aux.getSender().getName()+"\n Recipient: "+aux.getRecipient().getName()+"\n Moment: "+aux.getMoment()+"\n Subject: "+aux.getSubject()+"\n Body: "+aux.getBody()+"\"\"");
		
		
		result= createReplyModelAndView(message);
		
		return result;
		
	}
	
	// Ancillary methods ------------------------------------------------
	
	protected ModelAndView createNewModelAndView(Message m){
		ModelAndView result;
		result = createNewModelAndView(m, null);
		return result;
	}
	
	protected ModelAndView createNewModelAndView(Message m, String message){
		ModelAndView result;
		
		result = new ModelAndView("message/actor/send");
		
		
		Actor actor = actorService.findByPrincipal();
		Collection<Actor> actors = actorService.findAll();
		actors.remove(actor);
		
		result.addObject("actors", actors);
		result.addObject("message", message);
		result.addObject("m", m);
		
		return result;	
	}
	
	protected ModelAndView createReplyModelAndView(Message m){
		ModelAndView result;
		result = createReplyModelAndView(m, null);
		return result;
	}
	
	protected ModelAndView createReplyModelAndView(Message m, String message){
		ModelAndView result;
		result = new ModelAndView("message/actor/reply");
		
		result.addObject("message", message);
		result.addObject("m", m);
		return result;
	}

}
