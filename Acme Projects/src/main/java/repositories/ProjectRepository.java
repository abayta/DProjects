package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

	// Free Services. FR-1
	// List the projects managed by Acme Projects, Inc. as long as they are not
	// finished. The system must show the title of every project, its creation
	// date, and whether new participants can join them or not.
	@Query("select p from Project p where p.state != 'Finished'")
	Collection<Project> findAllActive();
	
	// Free Services. FR-9
	// List the projects in which he or she can participate. These projects are
	// those that have at least one vacancy for the group to which the user
	// belongs.
	@Query("select r.project from Requirement r where r.group.id = ?1 and r.project.state = 'Recruiting'")
	Collection<Project> findAllVacancyByGroup(int groupId);
	
	// Free Services. FR-11
	// List the projects in which he or she participates.
	@Query("select r.project from Registration r where r.user.id = ?1")
	Collection<Project> findAllJoined(int userId);

	// Free Services. FR-14
	// List the projects that he or she has created.
	@Query("select p from Project p where p.creator.id = ?1")
	Collection<Project> findAllCreated(int userId);

	// Ancillary Queries -------------------------------------------------------
	
	// Joined user's number of project.
	@Query("select count(r) from Registration r where r.project.id = ?1")
	long participantsOfProject(int projectId);
	
	// Number of people that requires a project.
	@Query("select sum(r.peopleNumber) from Requirement r where r.project.id = ?1")
	Long numberOfPeopleRequiredByProject(int projectId);
	
}
