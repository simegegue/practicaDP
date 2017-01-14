package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;


@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity{

	// Constructors -----------------------------------------------------------
	
	public Category(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	private String name;
	private String description;
	private String picture;
	private Collection<String> tags;
	
	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@URL
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@ElementCollection
	public Collection<String> getTags() {
		return tags;
	}
	public void setTags(Collection<String> tags) {
		this.tags = tags;
	}

	
	// Relationships ----------------------------------------------------------
	
	private Administrator administrator;
	private Collection<Category> subCategories;
	private Category superCategory;
	private Collection<Recipe> recipes;

	@Valid
	@ManyToMany(mappedBy="categories")
	public Collection<Recipe> getRecipes() {
		return recipes;
	}
	public void setRecipes(Collection<Recipe> recipes) {
		this.recipes = recipes;
	}

	@Valid
	@ManyToOne(optional=true)
	public Category getSuperCategory() {
		return superCategory;
	}
	public void setSuperCategory(Category superCategory) {
		this.superCategory = superCategory;
	}

	@Valid
	@OneToMany(mappedBy="superCategory")
	public Collection<Category> getSubCategories() {
		return subCategories;
	}
	public void setSubCategories(Collection<Category> subCategories) {
		this.subCategories = subCategories;
	}

	@Valid
	@ManyToOne(optional=false)
	public Administrator getAdministrator(){
		return administrator;
	}
	public void setAdministrator(Administrator administrator){
		this.administrator = administrator;
	}
	
}
