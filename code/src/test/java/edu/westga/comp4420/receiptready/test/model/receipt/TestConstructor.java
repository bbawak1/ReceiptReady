package edu.westga.comp4420.receiptready.test.model.receipt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import edu.westga.comp4420.receiptready.model.Category;
import edu.westga.comp4420.receiptready.model.Receipt;

class TestConstructor {

	private static final LocalDate PURCHASE_DATE = LocalDate.of(2026, 10, 2);

	@Test
	void testValidReceiptStoresAllDetails() {
		Receipt receipt = new Receipt("Office Depot", PURCHASE_DATE, 45.20, Category.SUPPLIES, true);

		assertEquals("Office Depot", receipt.getVendor(), "checking the vendor");
		assertEquals(PURCHASE_DATE, receipt.getDate(), "checking the date");
		assertEquals(45.20, receipt.getAmount(), 0.001, "checking the amount");
		assertEquals(Category.SUPPLIES, receipt.getCategory(), "checking the category");
		assertTrue(receipt.isTaxRelated(), "checking the tax-related flag");
	}

	@Test
	void testReceiptThatIsNotTaxRelated() {
		Receipt receipt = new Receipt("Panera Bread", PURCHASE_DATE, 14.60, Category.MEALS, false);

		assertFalse(receipt.isTaxRelated(), "checking the tax-related flag");
	}

	@Test
	void testVendorIsTrimmed() {
		Receipt receipt = new Receipt("  Shell Gas  ", PURCHASE_DATE, 38.75, Category.TRAVEL, true);

		assertEquals("Shell Gas", receipt.getVendor(), "checking surrounding whitespace was removed");
	}

	@Test
	void testZeroAmountIsAllowed() {
		Receipt receipt = new Receipt("Free Sample", PURCHASE_DATE, 0, Category.OTHER, false);

		assertEquals(0, receipt.getAmount(), 0.001, "checking the boundary amount of zero");
	}

	@Test
	void testNullVendor() {
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
			() -> new Receipt(null, PURCHASE_DATE, 1, Category.OTHER, false));

		assertEquals(Receipt.VENDOR_REQUIRED, error.getMessage(), "checking the error message");
	}

	@Test
	void testBlankVendor() {
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
			() -> new Receipt("   ", PURCHASE_DATE, 1, Category.OTHER, false));

		assertEquals(Receipt.VENDOR_REQUIRED, error.getMessage(), "checking the error message");
	}

	@Test
	void testNullDate() {
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
			() -> new Receipt("Office Depot", null, 1, Category.OTHER, false));

		assertEquals(Receipt.DATE_REQUIRED, error.getMessage(), "checking the error message");
	}

	@Test
	void testNegativeAmount() {
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
			() -> new Receipt("Office Depot", PURCHASE_DATE, -0.01, Category.OTHER, false));

		assertEquals(Receipt.AMOUNT_NEGATIVE, error.getMessage(), "checking the error message");
	}

	@Test
	void testNullCategory() {
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
			() -> new Receipt("Office Depot", PURCHASE_DATE, 1, null, false));

		assertEquals(Receipt.CATEGORY_REQUIRED, error.getMessage(), "checking the error message");
	}
}
