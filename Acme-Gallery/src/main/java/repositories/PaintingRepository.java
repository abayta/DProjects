package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Painting;

@Repository
public interface PaintingRepository extends JpaRepository<Painting, Integer> {

	// Free Services. FR-1
	// List the customer's paintings.
	@Query("select o.painting from Ownership o where o.legalOwner.id = ?1 and o.endMoment = null")
	Collection<Painting> findAllOfCustomer(int customerId);
	
	// Free Services. FR-11
	// List the customer's paintings without gallery.
	@Query("select o.painting from Ownership o where o.legalOwner.id = ?1 and o.painting.gallery.id = null")
	Collection<Painting> findAllWithoutGalleryOfCustomer(int customerId);

	// Free Services. FR-10
	// List the paintings of a gallery
	@Query("select g.paintings from Gallery g where g.id = ?1")
	Collection<Painting> findAllOfGallery(int galleryId);
	

	// Free Services. FR-21
	// Find the painting/s that has/have the majority of comments.
	@Query("select p from Painting p where p.comments.size = (select max(p.comments.size) from Painting p)")
	Collection<Painting> findWithMoreComments();
	
}
