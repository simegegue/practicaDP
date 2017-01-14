package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PropertyRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Ingredient;
import domain.Property;

@Service
@Transactional
public class PropertyService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PropertyRepository propertyRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private IngredientService ingredientService;
	
	// Constructors -----------------------------------------------------------
	
	public PropertyService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Property create() {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("NUTRITIONIST");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Property result;

		result = new Property();

		return result;
	}

	public Collection<Property> findAll() {
		Collection<Property> result;

		result = propertyRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Property findOne(int propertyId) {
		Property result;

		result = propertyRepository.findOne(propertyId);
		Assert.notNull(result);

		return result;
	}

	public Property save(Property property) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("NUTRITIONIST");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(property);
		
		Property result;

		result = propertyRepository.save(property);
		
		return result;
	}

	public void delete(Property property) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("NUTRITIONIST");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		Assert.notNull(property);
		Assert.isTrue(property.getId() != 0);

		propertyRepository.delete(property);
	}
	
	// Other business methods -------------------------------------------------

	public void deleteProperty(Property p){
		Collection<Ingredient> ing=ingredientService.findAll();
		List<Property>aux=new ArrayList<Property>();
		for(Ingredient i:ing){
			for(Property po:i.getProperties()){
				if(!aux.contains(po)){
					aux.add(po);
				}
			}
		}
		if(!aux.contains(p)){
			delete(p);
		}
	}
}
