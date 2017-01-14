package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class LearningMaterial extends DomainEntity{
	
	// Constructor -----------------------------------------
	
	public LearningMaterial(){
		super();
	}
	
	// Attributes ------------------------------------------
	
	private String title;
	private String abstrac;
	private String attachment;
	
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotBlank
	public String getAbstrac() {
		return abstrac;
	}
	public void setAbstrac(String abstrac) {
		this.abstrac = abstrac;
	}
	
	@URL
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	// Relationship ----------------------------------------

	private MasterClass masterClass;

	@Valid
	@ManyToOne(optional=false)
	public MasterClass getMasterClass(){
		return masterClass;
	}
	public void setMasterClass(MasterClass masterClass) {
		this.masterClass = masterClass;
	}
}
