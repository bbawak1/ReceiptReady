package edu.westga.comp4420.receiptready.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Stores the details of a single purchase recorded from a receipt.
 *
 * @author Bernard Bawak
 * @version Fall 2026
 */
public class Receipt {
	public static final String VENDOR_REQUIRED = "Vendor is required.";
	public static final String DATE_REQUIRED = "Date is required.";
	public static final String AMOUNT_REQUIRED = "Amount is required.";
	public static final String AMOUNT_NOT_A_NUMBER = "Amount must be a number.";
	public static final String AMOUNT_NEGATIVE = "Amount must not be negative.";
	public static final String CATEGORY_REQUIRED = "Category is required.";

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	private String vendor;
	private LocalDate date;
	private double amount;
	private Category category;
	private boolean taxRelated;

	/**
	 * Creates a receipt entry with the given details.
	 *
	 * @precondition vendor != null && !vendor.isBlank() && date != null &&
	 *               amount is a finite number >= 0 && category != null
	 * @postcondition getVendor() equals vendor without surrounding whitespace &&
	 *                getDate() == date && getAmount() == amount &&
	 *                getCategory() == category && isTaxRelated() == taxRelated
	 *
	 * @param vendor     the store or business the purchase was made from
	 * @param date       the date of the purchase
	 * @param amount     the total amount paid
	 * @param category   the spending category of the purchase
	 * @param taxRelated true if the purchase is a tax-related business cost
	 */
	public Receipt(String vendor, LocalDate date, double amount, Category category, boolean taxRelated) {
		this.setVendor(vendor);
		this.setDate(date);
		this.setAmount(amount);
		this.setCategory(category);
		this.setTaxRelated(taxRelated);
	}

	/**
	 * Converts text entered by the user into a purchase amount. A leading dollar
	 * sign and surrounding whitespace are allowed.
	 *
	 * @precondition text != null && !text.isBlank() && text is a number >= 0
	 * @postcondition none
	 *
	 * @param text the amount as typed by the user, for example "45.20" or "$45.20"
	 *
	 * @return the amount represented by the text
	 */
	public static double parseAmount(String text) {
		if (text == null || text.isBlank()) {
			throw new IllegalArgumentException(AMOUNT_REQUIRED);
		}
		String digits = text.strip();
		if (digits.startsWith("$")) {
			digits = digits.substring(1);
		}
		double amount;
		try {
			amount = Double.parseDouble(digits);
		} catch (NumberFormatException exception) {
			throw new IllegalArgumentException(AMOUNT_NOT_A_NUMBER);
		}
		checkAmount(amount);
		return amount;
	}

	/**
	 * Formats an amount of money for display, for example 45.2 becomes "$45.20".
	 *
	 * @precondition none
	 * @postcondition none
	 *
	 * @param amount the amount of money to format
	 *
	 * @return the amount with a dollar sign and exactly two decimal places
	 */
	public static String formatAmount(double amount) {
		return String.format("$%.2f", amount);
	}

	private static void checkAmount(double amount) {
		if (!Double.isFinite(amount)) {
			throw new IllegalArgumentException(AMOUNT_NOT_A_NUMBER);
		}
		if (amount < 0) {
			throw new IllegalArgumentException(AMOUNT_NEGATIVE);
		}
	}

	/**
	 * Gets the store or business the purchase was made from.
	 *
	 * @return the vendor
	 */
	public String getVendor() {
		return this.vendor;
	}

	/**
	 * Changes the store or business the purchase was made from.
	 *
	 * @precondition vendor != null && !vendor.isBlank()
	 * @postcondition getVendor() equals vendor without surrounding whitespace
	 *
	 * @param vendor the new vendor
	 */
	public void setVendor(String vendor) {
		if (vendor == null || vendor.isBlank()) {
			throw new IllegalArgumentException(VENDOR_REQUIRED);
		}
		this.vendor = vendor.strip();
	}

	/**
	 * Gets the date of the purchase.
	 *
	 * @return the purchase date
	 */
	public LocalDate getDate() {
		return this.date;
	}

	/**
	 * Gets the date of the purchase formatted for display as MM/dd/yyyy.
	 *
	 * @return the formatted purchase date
	 */
	public String getFormattedDate() {
		return this.date.format(DATE_FORMAT);
	}

	/**
	 * Changes the date of the purchase.
	 *
	 * @precondition date != null
	 * @postcondition getDate() == date
	 *
	 * @param date the new purchase date
	 */
	public void setDate(LocalDate date) {
		if (date == null) {
			throw new IllegalArgumentException(DATE_REQUIRED);
		}
		this.date = date;
	}

	/**
	 * Gets the total amount paid.
	 *
	 * @return the amount
	 */
	public double getAmount() {
		return this.amount;
	}

	/**
	 * Gets the total amount paid formatted for display, for example "$45.20".
	 *
	 * @return the formatted amount
	 */
	public String getFormattedAmount() {
		return formatAmount(this.amount);
	}

	/**
	 * Changes the total amount paid.
	 *
	 * @precondition amount is a finite number && amount >= 0
	 * @postcondition getAmount() == amount
	 *
	 * @param amount the new amount
	 */
	public void setAmount(double amount) {
		checkAmount(amount);
		this.amount = amount;
	}

	/**
	 * Gets the spending category of the purchase.
	 *
	 * @return the category
	 */
	public Category getCategory() {
		return this.category;
	}

	/**
	 * Changes the spending category of the purchase.
	 *
	 * @precondition category != null
	 * @postcondition getCategory() == category
	 *
	 * @param category the new category
	 */
	public void setCategory(Category category) {
		if (category == null) {
			throw new IllegalArgumentException(CATEGORY_REQUIRED);
		}
		this.category = category;
	}

	/**
	 * Checks whether the purchase is a tax-related business cost.
	 *
	 * @return true if the purchase is tax-related, false otherwise
	 */
	public boolean isTaxRelated() {
		return this.taxRelated;
	}

	/**
	 * Marks whether the purchase is a tax-related business cost.
	 *
	 * @precondition none
	 * @postcondition isTaxRelated() == taxRelated
	 *
	 * @param taxRelated true if the purchase is tax-related, false otherwise
	 */
	public void setTaxRelated(boolean taxRelated) {
		this.taxRelated = taxRelated;
	}
}
