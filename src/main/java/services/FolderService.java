package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class FolderService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FolderRepository folderRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------------
	
	public FolderService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Folder create() {
		Folder folder = new Folder();
		Actor actor;
		
		actor=actorService.findByPrincipal();
		
		
		folder.setActor(actor);
		folder.setMessages(new ArrayList<Message>());
		folder.setPredefined(false);
		return folder;
	}

	public Collection<Folder> findAll() {
		Collection<Folder> result;

		result = folderRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Folder findOne(int folderId) {
		Folder result;

		result = folderRepository.findOne(folderId);
		Assert.notNull(result);

		return result;
	}

	public void save(Folder folder) {
		Assert.notNull(folder);
		
		folderRepository.save(folder);
		
	}

	public void delete(Folder folder) {
		Assert.notNull(folder);
		checkFolder(folder);
		Assert.isTrue(!folder.getPredefined());	
		
		folderRepository.delete(folder);
	}
	
	// Other business methods -------------------------------------------------
	
	public void createDefaultFolders(Actor actor) {
		Folder inbox = new Folder();
		Folder outbox = new Folder();
		Folder trashbox = new Folder();
		Folder spambox = new Folder();
				
		inbox.setName("Inbox");
		inbox.setActor(actor);
		inbox.setPredefined(true);
		inbox.setMessages(new ArrayList<Message>());
		
		save(inbox);
		
		outbox.setName("Outbox");
		outbox.setActor(actor);
		outbox.setPredefined(true);
		outbox.setMessages(new ArrayList<Message>());


		save(outbox);		
		
		trashbox.setName("Trashbox");
		trashbox.setActor(actor);
		trashbox.setPredefined(true);
		trashbox.setMessages(new ArrayList<Message>());

		
		save(trashbox);
		
		spambox.setName("Spambox");
		spambox.setActor(actor);
		spambox.setPredefined(true);
		spambox.setMessages(new ArrayList<Message>());

		
		save(spambox);
		
	}

	public Folder findInboxActor(Actor actor){
		
		Assert.notNull(actor);
		
		Folder folder = folderRepository.inboxFolder(actor);
		Assert.notNull(folder);
		
		return folder;
	}
	
	public Folder findOutboxActor(Actor actor){
	
		Assert.notNull(actor);
		
		Folder folder = folderRepository.outboxFolder(actor);
		Assert.notNull(folder);
		
		return folder;
	}
	
	public Folder findTrashboxActor(Actor actor){
		
		Assert.notNull(actor);
		
		Folder folder = folderRepository.trashboxFolder(actor);
		Assert.notNull(folder);
		
		return folder;
	}
	
	public Folder findSpamboxActor(Actor actor){
		
		Assert.notNull(actor);
		
		Folder folder = folderRepository.spamboxFolder(actor);
		Assert.notNull(folder);
		
		return folder;
	}
	
	public Collection<Folder> findFoldersByActor(Actor actor){
		
		Assert.notNull(actor);
		
		Collection<Folder> folders = folderRepository.findFoldersByActorId(actor);
		Assert.notNull(folders);
		
		return folders;
	}
	
	public void checkFolder(Folder folder){		
		Assert.notNull(folder);
		
		UserAccount userAccount;
		int userAccountId1;
		int userAccountId2;
				
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		
		userAccountId1 = userAccount.getId();
		Assert.notNull(userAccountId1);

		userAccountId2 = folder.getActor().getUserAccount().getId();
		Assert.notNull(userAccountId2);
		
		Assert.isTrue(userAccountId1 == userAccountId2);		
	}
		
	public void checkPrincipal(Folder folder) {
		Assert.notNull(folder);

		UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		
		Assert.isTrue(folder.getActor().getUserAccount().equals(userAccount));

	}
	
	public Collection<Folder> findAllFoldersByActorId(){
		Collection<Folder> all;
		Actor actor;
		
		
		actor = actorService.findByPrincipal();
		Assert.notNull(actor);

		
		all = folderRepository.findFoldersByActorId(actor);
		Assert.notNull(all);
		
		return all;
	}
	
	public Folder findTrashboxActor(){
		Actor actor;
		Folder folder;
		
		actor = actorService.findByPrincipal();
		Assert.notNull(actor);
		
		
		folder = folderRepository.trashboxFolder(actor);
		Assert.notNull(folder);
		
		return folder;
	}
}
