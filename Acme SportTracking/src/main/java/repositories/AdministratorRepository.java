package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	//Find a administrator from userAccount.id
	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findByUserAccountId(int userAccountId);
	
	//Sum all rating from assessmentRoutes of a route that is not null
	@Query("select sum(a.assessment.rating) from AssessmentRoute a where a.route.id=?1")
	Long sumaRatingRoute(int routeId);
	
	//Count all rating from assessmentRotes of a route that is not null
	@Query("select count(a.assessment.rating) from AssessmentRoute a where a.route.id=?1")
	Long numberRatingRoute(int routeId);
	
	//Sum all rating from registration of a event that is not null 
	@Query("select sum(r.assessment.rating) from Registration r where r.event.id=?1")
	Long sumaRatingEvent(int eventId);
	
	//Count all rating from registration of a event that is not null
	@Query("select count(r.assessment.rating) from Registration r where r.event.id=?1")
	Long numberRatingEvent(int eventId);
	
	
	
}
