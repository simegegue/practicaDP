package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;

import domain.Actor;
import domain.MasterClass;

@Repository
public interface MasterClassRepository extends JpaRepository<MasterClass, Integer>{

	// Admin dashboard ----------------------------------------------------
	
	@Query("select mc from MasterClass mc where mc.cook.userAccount = ?1")
	Collection<MasterClass> findByUserAccount(UserAccount userAccount);
	
	@Query("select count(mc) from MasterClass mc where mc.promoted = true")
	Integer masterClassesPromoted();
	
	@Query("select min(c.masterClasses.size) from Cook c")
	Integer minMasterClasses();
	
	@Query("select max(c.masterClasses.size) from Cook c")
	Integer maxMasterClasses();
	
	@Query("select avg(c.masterClasses.size) from Cook c")
	Double avgMasterClasses();
	
	@Query("select stddev(c.masterClasses.size) from Cook c")
	Double stdDevMasterClasses();
	
	@Query("select 1.0*(select count(mc) from MasterClass mc where mc.promoted=true)/count(mc1) from MasterClass mc1")
	Double avgMasterClassesPromotedPerCook();
	
	@Query("select 1.0*(select count(mc) from MasterClass mc where mc.promoted=false)/count(mc1) from MasterClass mc1")
	Double avgMasterClassesDemotedPerCook();
	
	@Query("select nom from MasterClass mc join mc.registered nom where mc.id = ?1")
	Collection<Actor> findActorByMasterClass(int masterClassId);
		
}
