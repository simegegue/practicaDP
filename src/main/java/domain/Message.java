package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity{
	
	// Constructors -----------------------------------------------------------
	
	public Message(){
		super();
	}
	
	// Attributes -------------------------------------------------------------

	private Date moment;
	private String subject;
	private String body;
	private String priority;
	private boolean spam;
	
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	@NotNull
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@NotNull
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	@NotBlank
	@Pattern(regexp = "^LOW$|^NEUTRAL$|^HIGH$")
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public boolean getSpam() {
		return spam;
	}
	public void setSpam(boolean spam) {
		this.spam = spam;
	}

			
	// Relationships ----------------------------------------------------------

	private Folder folder;
	private Actor sender;
	private Actor recipient;

	@Valid
	@ManyToOne(optional=false)
	public Actor getSender() {
		return sender;
	}
	public void setSender(Actor sender) {
		this.sender = sender;
	}

	@Valid
	@ManyToOne(optional=false)
	public Actor getRecipient() {
		return recipient;
	}
	public void setRecipient(Actor recipient) {
		this.recipient = recipient;
	}

	@Valid
	@ManyToOne(optional=false)
	public Folder getFolder(){
		return folder;
	}
	public void setFolder(Folder folder){
		this.folder = folder;
	}
}
