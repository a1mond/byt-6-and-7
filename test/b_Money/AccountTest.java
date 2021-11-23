package b_Money;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() throws AccountDoesNotExistException {
		// Checking initial money on the account
		Assert.assertEquals(10000000,testAccount.getBalance().getAmount(), 0);

		// Adding timed payment
		testAccount.addTimedPayment("1", 3, 3, new Money(1000, SEK), SweBank, "Alice");

		// Checking again if money were not transferred before ticking
		Assert.assertEquals(10000000,testAccount.getBalance().getAmount(), 0);

		// 'Time passes'
		for (int i = 0; i < 4; i++) testAccount.tick();

		// Checking money on the accounts after the transfer
		Assert.assertEquals(9999000, testAccount.getBalance().getAmount(), 0);
		Assert.assertEquals(1001000, SweBank.getBalance("Alice"), 0);

		// Removing timed payment
		testAccount.removeTimedPayment("1");

		// 'Time passes'
		for (int i = 0; i < 4; i++) testAccount.tick();

		// Checking if money are same as before, because we removed timed payment
		Assert.assertEquals(9999000, testAccount.getBalance().getAmount(), 0);
		Assert.assertEquals(1001000, SweBank.getBalance("Alice"), 0);
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		// Checking initial money on the account
		Assert.assertEquals(10000000,testAccount.getBalance().getAmount(), 0);

		// Adding timed payment
		testAccount.addTimedPayment("1", 3, 3, new Money(1000, SEK), SweBank, "Alice");

		// Checking again if money were not transferred before ticking
		Assert.assertEquals(10000000,testAccount.getBalance().getAmount(), 0);

		// 'Time passes'
		for (int i = 0; i < 4; i++) testAccount.tick();

		// Checking money on the accounts after the transfer
		Assert.assertEquals(9999000, testAccount.getBalance().getAmount(), 0);
		Assert.assertEquals(1001000, SweBank.getBalance("Alice"), 0);
	}

	@Test
	public void testAddWithdraw() {
		// Checking initial money
		Assert.assertEquals(10000000, testAccount.getBalance().getAmount(), 0);

		// Doing withdraw from test account
		testAccount.withdraw(new Money(10000000, SEK));

		// Checking if money were transferred
		Assert.assertEquals(0d, testAccount.getBalance().getAmount(), 0);
	}
	
	@Test
	public void testGetBalance() {
		// Checking if balance is the same as with entity money
		Assert.assertEquals(0, new Money(10000000, SEK).compareTo(testAccount.getBalance()));
	}
}
