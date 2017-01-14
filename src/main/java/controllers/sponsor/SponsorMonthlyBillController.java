package controllers.sponsor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MonthlyBillService;

import controllers.AbstractController;
import domain.MonthlyBill;

@Controller
@RequestMapping("/sponsor/monthlybill")
public class SponsorMonthlyBillController extends AbstractController{
	
	// Services --------------------------------------------------------
	
	@Autowired
	private MonthlyBillService monthlyBillService;
	
	// Constructor -----------------------------------------------------
	
	public SponsorMonthlyBillController(){
		super();
	}
	
	// Listing ---------------------------------------------------------
	
	@RequestMapping(value="/listall", method = RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		
		Collection<MonthlyBill> monthlyBills = monthlyBillService.findAllMonthlyBillBySponsor();
		
		result = new ModelAndView("monthlyBills/listall");
		result.addObject("monthlyBill", monthlyBills);
		
		return result;
	}
	
	@RequestMapping(value = "/listunpaid", method = RequestMethod.GET)
	public ModelAndView listUnpaid(){
		ModelAndView result;
		
		Collection<MonthlyBill> monthlyBills = monthlyBillService.findUnpaidMonthlyBillBySponsor();
		result = new ModelAndView("monthlyBills/listunpaid");
		result.addObject("monthlyBill", monthlyBills);
		
		return result;
	}
	
	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public ModelAndView pay(@RequestParam int monthlyBillId){
		ModelAndView result;
		MonthlyBill monthlyBill = monthlyBillService.findOne(monthlyBillId);
		
		try{
			monthlyBillService.payMonthlyBill(monthlyBill);
			result = new ModelAndView("redirect:/sponsor/monthlybill/listall.do");
		}catch(Throwable oops){
			result = payModelAndView(monthlyBill, "monthlyBill.pay.error");
		}
		
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
	
	// Ancillary methods -------------------------------------------------------------
	
	protected ModelAndView payModelAndView(MonthlyBill monthlyBill, String message){
		ModelAndView result;
		
		Collection<MonthlyBill> monthlyBills = monthlyBillService.findUnpaidMonthlyBillBySponsor();
		
		result = new ModelAndView("monthlybill/listunpaid");
		result.addObject("monthlyBill", monthlyBills);
		result.addObject("message", message);
		
		return result;
	}

}
