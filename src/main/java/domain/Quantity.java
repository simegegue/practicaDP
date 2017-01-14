package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Digits;


@Entity
@Access(AccessType.PROPERTY)
public class Quantity extends DomainEntity {
	
	// Constructors -----------------------------------------------------------
	
	public Quantity(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	private double measure;
	
	
	@Digits(fraction = 2, integer = 3)
	public double getMeasure() {
		return measure;
	}
	public void setMeasure(double measure) {
		this.measure = measure;
	}
	
	// Relationships ----------------------------------------------------------
	
	private Recipe recipe;
	private Ingredient ingredient;
	private Unit unit;

	@Valid
	@ManyToOne(optional=false)
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	@Valid
	@ManyToOne(optional=false)
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	@Valid
	@ManyToOne(optional=false)
	public Ingredient getIngredient() {
		return ingredient;
	}
	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}
}
