package edu.westga.comp4420.receiptready.test.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.comp4420.receiptready.model.Expense;

class TestExpense {

	@Test
	void testValidExpense() {
		Expense expense = new Expense("Office Depot", 24.50, "Supplies", true);
		assertEquals("Office Depot", expense.getVendor());
		assertEquals(24.50, expense.getAmount(), 0.001);
		assertEquals("Supplies", expense.getCategory());
		assertTrue(expense.isTaxRelated());
	}

	@Test
	void testPersonalExpense() {
		Expense expense = new Expense("Cafe", 5.00, "Food", false);
		assertFalse(expense.isTaxRelated());
	}

	@Test
	void testInvalidVendor() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Expense(" ", 10.0, "Travel", true);
		});
	}

	@Test
	void testNegativeAmount() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Expense("Store", -1.0, "Travel", true);
		});
	}

	@Test
	void testInvalidCategory() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Expense("Store", 10.0, "", true);
		});
	}
}
