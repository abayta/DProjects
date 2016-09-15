package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Bid;

@Repository
public interface BidRepository extends JpaRepository<Bid, Integer> {

	// Free Services. FR-7
	// Find the highest bid,.
	@Query("select b from Bid b where b.moneyAmount = (select max(b.moneyAmount) from Bid b)")
	Collection<Bid> findHighest();

	@Query("select b from Bid b where b.auction.id=?1 and b.customer.id=?2")
	Bid findByCustomerAndAuction(int auctionId, int customerId);
	
	@Query("select b from Bid b where b.auction.id=?1")
	Collection<Bid> findByAuction(int auctionId);
	
}
