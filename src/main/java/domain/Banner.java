package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.URL;



@Entity
@Access(AccessType.PROPERTY)
public class Banner extends DomainEntity{
	//Constructor--------------------------------------------
	
	public Banner(){
		super();
	}
	
	//Attributes---------------------------------------------
	private int maxDisplay;
	private int displayed;
	private String image;
		
	@Min(1)
	public int getMaxDisplay() {
		return maxDisplay;
	}
	public void setMaxDisplay(int maxDisplay) {
		this.maxDisplay = maxDisplay;
	}
	
	@Min(0)
	public int getDisplayed() {
		return displayed;
	}
	public void setDisplayed(int displayed) {
		this.displayed = displayed;
	}
	
	@URL
	public String getImage(){
		return image;
	}
	public void setImage(String image){
		this.image = image;
	}
	
	//Relationships------------------------------------------
	
	private Campaign campaign;
	
	@ManyToOne(optional=false)
	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
	

}
