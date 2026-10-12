package edu.westga.comp4420.receiptready.test.model.category;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import edu.westga.comp4420.receiptready.model.Category;

class TestToString {

	@ParameterizedTest
	@CsvSource({
		"SUPPLIES, Supplies",
		"MEALS, Meals",
		"TRAVEL, Travel",
		"UTILITIES, Utilities",
		"EQUIPMENT, Equipment",
		"OTHER, Other"
	})
	void testEachCategoryShowsItsDisplayName(Category category, String expectedName) {
		String result = category.toString();

		assertEquals(expectedName, result, "checking the text shown for the category");
		assertEquals(expectedName, category.getDisplayName(), "checking the display name of the category");
	}
}
