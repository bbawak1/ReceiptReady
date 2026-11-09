package edu.westga.comp4420.receiptready.model;

/**
 * The spending categories a receipt entry can be organized into.
 *
 * @author Bernard Bawak
 * @version Fall 2026
 */
public enum Category {
	SUPPLIES("Supplies"),
	MEALS("Meals"),
	TRAVEL("Travel"),
	UTILITIES("Utilities"),
	EQUIPMENT("Equipment"),
	OTHER("Other");

	private final String displayName;

	Category(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Gets the name of the category as it is shown to the user.
	 *
	 * @precondition none
	 * @postcondition none
	 *
	 * @return the user-facing name of the category
	 */
	public String getDisplayName() {
		return this.displayName;
	}

	/**
	 * Returns the user-facing name of the category so it displays correctly in
	 * selection controls.
	 *
	 * @precondition none
	 * @postcondition none
	 *
	 * @return the user-facing name of the category
	 */
	@Override
	public String toString() {
		return this.displayName;
	}
}
