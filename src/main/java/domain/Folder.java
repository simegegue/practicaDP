package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Folder extends DomainEntity{
	
	// Constructors -----------------------------------------------------------
	
	public Folder(){
		super();
	}
	
	// Attributes -------------------------------------------------------------

	private String name;
	private boolean predefined;
	
	public boolean getPredefined() {
		return predefined;
	}
	public void setPredefined(boolean predefined) {
		this.predefined = predefined;
	}

	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
			
	// Relationships ----------------------------------------------------------
	
	private Actor actor;
	private Collection<Message> messages;

	@Valid
	@ManyToOne(optional=false)
	public Actor getActor() {
		return actor;
	}
	public void setActor(Actor actor) {
		this.actor = actor;
	}

	@Valid
	@OneToMany(mappedBy="folder")
	public Collection<Message> getMessages(){
		return messages;
	}
	public void setMessages(Collection<Message> messages){
		this.messages = messages;
	}
}
