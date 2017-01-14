package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.SpamWord;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class SpamWordServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private SpamWordService spamWordService;
	
	@Autowired
	private AdministratorService administratorService;
	//Tests ---------------------------------------
	//Positive ------------------------------------
	@Test
	public void testCreate(){
		SpamWord spamWord;
		super.authenticate("admin");
		spamWord = spamWordService.create();
		super.authenticate(null);
		Assert.notNull(spamWord);
	}

	@Test
	public void testSave(){
		Collection<SpamWord> spamWords;
		SpamWord spamWord, saved;
		Administrator administrator;
		super.authenticate("admin");
		spamWord = spamWordService.create();
		administrator = administratorService.findByPrincipal();
		spamWord.setName("Spamword 1");
		spamWord.setAdministrator(administrator);
		saved = spamWordService.save(spamWord);
		spamWords = spamWordService.findAll();
		super.authenticate(null);
		Assert.isTrue(spamWords.contains(saved));
	}
	
	@Test
	public void testDelete(){
		Collection<SpamWord> spamWords;
		SpamWord spamWord, saved;
		Administrator administrator;
		super.authenticate("admin");
		spamWord = spamWordService.create();
		administrator = administratorService.findByPrincipal();
		spamWord.setName("Spamword 1");
		spamWord.setAdministrator(administrator);
		saved = spamWordService.save(spamWord);
		spamWordService.delete(saved);
		spamWords = spamWordService.findAll();
		super.authenticate(null);
		Assert.isTrue(!spamWords.contains(saved));
	}
	
	@Test
	public void testFindOne(){
		SpamWord spamWord, saved, spamWordFounds;
		Administrator administrator;
		super.authenticate("admin");
		spamWord = spamWordService.create();
		administrator = administratorService.findByPrincipal();
		spamWord.setName("Spamword 1");
		spamWord.setAdministrator(administrator);
		saved = spamWordService.save(spamWord);
		spamWordFounds = spamWordService.findOne(saved.getId());
		super.authenticate(null);
		Assert.isTrue(spamWordFounds.equals(saved));
	}
	
	@Test
	public void testFindAll(){
		Collection<SpamWord> spamWords;
		spamWords = spamWordService.findAll();
		Assert.notNull(spamWords);
	}
	
	//Negative ---------------------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void testNCreate(){
		SpamWord spamWord;
		super.authenticate("cook1");
		spamWord = spamWordService.create();
		spamWord = null;
		super.authenticate(null);
		Assert.isNull(spamWord);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNSave(){
		Collection<SpamWord> spamWords;
		SpamWord spamWord, saved;
		spamWord = spamWordService.create();
		spamWord.setName("Spamword 1");
		saved = spamWordService.save(spamWord);
		spamWords = spamWordService.findAll();
		super.authenticate(null);
		Assert.isTrue(!spamWords.contains(saved));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNDelete(){
		Collection<SpamWord> spamWords;
		SpamWord spamWord, saved;
		Administrator administrator;
		super.authenticate("administrator");
		spamWord = spamWordService.create();
		administrator = administratorService.findByPrincipal();
		spamWord.setName("Spamword 1");
		spamWord.setAdministrator(administrator);
		saved = spamWordService.save(spamWord);
		spamWordService.delete(spamWord);
		spamWords = spamWordService.findAll();
		super.authenticate(null);
		Assert.isTrue(spamWords.contains(saved));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindOne(){
		SpamWord spamWord, saved, spamWordFounds;
		Administrator administrator;
		super.authenticate("administrator");
		spamWord = spamWordService.create();
		administrator = administratorService.findByPrincipal();
		spamWord.setName("Spamword 1");
		spamWord.setAdministrator(administrator);
		saved = spamWordService.save(spamWord);
		spamWordFounds = spamWordService.findOne(999);
		super.authenticate(null);
		Assert.isTrue(spamWordFounds.equals(saved));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindAll(){
		Collection<SpamWord> spamWords;
		spamWords = spamWordService.findAll();
		spamWords = null;
		Assert.notNull(spamWords);
	}

}