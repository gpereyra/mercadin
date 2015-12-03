package mercadin.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Market {
	Map<Product,List<Bid>> outstandingBids = new LinkedHashMap<Product,List<Bid>>();
	Map<Product,List<Ask>> outstandingAsks = new LinkedHashMap<Product,List<Ask>>();
	List<Deal> deals;
	
	public synchronized Deal place(Posture p) {
		Deal d = p.match(this);
		if (d == null){
			p.accept(this);
		}
		return d;
	}

	protected Deal matchAsk(Ask a) {
		List<Bid> l = safeGetList(a.p, outstandingBids);
		Bid bestBid = null;
		for (Bid b : l) {
			if (b.q.price.compareTo(a.q.price) >= 0){
				//Ordered by arrival, at equal price, first bid wins
				if(bestBid == null || bestBid.q.price.compareTo(b.q.price) < 0){
					bestBid = b;
				}
			}
		}
		if (bestBid != null){
			updatePostures(l, a, bestBid);
			return new Deal(bestBid, a);
		} else {
			return null;
		}
	}

	protected Deal matchBid(Bid b) {
		List<Ask> l = safeGetList(b.p, outstandingAsks);
		Ask bestAsk = null;
		for (Ask a : l) {
			if (b.q.price.compareTo(a.q.price) >= 0){
				//Ordered by arrival, at equal price, first Ask wins
				if(bestAsk == null || bestAsk.q.price.compareTo(a.q.price) > 0){
					bestAsk = a;
				}
			}
		}
		if (bestAsk != null){
			updatePostures(l, bestAsk, b);
			return new Deal(b, bestAsk);
		} else {
			return null;
		}
	}

	private <T extends Posture> void updatePostures(List<T> l, Ask a, Bid b) {
		//FIXME Support matching the remaining of an unconsumed posture. YEAH!
		if (a.getQuantity().compareTo(b.getQuantity()) < 0){
			l.remove(a);
			b.setQuantity(b.getQuantity().subtract(a.getQuantity()));
		} else if (a.getQuantity().compareTo(b.getQuantity()) > 0) {
			l.remove(b);
			a.setQuantity(a.getQuantity().subtract(b.getQuantity()));
		} else {
			l.remove(a);
			l.remove(b);
		}
	}

	protected void placeBid(Bid b) {
		placeInQueue(b, outstandingBids);
	}

	protected void placeAsk(Ask b) {
		placeInQueue(b, outstandingAsks);
	}

	private <T extends Posture> void  placeInQueue(T b, Map<Product, List<T>> queue) {
		List<T> l = safeGetList(b.p, queue);
		l.add(b);
		queue.put(b.p, l);
	}

	private <T extends Posture> List<T> safeGetList(Product p,
			Map<Product, List<T>> queue) {
		List<T> l = queue.get(p);
		if (l == null){
			l = new ArrayList<T>();
		}
		return l;
	}

	public boolean noMoreBids(Product p) {
		return safeGetList(p, outstandingBids).isEmpty();
	}

	public boolean noMoreAsks(Product p) {
		return safeGetList(p, outstandingAsks).isEmpty();
	}

	
}
