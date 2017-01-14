package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.SocialActor;
import domain.User;

@Repository
public interface SocialActorRepository extends JpaRepository<SocialActor, Integer>{

	@Query("select a from SocialActor a where a.userAccount.id=?1")
	User findByUserAccountId(int id);
}
