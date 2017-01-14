package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Curricula extends DomainEntity{
	
	// Constructor -------------------------------------------------
	
	public Curricula(){
		super();
	}
	
	// Attributes --------------------------------------------------
	
	private String title;
	private String photo;
	private String educationSection;
	private String experienceSection;
	private String hobbySection;
	
	@NotBlank
	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	
	@URL
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@NotBlank
	public String getEducationSection() {
		return educationSection;
	}
	public void setEducationSection(String educationSection) {
		this.educationSection = educationSection;
	}
	
	@NotBlank
	public String getExperienceSection() {
		return experienceSection;
	}
	public void setExperienceSection(String experienceSection) {
		this.experienceSection = experienceSection;
	}
	
	@NotNull
	public String getHobbySection() {
		return hobbySection;
	}
	public void setHobbySection(String hobbySection) {
		this.hobbySection = hobbySection;
	}
	
	// Relationships -----------------------------------------------
	
	private Collection<Endorser> endorsers;

	@Valid
	@OneToMany(mappedBy="curricula")
	public Collection<Endorser> getEndorsers() {
		return endorsers;
	}
	public void setEndorsers(Collection<Endorser> endorsers) {
		this.endorsers = endorsers;
	}
}
