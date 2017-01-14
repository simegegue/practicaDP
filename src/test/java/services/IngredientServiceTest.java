package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Ingredient;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class IngredientServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private IngredientService ingredientService;
	
	//Tests ---------------------------------------
	//Positive ------------------------------------
	@Test
	public void testCreate(){
		Ingredient ingredient;
		super.authenticate("nutritionist1");
		ingredient = ingredientService.create();
		super.authenticate(null);
		Assert.notNull(ingredient);
	}
	
	@Test
	public void testSave(){
		Collection<Ingredient> ingredients;
		Ingredient ingredient, saved;
		super.authenticate("nutritionist1");
		ingredient = ingredientService.create();
		ingredient.setName("Ingrediente Test 1");
		ingredient.setDescription("Description 1");
		saved = ingredientService.save(ingredient);
		ingredients = ingredientService.findAll();
		super.authenticate(null);
		Assert.isTrue(ingredients.contains(saved));
	}
	
	@Test
	public void testDelete(){
		Collection<Ingredient> ingredients;
		Ingredient ingredient, saved;
		super.authenticate("nutritionist1");
		ingredient = ingredientService.create();
		ingredient.setName("Ingrediente Test 1");
		ingredient.setDescription("Description 1");
		saved = ingredientService.save(ingredient);
		ingredientService.delete(saved);
		ingredients = ingredientService.findAll();
		super.authenticate(null);
		Assert.isTrue(!ingredients.contains(ingredient));
	}
	
	@Test
	public void testFindOne(){
		Ingredient ingredient, saved, found;
		super.authenticate("nutritionist1");
		ingredient = ingredientService.create();
		ingredient.setName("Ingrediente Test 1");
		ingredient.setDescription("Description 1");
		saved = ingredientService.save(ingredient);
		found = ingredientService.findOne(saved.getId());
		super.authenticate(null);
		Assert.isTrue(saved.equals(found));
	}
	
	@Test
	public void testFindAll(){
		Collection<Ingredient> ingredients;
		ingredients = ingredientService.findAll();
		Assert.notEmpty(ingredients);
	}

	//Negative ----------------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void testNCreate(){
		Ingredient ingredient;
		super.authenticate("administrator");
		ingredient = ingredientService.create();
		super.authenticate(null);
		Assert.notNull(ingredient);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNSave(){
		Collection<Ingredient> ingredients;
		Ingredient ingredient, saved;
		super.authenticate("sponsor1");
		ingredient = ingredientService.create();
		ingredient.setName("Ingrediente Test 1");
		ingredient.setDescription("Description 1");
		saved = ingredientService.save(ingredient);
		ingredients = ingredientService.findAll();
		super.authenticate(null);
		Assert.isTrue(ingredients.contains(saved));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNDelete(){
		Collection<Ingredient> ingredients;
		Ingredient ingredient, saved;
		super.authenticate("cook1");
		ingredient = ingredientService.create();
		ingredient.setName("Ingrediente Test 1");
		ingredient.setDescription("Description 1");
		saved = ingredientService.save(ingredient);
		ingredientService.delete(saved);
		ingredients = ingredientService.findAll();
		super.authenticate(null);
		Assert.isTrue(!ingredients.contains(saved));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindOne(){
		Ingredient ingredient, saved, found;
		super.authenticate("user1");
		ingredient = ingredientService.create();
		ingredient.setName("Ingrediente Test 1");
		ingredient.setDescription("Description 1");
		saved = ingredientService.save(ingredient);
		found = ingredientService.findOne(saved.getId());
		super.authenticate(null);
		Assert.isTrue(saved.equals(found));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindAll(){
		Collection<Ingredient> ingredients;
		ingredients = ingredientService.findAll();
		ingredients = null;
		Assert.notEmpty(ingredients);
	}


}