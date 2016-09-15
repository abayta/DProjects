package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	private CreditCard creditCard;

	private Collection<Gallery> galleries;
	private Collection<Ownership> ownerships;
	private Collection<Comment> comments;
	private Collection<Auction> auctions;
	private Collection<Bid> bids;

	public Customer() {
		super();
		galleries = new HashSet<Gallery>();
		ownerships = new HashSet<Ownership>();
		comments = new HashSet<Comment>();
		auctions = new HashSet<Auction>();
		bids = new HashSet<Bid>();
	}

	@NotNull
	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<Gallery> getGalleries() {
		return galleries;
	}

	public void setGalleries(Collection<Gallery> galleries) {
		this.galleries = galleries;
	}
	
	public void addGallery(Gallery gallery) {
		galleries.add(gallery);
		gallery.setCustomer(this);
	}

	public void removeGallery(Gallery gallery) {
		galleries.remove(gallery);
		gallery.setCustomer(null);
	}

	@Valid
	@OneToMany(mappedBy = "legalOwner")
	public Collection<Ownership> getOwnerships() {
		return ownerships;
	}

	public void setOwnerships(Collection<Ownership> ownerships) {
		this.ownerships = ownerships;
	}

	public void addOwnership(Ownership ownership) {
		ownerships.add(ownership);
		ownership.setLegalOwner(this);
	}

	public void removeOwnership(Ownership ownership) {
		ownerships.remove(ownership);
		ownership.setLegalOwner(null);
	}
	
	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

	public void addComment(Comment comment) {
		comments.add(comment);
		comment.setCustomer(this);
	}

	public void removeComment(Comment comment) {
		comments.remove(comment);
		comment.setCustomer(null);
	}
	
	@Valid
	@OneToMany(mappedBy = "creator")
	public Collection<Auction> getAuctions() {
		return auctions;
	}

	public void setAuctions(Collection<Auction> auctions) {
		this.auctions = auctions;
	}

	public void addAuction(Auction auction) {
		auctions.add(auction);
		auction.setCreator(this);
	}

	public void removeAuction(Auction auction) {
		auctions.remove(auction);
		auction.setCreator(null);
	}
	
	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<Bid> getBids() {
		return bids;
	}

	public void setBids(Collection<Bid> bids) {
		this.bids = bids;
	}

	public void addBid(Bid bid) {
		bids.add(bid);
		bid.setCustomer(this);
	}

	public void removeBid(Bid bid) {
		bids.remove(bid);
		bid.setCustomer(null);
	}
	
}
