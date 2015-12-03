package mercadin.model;

import java.util.Date;

public class Deal {
	public Deal(Bid b, Ask a) {
		time = new Date();
		bid = b;
		ask = a;
	}
	private Date time;
	private Bid bid;
	private Ask ask;
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Bid getBid() {
		return bid;
	}
	public void setBid(Bid bid) {
		this.bid = bid;
	}
	public Ask getAsk() {
		return ask;
	}
	public void setAsk(Ask ask) {
		this.ask = ask;
	}
	
	
}
