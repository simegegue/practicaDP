package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Review extends DomainEntity{
	// Constructors -----------------------------------------------------------
	public Review(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	private String title;
	private String text;
	private String valoration;
	private Date authoredMoment;
	
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@NotBlank
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@NotBlank
	public String getValoration() {
		return valoration;
	}
	public void setValoration(String valoration) {
		this.valoration = valoration;
	}
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getAuthoredMoment() {
		return authoredMoment;
	}
	public void setAuthoredMoment(Date authoredMoment) {
		this.authoredMoment = authoredMoment;
	}
	
	// Relationships -----------------------------------------------------------
	private Critic critic;
	private Recipe recipe;
	
	@Valid
	@ManyToOne(optional=false)
	public Critic getCritic() {
		return critic;
	}
	
	public void setCritic(Critic critic) {
		this.critic = critic;
	}
	@Valid
	@ManyToOne(optional=false)
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	
}
