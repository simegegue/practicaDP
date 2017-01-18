package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Recipe extends DomainEntity{
	
	// Constructors -----------------------------------------------------------
	
	public Recipe(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	private String ticker;
	private String title;
	private String summary;
	private Date authoredMoment;
	private Date updateMoment;
	private String picture;
	private String hint;
	private Boolean qualified;
	
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp="^(\\d{6})[-]([a-zA-Z]{4})")
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotBlank
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
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
	
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getUpdateMoment() {
		return updateMoment;
	}
	public void setUpdateMoment(Date updateMoment) {
		this.updateMoment = updateMoment;
	}
	
	@URL
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public String getHint(){
		return hint;
	}
	public void setHint(String hint){
		this.hint = hint;
	}
	
	public Boolean getQualified(){
		return qualified;
	}
	public void setQualified(Boolean qualified){
		this.qualified = qualified;
	}
	
	// Relationships ----------------------------------------------------------
	
	private User user;
	private Collection<Comment> comments;
	private Collection<Category> categories;
	private Collection<Step> steps;
	private Collection<Quantity> quantities;
	private Collection<RelationLike> relationLikes;
	private Collection<RelationDislike> relationDislikes;
	private Collection<Review> reviews;
	
	@Valid
	@OneToMany(mappedBy="recipe")
	public Collection<Review> getReviews() {
		return reviews;
	}
	public void setReviews(Collection<Review> reviews) {
		this.reviews = reviews;
	}
	@OneToMany(mappedBy="recipe")
	public Collection<RelationLike> getRelationLikes() {
		return relationLikes;
	}
	
	public void setRelationLikes(Collection<RelationLike> relationLikes) {
		this.relationLikes = relationLikes;
	}
	
	@OneToMany(mappedBy="recipe")
	public Collection<RelationDislike> getRelationDislikes() {
		return relationDislikes;
	}
	
	public void setRelationDislikes(Collection<RelationDislike> relationDislikes) {
		this.relationDislikes = relationDislikes;
	}
	
	
	@Valid
	@OneToMany(mappedBy="recipe")
	public Collection<Quantity> getQuantities() {
		return quantities;
	}
	
	public void setQuantities(Collection<Quantity> quantities) {
		this.quantities = quantities;
	}

	@Valid
	@ManyToOne(optional=false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	@Valid
	@OneToMany(mappedBy="recipe", cascade=CascadeType.ALL)
	public Collection<Comment> getComments() {
		return comments;
	}
	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

	@Valid
	@ManyToMany
	public Collection<Category> getCategories() {
		return categories;
	}
	public void setCategories(Collection<Category> categories) {
		this.categories = categories;
	}

	@Valid
	@OneToMany(mappedBy="recipe", cascade=CascadeType.ALL)
	public Collection<Step> getSteps() {
		return steps;
	}
	public void setSteps(Collection<Step> steps) {
		this.steps = steps;
	}

	
}
