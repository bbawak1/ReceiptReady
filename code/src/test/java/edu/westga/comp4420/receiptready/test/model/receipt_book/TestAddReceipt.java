package edu.westga.comp4420.receiptready.test.model.receipt_book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import edu.westga.comp4420.receiptready.model.Category;
import edu.westga.comp4420.receiptready.model.Receipt;
import edu.westga.comp4420.receiptready.model.ReceiptBook;

class TestAddReceipt {

	private static Receipt createReceipt(String vendor) {
		return new Receipt(vendor, LocalDate.of(2026, 10, 2), 10, Category.OTHER, false);
	}

	@Test
	void testNewBookIsEmpty() {
		ReceiptBook book = new ReceiptBook();

		assertEquals(0, book.size(), "checking the size of a new book");
		assertEquals(0, book.getReceipts().size(), "checking the receipts of a new book");
	}

	@Test
	void testAddOneReceipt() {
		ReceiptBook book = new ReceiptBook();
		Receipt receipt = createReceipt("Office Depot");

		book.addReceipt(receipt);

		assertEquals(1, book.size(), "checking the size after one add");
		assertSame(receipt, book.getReceipts().get(0), "checking the receipt that was added");
	}

	@Test
	void testAddMultipleReceiptsKeepsOrder() {
		ReceiptBook book = new ReceiptBook();
		Receipt first = createReceipt("Office Depot");
		Receipt second = createReceipt("Shell Gas");
		Receipt third = createReceipt("Panera Bread");

		book.addReceipt(first);
		book.addReceipt(second);
		book.addReceipt(third);

		assertEquals(3, book.size(), "checking the size after three adds");
		assertSame(first, book.getReceipts().get(0), "checking the first receipt");
		assertSame(second, book.getReceipts().get(1), "checking the second receipt");
		assertSame(third, book.getReceipts().get(2), "checking the third receipt");
	}

	@Test
	void testAddNullReceipt() {
		ReceiptBook book = new ReceiptBook();

		IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> book.addReceipt(null));

		assertEquals(ReceiptBook.RECEIPT_REQUIRED, error.getMessage(), "checking the error message");
		assertEquals(0, book.size(), "checking nothing was added");
	}

	@Test
	void testAddSameReceiptTwice() {
		ReceiptBook book = new ReceiptBook();
		Receipt receipt = createReceipt("Office Depot");
		book.addReceipt(receipt);

		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
			() -> book.addReceipt(receipt));

		assertEquals(ReceiptBook.RECEIPT_ALREADY_ADDED, error.getMessage(), "checking the error message");
		assertEquals(1, book.size(), "checking the receipt was not added twice");
	}

	@Test
	void testReceiptsCannotBeModifiedDirectly() {
		ReceiptBook book = new ReceiptBook();

		assertThrows(UnsupportedOperationException.class,
			() -> book.getReceipts().add(createReceipt("Office Depot")));
	}
}
