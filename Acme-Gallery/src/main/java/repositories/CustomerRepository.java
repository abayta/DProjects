package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	// Find customer by userAccount.id
	@Query("select c from Customer c where c.userAccount.id = ?1")
	Customer findOneByUserAccount(int userAccountId);

	// Free Services. FR-7
	// Find the customer with more paintings.
	@Query("select o.legalOwner from Ownership o group by o.legalOwner having (select count(u) from Ownership u where o.legalOwner.id=u.legalOwner.id and u.endMoment is null) >= all (select count(o) from Ownership o where o.endMoment is null group by o.legalOwner)")
	Collection<Customer> findWithMorePaintings();
	
	// Free Services. FR-21
	// Find the customer/s who has/have created the majority of comments.
	@Query("select c from Customer c where c.comments.size = (select max(c.comments.size) from Customer c)")
	Collection<Customer> findWithMoreComments();
	
	//Find legalOwner 
	@Query("select o.legalOwner from Ownership o where o.painting.id = ?1 and o.endMoment = null")
	Customer findCustomer(int paintingId);

}
