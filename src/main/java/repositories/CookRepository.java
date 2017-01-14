package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Cook;

@Repository
public interface CookRepository extends JpaRepository<Cook, Integer>{
	
	// Admin dashboard ----------------------------------------------------------
		
	@Query("select c from Cook c where c.userAccount.id = ?1")
	Cook findByUserAccountId(int id);
	
	@Query("select mc1.cook as c from MasterClass mc1 where mc1.promoted=true group by mc1.cook order by count(mc1) DESC")
	Collection<Cook> listCooksByMasterClassesPromoted();
}
