package repositories;



import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer>{
	
	@Query("select f from Folder f where f.actor=?1")
	Collection<Folder> findFoldersByActorId(Actor actor);
	
	@Query("select f from Folder f  where f.actor=?1 and f.name='Inbox'")
	Folder inboxFolder(Actor actor);
	
	@Query("select f from Folder f  where f.actor=?1 and f.name='Outbox'")
	Folder outboxFolder(Actor actor);
	
	@Query("select f from Folder f  where f.actor=?1 and f.name='Trashbox'")
	Folder trashboxFolder(Actor actor);
	
	@Query("select f from Folder f  where f.actor=?1 and f.name='Spambox'")
	Folder spamboxFolder(Actor actor);
	
}
