package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Contest;
import domain.Qualify;
import domain.Recipe;

@Repository
public interface QualifyRepository extends JpaRepository<Qualify, Integer>{
	
	@Query("select q.recipe from Qualify q where q.contest=?1")
	List<Recipe> findRecipeForContest(Contest contest);
	
	@Query("select q from Qualify q where q.recipe=?1")
	Qualify qualifyRecipe(Recipe recipe);

}
