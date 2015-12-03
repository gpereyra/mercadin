package mercadin.model;

import java.math.BigDecimal;
import java.util.Date;

public class Ask extends Posture {

	public Ask(Date t, EconomicAgent a, Product p, Quote q, BigDecimal quantity) {
		super(t, a, p, q, quantity);
	}

	@Override
	protected void accept(Market m) {
		m.placeAsk(this);
	}

	@Override
	protected Deal match(Market m) {
		return m.matchAsk(this);
	}
	

}
