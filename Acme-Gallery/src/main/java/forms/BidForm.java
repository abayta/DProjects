package forms;


import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import domain.Auction;

public class BidForm {
	
	private double moneyAmount;
	private Auction auction;
	private int id;
	private int version;

	
	public BidForm() {
		super();
	}

	@Min(0)
	@Digits(integer = 8, fraction = 2)
	public double getMoneyAmount() {
		return moneyAmount;
	}

	public void setMoneyAmount(double moneyAmount) {
		this.moneyAmount = moneyAmount;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Auction getAuction() {
		return auction;
	}

	public void setAuction(Auction auction) {
		this.auction = auction;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
