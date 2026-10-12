package edu.westga.comp4420.receiptready.model;

/**
 * Calculates spending totals from the receipt entries in a receipt book.
 *
 * @author Bernard Bawak
 * @version Fall 2026
 */
public class ReceiptSummary {
	public static final String RECEIPT_BOOK_REQUIRED = "Receipt book is required.";

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
}
