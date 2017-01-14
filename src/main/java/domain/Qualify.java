package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Access(AccessType.PROPERTY)
public class Qualify extends DomainEntity{
	
	// Constructor -----------------------------------------------------
	
	// Attributes ------------------------------------------------------
	
	private Integer position;

	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	
	// Relationships ---------------------------------------------------
	
	private Recipe recipe;
	private Contest contest;

	@ManyToOne(optional = false)
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	
	@ManyToOne(optional = false)
	public Contest getContest() {
		return contest;
	}
	public void setContest(Contest contest) {
		this.contest = contest;
	}
	
	

}
