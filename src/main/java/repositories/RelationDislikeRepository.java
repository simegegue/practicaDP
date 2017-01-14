package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import domain.RelationDislike;

@Repository
public interface RelationDislikeRepository extends JpaRepository<RelationDislike, Integer>{
	
}
