package edu.westga.comp4420.receiptready.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Tracks a collection of business expenses.
 *
 * @author Bernard Bawak
 * @version Fall 2026
 */
public class ExpenseTracker {
	private final List<Expense> expenses;

	/**
	 * Creates an empty expense tracker.
	 */
	public ExpenseTracker() {
		this.expenses = new ArrayList<>();
	}

	/**
	 * Adds an expense to the tracker.
	 *
	 * @param expense the expense to add
	 */
	public void addExpense(Expense expense) {
		if (expense == null) {
			throw new IllegalArgumentException("expense cannot be null");
		}
		this.expenses.add(expense);
	}

	/**
	 * Gets all tracked expenses.
	 *
	 * @return a copy of the expense list
	 */
	public List<Expense> getExpenses() {
		return new ArrayList<>(this.expenses);
	}

	/**
	 * Calculates the total of all tracked expenses.
	 *
	 * @return the total amount
	 */
	public double getTotalSpending() {
		double total = 0.0;
		for (Expense current : this.expenses) {
			total += current.getAmount();
		}
		return total;
	}

	/**
	 * Calculates the total of tax-related expenses.
	 *
	 * @return the tax-related total
	 */
	public double getTaxRelatedTotal() {
		double total = 0.0;
		for (Expense current : this.expenses) {
			if (current.isTaxRelated()) {
				total += current.getAmount();
			}
		}
		return total;
	}

	/**
	 * Counts how many expenses are currently tracked.
	 *
	 * @return the number of expenses
	 */
	public int getExpenseCount() {
		return this.expenses.size();
	}
}
