package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LearningMaterial;

@Repository
public interface LearningMaterialRepository extends JpaRepository<LearningMaterial, Integer>{

	// Admin dashboard --------------------------------------------------------------------
	
	@Query("select count(l)/(select count(m) from MasterClass m)*(1.0) from LearningMaterial l group by l.class")
	Collection<Double> avgLearningMaterials();
	
}
