package mercadin.test;

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.TestCase;
import mercadin.model.Ask;
import mercadin.model.Bid;
import mercadin.model.Deal;
import mercadin.model.EconomicAgent;
import mercadin.model.Market;
import mercadin.model.Product;
import mercadin.model.Quote;

public class TradingTestCase extends TestCase {
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}
	public void testSimpleTradeFirstBid(){
		Market m = new Market();
		Product p = new Product();
		EconomicAgent ea1 = new EconomicAgent();
		EconomicAgent ea2 = new EconomicAgent();
		
		Bid b = new Bid(new Date(), ea1, p, new Quote(1.50), BigDecimal.ONE);
		Ask a = new Ask(new Date(), ea2, p, new Quote(1.50), BigDecimal.ONE);
		
		assertNull(m.place(a));
		Deal d = m.place(b);
		
		assertNotNull(d);
		assertEquals(b, d.getBid());
		assertEquals(a, d.getAsk());
		
		assertTrue(m.noMoreBids(p));
		assertTrue(m.noMoreAsks(p));
	}
	
	public void testSimpleTradeFirstAsk(){
		Market m = new Market();
		Product p = new Product();
		EconomicAgent ea1 = new EconomicAgent();
		EconomicAgent ea2 = new EconomicAgent();
		
		Bid b = new Bid(new Date(), ea1, p, new Quote(1.50), BigDecimal.ONE);
		Ask a = new Ask(new Date(), ea2, p, new Quote(1.50), BigDecimal.ONE);
		
		assertNull(m.place(a));
		Deal d = m.place(b);
		
		assertNotNull(d);
		assertEquals(a, d.getAsk());
		assertEquals(b, d.getBid());

		assertTrue(m.noMoreBids(p));
		assertTrue(m.noMoreAsks(p));
	}
	
	public void testNoTradeFirstAsk(){
		Market m = new Market();
		Product p = new Product();
		EconomicAgent ea1 = new EconomicAgent();
		EconomicAgent ea2 = new EconomicAgent();
		
		Bid b = new Bid(new Date(), ea1, p, new Quote(0.50), BigDecimal.ONE);
		Ask a = new Ask(new Date(), ea2, p, new Quote(1.50), BigDecimal.ONE);
		
		assertNull(m.place(a));
		assertNull(m.place(b));
		
		assertFalse(m.noMoreBids(p));
		assertFalse(m.noMoreAsks(p));
	}
	
	public void testNoTradeFirstBid(){
		Market m = new Market();
		Product p = new Product();
		EconomicAgent ea1 = new EconomicAgent();
		EconomicAgent ea2 = new EconomicAgent();
		
		Bid b = new Bid(new Date(), ea1, p, new Quote(0.50), BigDecimal.ONE);
		Ask a = new Ask(new Date(), ea2, p, new Quote(1.50), BigDecimal.ONE);
		
		assertNull(m.place(b));
		assertNull(m.place(a));
		
		assertFalse(m.noMoreBids(p));
		assertFalse(m.noMoreAsks(p));
	}
	
	public void testTradeBestBidFirstAsk(){
		Market m = new Market();
		Product p = new Product();
		EconomicAgent ea1 = new EconomicAgent();
		EconomicAgent ea2 = new EconomicAgent();
		EconomicAgent ea3 = new EconomicAgent();
		
		Bid badBid = new Bid(new Date(), ea1, p, new Quote(0.50), BigDecimal.ONE);
		Ask a = new Ask(new Date(), ea2, p, new Quote(1.50), BigDecimal.ONE);
		Bid goodBid = new Bid(new Date(), ea3, p, new Quote(2.50), BigDecimal.ONE);
		
		assertNull(m.place(a));
		assertNull(m.place(badBid));
		Deal d = m.place(goodBid);
		
		assertNotNull(d);
		assertEquals(a, d.getAsk());
		assertEquals(goodBid, d.getBid());
		
		assertFalse(m.noMoreBids(p));
		assertTrue(m.noMoreAsks(p));
	}
	
	public void testTradeBestBidFirstBid(){
		Market m = new Market();
		Product p = new Product();
		EconomicAgent ea1 = new EconomicAgent();
		EconomicAgent ea2 = new EconomicAgent();
		EconomicAgent ea3 = new EconomicAgent();
		
		Bid badBid = new Bid(new Date(), ea1, p, new Quote(0.50), BigDecimal.ONE);
		Ask a = new Ask(new Date(), ea2, p, new Quote(1.50), BigDecimal.ONE);
		Bid goodBid = new Bid(new Date(), ea3, p, new Quote(2.50), BigDecimal.ONE);
		
		assertNull(m.place(badBid));
		assertNull(m.place(goodBid));
		Deal d = m.place(a);
		
		assertNotNull(d);
		assertEquals(a, d.getAsk());
		assertEquals(goodBid, d.getBid());
		
		assertFalse(m.noMoreBids(p));
		assertTrue(m.noMoreAsks(p));
	}
	
	public void testTradeFirstBid(){
		Market m = new Market();
		Product p = new Product();
		EconomicAgent ea1 = new EconomicAgent();
		EconomicAgent ea2 = new EconomicAgent();
		EconomicAgent ea3 = new EconomicAgent();
		
		Bid lastBid = new Bid(new Date(), ea1, p, new Quote(2.50), BigDecimal.ONE);
		Ask a = new Ask(new Date(), ea2, p, new Quote(1.50), BigDecimal.ONE);
		Bid firstBid = new Bid(new Date(), ea3, p, new Quote(2.50), BigDecimal.ONE);
		
		assertNull(m.place(firstBid));
		assertNull(m.place(lastBid));
		Deal d = m.place(a);
		
		assertNotNull(d);
		assertEquals(a, d.getAsk());
		assertEquals(firstBid, d.getBid());
		
		assertFalse(m.noMoreBids(p));
		assertTrue(m.noMoreAsks(p));
	}
	
	public void testTradeFirstAsk(){
		Market m = new Market();
		Product p = new Product();
		EconomicAgent ea1 = new EconomicAgent();
		EconomicAgent ea2 = new EconomicAgent();
		EconomicAgent ea3 = new EconomicAgent();
		
		Bid b = new Bid(new Date(), ea1, p, new Quote(2.50), BigDecimal.ONE);
		Ask firstAsk = new Ask(new Date(), ea2, p, new Quote(1.50), BigDecimal.ONE);
		Ask lastAsk = new Ask(new Date(), ea3, p, new Quote(1.50), BigDecimal.ONE);
		
		assertNull(m.place(firstAsk));
		assertNull(m.place(lastAsk));
		Deal d = m.place(b);
		
		assertNotNull(d);
		assertEquals(firstAsk, d.getAsk());
		assertEquals(b, d.getBid());
		
		assertTrue(m.noMoreBids(p));
		assertFalse(m.noMoreAsks(p));
	}

}

