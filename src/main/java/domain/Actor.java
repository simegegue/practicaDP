package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import security.UserAccount;
import javax.persistence.CascadeType;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {

	// Constructors -----------------------------------------------------------

	// Attributes -------------------------------------------------------------

	private String name;
	private String surname;
	private String email;
	private String phone;
	private String postalAddress;

	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@Email
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Pattern(regexp = "^([+]\\d{1,3})?[ ]?([(]\\d{3}[)])?[ ]?([0-9A-Za-z _]{4,})?")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPostalAddress() {
		return postalAddress;
	}
	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	// Relationships ----------------------------------------------------------

	private UserAccount userAccount;
	private Collection<SocialIdentity> socialIdentities;
	private Collection<Message> sendedMessages;
	private Collection<Message> receivedMessages;
	
	@Valid
	@OneToMany(mappedBy="actor")
	public Collection<SocialIdentity> getSocialIdentities() {
		return socialIdentities;
	}
	public void setSocialIdentities(Collection<SocialIdentity> socialIdentities) {
		this.socialIdentities = socialIdentities;
	}

	@Valid
	@OneToMany(mappedBy="sender")
	public Collection<Message> getSendedMessages() {
		return sendedMessages;
	}
	public void setSendedMessages(Collection<Message> sendedMessages) {
		this.sendedMessages = sendedMessages;
	}

	@Valid
	@OneToMany(mappedBy="recipient")
	public Collection<Message> getReceivedMessages() {
		return receivedMessages;
	}
	public void setReceivedMessages(Collection<Message> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}

	@Valid
	@OneToOne(cascade=CascadeType.ALL,optional = false)
	public UserAccount getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
}
