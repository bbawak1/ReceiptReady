package edu.westga.comp4420.receiptready.model;

import java.time.LocalDate;

/**
 * Calculates spending totals from the receipt entries in a receipt book.
 *
 * @author Bernard Bawak
 * @version Fall 2026
 */
public class ReceiptSummary {
	public static final String RECEIPT_BOOK_REQUIRED = "Receipt book is required.";
	public static final String START_DATE_REQUIRED = "Start date is required.";
	public static final String END_DATE_REQUIRED = "End date is required.";
	public static final String START_AFTER_END = "Start date must not be after the end date.";

	private final ReceiptBook receiptBook;

	/**
	 * Creates a summary for the given receipt book. Totals always reflect the
	 * current contents of the book.
	 *
	 * @precondition receiptBook != null
	 * @postcondition none
	 *
	 * @param receiptBook the receipt book to summarize
	 */
	public ReceiptSummary(ReceiptBook receiptBook) {
		if (receiptBook == null) {
			throw new IllegalArgumentException(RECEIPT_BOOK_REQUIRED);
		}
		this.receiptBook = receiptBook;
	}

	/**
	 * Calculates the total amount spent in a category.
	 *
	 * @precondition category != null
	 * @postcondition none
	 *
	 * @param category the category to total
	 *
	 * @return the sum of the amounts of all receipts in the category
	 */
	public double totalForCategory(Category category) {
		if (category == null) {
			throw new IllegalArgumentException(Receipt.CATEGORY_REQUIRED);
		}
		double total = 0;
		for (Receipt receipt : this.receiptBook.getReceipts()) {
			if (receipt.getCategory() == category) {
				total += receipt.getAmount();
			}
		}
		return total;
	}

	/**
	 * Calculates the total amount spent between two dates. Both dates are
	 * included in the range.
	 *
	 * @precondition start != null && end != null && !start.isAfter(end)
	 * @postcondition none
	 *
	 * @param start the first day of the range
	 * @param end   the last day of the range
	 *
	 * @return the sum of the amounts of all receipts dated from start through end
	 */
	public double totalBetween(LocalDate start, LocalDate end) {
		if (start == null) {
			throw new IllegalArgumentException(START_DATE_REQUIRED);
		}
		if (end == null) {
			throw new IllegalArgumentException(END_DATE_REQUIRED);
		}
		if (start.isAfter(end)) {
			throw new IllegalArgumentException(START_AFTER_END);
		}
		double total = 0;
		for (Receipt receipt : this.receiptBook.getReceipts()) {
			LocalDate date = receipt.getDate();
			if (!date.isBefore(start) && !date.isAfter(end)) {
				total += receipt.getAmount();
			}
		}
		return total;
	}

	/**
	 * Calculates the total amount of all tax-related receipt entries.
	 *
	 * @precondition none
	 * @postcondition none
	 *
	 * @return the sum of the amounts of all tax-related receipts
	 */
	public double totalTaxRelated() {
		double total = 0;
		for (Receipt receipt : this.receiptBook.getReceipts()) {
			if (receipt.isTaxRelated()) {
				total += receipt.getAmount();
			}
		}
		return total;
	}
}
