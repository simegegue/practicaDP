package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;
import domain.Recipe;
import domain.User;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer>{
	
	@Query("select distinct c.recipes from Category c order by c")
	Collection<Recipe>listByCategory();
	
	@Query("select r from Recipe r where r.ticker like %?1% or r.title like %?1% or r.summary like %?1%")
	Collection<Recipe> findByKey(String key);
	
	@Query("select r from Recipe r where r.user=?1")
	Collection<Recipe> findByCreator(User user);
	
	@Query("select r.categories from Recipe r where r=?1")
	Collection<Category> findCategoriesByRecipe(Recipe r);
	
	// Administrator dashboard -----------------------------------------------------------
	
	@Query("select avg(r.steps.size) from Recipe r")
	Double findAvgStepsPerRecipe();
	
	@Query("select stddev(r.steps.size) from Recipe r")
	Double findStandardDeviationStepsPerRecipe();
	
	@Query("select avg(r.quantities.size) from Recipe r")
	Double findAvgIngredientsPerRecipe();
	
	@Query("select stddev(r.quantities.size) from Recipe r")
	Double findSandardDeviationIngredientsPerRecipe();
	
}
	