package b_Money;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		SweBank.deposit("Bob", new Money(1000, SEK));
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
		DanskeBank.deposit("Gertrud", new Money(10000, DKK));
	}

	@Test
	public void testGetName() {
		Assert.assertEquals("SweBank", SweBank.getName());
	}

	@Test
	public void testGetCurrency() {
		Assert.assertEquals(SEK, SweBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		// Opening test account and depositing '1.00 SEK'
		SweBank.openAccount("TestOpenAccount");
		SweBank.deposit("TestOpenAccount", new Money(100, SEK));

		// Checking if money are on the account
		Assert.assertEquals(100, SweBank.getBalance("TestOpenAccount"), 0);
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		// Making variables for storing balance and added money before the deposit
		double balanceBefore = SweBank.getBalance("Ulrika");
		int added = 100;

		// Doing deposit
		SweBank.deposit("Ulrika", new Money(added, SEK));

		// Checking if money are in the account
		Assert.assertEquals(balanceBefore + added, SweBank.getBalance("Ulrika"), 0);
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		// Making variables for storing balance and subbed money before the deposit
		double balanceBefore = SweBank.getBalance("Ulrika");
		int sub = 100;

		// Doing deposit
		SweBank.withdraw("Ulrika", new Money(sub, SEK));

		// Checking if money are in the account
		Assert.assertEquals(balanceBefore - sub, SweBank.getBalance("Ulrika"), 0);
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		Assert.assertEquals(1000, SweBank.getBalance("Bob"), 0);
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		// Checking initial money
		Assert.assertEquals(0, SweBank.getBalance("Ulrika"), 0);
		Assert.assertEquals(1000, SweBank.getBalance("Bob"), 0);

		// Doing transfer from 'Bob' to 'Ulrika'
		SweBank.transfer("Bob", SweBank, "Ulrika", new Money(500, SEK));

		// Checking money after transfer
		Assert.assertEquals(500, SweBank.getBalance("Ulrika"), 0);
		Assert.assertEquals(500, SweBank.getBalance("Bob"), 0);
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		// Checking initial money
		Assert.assertEquals(10000, DanskeBank.getBalance("Gertrud"), 0);
		Assert.assertEquals(1000, SweBank.getBalance("Bob"), 0);

		// Adding timed payment to 'Gertrud' for transfer to 'Bob'
		DanskeBank.addTimedPayment("Gertrud", "1", 5, 5, new Money(100, DKK), SweBank, "Bob");

		// Checking if timed payment was not executed before interval
		Assert.assertEquals(10000, DanskeBank.getBalance("Gertrud"), 0);
		Assert.assertEquals(1000, SweBank.getBalance("Bob"), 0);

		// 'Time passes'
		for (int i = 0; i < 6; i++) DanskeBank.tick();

		// Checking money after the timed payment execution
		Assert.assertEquals(9900, DanskeBank.getBalance("Gertrud"), 0);
		Assert.assertEquals(1075, SweBank.getBalance("Bob"), 0);
	}
}
