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

import security.Authority;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class AdministratorServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private AdministratorService administratorService;
	
	//Tests ---------------------------------------
	//Positive
	@Test
	public void testCreate(){
		Administrator admin = administratorService.create();
		Assert.notNull(admin);
	}
	
	@Test
	public void testFindAll(){
		Collection<Administrator> all = administratorService.findAll();
		Assert.notNull(all);
		Assert.isTrue(all.size() == 3);
	}
	
	@Test
	public void testFindOne(){
		Administrator admin = administratorService.findOne(19);
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.notNull(admin);
		Assert.isTrue(admin.getUserAccount().getAuthorities().contains(au));
	}

	@Test
	public void testSave(){
		int adminId = 2;
		String name = "Saturno";
		
		Administrator admin = administratorService.findByUserAccountId(adminId);
		Assert.notNull(admin);
		admin.setName(name);
		administratorService.save(admin);
		
		Administrator adminMod = administratorService.findByUserAccountId(adminId);
		Assert.isTrue(adminMod.getName().equals(name));
	}

	@Test
	public void testDelete(){
		// Before deleting the actor --------------------
		Administrator admin = administratorService.findOne(20);
		Collection<Administrator> all1 = administratorService.findAll();
		Assert.isTrue(all1.contains(admin));
				
		administratorService.delete(admin);
				
		// After deleting the actor ---------------------
		Collection<Administrator> all2 = administratorService.findAll();
		Assert.isTrue(!all2.contains(admin));
	}

	@Test
	public void testFindByPrincipal(){
		super.authenticate("admin");
		
		Administrator principal = administratorService.findOne(18);
		
		Administrator admin = administratorService.findByPrincipal();
		Assert.isTrue(principal.equals(admin));
		
		super.authenticate(null);
	}

	@Test
	public void testFindByUserAccountId(){
		Administrator admin1 = administratorService.findByUserAccountId(2);
		Administrator admin2 = administratorService.findOne(18);
		
		Assert.isTrue(admin1.equals(admin2));
	}
	
	//Negative ------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void testNCreate(){
		Administrator admin = administratorService.create();
		admin = null;
		Assert.notNull(admin);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindAll(){
		Collection<Administrator> all = administratorService.findAll();
		all=null;
		Assert.notNull(all);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindOne(){
		Administrator admin = administratorService.findOne(89);
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.notNull(admin);
		Assert.isTrue(admin.getUserAccount().getAuthorities().contains(au));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNSave(){
		int adminId = 966;
		String name = "Saturno";
		
		Administrator admin = administratorService.findByUserAccountId(adminId);
		Assert.notNull(admin);
		admin.setName(name);
		administratorService.save(admin);
		
		Administrator adminMod = administratorService.findByUserAccountId(adminId);
		Assert.isTrue(adminMod.getName().equals(name));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNDelete(){
		// Before deleting the actor --------------------
		Administrator admin = administratorService.findOne(36);
		Collection<Administrator> all1 = administratorService.findAll();
		Assert.isTrue(all1.contains(admin));
				
		administratorService.delete(admin);
				
		// After deleting the actor ---------------------
		Collection<Administrator> all2 = administratorService.findAll();
		Assert.isTrue(!all2.contains(admin));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNFindByPrincipal(){
		super.authenticate("cook");
		
		Administrator principal = administratorService.findOne(19);
		
		Administrator admin = administratorService.findByPrincipal();
		Assert.isTrue(principal.equals(admin));
		
		super.authenticate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNFindByUserAccountId(){
		Administrator admin1 = administratorService.findByUserAccountId(16);
		Administrator admin2 = administratorService.findOne(96);
		
		Assert.isTrue(admin1.equals(admin2));
	}
}
