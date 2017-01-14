package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import domain.RelationLike;

@Repository
public interface RelationLikeRepository extends JpaRepository<RelationLike, Integer>{

	@Query("select rl from RelationLike rl where rl.recipe.id=?1")
	Collection<RelationLike> recipesLikes(int recipeId);
}
