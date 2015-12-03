package mercadin.model;

import java.math.BigDecimal;
import java.util.Date;


public abstract class Posture {
	Date time;
	EconomicAgent agent;
	Product p;
	Quote q;
	BigDecimal quantity;
	BigDecimal originalQuantity;
	

	public Posture(Date t, EconomicAgent a, Product p, Quote q, BigDecimal qtty) {
		this.q = q;
		this.p = p;
		this.agent = a;
		this.time = t;
		this.originalQuantity = qtty;
		this.quantity = qtty;
	}
	
	protected abstract void accept(Market m);

	protected abstract Deal match(Market market);

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	
	
}
