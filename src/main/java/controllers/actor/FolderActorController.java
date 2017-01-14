package controllers.actor;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;

import controllers.AbstractController;
import domain.Actor;
import domain.Folder;

@Controller
@RequestMapping("/folder/actor")
public class FolderActorController extends AbstractController{
	
	// Services -----------------------------------------------------------------
	
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private ActorService actorService;
	
	// Constructor --------------------------------------------------------------
	
	public FolderActorController(){
		super();
	}
	
	// Listing methods ----------------------------------------------------------
	
	@RequestMapping(value = "/listall", method = RequestMethod.GET)
	public ModelAndView list(){
		
		ModelAndView result;
		Collection<Folder> folders;
		Collection<Folder> foldersRemove = new ArrayList<Folder>();
		
		Actor actor = actorService.findByPrincipal();
		
		folders = folderService.findFoldersByActor(actor);
		
		folders.removeAll(foldersRemove);
		
		result = new ModelAndView("folder/actor/list");
		result.addObject("folders", folders);
		result.addObject("requestURI", "folder/actor/listall.do");
		
		return result;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int folderId){
		ModelAndView result;
		
		result = new ModelAndView("folder/actor/list");
		result.addObject("requestURI", "folder/actor/list.do");
		
		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(){
		
		ModelAndView result;
		
		Folder folder;
		folder = folderService.create();
		
		result = createEditModelAndView(folder, "create");
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int folderId){
		 ModelAndView result;
		 
		 Folder folder;
		 folder = folderService.findOne(folderId);
		 
		 result = createEditModelAndView(folder, "edit");
		 
		 return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Folder folder, BindingResult binding){
		
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(folder, "edit");
		}else{
			try{
				folderService.save(folder);
				result = new ModelAndView("redirect:listall.do");
			}catch(Throwable oops){
				result = createEditModelAndView(folder, "edit", "folder.commit.error");
			}
		}
		
		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteFolder(Folder folder){
		ModelAndView result;
		
		try{
			folderService.delete(folder);
			result = new ModelAndView("redirect:listall.do");
		}catch(Throwable oops){
			result = createEditModelAndView(folder, "edit", "folder.commit.error");
		}
		
		return result;
	}
	
	// Ancillary methods ------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Folder folder, String selectView){
		ModelAndView result;
		
		result = createEditModelAndView(folder, selectView, null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Folder folder, String selectView, String message){
		
		ModelAndView result;
		
		Actor actor = actorService.findByPrincipal();
		Collection<Folder> folders = folderService.findFoldersByActor(actor);
		folders.remove(folder);
		
		result = new ModelAndView("folder/actor/" + selectView);
		result.addObject("folder", folder);
		result.addObject("folders", folders);
		result.addObject("message", message);
		
		return result;
	}

}
