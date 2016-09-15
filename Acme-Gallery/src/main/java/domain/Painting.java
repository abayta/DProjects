package domain;

import java.util.Collection;

import java.util.Date;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Painting extends DomainEntity {

	private String title;
	private String author;
	private Date date;
	private double appraisal;

	private Collection<Ownership> ownerships;
	private Collection<Auction> auctions;
	private Gallery gallery;
	private Collection<Comment> comments;

	public Painting() {
		super();
		ownerships = new HashSet<Ownership>();
		auctions = new HashSet<Auction>();
		comments = new HashSet<Comment>();
	}

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Min(0)
	@Digits(integer = 8, fraction = 2)
	public double getAppraisal() {
		return appraisal;
	}

	public void setAppraisal(double appraisal) {
		this.appraisal = appraisal;
	}

	@NotEmpty
	@Valid

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "painting")
	public Collection<Ownership> getOwnerships() {
		return ownerships;
	}

	public void setOwnerships(Collection<Ownership> ownerships) {
		this.ownerships = ownerships;
	}

	public void addOwnership(Ownership ownership) {
		ownership.setPainting(this);
		ownerships.add(ownership);
	}

	public void removeOwnership(Ownership ownership) {
		ownerships.remove(ownership);
		ownership.setPainting(null);
	}
	
	@Valid
	@OneToMany(mappedBy = "painting")
	public Collection<Auction> getAuctions() {
		return auctions;
	}

	public void setAuctions(Collection<Auction> auctions) {
		this.auctions = auctions;
	}
	
	public void addAuction(Auction auction) {
		auctions.add(auction);
		auction.setPainting(this);
	}

	public void removeAuction(Auction auction) {
		auctions.remove(auction);
		auction.setPainting(null);
	}

	@Valid
	@ManyToOne(optional = true)
	public Gallery getGallery() {
		return gallery;
	}

	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}

	@Valid
	@OneToMany(mappedBy = "painting")
	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
		comment.setPainting(this);
	}

	public void removeComment(Comment comment) {
		comments.remove(comment);
		comment.setPainting(null);
	}

}
