package edu.westga.comp4420.receiptready.model;

/**
 * Stores a single business expense captured from a receipt.
 *
 * @author Bernard Bawak
 * @version Fall 2026
 */
public class Expense {
	private String vendor;
	private double amount;
	private String category;
	private boolean taxRelated;

	/**
	 * Creates a new expense.
	 *
	 * @param vendor the store or vendor name
	 * @param amount the purchase amount
	 * @param category the spending category
	 * @param taxRelated true if the expense is tax-related
	 */
	public Expense(String vendor, double amount, String category, boolean taxRelated) {
		if (vendor == null || vendor.isBlank()) {
			throw new IllegalArgumentException("vendor cannot be empty");
		}
		if (amount < 0) {
			throw new IllegalArgumentException("amount cannot be negative");
		}
		if (category == null || category.isBlank()) {
			throw new IllegalArgumentException("category cannot be empty");
		}
		this.vendor = vendor;
		this.amount = amount;
		this.category = category;
		this.taxRelated = taxRelated;
	}

	/**
	 * Gets the vendor name.
	 *
	 * @return the vendor
	 */
	public String getVendor() {
		return this.vendor;
	}

	/**
	 * Gets the purchase amount.
	 *
	 * @return the amount
	 */
	public double getAmount() {
		return this.amount;
	}

	/**
	 * Gets the spending category.
	 *
	 * @return the category
	 */
	public String getCategory() {
		return this.category;
	}

	/**
	 * Checks whether the expense is tax-related.
	 *
	 * @return true if tax-related
	 */
	public boolean isTaxRelated() {
		return this.taxRelated;
	}
}
