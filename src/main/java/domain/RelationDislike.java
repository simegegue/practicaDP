package domain;



import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
@Access(AccessType.PROPERTY)
public class RelationDislike extends DomainEntity{
	// Constructor ----------------------------------------------------
	
	
	// Attributes -----------------------------------------------------
	
	
	// Relationships --------------------------------------------------
	
	private SocialActor socialActor;
	private Recipe recipe;
	
	@ManyToOne(optional=true)
	@ElementCollection
	public SocialActor getSocialActor() {
		return socialActor;
	}
	
	public void setSocialActor(SocialActor socialActor) {
		this.socialActor = socialActor;
	}
	@ManyToOne(optional=true)
	@ElementCollection
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	
	
}