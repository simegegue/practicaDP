package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Folder;
import domain.MasterClass;
import domain.Message;
import domain.MonthlyBill;
import domain.SpamWord;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MessageRepository messageRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private MasterClassService masterClassService;
	
	@Autowired
	private SpamWordService spamWordService;
	
	@Autowired
	private MonthlyBillService monthlyBillService;
	
	// Constructors -----------------------------------------------------------
	
	public MessageService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Message create() {
		Message message = new Message();

		Actor sender = actorService.findByPrincipal();

		message.setSender(sender);	
		message.setMoment(new Date());

		Folder folder = folderService.findOutboxActor(sender);

		message.setFolder(folder);

		return message;	
	}

	public Collection<Message> findAll() {
		Collection<Message> result;

		result = messageRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Message findOne(int messageId) {
		Message result;

		result = messageRepository.findOne(messageId);
		Assert.notNull(result);

		return result;
	}

	public void save(Message message) {
		checkPrincipal(message);

		Assert.isTrue(!message.getRecipient().equals(message.getSender()));

		Date d = new Date(System.currentTimeMillis() - 1000);
		

		message.setMoment(d);		
		
		messageRepository.save(message);

		if(message.getId() == 0){

			Message messageToSend = message;
			Actor recipient = message.getRecipient();
			Folder folder = folderService.findInboxActor(recipient);
			Assert.notNull(folder);	

			messageToSend.setFolder(folder);
			
			if(messageHaveSpam(message)){
				message.setSpam(true);
				message.setFolder(folderService.findSpamboxActor(recipient));
			}
			messageRepository.save(messageToSend);

		}
	}

	public void delete(Message message) {
		Assert.notNull(message);	

		Folder f;
		String n;
		String t;
		
		f=folderService.findTrashboxActor();
		Assert.notNull(f);	
		n=message.getFolder().getName();
		t=f.getName();
		
		checkPrincipal(message);
		
		if(n.equals(t)){
			messageRepository.delete(message);

		
		}else{
			changeFolder(message, f);

		}
	}
	
	public Message create2(){
		return new Message();
	}
	
	public void save2(Message m){
		messageRepository.save(m);
	}
	// Other business methods -------------------------------------------------
	public Collection<Message> getByFolder(int folderId){
		
		Folder folder = folderService.findOne(folderId);
		Assert.notNull(folder);
		
		return folder.getMessages();
	}
	
	public Collection<Message> findMessagesByActorId(){
		
		Actor actor = actorService.findByPrincipal();
		Assert.notNull(actor);
		
		Collection<Message> all = messageRepository.allMessagesByActorId(actor.getId());
		Assert.notNull(all);
		
		return all;
	}
	
	public void changeFolder(Message message, Folder folder){
		
		Assert.notNull(message);
		Assert.notNull(folder);
		
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		
		Assert.isTrue(message.getSender().getUserAccount().equals(userAccount) || message.getRecipient().getUserAccount().equals(userAccount));
		Assert.isTrue(folder.getActor().getUserAccount().equals(userAccount));
		
		message.setFolder(folder);
		
		save(message);
	}
	
	public void checkPrincipal(Message message){
		Assert.notNull(message);
		
		UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(message.getSender().getUserAccount().equals(userAccount) || message.getRecipient().getUserAccount().equals(userAccount) || userAccount.getAuthorities().contains(au));
	}
	
	public void bulkMessage(int monthlyBillId){
		Actor actor = actorService.findByPrincipal();
		Assert.notNull(actor);
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(au));
		
		MonthlyBill monthlyBill = monthlyBillService.findOne(monthlyBillId);
		Assert.notNull(monthlyBill);
		String description = "The monthly bill is unpaid" + monthlyBill.getDescription();
		String subject = "Important message";
		Date moment = new Date(System.currentTimeMillis()-1);
		Actor admin = actorService.findByPrincipal();
		
		Message message = create2();
		Message message2 = create2();
		
		message.setBody(description);
		message.setMoment(moment);
		message.setRecipient(monthlyBill.getSponsor());
		message.setSender(admin);
		message.setFolder(folderService.findInboxActor(monthlyBill.getSponsor()));
		message.setSpam(false);
		message.setPriority("HIGH");
		message.setSubject(subject);
		
		message2.setBody(description);
		message2.setMoment(moment);
		message2.setRecipient(monthlyBill.getSponsor());
		message2.setSender(admin);
		message2.setFolder(folderService.findOutboxActor(admin));
		message2.setSpam(false);
		message2.setPriority("HIGH");
		message2.setSubject(subject);
		
		save2(message);
		save2(message2);
		
	}

	public void messageMasterClassDelete(MasterClass masterClass){
		Collection<Actor> signed = masterClassService.actorsOfMasterClass(masterClass);
		Date moment = new Date(System.currentTimeMillis()-1);
		String body = "Le informamos que la " + masterClass.getTitle() + ", ha sido eliminada del sistema. \nUn saludo, la dirección";
		String subject = "Información sobre " + masterClass.getTitle();
		String priority = "HIGH";
		boolean spam = false;
		Actor admin = actorService.findActorByUsername("admin3");
		
		
		for(Actor a : signed){
			Message m = create2();
			Message m2 = create2();
			m.setBody(body);
			m.setMoment(moment);
			m.setPriority(priority);
			m.setSubject(subject);
			m.setSpam(spam);
			m.setRecipient(a);
			m.setSender(admin);
			m.setFolder(folderService.findInboxActor(a));
			Collection<Message> recived = a.getReceivedMessages();
			
			save2(m);
			recived.add(m);
			a.setReceivedMessages(recived);
			actorService.save(a);
			
			m2.setBody(body);
			m2.setMoment(moment);
			m2.setPriority(priority);
			m2.setSubject(subject);
			m2.setSpam(spam);
			m2.setRecipient(a);
			m2.setSender(admin);
			m2.setFolder(folderService.findOutboxActor(admin));
			
			save2(m2);
		}
		
		actorService.save(admin);
	}

	public boolean messageHaveSpam(Message message){
		boolean result = false;
		Collection<SpamWord> spamWords = spamWordService.findAll();
		for(SpamWord sw : spamWords){
			if(message.getBody().contains(sw.getName())){
				result = true;
				break;
			}
			if(message.getSubject().contains(sw.getName())){
				result = true;
				break;
			}
		}
		return result;
	}
}
