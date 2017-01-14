package repositories;



import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import security.UserAccount;


import domain.SocialIdentity;

@Repository
public interface SocialIdentityRepository extends JpaRepository<SocialIdentity, Integer>{
	@Query("select s from SocialIdentity s where s.actor.userAccount = ?1")
	Collection<SocialIdentity> findByUserAccount(UserAccount userAccount);
	
	
}
