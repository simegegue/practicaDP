package controllers;

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

import domain.LearningMaterial;
import domain.MasterClass;
import domain.Presentation;
import domain.Text;
import domain.Video;
import services.LearningMaterialService;
import services.MasterClassService;
import services.PresentationService;
import services.TextService;
import services.VideoService;

@Controller
@RequestMapping("/learningMaterial")
public class LearningMaterialController extends AbstractController{
	
	//Services-------------------------
	
	@Autowired
	private LearningMaterialService learningMaterialService;
	
	@Autowired
	private MasterClassService masterClassService;
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private TextService textService;
	
	@Autowired
	private PresentationService presentationService;
		
	//Constructor----------------------
		
	public LearningMaterialController(){
			super();
	}
	

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/browse", method = RequestMethod.GET)
	public ModelAndView browse(@RequestParam int masterClassId) {
		ModelAndView result;
		
		MasterClass mc = masterClassService.findOne(masterClassId);
		Collection<LearningMaterial> learningMaterials = mc.getLearningMaterials();
		
		Collection<Video> videos = learningMaterialService.findVideos(learningMaterials);
		Collection<Text> texts = learningMaterialService.findTexts(learningMaterials);
		Collection<Presentation> presentations = learningMaterialService.findPresentations(learningMaterials);

		result = new ModelAndView("learningMaterial/browse");
		result.addObject("learningMaterials", learningMaterials);
		result.addObject("videos", videos);
		result.addObject("texts", texts);
		result.addObject("presentations", presentations);
		
		return result;
	}
	// Create ----------------------------------------------------------------
	
	@RequestMapping(value = "/createVideo", method = RequestMethod.GET)
	public ModelAndView createVideo(int masterClassId){
	
		ModelAndView result;
		Video video;

		video = videoService.create();
		video.setMasterClass(masterClassService.findOne(masterClassId));
		result = createEditModelAndView(video);

		return result;
		
	}
	
	// Create ----------------------------------------------------------------
	
		@RequestMapping(value = "/createText", method = RequestMethod.GET)
		public ModelAndView createText(int masterClassId){
		
			ModelAndView result;
			Text text;

			text = textService.create();
			text.setMasterClass(masterClassService.findOne(masterClassId));
			result = createEditModelAndView(text);

			return result;
			
		}
		
	// Create ----------------------------------------------------------------
		
		@RequestMapping(value = "/createPresentation", method = RequestMethod.GET)
		public ModelAndView createPresentation(int masterClassId){
		
			ModelAndView result;
			Presentation presentation;

			presentation = presentationService.create();
			presentation.setMasterClass(masterClassService.findOne(masterClassId));
			result = createEditModelAndView(presentation);

			return result;
			
		}
		
		//Edition--------------------------
		
		@RequestMapping(value = "/editVideo", method = RequestMethod.GET)
		public ModelAndView editVideo(@RequestParam int videoId){
			
			ModelAndView result;
			Video video;
			
			video = videoService.findOne(videoId);
			Assert.notNull(video);
			result = createEditModelAndView(video);
			
			return result;
			
		}
		
		@RequestMapping(value = "/editVideo", method = RequestMethod.POST, params = "save")
		public ModelAndView saveVideo(@Valid Video video, BindingResult binding){
			
			ModelAndView result;
			
			if(binding.hasErrors()){
				result = createEditModelAndView(video);
			}else{
				try{
					videoService.save2(video);
					result = new ModelAndView("redirect:/cook/masterClass/browse.do");
				}catch(Throwable oops){
					result = createEditModelAndView(video, "learningMaterial.commit.error");
				}
			}
			return result;
		}
		
		@RequestMapping(value = "/editVideo", method = RequestMethod.POST, params = "delete")
		public ModelAndView deleteVideo(Video video, BindingResult binding){
			
			ModelAndView result;
			
			try{
				videoService.delete(video);
				result = new ModelAndView("redirect:/cook/masterClass/browse.do");
			}catch(Throwable oops){
				result = createEditModelAndView(video, "learningMaterial.commit.error");
			}
		return result;	
		}
		

