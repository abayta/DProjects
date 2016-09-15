package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Auction;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Integer> {
	
	//RF 2
	//List active auction
	@Query("select a from Auction a where a.startPeriod<CURRENT_DATE and a.endPeriod>CURRENT_DATE")
	Collection<Auction> findActiveAuctions();
	
	// Free Services. FR-7
	// Find the auction with more bids.
	@Query("select a from Auction a where a.bids.size = (select max(a.bids.size) from Auction a)")
	Collection<Auction> findWithMoreBids();
	
	//RF 2
	//List active auction the order customer
	@Query("select a from Auction a where a.startPeriod<CURRENT_DATE and a.endPeriod>CURRENT_DATE and a.creator.id!=?1")
	Collection<Auction> findActiveAuctionsOtherCustomer(int customerId);
	
	//RF 14
	//Find auction by painting
	@Query("select a from Auction a where a.painting.id=?1 and a.endPeriod>CURRENT_DATE")
	Auction findByPaintingFinished(int paintingId);
	
	//RF 6
	//Find finished Auction
	@Query("select a from Auction a where a.endPeriod<CURRENT_DATE")
	Collection<Auction> findFinishedAuction();
	
	// Free Services. FR-9
	// List the customer's paintings without active auctions.
	@Query("select a from Auction a where a.painting.id = ?1 and a.startPeriod <= CURRENT_DATE and a.endPeriod >= CURRENT_DATE")
	Collection<Auction> findAllWithoutActiveAuctions(int paintingId);

	//RF 15
	//Find auctions created by a customer
	@Query("select a from Auction a where a.creator.id=?1")
	Collection<Auction> findCreatedAuction(int id);

}
