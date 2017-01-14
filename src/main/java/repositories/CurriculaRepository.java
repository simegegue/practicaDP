package repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;

import domain.Curricula;

@Repository
public interface CurriculaRepository extends JpaRepository<Curricula, Integer>{
	@Query("select n.curricula from Nutritionist n where n.userAccount = ?1")
	Curricula findByUserAccount(UserAccount userAccount);
	
}
