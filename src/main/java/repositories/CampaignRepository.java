package repositories;



import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import domain.Campaign;
import domain.Sponsor;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer>{
	
	@Query("select c from Campaign c where c.sponsor=?1")
	Collection<Campaign> findCampaignSponsor(Sponsor sponsor);

	@Query("select c from Campaign c where c.starred = true")
	Collection<Campaign> findCampaignStarred();
		
}
