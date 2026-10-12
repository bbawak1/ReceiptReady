package edu.westga.comp4420.receiptready.test.model.receipt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import edu.westga.comp4420.receiptready.model.Category;
import edu.westga.comp4420.receiptready.model.Receipt;

class TestSetters {

	private Receipt receipt;

	@BeforeEach
	void setUp() {
		this.receipt = new Receipt("Office Depot", LocalDate.of(2026, 10, 2), 45.20, Category.SUPPLIES, true);
	}

	@Test
	void testSetVendor() {
		this.receipt.setVendor(" Staples ");

		assertEquals("Staples", this.receipt.getVendor(), "checking the updated vendor");
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "   "})
	void testSetVendorRejectsBlank(String vendor) {
		assertThrows(IllegalArgumentException.class, () -> this.receipt.setVendor(vendor));

		assertEquals("Office Depot", this.receipt.getVendor(), "checking the vendor was not changed");
	}

	@Test
	void testSetVendorRejectsNull() {
		assertThrows(IllegalArgumentException.class, () -> this.receipt.setVendor(null));
	}

	@Test
	void testSetDate() {
		LocalDate newDate = LocalDate.of(2026, 10, 9);

		this.receipt.setDate(newDate);

		assertEquals(newDate, this.receipt.getDate(), "checking the updated date");
	}

	@Test
	void testSetDateRejectsNull() {
		assertThrows(IllegalArgumentException.class, () -> this.receipt.setDate(null));

		assertEquals(LocalDate.of(2026, 10, 2), this.receipt.getDate(), "checking the date was not changed");
	}

	@ParameterizedTest
	@ValueSource(doubles = {0, 0.01, 99.99, 10000})
	void testSetAmount(double amount) {
		this.receipt.setAmount(amount);

		assertEquals(amount, this.receipt.getAmount(), 0.001, "checking the updated amount");
	}

	@ParameterizedTest
	@ValueSource(doubles = {-0.01, -50})
	void testSetAmountRejectsNegative(double amount) {
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
			() -> this.receipt.setAmount(amount));

		assertEquals(Receipt.AMOUNT_NEGATIVE, error.getMessage(), "checking the error message");
		assertEquals(45.20, this.receipt.getAmount(), 0.001, "checking the amount was not changed");
	}

	@ParameterizedTest
	@ValueSource(doubles = {Double.NaN, Double.POSITIVE_INFINITY})
	void testSetAmountRejectsNonFiniteValues(double amount) {
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
			() -> this.receipt.setAmount(amount));

		assertEquals(Receipt.AMOUNT_NOT_A_NUMBER, error.getMessage(), "checking the error message");
	}

	@Test
	void testSetCategory() {
		this.receipt.setCategory(Category.EQUIPMENT);

		assertEquals(Category.EQUIPMENT, this.receipt.getCategory(), "checking the updated category");
	}

	@Test
	void testSetCategoryRejectsNull() {
		assertThrows(IllegalArgumentException.class, () -> this.receipt.setCategory(null));

		assertEquals(Category.SUPPLIES, this.receipt.getCategory(), "checking the category was not changed");
	}

	@Test
	void testSetTaxRelated() {
		this.receipt.setTaxRelated(false);
		assertFalse(this.receipt.isTaxRelated(), "checking the flag was turned off");

		this.receipt.setTaxRelated(true);
		assertTrue(this.receipt.isTaxRelated(), "checking the flag was turned back on");
	}
}
