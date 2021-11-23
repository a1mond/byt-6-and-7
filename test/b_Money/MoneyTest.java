package b_Money;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100, DKK10, SEK100_C;
	
	@Before
	public void setUp() {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		DKK10 = new Money(10, DKK);
		SEK100 = new Money(10000, SEK);
		SEK100_C = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		Assert.assertEquals(10000, SEK100.getAmount(), 0);
		Assert.assertEquals(1000, EUR10.getAmount(), 0);
		Assert.assertEquals(20000, SEK200.getAmount(), 0);
	}

	@Test
	public void testGetCurrency() {
		Assert.assertEquals(SEK, SEK100.getCurrency());
		Assert.assertEquals(EUR, EUR0.getCurrency());
		Assert.assertEquals(DKK, DKK10.getCurrency());
	}

	@Test
	public void testToString() {
		// Correct printing should be like -- Money(int 10000, SEK) => "100.00 SEK"
		Assert.assertEquals("10.0 EUR", EUR10.toString());
		Assert.assertEquals("200.0 SEK", SEK200.toString());
		Assert.assertEquals("100.0 SEK", SEK100.toString());
	}

	@Test
	public void testGlobalValue() {
		Assert.assertEquals(1500, SEK100.universalValue(), 0);
		Assert.assertEquals(1500, EUR10.universalValue(), 0);
	}

	@SuppressWarnings("EqualsWithItself")
	@Test
	public void testEqualsMoney() {
		// Checking if same Money are equal
		Assert.assertTrue(EUR10.equals(EUR10));

		// Checking not equal money
		Assert.assertFalse(EUR10.equals(SEK200));
	}

	@Test
	public void testAdd() {
		// Checking initial money
		Assert.assertEquals(10000, SEK100.getAmount(), 0);

		// Checking after the adding
		Assert.assertEquals(30000, SEK100.add(SEK200).getAmount(), 0);
	}

	@Test
	public void testSub() {
		// Checking initial money
		Assert.assertEquals(10000, SEK100.getAmount(), 0);

		// Checking after the subbing
		Assert.assertEquals(-10000, SEK100.sub(SEK200).getAmount(), 0);
	}

	@Test
	public void testIsZero() {
		Assert.assertTrue(EUR0.isZero());
		Assert.assertFalse(SEK100.isZero());
	}

	@Test
	public void testNegate() {
		// Checking money after the performed negation
		Assert.assertEquals(-10000, SEK100.negate().getAmount(), 0);
	}

	@Test
	public void testCompareTo() {
		// Same money
		Assert.assertEquals(0, SEK100_C.compareTo(SEK100));

		// First money are smaller than other passed in method (should output -1)
		Assert.assertEquals(-1, SEKn100.compareTo(SEK100));

		// Second money are bigger than other passed in method (should output 1)
		Assert.assertEquals(1, EUR20.compareTo(SEK100));
	}
}
