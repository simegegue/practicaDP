package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Critic;
import domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>{

		@Query("select 1.0*(select count(r) from Review r where valoration like 'positive')/count(rT) from Review rT")
		Double ratioPositiveReview();
		
		@Query("select r from Review r where r.critic=?1")
		Collection<Review> findByCreator(Critic critic);
}
