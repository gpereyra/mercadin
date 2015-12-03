package mercadin.model;

import java.math.BigDecimal;

public class Quote {
	public Quote(double d) {
		this.price = new BigDecimal(d);
	}

	BigDecimal price;
}
