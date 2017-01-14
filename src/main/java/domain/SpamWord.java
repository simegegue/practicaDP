package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;


import org.hibernate.validator.constraints.NotBlank;


@Entity
@Access(AccessType.PROPERTY)
public class SpamWord extends DomainEntity{
	
	// Constructors -----------------------------------------------------------
	
	public SpamWord(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	private String name;
	
	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

				
	// Relationships ----------------------------------------------------------

	private Administrator administrator;

	@Valid
	@ManyToOne(optional=false)
	public Administrator getAdministrator(){
		return administrator;
	}
	public void setAdministrator(Administrator administrator){
		this.administrator = administrator;
	}
	
}
