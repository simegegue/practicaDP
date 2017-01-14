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
import domain.Category;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class CategoryServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private AdministratorService administratorService;
	
	//Tests ---------------------------------------
	//Positive-------------------------------------

	@Test
	public void testCreateCategory(){
		Category category;
		super.authenticate("admin");
		category = categoryService.create();
		Assert.notNull(category);
		super.authenticate(null);
		
	}
	
	@Test
	public void testSaveCategory(){
		Collection<Category> categories;
		Category category, saved;
		Administrator administrator;
		super.authenticate("admin");
		administrator = administratorService.findByPrincipal(); 
		category = categoryService.create();
		category.setAdministrator(administrator);
		category.setName("category1");
		category.setDescription("Descripción categoria 1");
		category.setPicture("https://www.facebook.com/");
		Assert.notNull(category);
		saved = categoryService.save(category);
		categories = categoryService.findAll();
		Assert.isTrue(categories.contains(saved));
		super.authenticate(null);
	}
	
	@Test
	public void testDeleteCategory(){
		Collection<Category> categories;
		Category category, saved;
		Administrator administrator;
		super.authenticate("admin");
		administrator = administratorService.findByPrincipal(); 
		category = categoryService.create();
		category.setAdministrator(administrator);
		category.setName("category1");
		category.setDescription("Descripción categoria 1");
		category.setPicture("https://www.facebook.com/");
		Assert.notNull(category);
		saved = categoryService.save(category);
		categoryService.delete(saved);
		categories = categoryService.findAll();
		Assert.isTrue(!categories.contains(saved));
		super.authenticate(null);
	}

	@Test
	public void testFindAll(){
		Collection<Category> categories;
		categories = categoryService.findAll();
		Assert.notNull(categories);
	}
	
	@Test
	public void testFindOne(){
		Category category = categoryService.findOne(21);
		Assert.notNull(category);
	}
	
	
	//Negative -----------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void testNCreateCategory(){
		Category category;
		super.authenticate("administrator");
		category = categoryService.create();
		category = null;
		super.authenticate(null);
		Assert.notNull(category);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNSaveCategory(){
		Collection<Category> categories;
		Category category;
		Administrator administrator;
		super.authenticate("administrator");
		administrator = administratorService.findByPrincipal(); 
		category = categoryService.create();
		category.setAdministrator(administrator);
		category.setName("category1");
		category.setDescription("Descripción categoria 1");
		category.setPicture("https://www.facebook.com/");
		Assert.notNull(category);
		categoryService.save(category);
		categories = categoryService.findAll();
		Assert.isTrue(categories.contains(category));
		super.authenticate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNDeleteCategory(){
		Collection<Category> categories;
		Category category, saved;
		Administrator administrator;
		super.authenticate("administrator");
		administrator = administratorService.findByPrincipal(); 
		category = categoryService.create();
		category.setAdministrator(administrator);
		category.setName("category1");
		category.setDescription("Descripción categoria 1");
		category.setPicture("https://www.facebook.com/");
		Assert.notNull(category);
		saved = categoryService.save(category);
		categoryService.delete(saved);
		categories = categoryService.findAll();
		Assert.isTrue(categories.contains(saved));
		super.authenticate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNFindAll(){
		Collection<Category> categories;
		categories = categoryService.findAll();
		categories = null;
		Assert.notNull(categories);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindOne(){
		Assert.notNull(categoryService.findOne(13));
	}

}