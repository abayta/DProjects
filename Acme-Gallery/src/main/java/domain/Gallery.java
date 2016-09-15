package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Gallery extends DomainEntity {

	private String title;

	private Customer customer;
	private Collection<Painting> paintings;

	public Gallery() {
		super();
		paintings = new HashSet<Painting>();
	}

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	@Valid
	@OneToMany(mappedBy = "gallery", cascade = { CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	public Collection<Painting> getPaintings() {
		return paintings;
	}

	public void setPaintings(Collection<Painting> paintings) {
		this.paintings = paintings;
	}
	
	public void addPainting(Painting painting) {
		paintings.add(painting);
		painting.setGallery(this);
	}

	public void removePainting(Painting painting) {
		paintings.remove(painting);
		painting.setGallery(null);
	}
	
}
