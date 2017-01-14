package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Recipe;
import domain.Step;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class StepServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private StepService stepService;
	
	@Autowired
	private RecipeService recipeService;
	//Tests ---------------------------------------
	@Test
	public void testCreate(){
		Step step;
		Recipe recipe;
		super.authenticate("user1");
		recipe = recipeService.findOne(111);
		step = stepService.create(recipe);
		super.authenticate(null);
		Assert.notNull(step);
	}
	
	@Test
	public void testSave(){
		Collection<Step> steps;
		Recipe recipe;
		Step step, saved;
		super.authenticate("user1");
		recipe = recipeService.findOne(111);
		step = stepService.create(recipe);
		step.setDescription("Description Step");
		saved = stepService.save(step);
		steps = stepService.findAll();
		super.authenticate(null);
		Assert.isTrue(steps.contains(saved));
	}
	
	@Test
	public void testDelete(){
		Collection<Step> steps;
		Recipe recipe;
		Step step, saved;
		super.authenticate("user1");
		recipe = recipeService.findOne(111);
		step = stepService.create(recipe);
		step.setDescription("Description Step");
		saved = stepService.save(step);
		stepService.delete(saved);
		steps = stepService.findAll();
		super.authenticate(null);
		Assert.isTrue(!steps.contains(saved));
	}
	
	@Test
	public void testFindOne(){
		Recipe recipe;
		Step step, saved, found;
		super.authenticate("user1");
		recipe = recipeService.findOne(111);
		step = stepService.create(recipe);
		step.setDescription("Description Step");
		saved = stepService.save(step);
		found = stepService.findOne(saved.getId());
		super.authenticate(null);
		Assert.isTrue(found.equals(saved));
	}
	
	@Test
	public void testFindAll(){
		Collection<Step> steps;
		steps = stepService.findAll();
		Assert.notNull(steps);
		
	}
	//Negative test--------------------------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void testNCreate(){
		Step step;
		super.authenticate("cook1");
		step = stepService.create(null);
		super.authenticate(null);
		Assert.notNull(step);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNSave(){
		Collection<Step> steps;
		Recipe recipe;
		Step step, saved;
		super.authenticate("cook1");
		recipe = recipeService.findOne(111);
		step = stepService.create(recipe);
		step.setDescription("Description Step");
		saved = stepService.save(step);
		steps = stepService.findAll();
		super.authenticate(null);
		Assert.isTrue(steps.contains(saved));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNDelete(){
		Collection<Step> steps;
		Recipe recipe;
		Step step, saved;
		super.authenticate("cook1");
		recipe = recipeService.findOne(111);
		step = stepService.create(recipe);
		step.setDescription("Description Step");
		saved = stepService.save(step);
		stepService.delete(saved);
		steps = stepService.findAll();
		super.authenticate(null);
		Assert.isTrue(!steps.contains(saved));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindOne(){
		Recipe recipe;
		Step step, saved, found;
		super.authenticate("cook1");
		recipe = recipeService.findOne(111);
		step = stepService.create(recipe);
		saved = stepService.save(step);
		found = stepService.findOne(saved.getId());
		super.authenticate(null);
		Assert.isTrue(found.equals(saved));
	}
	
	
}
