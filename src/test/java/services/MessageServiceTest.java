package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Folder;
import domain.Message;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class MessageServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private FolderService folderService;
	
	//Tests ---------------------------------------
	
	@Test
	public void testCreate(){
		super.authenticate("user1");
		Message message = messageService.create();
		Assert.notNull(message);
	}
	
	@Test
	public void testFindAll(){
		Collection<Message> messages = messageService.findAll();
		Assert.notEmpty(messages);
	}
	
	@Test
	public void testFindOne(){
		Message message = messageService.findOne(179);
		Assert.notNull(message);
	}
	
	@Test
	public void testSave(){
		super.authenticate("user1");
		Message message = messageService.findOne(179);
		message.setSpam(true);
		messageService.save(message);
		Message messageS = messageService.findOne(179);
		Assert.isTrue(messageS.getSpam());
		super.authenticate(null);
	}
	
	@Test
	public void testDeleteNotInTrashboxAndBeforeInTrashbox(){
		super.authenticate("user1");
		Message message = messageService.findOne(179);
		messageService.delete(message);
		Folder folder = folderService.findTrashboxActor();
		Assert.isTrue(message.getFolder().equals(folder));
		messageService.delete(message);
		super.authenticate(null);
	}
	
	@Test
	public void testGetByFolder(){
		Collection<Message> messages = messageService.getByFolder(55);
		Assert.notEmpty(messages);
	}
	
	@Test
	public void testFindMessageByActorId(){
		super.authenticate("user1");
		Collection<Message> messages = messageService.findMessagesByActorId();
		Assert.notEmpty(messages);
		super.authenticate(null);
	}
	
	@Test
	public void testChangeFolder(){
		super.authenticate("user1");
		Folder folder = folderService.findOne(58);
		Message message = messageService.findOne(179);
		messageService.changeFolder(message, folder);
		Message messageS = messageService.findOne(179);
		Assert.isTrue(messageS.getFolder().equals(folder));
		super.authenticate(null);
	}
	
	@Test
	public void testCheckPrincipal(){
		super.authenticate("user1");
		Message message = messageService.findOne(179);
		messageService.checkPrincipal(message);
		super.authenticate(null);
	}
	
	@Test
	public void testBulkMessage(){
		super.authenticate("admin");
		int monthlyBillId = 187;
		messageService.bulkMessage(monthlyBillId);
		super.authenticate(null);
	}
	
	// Negative --------------------------------------------------
	
	@Test(expected = IllegalArgumentException.class)
	public void testBulkMessageNegative(){
		super.authenticate("user1");
		int monthlyBillId = 185;
		messageService.bulkMessage(monthlyBillId);
		super.authenticate(null);
	}
}