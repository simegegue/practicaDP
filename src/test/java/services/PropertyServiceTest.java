package services;

import java.util.Collection;
import java.util.HashSet;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Property;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml"})
@Transactional
public class PropertyServiceTest extends AbstractTest{

	//Service under test --------------------------
	@Autowired
	private PropertyService propertyService;
	
	//Tests ---------------------------------------

	//Positive-------------------------------------
	
	@Test
	public void testCreate(){
		Property property;
		super.authenticate("nutritionist1");
		property = propertyService.create();
		Assert.notNull(property);
		super.authenticate(null);
	}
	
	@Test
	public void testSave(){
		super.authenticate("nutritionist1");
		Collection<Property> properties = new HashSet<Property>();
		Property property, saved;
		property = propertyService.create();
		property.setName("property70");
		property.setDescription("descriptionProperty70");
		property.setValue(50.0);
		Assert.notNull(property);
		
		saved = propertyService.save(property);
		properties = propertyService.findAll();
		Assert.isTrue(properties.contains(saved));
		super.authenticate(null);
	}	
	
	@Test
	public void testDelete(){
		super.authenticate("nutritionist1");
		Collection<Property> properties = new HashSet<Property>();
		Property property, saved;
		property = propertyService.create();
		property.setName("property70");
		property.setDescription("descriptionProperty70");
		property.setValue(50.0);
		Assert.notNull(property);
		
		saved = propertyService.save(property);
		propertyService.delete(saved);
		properties = propertyService.findAll();
		Assert.isTrue(!properties.contains(saved));
		super.authenticate(null);
	}
	
	
	@Test
	public void testFindAll(){
		Collection<Property>properties;
		properties=propertyService.findAll();
		Assert.notNull(properties);
	}
	
	@Test
	public void testFindOne(){
		Property property;
		property=propertyService.findOne(158);
		Assert.notNull(property);
	}
	
	@Test
	public void testDeleteProperty(){
		Property property;
		super.authenticate("nutritionist1");
		property = propertyService.create();
		property.setId(25);
		propertyService.deleteProperty(property);
		Assert.notNull(property);
		super.authenticate(null);
	}

	//Negative-------------------------------------
	
	//Sponsor no puede crear un property
	@Test(expected = IllegalArgumentException.class)
	public void testNegCreate(){
		Property property;
		super.authenticate("sponsor1");
		property = propertyService.create();
		Assert.notNull(property);
		super.authenticate(null);
	}
	
	//Atributo name vacio
	@Test(expected = ConstraintViolationException.class)
	public void testNegSave(){
		super.authenticate("nutritionist1");
		Collection<Property> properties = new HashSet<Property>();
		Property property, saved;
		property = propertyService.create();
		property.setName("");
		property.setDescription("descriptionProperty70");
		property.setValue(50.0);
		Assert.notNull(property);
		
		saved = propertyService.save(property);
		properties = propertyService.findAll();
		Assert.isTrue(properties.contains(saved));
		super.authenticate(null);
	}	
	
	//Atributo description vacio
	@Test(expected = ConstraintViolationException.class)
	public void testNegDelete(){
		super.authenticate("nutritionist1");
		Collection<Property> properties = new HashSet<Property>();
		Property property, saved;
		property = propertyService.create();
		property.setName("property70");
		property.setDescription("");
		property.setValue(50.0);
		Assert.notNull(property);
		
		saved = propertyService.save(property);
		propertyService.delete(saved);
		properties = propertyService.findAll();
		Assert.isTrue(!properties.contains(saved));
		super.authenticate(null);
	}
	
	//Variable properties nula
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindAll(){
		Collection<Property>properties;
		properties=propertyService.findAll();
		properties = null;
		Assert.notNull(properties);
	}
	
	//Id incorrecta
	@Test(expected = IllegalArgumentException.class)
	public void testNegFindOne(){
		Property property;
		property=propertyService.findOne(25);
		Assert.notNull(property);
	}
	
	//Cook no puede crear un property
	@Test(expected = IllegalArgumentException.class)
	public void testNegDeleteProperty(){
		Property property;
		super.authenticate("cook1");
		property = propertyService.create();
		property.setId(25);
		propertyService.deleteProperty(property);
		Assert.notNull(property);
		super.authenticate(null);
	}
}