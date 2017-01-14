package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query("select u from User u where u.name like %?1%")
	Collection<User> findByKey(String key);
	
	@Query("select a from User a where a.userAccount.id=?1")
	User findByUserAccountId(int id);
	
	// Administrator dashboard -----------------------------------------------------------
	
	@Query("select u from User u where (u.recipes.size=(select max(u.recipes.size) from User u))")
	Collection<User> usersMoreRecipesAuthored();
	
	@Query("select u from User u Order by u.followers.size DESC")
	Collection<User> usersByPopularity();
	
	@Query("select u from User u order by ((select avg(r.relationLikes.size) from Recipe r where r.user=u) + (select avg(r.relationDislikes.size) from Recipe r where r.user=u))/2 DESC")
	Collection<User> usersAvgLikesDislikes();
	
	@Query("select min(u.recipes.size) from User u")
	Integer findMinRecipesPerUser();
	
	@Query("select avg(u.recipes.size) from User u")
	Double findAvgRecipesPerUser();
	
	@Query("select max(u.recipes.size) from User u")
	Integer findMaxRecipesPerUser();
}


