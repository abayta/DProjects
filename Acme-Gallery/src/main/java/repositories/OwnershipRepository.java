package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Ownership;

@Repository
public interface OwnershipRepository extends JpaRepository<Ownership, Integer> {

	// Free Services. FR-5
	// Show the ownership history of every painting.
	@Query("select p.ownerships from Painting p where p.id = ?1")
	Collection<Ownership> findAllByPainting(int paintingId);

	//
	@Query("select o from Ownership o where o.painting.id=?1 and o.legalOwner.id=?2")
	Ownership findByPaintingAndCustomer(int paintingId, int creatorId);
	
	//Ownership by painting and legalOwner
	@Query("select o from Ownership o where o.painting.id=?1 and o.legalOwner.id=?2 and o.endMoment is null")
	Ownership findOneByLegalOwner(int paintingId, int customerId);
	
}
