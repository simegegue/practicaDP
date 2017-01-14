package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;


import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Contest extends DomainEntity{

	// Constructors -----------------------------------------------------------
	
	public Contest(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	private String title;
	private Date openingTime;
	private Date closingTime;
	
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getOpeningTime() {
		return openingTime;
	}
	public void setOpeningTime(Date openingTime) {
		this.openingTime = openingTime;
	}
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getClosingTime() {
		return closingTime;
	}
	public void setClosingTime(Date closingTime) {
		this.closingTime = closingTime;
	}	
			
	// Relationships ----------------------------------------------------------
	
	private Collection<Qualify> qualifies;

	@Valid
	@OneToMany(mappedBy="contest")
	public Collection<Qualify> getQualifies(){
		return qualifies;
	}
	public void setQualifies
	(Collection<Qualify> qualifies){
		this.qualifies = qualifies;
	}
	
}
