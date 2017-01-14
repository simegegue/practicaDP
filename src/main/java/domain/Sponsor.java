package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.Entity;
import javax.persistence.AccessType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Access(AccessType.PROPERTY)
public class Sponsor extends Actor{
	
	// Constructors -----------------------------------------------------------

	public Sponsor() {
		super();
	}
		
	// Attributes -------------------------------------------------------------
	
	private String companyName;
	private CreditCard creditCard;
	private Date lastTimeManages;
	
	@Past
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getLastTimeManages() {
		return lastTimeManages;
	}
	public void setLastTimeManages(Date lastTimeManages) {
		this.lastTimeManages = lastTimeManages;
	}
	@NotBlank
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	
		
	// Relationships ----------------------------------------------------------
	
	private Collection<MonthlyBill> monthlyBills;
	private Collection<Campaign> campaigns;

	@Valid
	@OneToMany(mappedBy="sponsor")
	public Collection<MonthlyBill> getMonthlyBills() {
		return monthlyBills;
	}
	public void setMonthlyBills(Collection<MonthlyBill> monthlyBills) {
		this.monthlyBills = monthlyBills;
	}

	@Valid
	@OneToMany(mappedBy="sponsor")
	public Collection<Campaign> getCampaigns() {
		return campaigns;
	}
	public void setCampaigns(Collection<Campaign> campaigns) {
		this.campaigns = campaigns;
	}
	
	
}
