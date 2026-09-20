package edu.westga.comp4420.receiptready.test.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.comp4420.receiptready.model.Expense;
import edu.westga.comp4420.receiptready.model.ExpenseTracker;

class TestExpenseTracker {

	@Test
	void testAddAndCount() {
		ExpenseTracker tracker = new ExpenseTracker();
		tracker.addExpense(new Expense("Store", 10.0, "Supplies", true));
		assertEquals(1, tracker.getExpenseCount());
		assertEquals(1, tracker.getExpenses().size());
	}

	@Test
	void testTotals() {
		ExpenseTracker tracker = new ExpenseTracker();
		tracker.addExpense(new Expense("Store", 10.0, "Supplies", true));
		tracker.addExpense(new Expense("Cafe", 5.0, "Food", false));
		assertEquals(15.0, tracker.getTotalSpending(), 0.001);
		assertEquals(10.0, tracker.getTaxRelatedTotal(), 0.001);
	}

	@Test
	void testNullExpense() {
		ExpenseTracker tracker = new ExpenseTracker();
		assertThrows(IllegalArgumentException.class, () -> {
			tracker.addExpense(null);
		});
	}

	@Test
	void testExpenseListIsCopy() {
		ExpenseTracker tracker = new ExpenseTracker();
		tracker.addExpense(new Expense("Store", 10.0, "Supplies", true));
		tracker.getExpenses().clear();
		assertEquals(1, tracker.getExpenseCount());
		assertTrue(tracker.getExpenses().get(0).isTaxRelated());
	}
}
