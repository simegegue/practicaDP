package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Curricula;


import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class CurriculaServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private CurriculaService curriculaService;
	

	
	//Tests ---------------------------------------
	@Test
	public void testCreate(){
		Curricula curricula;
		super.authenticate("nutritionist2");
		curricula=curriculaService.create();
		Assert.notNull(curricula);
		super.authenticate(null);
	}
	
	@Test
	public void testSave(){
		Curricula curricula,saved;
		super.authenticate("nutritionist2");
		curricula=curriculaService.findOne(32);
		Assert.notNull(curricula);
		String s="EducationMod";
		curricula.setEducationSection(s);
		saved=curriculaService.save(curricula);
		Assert.isTrue(saved.getEducationSection().equals(s));
		super.authenticate(null);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testDelete(){
		Curricula curricula,saved;
		super.authenticate("nutritionist1");
		curricula=curriculaService.create();
		Assert.notNull(curricula);
		saved=curriculaService.save(curricula);
		curriculaService.delete(curricula);
		Collection<Curricula>curriculas=curriculaService.findAll();
		Assert.isTrue(!curriculas.contains(saved));
		super.authenticate(null);
	}
	
	@Test
	public void testFindAll(){
		Collection<Curricula>curriculas;
		curriculas=curriculaService.findAll();
		Assert.notNull(curriculas);
	}
	
	@Test
	public void testFindOne(){
		Curricula c;
		c=curriculaService.findOne(32);
		Assert.notNull(c);
	}
	
	//Negetive --------------------------------------
	@Test(expected = IllegalArgumentException.class)
	public void testNCreate(){
		Curricula curricula;
		super.authenticate("user1");
		curricula=curriculaService.create();
		Assert.notNull(curricula);
		super.authenticate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNSave(){
		Curricula curricula,saved;
		super.authenticate("cook");
		curricula=curriculaService.findOne(31);
		Assert.notNull(curricula);
		String s="EducationMod";
		curricula.setEducationSection(s);
		saved=curriculaService.save(curricula);
		Assert.isTrue(saved.getEducationSection().equals(s));
		super.authenticate(null);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testNDelete(){
		Curricula curricula,saved;
		super.authenticate("sponsor1");
		curricula=curriculaService.create();
		Assert.notNull(curricula);
		saved=curriculaService.save(curricula);
		curriculaService.delete(curricula);
		Collection<Curricula>curriculas=curriculaService.findAll();
		Assert.isTrue(!curriculas.contains(saved));
		super.authenticate(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindAll(){
		Collection<Curricula>curriculas;
		curriculas=curriculaService.findAll();
		curriculas = null;
		Assert.notNull(curriculas);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNFindOne(){
		Curricula c;
		c=curriculaService.findOne(319);
		Assert.notNull(c);
	}
}