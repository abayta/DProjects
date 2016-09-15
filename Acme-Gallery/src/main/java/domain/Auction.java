package domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Index;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {@Index(columnList = "startPeriod"),@Index(columnList = "endPeriod")})
public class Auction extends DomainEntity {

	private Date startPeriod;
	private Date endPeriod;
	private double startingPrice;

	private Customer creator;
	private Bid winner;
	private Collection<Bid> bids;
	private Painting painting;

	public Auction() {
		super();
		bids = new HashSet<Bid>();
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartPeriod() {
		return startPeriod;
	}

	public void setStartPeriod(Date startPeriod) {
		this.startPeriod = startPeriod;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndPeriod() {
		return endPeriod;
	}

	public void setEndPeriod(Date endPeriod) {
		this.endPeriod = endPeriod;
	}

	@Min(0)
	@Digits(integer = 8, fraction = 2)
	public double getStartingPrice() {
		return startingPrice;
	}

	public void setStartingPrice(double startingPrice) {
		this.startingPrice = startingPrice;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Customer getCreator() {
		return creator;
	}

	public void setCreator(Customer creator) {
		this.creator = creator;
	}

	@Valid
	@OneToOne(optional = true)
	public Bid getWinner() {
		return winner;
	}

	public void setWinner(Bid winner) {
		this.winner = winner;
	}

	@Valid
	@OneToMany(mappedBy = "auction")
	public Collection<Bid> getBids() {
		return bids;
	}

	public void setBids(Collection<Bid> bids) {
		this.bids = bids;
	}

	public void addBid(Bid bid) {
		bids.add(bid);
		bid.setAuction(this);
	}

	public void removeBid(Bid bid) {
		bids.remove(bid);
		bid.setAuction(null);
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Painting getPainting() {
		return painting;
	}

	public void setPainting(Painting painting) {
		this.painting = painting;
	}

}
