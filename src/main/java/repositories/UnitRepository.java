package repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import domain.Unit;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Integer>{

		
}
