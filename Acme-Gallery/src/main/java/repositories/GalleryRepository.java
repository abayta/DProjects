package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Gallery;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Integer> {

	// Free Services. FR-1
	// List the customer's galleries.
	@Query("select g from Gallery g where g.customer.id = ?1")
	Collection<Gallery> findAllOfCustomer(int customerId);
	
	// Free Services. FR-7
	// List the most expensive galleries.
	@Query("select p.gallery, sum(p.appraisal) from Painting p group by p.gallery having sum(p.appraisal) >= all (select sum(p.appraisal) from Painting p group by p.gallery)")
	Collection<Object[]> findMostExpensive();

}
