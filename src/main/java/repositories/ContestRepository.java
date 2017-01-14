package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Contest;

@Repository
public interface ContestRepository extends JpaRepository<Contest, Integer>{

	// Admin dashboard queries ---------------------------------------------------
	
	@Query("select min(c.qualifies.size) from Contest c")
	Integer findMinRecipesPerContest();
	
	@Query("select avg(c.qualifies.size) from Contest c")
	Double findAvgRecipesPerContest();
	
	@Query("select max(c.qualifies.size) from Contest c")
	Integer findMaxRecipesPerContest();
	
	@Query("select c from Contest c where c.qualifies.size = (select max(c.qualifies.size) from Contest c)")
	Collection<Contest> findContestMoreRecipes();

	@Query("select c from Contest c where c.closingTime>current_date")
	Collection<Contest> findContestOpened();
}
