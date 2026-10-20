package edu.westga.comp4420.receiptready.test.model.receipt_book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.comp4420.receiptready.model.Category;
import edu.westga.comp4420.receiptready.model.Receipt;
import edu.westga.comp4420.receiptready.model.ReceiptBook;

class TestSearchByVendor {

	private ReceiptBook book;
	private Receipt depot;
	private Receipt staples;
	private Receipt staplesPlus;

	@BeforeEach
	void setUp() {
		this.book = new ReceiptBook();
		this.depot = new Receipt("Office Depot", LocalDate.of(2026, 10, 2), 45.20, Category.SUPPLIES, true);
		this.staples = new Receipt("Staples", LocalDate.of(2026, 10, 3), 12.50, Category.SUPPLIES, false);
		this.staplesPlus = new Receipt("Staples Plus", LocalDate.of(2026, 10, 4), 99.99, Category.EQUIPMENT, true);
		this.book.addReceipt(this.depot);
		this.book.addReceipt(this.staples);
		this.book.addReceipt(this.staplesPlus);
	}

	@Test
	void testSearchFindsMatchingVendors() {
		List<Receipt> result = this.book.searchByVendor("staples");

		assertEquals(2, result.size(), "checking both Staples vendors were found");
	}

	@Test
	void testSearchIgnoresCase() {
		List<Receipt> result = this.book.searchByVendor("stAPLES");

		assertEquals(2, result.size(), "checking mixed case still finds both Staples vendors");
	}

	@Test
	void testSearchIgnoresSurroundingSpaces() {
		List<Receipt> result = this.book.searchByVendor(" depot ");

		assertEquals(1, result.size(), "checking spaces around the text are ignored");
		assertSame(this.depot, result.get(0), "checking the Office Depot receipt was found");
	}

	@Test
	void testSearchWithNoMatches() {
		List<Receipt> result = this.book.searchByVendor("target");

		assertTrue(result.isEmpty(), "checking nothing was found");
	}

	@Test
	void testSearchWithBlankTextReturnsAll() {
		List<Receipt> result = this.book.searchByVendor("   ");

		assertEquals(3, result.size(), "checking blank search returns every receipt");
	}

	@Test
	void testSearchNullText() {
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
			() -> this.book.searchByVendor(null));

		assertEquals(ReceiptBook.SEARCH_TEXT_REQUIRED, error.getMessage(), "checking the error message");
	}
}