package mercadin.model;

import java.math.BigDecimal;
import java.util.Date;

public class Bid extends Posture {

	public Bid(Date t, EconomicAgent a, Product p, Quote q, BigDecimal quantity) {
		super(t, a, p, q, quantity);
	}

	@Override
	protected void accept(Market m) {
		m.placeBid(this);
	}

	@Override
	protected Deal match(Market m) {
		return m.matchBid(this);
	}

}
