package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;



@Entity
@Access(AccessType.PROPERTY)
public class Nutritionist extends SocialActor{
	
	// Constructors -----------------------------------------------------------

		public Nutritionist() {
			super();
		}
		
	// Attributes -------------------------------------------------------------
		
		
	// Relationships ----------------------------------------------------------
		
		private Curricula curricula;
		private Collection<Comment> comments;	

		@Valid
		@OneToOne(optional=true)
		public Curricula getCurricula() {
			return curricula;
		}
		public void setCurricula(Curricula curricula) {
			this.curricula = curricula;
		}

		@Valid
		@OneToMany(mappedBy="nutritionist")
		public Collection<Comment> getComments() {
			return comments;
		}
		public void setComments(Collection<Comment> comments) {
			this.comments = comments;
		}

}
