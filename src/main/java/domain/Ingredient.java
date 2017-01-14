package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Ingredient extends DomainEntity{
	
	// Constructor ----------------------------------------------
	
	public Ingredient(){
		super();
	}
	
	// Attributes ------------------------------------------------
	
	private String name;
	private String description;
	private String picture;
	
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
	
	// Relationships --------------------------------------------
	
	private Collection<Property> properties;
	private Collection<Quantity> quantities;

	@Valid
	@OneToMany(mappedBy="ingredient")
	public Collection<Quantity> getQuantities() {
		return quantities;
	}
	public void setQuantities(Collection<Quantity> quantities) {
		this.quantities = quantities;
	}

	@Valid
	@ManyToMany
	public Collection<Property> getProperties() {
		return properties;
	}
	public void setProperties(Collection<Property> properties) {
		this.properties = properties;
	}
	
}
