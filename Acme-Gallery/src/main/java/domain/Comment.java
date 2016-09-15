package domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

	private Date creationMoment;
	private String text;

	private Customer customer;
	private Painting painting;
	private Comment parent;
	private Collection<Comment> children;

	public Comment() {
		super();
		children = new HashSet<Comment>();
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCreationMoment() {
		return creationMoment;
	}

	public void setCreationMoment(Date creationMoment) {
		this.creationMoment = creationMoment;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@NotBlank
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	@Valid
	@ManyToOne(optional = true)
	public Comment getParent() {
		return parent;
	}

	public void setParent(Comment parent) {
		this.parent = parent;
	}

	@Valid
	@OneToMany(mappedBy = "parent")
	public Collection<Comment> getChildren() {
		return children;
	}

	public void setChildren(Collection<Comment> children) {
		this.children = children;
	}
	
	public void addComment(Comment comment) {
		children.add(comment);
		comment.setParent(this);
	}

	public void removeComment(Comment comment) {
		children.remove(comment);
		comment.setParent(null);
	}

}
