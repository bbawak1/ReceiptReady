package edu.westga.comp4420.receiptready.test.model.receipt_summary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import edu.westga.comp4420.receiptready.model.Category;
import edu.westga.comp4420.receiptready.model.Receipt;
import edu.westga.comp4420.receiptready.model.ReceiptBook;
import edu.westga.comp4420.receiptready.model.ReceiptSummary;

class TestTotalForCategory {

	private ReceiptBook book;
	private ReceiptSummary summary;

	@BeforeEach
	void setUp() {
		this.book = new ReceiptBook();
		this.summary = new ReceiptSummary(this.book);
		LocalDate date = LocalDate.of(2026, 10, 2);
		this.book.addReceipt(new Receipt("Office Depot", date, 45.20, Category.SUPPLIES, true));
		this.book.addReceipt(new Receipt("Staples", date, 4.80, Category.SUPPLIES, false));
		this.book.addReceipt(new Receipt("Shell Gas", date, 38.75, Category.TRAVEL, true));
		this.book.addReceipt(new Receipt("Georgia Power", date, 112.00, Category.UTILITIES, true));
	}

	@Test
	void testNullReceiptBook() {
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
			() -> new ReceiptSummary(null));

		assertEquals(ReceiptSummary.RECEIPT_BOOK_REQUIRED, error.getMessage(), "checking the error message");
	}

	@Test
	void testEmptyBookTotalsZero() {
		ReceiptSummary emptySummary = new ReceiptSummary(new ReceiptBook());

		assertEquals(0, emptySummary.totalForCategory(Category.SUPPLIES), 0.001, "checking an empty book");
	}

	@ParameterizedTest
	@CsvSource({
		"SUPPLIES, 50.00",
		"TRAVEL, 38.75",
		"UTILITIES, 112.00",
		"MEALS, 0",
		"EQUIPMENT, 0",
		"OTHER, 0"
	})
	void testTotalForEachCategory(Category category, double expectedTotal) {
		double result = this.summary.totalForCategory(category);

		assertEquals(expectedTotal, result, 0.001, "checking the total for the category");
	}

	@Test
	void testTotalReflectsLaterChangesToTheBook() {
		Receipt lunch = new Receipt("Panera Bread", LocalDate.of(2026, 10, 5), 14.60, Category.MEALS, false);
		this.book.addReceipt(lunch);

		assertEquals(14.60, this.summary.totalForCategory(Category.MEALS), 0.001, "checking after an add");

		lunch.setCategory(Category.TRAVEL);

		assertEquals(0, this.summary.totalForCategory(Category.MEALS), 0.001, "checking after a category edit");
		assertEquals(53.35, this.summary.totalForCategory(Category.TRAVEL), 0.001, "checking the new category");
	}

	@Test
	void testNullCategory() {
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
			() -> this.summary.totalForCategory(null));

		assertEquals(Receipt.CATEGORY_REQUIRED, error.getMessage(), "checking the error message");
	}
}
