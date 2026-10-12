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

class TestTotalBetween {

	private ReceiptSummary summary;

	@BeforeEach
	void setUp() {
		ReceiptBook book = new ReceiptBook();
		book.addReceipt(new Receipt("Office Depot", LocalDate.of(2026, 9, 30), 10, Category.SUPPLIES, true));
		book.addReceipt(new Receipt("Shell Gas", LocalDate.of(2026, 10, 1), 20, Category.TRAVEL, true));
		book.addReceipt(new Receipt("Panera Bread", LocalDate.of(2026, 10, 15), 40, Category.MEALS, false));
		book.addReceipt(new Receipt("Georgia Power", LocalDate.of(2026, 10, 31), 80, Category.UTILITIES, true));
		book.addReceipt(new Receipt("Staples", LocalDate.of(2026, 11, 1), 160, Category.SUPPLIES, false));
		this.summary = new ReceiptSummary(book);
	}

	@ParameterizedTest
	@CsvSource({
		"2026-10-01, 2026-10-31, 140",
		"2026-09-30, 2026-11-01, 310",
		"2026-10-02, 2026-10-30, 40",
		"2026-10-15, 2026-10-15, 40",
		"2026-10-01, 2026-10-01, 20",
		"2026-10-31, 2026-10-31, 80",
		"2026-12-01, 2026-12-31, 0"
	})
	void testTotalForRange(LocalDate start, LocalDate end, double expectedTotal) {
		double result = this.summary.totalBetween(start, end);

		assertEquals(expectedTotal, result, 0.001, "checking the total for the date range");
	}

	@Test
	void testNullStartDate() {
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
			() -> this.summary.totalBetween(null, LocalDate.of(2026, 10, 31)));

		assertEquals(ReceiptSummary.START_DATE_REQUIRED, error.getMessage(), "checking the error message");
	}

	@Test
	void testNullEndDate() {
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
			() -> this.summary.totalBetween(LocalDate.of(2026, 10, 1), null));

		assertEquals(ReceiptSummary.END_DATE_REQUIRED, error.getMessage(), "checking the error message");
	}

	@Test
	void testStartAfterEnd() {
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
			() -> this.summary.totalBetween(LocalDate.of(2026, 10, 2), LocalDate.of(2026, 10, 1)));

		assertEquals(ReceiptSummary.START_AFTER_END, error.getMessage(), "checking the error message");
	}
}
