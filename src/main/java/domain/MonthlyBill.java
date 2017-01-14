package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class MonthlyBill extends DomainEntity{
	
	// Constructor -------------------------------------------
	
	public MonthlyBill(){
		super();
	}
	
	// Attributes --------------------------------------------
	
	private Date createMoment;
	private Date payMoment;
	private double cost;
	private String description;
	
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCreateMoment() {
		return createMoment;
	}
	public void setCreateMoment(Date createMoment) {
		this.createMoment = createMoment;
	}
	
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getPayMoment() {
		return payMoment;
	}
	public void setPayMoment(Date payMoment) {
		this.payMoment = payMoment;
	}
	
	@Min(0)
	@Digits(fraction = 2, integer = 3)
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	@NotBlank
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	// Relationship ------------------------------------------
	
	private Sponsor sponsor;

	@Valid
	@ManyToOne(optional=false)
	public Sponsor getSponsor() {
		return sponsor;
	}
	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}
	
}
