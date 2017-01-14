package controllers.administrator;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CampaignService;
import services.FolderService;
import services.MessageService;
import services.MonthlyBillService;

import controllers.AbstractController;
import domain.Actor;
import domain.Campaign;
import domain.Folder;
import domain.MonthlyBill;

@Controller
@RequestMapping("/administrator/monthlybill")
public class AdministratorMonthlyBillController extends AbstractController{
	
	// Services -------------------------------------------
	
	@Autowired
	private MonthlyBillService monthlyBillService;
	
	@Autowired
	private CampaignService campaignService;
	
	@Autowired
	private MessageService	messageService;
	
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private ActorService actorService;
	
	// Constructor ----------------------------------------
	
	public AdministratorMonthlyBillController(){
		super();
	}
	
	// Listing methods ----------------------------------------------------
	
	@RequestMapping(value = "/listall", method = RequestMethod.GET)
	public ModelAndView listall(){
		ModelAndView result;
		Collection<MonthlyBill> monthlyBills;
		
		monthlyBills = monthlyBillService.findAll();
		result = new ModelAndView("monthlyBills/listall");
		result.addObject("monthlyBill", monthlyBills);
		
		return result;
	}
	
	@RequestMapping(value = "/listunpaid", method = RequestMethod.GET)
	public ModelAndView listunpaid(){
		ModelAndView result;
		Collection<MonthlyBill> monthlyBills;
		Date moment = new Date(System.currentTimeMillis()-1);
		
		monthlyBills = monthlyBillService.findUnpaidMonthlyBill();
		result = new ModelAndView("monthlyBills/listunpaid");
		result.addObject("monthlyBill", monthlyBills);
		result.addObject("current", moment);
		
		return result;
	}
	
	// View method -------------------------------------------------------------------
	
		@RequestMapping(value="/view", method=RequestMethod.GET)
		public ModelAndView display(@RequestParam int monthlyBillId) {
				ModelAndView result;

				MonthlyBill monthlyBill = monthlyBillService.findOne(monthlyBillId);		
				
				result=new ModelAndView("monthlyBills/view");
				result.addObject("monthlyBillId", monthlyBill.getId());
				result.addObject("monthlyBill", monthlyBill);
				
				return result;
			}
	
	// Create methods -----------------------------------------------------
	
	@RequestMapping(value="/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int campaignId){
		ModelAndView result;
		Campaign campaign = campaignService.findOne(campaignId);
		
		try{
			monthlyBillService.generateMonthlyBill(campaign);
			result = new ModelAndView("redirect:/administrator/monthlybill/listall.do");
		}catch(Throwable oops ){
			result = createEditModelAndView(campaign, "monthlyBill.pay.error");
		}
		return result;
	}
	
	@RequestMapping(value="/sendbulk", method = RequestMethod.GET)
	public ModelAndView send(@RequestParam int monthlyBillId){
		ModelAndView result;
		MonthlyBill monthlyBill = monthlyBillService.findOne(monthlyBillId);
		
		try{
			Actor actor = actorService.findByPrincipal();
			Folder folderToReturn = folderService.findOutboxActor(actor);
			messageService.bulkMessage(monthlyBillId);
			result = new ModelAndView("redirect:/message/actor/list.do?folderId="+folderToReturn.getId());
		}catch(Throwable oops ){
			result = sendBulkModelAndView(monthlyBill, "monthlyBill.pay.error");
		}
		
		return result;
	}
	
	// Ancillary methods -----------------------------------------------
				
	protected ModelAndView createEditModelAndView(Campaign campaign, String message){
		ModelAndView result;
		Collection<Campaign> campaigns = campaignService.findAll();
					
		result = new ModelAndView("campaign/list");
		result.addObject("campaign", campaigns);
		result.addObject("message", message);
					
		return result;
	}
	
	protected ModelAndView sendBulkModelAndView(MonthlyBill monthlyBill, String message){
		ModelAndView result;
		Collection<MonthlyBill> monthlyBills = monthlyBillService.findUnpaidMonthlyBill();
					
		result = new ModelAndView("monthlyBills/listunpaid");
		result.addObject("monthlyBill", monthlyBills);
		result.addObject("message", message);
					
		return result;
	}
	

}
