package edu.westga.comp4420.receiptready.test.model.receipt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import edu.westga.comp4420.receiptready.model.Receipt;

class TestParseAmount {

	@ParameterizedTest
	@CsvSource({
		"45.20, 45.20",
		"'$45.20', 45.20",
		"'  12 ', 12",
		"0, 0",
		"0.01, 0.01"
	})
	void testValidAmounts(String text, double expectedAmount) {
		double result = Receipt.parseAmount(text);

		assertEquals(expectedAmount, result, 0.001, "checking the parsed amount");
	}

	@Test
	void testNullText() {
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
			() -> Receipt.parseAmount(null));

		assertEquals(Receipt.AMOUNT_REQUIRED, error.getMessage(), "checking the error message");
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "   "})
	void testBlankText(String text) {
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
			() -> Receipt.parseAmount(text));

		assertEquals(Receipt.AMOUNT_REQUIRED, error.getMessage(), "checking the error message");
	}

	@ParameterizedTest
	@ValueSource(strings = {"abc", "12.3.4", "$", "NaN", "Infinity"})
	void testTextThatIsNotANumber(String text) {
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
			() -> Receipt.parseAmount(text));

		assertEquals(Receipt.AMOUNT_NOT_A_NUMBER, error.getMessage(), "checking the error message");
	}

	@Test
	void testNegativeAmount() {
		IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
			() -> Receipt.parseAmount("-0.01"));

		assertEquals(Receipt.AMOUNT_NEGATIVE, error.getMessage(), "checking the error message");
	}
}
