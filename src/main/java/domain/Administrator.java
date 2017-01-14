package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Administrator extends Actor {

	// Constructors -----------------------------------------------------------

	public Administrator() {
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	// Relationships ----------------------------------------------------------

	private Collection<SpamWord> spamWords;
	private Collection<Category> categories;
	
	@Valid
	@OneToMany(mappedBy="administrator")
	public Collection<SpamWord> getSpamWords(){
		return spamWords;
	}
	public void setSpamWords(Collection<SpamWord> spamWords){
		this.spamWords = spamWords;
	}

	@Valid
	@OneToMany(mappedBy="administrator")
	public Collection<Category> getCategories(){
		return categories;
	}
	public void setCategories(Collection<Category> categories){
		this.categories = categories;
	}
	
	
}