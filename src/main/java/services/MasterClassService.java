package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MasterClassRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Cook;
import domain.LearningMaterial;
import domain.MasterClass;

@Service
@Transactional
public class MasterClassService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MasterClassRepository masterClassRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private CookService cookService;
	
	@Autowired
	private LearningMaterialService learningMaterialService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------------
	
	public MasterClassService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public MasterClass create() {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("COOK");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		MasterClass result;
		result = new MasterClass();
		
		Cook cook;
		cook = cookService.findByPrincipal();
		
		result.setCook(cook);

		return result;
	}

	public Collection<MasterClass> findAll() {
		Collection<MasterClass> result;

		result = masterClassRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public MasterClass findOne(int masterClassId) {
		MasterClass result;

		result = masterClassRepository.findOne(masterClassId);
		Assert.notNull(result);

		return result;
	}
	
	public Collection<MasterClass> findByPrincipal() {
		Collection<MasterClass> result;
		UserAccount userAccount;
		
		userAccount = LoginService.getPrincipal();
		result = masterClassRepository.findByUserAccount(userAccount);		
		
		return result;
	}
	
	public MasterClass save(MasterClass masterClass) {
		
		Assert.notNull(masterClass);
		MasterClass result;
		result = masterClassRepository.save(masterClass);
		
		return result;
	}

	public MasterClass save2(MasterClass masterClass) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("COOK");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(masterClass);
		
		MasterClass result;

		result = masterClassRepository.save(masterClass);
		
		return result;
	}

	public void delete(MasterClass masterClass) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("COOK");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(masterClass);
		Assert.isTrue(masterClass.getId() != 0);
		//--------------------------
		messageService.messageMasterClassDelete(masterClass);
		//------------------------------
		Collection<LearningMaterial> learningMaterials = masterClass.getLearningMaterials();
		for(LearningMaterial lm : learningMaterials){
			learningMaterialService.delete(lm);
		}

		masterClassRepository.delete(masterClass);
	}
	
	

	// Other business methods -------------------------------------------------

	public Integer masterClassesPromoted(){
		Integer result;
		result = masterClassRepository.masterClassesPromoted();
		return result;
	}
	
	public Collection<Double> minMaxAvgMasterClasses(){
		Collection<Double> result = new ArrayList<Double>();
		
		Integer min = masterClassRepository.minMasterClasses();
		Double avg = masterClassRepository.avgMasterClasses();
		Integer max = masterClassRepository.maxMasterClasses();
		Double stdDev = masterClassRepository.stdDevMasterClasses();
			
		if(min == null || min == 0){
			result.add(0.0);
		}else{
			result.add(min*1.0);
		}
		if(max == null || max == 0){
			result.add(0.0);
		}else{
			result.add(max*1.0);
		}
		if(avg == null || avg == 0){
			result.add(0.0);
		}else{
			result.add(avg);
		}
		if(stdDev == null || stdDev == 0){
			result.add(0.0);
		}else{
			result.add(stdDev);
		}

		return result;
	}
	
	public Collection<Double> avgMasterClassesPromotedPerCook(){
		Collection<Double> result = new ArrayList<Double>();
		
		Double avgPro = masterClassRepository.avgMasterClassesPromotedPerCook();
		Double avgDemo = masterClassRepository.avgMasterClassesDemotedPerCook();
			
		if(avgPro == null || avgPro == 0){
			result.add(0.0);
		}else{
			result.add(avgPro);
		}
		if(avgDemo == null || avgDemo == 0){
			result.add(0.0);
		}else{
			result.add(avgDemo);
		}
		
		return result;
	}
	
	public void registerMC(int masterClassId) {
		UserAccount ua;
		Collection<String> uas;
		MasterClass mc;
		
		ua = LoginService.getPrincipal();
		mc = masterClassRepository.findOne(masterClassId);
		uas = mc.getRegistered();		
		if(!uas.contains(ua.getUsername())){
			uas.add(ua.getUsername());
		}else{
			
		}
		// Not necessary: customerService.save(customer);
		masterClassRepository.save(mc);
	}
	
	public void unregisterMC(int masterClassId) {
		UserAccount ua;
		Collection<String> uas;
		MasterClass mc;
		
		ua = LoginService.getPrincipal();
		mc = masterClassRepository.findOne(masterClassId);
		uas = mc.getRegistered();		
		if(uas.contains(ua.getUsername())){
			uas.remove(ua.getUsername());
		}else{
			
		}
		// Not necessary: customerService.save(customer);
		masterClassRepository.save(mc);
	}
	
	public void attendMasterClass(MasterClass masterClass){
		UserAccount ua = LoginService.getPrincipal();
		Collection<String> registered = new HashSet<String>();
		registered = masterClass.getRegistered();
		registered.add(ua.getUsername());
		masterClass.setRegistered(registered);
	}
	
	public void promoteDemoteMasterClass(MasterClass masterClass){
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		if(masterClass.getPromoted()){
			masterClass.setPromoted(false);
		}else{
			masterClass.setPromoted(true);
		}
	}
	
	public Collection<Actor> actorsOfMasterClass(MasterClass masterClass){
		Collection<Actor> result = new ArrayList<Actor>();
		
		for(String s : masterClass.getRegistered()){
			result.add(actorService.findActorByUsername(s));
		}
		
		return result;
	}

	public Collection<MasterClass> findMasterClassPromoted(){
		Collection<MasterClass> result = new ArrayList<MasterClass>();
		Collection<MasterClass> all = findAll();
		
		for(MasterClass mc : all){
			if(mc.getPromoted()){
				result.add(mc);
			}
		}
		
		return result;
	}
	
	public MasterClass showMasterClassPromoted(){
		MasterClass selected;
		Random rdn = new Random();
		List<MasterClass> mc = new ArrayList<MasterClass>();
		mc.addAll(findMasterClassPromoted());
		
		if(!mc.isEmpty()){
			selected = mc.get(rdn.nextInt(mc.size()-1));
		}else{
			selected = null;
		}
		
		return selected;
	}
}
