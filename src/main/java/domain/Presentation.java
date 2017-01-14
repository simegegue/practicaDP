package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Presentation extends LearningMaterial{
	
	// Constructor -------------------------------------------------
	
	public Presentation(){
		super();
	}
	
	// Attributes --------------------------------------------------
	
	private String path;

	@URL
	@NotBlank
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	// Relationships -----------------------------------------------

}
