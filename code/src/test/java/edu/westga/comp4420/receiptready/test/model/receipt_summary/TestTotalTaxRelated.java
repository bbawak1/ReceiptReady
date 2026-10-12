package edu.westga.comp4420.receiptready.test.model.receipt_summary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import edu.westga.comp4420.receiptready.model.Category;
import edu.westga.comp4420.receiptready.model.Receipt;
import edu.westga.comp4420.receiptready.model.ReceiptBook;
import edu.westga.comp4420.receiptready.model.ReceiptSummary;

class TestTotalTaxRelated {

	private static final LocalDate DATE = LocalDate.of(2026, 10, 2);

	@Test
	void testEmptyBook() {
		ReceiptSummary summary = new ReceiptSummary(new ReceiptBook());

		assertEquals(0, summary.totalTaxRelated(), 0.001, "checking an empty book");
	}

	@Test
	void testNoTaxRelatedReceipts() {
		ReceiptBook book = new ReceiptBook();
		book.addReceipt(new Receipt("Panera Bread", DATE, 14.60, Category.MEALS, false));
		ReceiptSummary summary = new ReceiptSummary(book);

		assertEquals(0, summary.totalTaxRelated(), 0.001, "checking a book with no tax-related receipts");
	}

	@Test
	void testOnlyTaxRelatedReceiptsAreCounted() {
		ReceiptBook book = new ReceiptBook();
		book.addReceipt(new Receipt("Office Depot", DATE, 45.20, Category.SUPPLIES, true));
		book.addReceipt(new Receipt("Shell Gas", DATE, 38.75, Category.TRAVEL, true));
		book.addReceipt(new Receipt("Panera Bread", DATE, 14.60, Category.MEALS, false));
		book.addReceipt(new Receipt("Georgia Power", DATE, 112.00, Category.UTILITIES, true));
		ReceiptSummary summary = new ReceiptSummary(book);

		assertEquals(195.95, summary.totalTaxRelated(), 0.001, "checking the tax-related total");
	}

	@Test
	void testTotalReflectsChangedTaxFlag() {
		ReceiptBook book = new ReceiptBook();
		Receipt lunch = new Receipt("Panera Bread", DATE, 14.60, Category.MEALS, false);
		book.addReceipt(lunch);
		ReceiptSummary summary = new ReceiptSummary(book);

		lunch.setTaxRelated(true);

		assertEquals(14.60, summary.totalTaxRelated(), 0.001, "checking after marking a receipt tax-related");
	}
}
