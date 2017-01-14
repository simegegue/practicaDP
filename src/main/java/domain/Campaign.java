package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;



@Entity
@Access(AccessType.PROPERTY)
public class Campaign extends DomainEntity{
	
	// Constructor --------------------------------------------------
	
	public Campaign(){
		super();
	}
	
	// Attributes ---------------------------------------------------
	
	private Date startDate;
	private Date endDate;
	private boolean starred;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public boolean getStarred() {
		return starred;
	}
	public void setStarred(boolean starred) {
		this.starred = starred;
	}	
	
	// Relationships ------------------------------------------------

	private Sponsor sponsor;
	private Collection<Banner> banners;
	
	@OneToMany(mappedBy="campaign")
	public Collection<Banner> getBanners() {
		return banners;
	}
	public void setBanners(Collection<Banner> banners) {
		this.banners = banners;
	}
	@Valid
	@ManyToOne(optional=false)
	public Sponsor getSponsor() {
		return sponsor;
	}
	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}
	
}
