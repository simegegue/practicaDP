package repositories;



import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import security.UserAccount;

import domain.Endorser;

@Repository
public interface EndorserRepository extends JpaRepository<Endorser, Integer>{
	
	@Query("select n.curricula.endorsers from Nutritionist n where n.userAccount = ?1")
	Collection<Endorser> findByUserAccount(UserAccount userAccount);
	
	
}
