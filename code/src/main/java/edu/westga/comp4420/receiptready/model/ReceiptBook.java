package edu.westga.comp4420.receiptready.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Keeps the collection of receipt entries recorded by the owner.
 *
 * @author Bernard Bawak
 * @version Fall 2026
 */
public class ReceiptBook {
	public static final String RECEIPT_REQUIRED = "Receipt is required.";
	public static final String RECEIPT_ALREADY_ADDED = "Receipt is already in the receipt book.";

	private final List<Receipt> receipts;

	/**
	 * Creates an empty receipt book.
	 *
	 * @precondition none
	 * @postcondition size() == 0
	 */
	public ReceiptBook() {
		this.receipts = new ArrayList<Receipt>();
	}

	/**
	 * Adds a receipt entry to the receipt book.
	 *
	 * @precondition receipt != null && the receipt is not already in the book
	 * @postcondition size() == size()@prev + 1 && getReceipts() contains receipt
	 *
	 * @param receipt the receipt entry to add
	 */
	public void addReceipt(Receipt receipt) {
		if (receipt == null) {
			throw new IllegalArgumentException(RECEIPT_REQUIRED);
		}
		if (this.receipts.contains(receipt)) {
			throw new IllegalArgumentException(RECEIPT_ALREADY_ADDED);
		}
		this.receipts.add(receipt);
	}

	/**
	 * Removes a receipt entry from the receipt book.
	 *
	 * @precondition receipt != null
	 * @postcondition getReceipts() does not contain receipt
	 *
	 * @param receipt the receipt entry to remove
	 *
	 * @return true if the receipt was in the book and was removed, false otherwise
	 */
	public boolean removeReceipt(Receipt receipt) {
		if (receipt == null) {
			throw new IllegalArgumentException(RECEIPT_REQUIRED);
		}
		return this.receipts.remove(receipt);
	}

	/**
	 * Gets the receipt entries in the order they were added. The returned list
	 * cannot be modified; use addReceipt and removeReceipt instead.
	 *
	 * @precondition none
	 * @postcondition none
	 *
	 * @return a read-only view of the receipt entries
	 */
	public List<Receipt> getReceipts() {
		return Collections.unmodifiableList(this.receipts);
	}

	/**
	 * Gets the number of receipt entries in the receipt book.
	 *
	 * @return the number of receipt entries
	 */
	public int size() {
		return this.receipts.size();
	}
}
