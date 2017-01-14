package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ManyToMany;


import javax.persistence.Entity;


import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class SocialActor extends Actor{
	
	// Constructor ----------------------------------------------------
	
	public SocialActor(){
		super();
	}
	
	// Attributes -----------------------------------------------------
	
	// Relationships --------------------------------------------------
	
	private Collection<SocialActor> following;
	private Collection<SocialActor> followers;
	private Collection<RelationLike> relationLikes;
	private Collection<RelationDislike> relationDislikes;
	
	@OneToMany(mappedBy="socialActor")
	public Collection<RelationLike> getRelationLikes() {
		return relationLikes;
	}
	public void setRelationLikes(Collection<RelationLike> relationLikes) {
		this.relationLikes = relationLikes;
	}
	@OneToMany(mappedBy="socialActor")
	public Collection<RelationDislike> getRelationDislikes() {
		return relationDislikes;
	}
	public void setRelationDislikes(Collection<RelationDislike> relationDislikes) {
		this.relationDislikes = relationDislikes;
	}
	

	
	@Valid
	@ManyToMany(mappedBy="followers")
	public Collection<SocialActor> getFollowing() {
		return following;
	}
	
	public void setFollowing(Collection<SocialActor> following) {
		this.following = following;
	}

	@Valid
	@ManyToMany
	public Collection<SocialActor> getFollowers() {
		return followers;
	}
	public void setFollowers(Collection<SocialActor> followers) {
		this.followers = followers;
	}

	
	

	
}
