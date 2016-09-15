package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

	// Free Services. FR-3
	// List the groups managed by Acme Projects, Inc.
	@Query("select g from Group g")
	Collection<Group> findAllToAdministrator();
	
}
