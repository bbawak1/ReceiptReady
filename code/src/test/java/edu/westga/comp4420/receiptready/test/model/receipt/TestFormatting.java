package edu.westga.comp4420.receiptready.test.model.receipt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import edu.westga.comp4420.receiptready.model.Category;
import edu.westga.comp4420.receiptready.model.Receipt;

class TestFormatting {

	@ParameterizedTest
	@CsvSource({
		"45.2, $45.20",
		"0, $0.00",
		"112, $112.00",
		"0.005, $0.01",
		"1234.5, $1234.50"
	})
	void testFormatAmount(double amount, String expectedText) {
		String result = Receipt.formatAmount(amount);

		assertEquals(expectedText, result, "checking the formatted amount");
	}

	@Test
	void testFormattedAmountOfReceipt() {
		Receipt receipt = new Receipt("Shell Gas", LocalDate.of(2026, 10, 3), 38.75, Category.TRAVEL, true);

		assertEquals("$38.75", receipt.getFormattedAmount(), "checking the formatted amount");
	}

	@Test
	void testFormattedDateOfReceipt() {
		Receipt receipt = new Receipt("Shell Gas", LocalDate.of(2026, 1, 3), 38.75, Category.TRAVEL, true);

		assertEquals("01/03/2026", receipt.getFormattedDate(), "checking the formatted date");
	}
}
