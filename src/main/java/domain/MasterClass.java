package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class MasterClass extends DomainEntity{
	
	// Constructor ------------------------------------------
	
	public MasterClass(){
		super();
	}
	
	// Attributes -------------------------------------------
	
	private String title;
	private String description;
	private boolean promoted;
	private Collection<String> registered;
	
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotBlank
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean getPromoted() {
		return promoted;
	}
	public void setPromoted(boolean promoted) {
		this.promoted = promoted;
	}
	
	@ElementCollection
	@NotNull
	public Collection<String> getRegistered() {
		return registered;
	}
	public void setRegistered(Collection<String> registered) {
		this.registered = registered;
	}	
		
	// Relationship -----------------------------------------

	private Cook cook;
	private Collection<LearningMaterial> learningMaterials;
	

	@Valid
	@ManyToOne(optional=false)
	public Cook getCook(){
		return cook;
	}
	public void setCook(Cook cook){
		this.cook = cook;
	}

	@Valid
	@OneToMany(mappedBy="masterClass")
	public Collection<LearningMaterial> getLearningMaterials(){
		return learningMaterials;
	}
	public void setLearningMaterials(Collection<LearningMaterial> LearningMaterials) {
		this.learningMaterials = LearningMaterials;
	}

	
	
	
	
}
