package services;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.CreditCard;
import domain.Folder;
import domain.Sponsor;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class FolderServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private SponsorService sponsorService;
	
	//Tests ---------------------------------------
	//Positive ------------------------------------
	@Test
	public void testCreateAndSaveAndDelete(){
		super.authenticate("admin2");
		Folder folder = folderService.create();
		Assert.notNull(folder);
		
		folder.setName("Libros");
		folderService.save(folder);
		Assert.isTrue(folder.getName().equals("Libros"));
		
		folderService.delete(folder);
		super.authenticate(null);
	}
	
	@Test
	public void testFindAll(){
		Collection<Folder> folders = folderService.findAll();
		Assert.notNull(folders);
		Assert.isTrue(!folders.isEmpty());
	}
	
	@Test
	public void testFindOne(){
		Folder folder = folderService.findOne(75);
		Assert.notNull(folder);
	}
	
	@Test
	public void testCreateDefaultFolders(){
		Date ltm = new Date();
		ltm.setTime(ltm.getTime()-1000000);
		
		CreditCard cd = new CreditCard();
		cd.setHolderName("Paco");
		cd.setBrandName("VISA");
		cd.setNumber("4079978752719950");
		cd.setExpirationMonth(10);
		cd.setExpirationYear(2019);
		cd.setCvv(555);
		
		Sponsor sS;
		Sponsor sponsor = sponsorService.create();
		sponsor.setName("Paco");
		sponsor.setSurname("Pesao Soy");
		sponsor.setEmail("Paco555@gmail.com");
		sponsor.setCompanyName("Zapateria Barrientos");
		sponsor.setCreditCard(cd);
		sponsor.setLastTimeManages(ltm);
		Assert.notNull(sponsor);
		sS = sponsorService.save(sponsor);
		folderService.createDefaultFolders(sS);
	}
	
	@Test
	public void testFindInboxActor(){
		Sponsor sponsor = sponsorService.findOne(43);
		Folder folder = folderService.findInboxActor(sponsor);
		Assert.notNull(folder);
		Assert.isTrue(folder.getPredefined()==true && folder.getName().equals("Inbox"));
	}
	
	@Test
	public void testFindOutboxActor(){
		Sponsor sponsor = sponsorService.findOne(44);
		Folder folder = folderService.findOutboxActor(sponsor);
		Assert.notNull(folder);
		Assert.isTrue(folder.getPredefined()==true && folder.getName().equals("Outbox"));
	}
	
	@Test
	public void testFindTrashboxActor(){
		Sponsor sponsor = sponsorService.findOne(44);
		Folder folder = folderService.findTrashboxActor(sponsor);
		Assert.notNull(folder);
		Assert.isTrue(folder.getPredefined()==true && folder.getName().equals("Trashbox"));
	}
	
	@Test
	public void testFindSpamboxActor(){
		Sponsor sponsor = sponsorService.findOne(44);
		Folder folder = folderService.findSpamboxActor(sponsor);
		Assert.notNull(folder);
		Assert.isTrue(folder.getPredefined()==true && folder.getName().equals("Spambox"));
	}
	
	@Test
	public void testFindFoldersByActor(){
		Sponsor sponsor = sponsorService.findOne(44);
		Collection<Folder> folders = folderService.findFoldersByActor(sponsor);
		Assert.notNull(folders);
	}
	
	@Test
	public void testCheckFolder(){
		super.authenticate("admin2");
		Folder folder = folderService.findOne(51);
		folderService.checkFolder(folder);
		super.authenticate(null);
	}
	
	@Test
	public void testCheckPrincipal(){
		super.authenticate("admin2");
		Folder folder = folderService.findOne(51);
		folderService.checkPrincipal(folder);
		super.authenticate(null);
	}
	
	@Test
	public void testFindAllFoldersByActorId(){
		super.authenticate("admin");
		Collection<Folder> folders = folderService.findAllFoldersByActorId();
		Assert.notNull(folders);
		super.authenticate(null);
	}
	
	// Test Negativos --------------------------------------------
	
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteNegative(){
		Folder folder = folderService.findOne(75);
		folderService.delete(folder);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCheckFolderNegative(){
		super.authenticate("administrator2");
		Folder folder = folderService.findOne(46);
		folderService.checkFolder(folder);
		super.authenticate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCheckPrincipalNegative(){
		super.authenticate("administrator2");
		Folder folder = folderService.findOne(47);
		folderService.checkPrincipal(folder);
		super.authenticate(null);
	}
}