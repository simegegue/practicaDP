package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Category;
import domain.Recipe;

@Service
@Transactional
public class CategoryService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CategoryRepository categoryRepository;
	
	// Supporting services ----------------------------------------------------
	
	// Constructors -----------------------------------------------------------
	
	public CategoryService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------
	
	public Category create() {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Category result;
		
		result = new Category();
		result.setRecipes(new ArrayList<Recipe>());
		result.setSubCategories(new ArrayList<Category>());
		return result;
	}

	public Collection<Category> findAll() {
		Collection<Category> result;

		result = categoryRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Category findOne(int categoryId) {
		Category result;

		result = categoryRepository.findOne(categoryId);
		Assert.notNull(result);

		return result;
	}

	public Category save(Category category) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(category);
		
		Category result;

		result = categoryRepository.save(category);
		
		return result;
	}

	public void delete(Category category) {
		
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Authority au = new Authority();
		au.setAuthority("ADMIN");
		Assert.isTrue(userAccount.getAuthorities().contains(au));
		
		Assert.notNull(category);
		Assert.isTrue(category.getId() != 0);
		Boolean recipe=false;
		Collection<Category>sub=category.getSubCategories();
		for(Category c:sub){
			if(!c.getRecipes().isEmpty()){
				recipe=true;
			}
		}
		if(recipe==false){
			for(Category s:sub){
				categoryRepository.delete(s);
			}
			categoryRepository.delete(category);
		}
		
	}
	
	public Category save2 (Category category){
		return categoryRepository.save(category);
	}
	
	// Other business methods -------------------------------------------------

}
