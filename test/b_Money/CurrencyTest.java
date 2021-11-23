package b_Money;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		NOK = new Currency("NOK", 0.5);
	}

	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
	}
	
	@Test
	public void testGetRate() {
		assertEquals(0.15d, SEK.getRate(), 0);
		assertEquals(0.20d, DKK.getRate(), 0);
		assertEquals(1.5d, EUR.getRate(), 0);
	}
	
	@Test
	public void testSetRate() {
		// Setting rate for 'NOK' and checking for updated value
		NOK.setRate(0.99);
		assertEquals(0.99, NOK.getRate(), 0);

		// Setting rate back for 'NOK' and checking again
		NOK.setRate(0.5);
		assertEquals(0.5, NOK.getRate(), 0);
	}
	
	@Test
	public void testGlobalValue() {
		assertEquals(15d, SEK.universalValue(100), 0);
		assertEquals(20d, DKK.universalValue(100), 0);
		assertEquals(150d, EUR.universalValue(100), 0);
	}
	
	@Test
	public void testValueInThisCurrency() {
		assertEquals(30d, SEK.valueInThisCurrency(100, NOK), 0);
		assertEquals(40d, DKK.valueInThisCurrency(100, NOK), 0);
		assertEquals(300d, EUR.valueInThisCurrency(100, NOK), 0);

	}

}