		//Edition--------------------------
		
		@RequestMapping(value = "/editText", method = RequestMethod.GET)
		public ModelAndView editText(@RequestParam int textId){
			
			ModelAndView result;
			Text text;
			
			text = textService.findOne(textId);
			Assert.notNull(text);
			result = createEditModelAndView(text);
			
			return result;
			
		}
		
		@RequestMapping(value = "/editText", method = RequestMethod.POST, params = "save")
		public ModelAndView saveText(@Valid Text text, BindingResult binding){
			
			ModelAndView result;
			
			if(binding.hasErrors()){
				result = createEditModelAndView(text);
			}else{
				try{
					textService.save(text);
					result = new ModelAndView("redirect:/cook/masterClass/browse.do");
				}catch(Throwable oops){
					result = createEditModelAndView(text, "learningMaterial.commit.error");
				}
			}
			return result;
		}
		
		@RequestMapping(value = "/editText", method = RequestMethod.POST, params = "delete")
		public ModelAndView deleteText(Text text, BindingResult binding){
			
			ModelAndView result;
			
			try{
				textService.delete(text);
				result = new ModelAndView("redirect:/cook/masterClass/browse.do");
			}catch(Throwable oops){
				result = createEditModelAndView(text, "learningMaterial.commit.error");
			}
		return result;	
		}
		

		//Edition--------------------------
		
		@RequestMapping(value = "/editPresentation", method = RequestMethod.GET)
		public ModelAndView editPresentation(@RequestParam int presentationId){
			
			ModelAndView result;
			Presentation presentation;
			
			presentation = presentationService.findOne(presentationId);
			Assert.notNull(presentation);
			result = createEditModelAndView(presentation);
			
			return result;
			
		}
		
		@RequestMapping(value = "/editPresentation", method = RequestMethod.POST, params = "save")
		public ModelAndView savePresentation(@Valid Presentation presentation, BindingResult binding){
			
			ModelAndView result;
			
			if(binding.hasErrors()){
				result = createEditModelAndView(presentation);
			}else{
				try{
					presentationService.save(presentation);
					result = new ModelAndView("redirect:/cook/masterClass/browse.do");
				}catch(Throwable oops){
					result = createEditModelAndView(presentation, "learningMaterial.commit.error");
				}
			}
			return result;
		}
		
		@RequestMapping(value = "/editPresentation", method = RequestMethod.POST, params = "delete")
		public ModelAndView deletePresentation(Presentation presentation, BindingResult binding){
			
			ModelAndView result;
			
			try{
				presentationService.delete(presentation);
				result = new ModelAndView("redirect:/cook/masterClass/browse.do");
			}catch(Throwable oops){
				result = createEditModelAndView(presentation, "learningMaterial.commit.error");
			}
		return result;	
		}


	//Ancillary Methods---------------------------
	
	protected ModelAndView createEditModelAndView(Video video){
		
		ModelAndView result;
		
		result = createEditModelAndView(video,null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Video video, String message){
		ModelAndView result;
		
		result = new ModelAndView("learningMaterial/editVideo");
		result.addObject("video", video);
		
		result.addObject("message", message);

		return result;
		
	}
	
	protected ModelAndView createEditModelAndView(Text text){
		
		ModelAndView result;
		
		result = createEditModelAndView(text,null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Text text, String message){
		ModelAndView result;
		
		result = new ModelAndView("learningMaterial/editText");
		result.addObject("text", text);
		
		result.addObject("message", message);

		return result;
		
	}
	
	protected ModelAndView createEditModelAndView(Presentation presentation){
		
		ModelAndView result;
		
		result = createEditModelAndView(presentation,null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Presentation presentation, String message){
		ModelAndView result;
		
		result = new ModelAndView("learningMaterial/editPresentation");
		result.addObject("presentation", presentation);
		
		result.addObject("message", message);

		return result;
		
	}

}
