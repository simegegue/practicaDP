package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsor;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer>{
	
	@Query("select s.monthlyBills from Sponsor s where s.id=?1")
	Collection<Sponsor> findMonthyBillsBySponsor(Sponsor s);
	
	@Query("select a from Sponsor a where a.userAccount.id=?1")
	Sponsor findByUserAccountId(int id);
	
	// Admin dashboard --------------------------------------------------------
	
	@Query("select min(s.campaigns.size) from Sponsor s")
	Integer findMinCampaignPerSponsor();
	
	@Query("select avg(s.campaigns.size) from Sponsor s")
	Double findAvgCampaignPerSponsor();
	
	@Query("select max(s.campaigns.size) from Sponsor s")
	Integer findMaxCampaignPerSponsor();
	
	@Query("select min(s.campaigns.size) from Sponsor s join s.campaigns c where current_date between c.startDate and c.endDate")
	Integer findMinActiveCampaignPerSponsor();
	
	@Query("select avg(s.campaigns.size) from Sponsor s join s.campaigns c where current_date between c.startDate and c.endDate")
	Double findAvgActiveCampaignPerSponsor();
	
	@Query("select max(s.campaigns.size) from Sponsor s join s.campaigns c where current_date between c.startDate and c.endDate")
	Integer findMaxActiveCampaignPerSponsor();
	
	@Query("select m.sponsor from MonthlyBill m where DAY(current_date)-DAY(m.createMoment)>30 and m.payMoment is null")
	Collection<Sponsor> findSponsorsByUnpaidMonthlyBills();
	
	@Query("select s from Sponsor s order by s.campaigns.size DESC")
	Collection<Sponsor> findCompanyRanking();
	
	@Query("select s from Sponsor s order by s.monthlyBills.size DESC")
	Collection<Sponsor> findCompanyRankingMonthlyBill();
	
	@Query("select s from Sponsor s where s.campaigns.size is NULL or MONTH(current_date)-MONTH(s.lastTimeManages)>3")
	Collection<Sponsor> findInactiveSponsor();
	
	@Query("select s from Sponsor s where ((select sum(m.cost) from MonthlyBill m where m.sponsor.companyName = s.companyName) < (select avg(m1.cost) from MonthlyBill m1))")
	Collection<Sponsor> findCompaniesSpendLessAverage();
	
	@Query("select s from Sponsor s where ((select sum(m.cost) from MonthlyBill m where m.sponsor = s)>= 0.9*(select max(mb.cost) from MonthlyBill mb))")
	Collection<Sponsor> findCompaniesSpendMoreThan90();
	
}
