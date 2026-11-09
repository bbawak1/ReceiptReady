package edu.westga.comp4420.receiptready.test.model.receipt_book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.comp4420.receiptready.model.Category;
import edu.westga.comp4420.receiptready.model.Receipt;
import edu.westga.comp4420.receiptready.model.ReceiptBook;

class TestRemoveReceipt {

	private ReceiptBook book;
	private Receipt first;
	private Receipt second;

	@BeforeEach
	void setUp() {
		this.book = new ReceiptBook();
		this.first = new Receipt("Office Depot", LocalDate.of(2026, 10, 2), 45.20, Category.SUPPLIES, true);
		this.second = new Receipt("Shell Gas", LocalDate.of(2026, 10, 3), 38.75, Category.TRAVEL, true);
		this.book.addReceipt(this.first);
		this.book.addReceipt(this.second);
	}

	@Test
	void testRemoveExistingReceipt() {
		boolean result = this.book.removeReceipt(this.first);

		assertTrue(result, "checking the receipt was reported as removed");
		assertEquals(1, this.book.size(), "checking the size after removing");
		assertSame(this.second, this.book.getReceipts().get(0), "checking the remaining receipt");
	}

	@Test
	void testRemoveReceiptNotInBook() {
		Receipt other = new Receipt("Staples", LocalDate.of(2026, 10, 4), 5, Category.SUPPLIES, false);

		boolean result = this.book.removeReceipt(other);

		assertFalse(result, "checking nothing was reported as removed");
		assertEquals(2, this.book.size(), "checking the size did not change");
	}

	@Test
	void testRemoveSameReceiptTwice() {
		this.book.removeReceipt(this.first);

		boolean result = this.book.removeReceipt(this.first);

		assertFalse(result, "checking the second removal found nothing");
		assertEquals(1, this.book.size(), "checking the size did not change again");
	}

	@Test
	void testRemoveNullReceipt() {
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
			() -> this.book.removeReceipt(null));

		assertEquals(ReceiptBook.RECEIPT_REQUIRED, error.getMessage(), "checking the error message");
		assertEquals(2, this.book.size(), "checking nothing was removed");
	}
}
