package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.Entity;
import javax.persistence.AccessType;
import javax.persistence.OneToMany;
import javax.validation.Valid;



@Entity
@Access(AccessType.PROPERTY)
public class Cook extends Actor{
	
	// Constructors -----------------------------------------------------------

	public Cook() {
		super();
	}
		
	// Attributes -------------------------------------------------------------
		
	// Relationships ----------------------------------------------------------

	private Collection<MasterClass> masterClasses;

	@Valid
	@OneToMany(mappedBy="cook")
	public Collection<MasterClass> getMasterClasses(){
		return masterClasses;
	}
	public void setMasterClasses(Collection<MasterClass> masterClasses) {
		this.masterClasses = masterClasses;
	}
	
}
