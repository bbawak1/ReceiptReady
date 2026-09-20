package edu.westga.comp4420.receiptready.view;

import edu.westga.comp4420.receiptready.model.Expense;
import edu.westga.comp4420.receiptready.model.ExpenseTracker;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main JavaFX window for adding and reviewing expenses.
 *
 * @author Bernard Bawak
 * @version Fall 2026
 */
public class MainWindow extends Application {
	private final ExpenseTracker tracker;
	private Label summaryLabel;

	/**
	 * Creates the main window.
	 */
	public MainWindow() {
		this.tracker = new ExpenseTracker();
	}

	/**
	 * Starts the JavaFX user interface.
	 *
	 * @param primaryStage the primary stage
	 */
	@Override
	public void start(Stage primaryStage) {
		TextField vendorField = new TextField();
		vendorField.setPromptText("Vendor");
		TextField amountField = new TextField();
		amountField.setPromptText("Amount");
		TextField categoryField = new TextField();
		categoryField.setPromptText("Category");
		CheckBox taxBox = new CheckBox("Tax-related");
		this.summaryLabel = new Label("Expenses: 0 | Total: 0.00");
		Button addButton = new Button("Add Expense");
		addButton.setOnAction(event -> this.handleAdd(vendorField, amountField, categoryField, taxBox));

		VBox root = new VBox(10, vendorField, amountField, categoryField, taxBox, addButton, this.summaryLabel);
		root.setPadding(new Insets(15));
		primaryStage.setTitle("ReceiptReady");
		primaryStage.setScene(new Scene(root, 360, 280));
		primaryStage.show();
	}

	private void handleAdd(TextField vendorField, TextField amountField, TextField categoryField, CheckBox taxBox) {
		try {
			double amount = Double.parseDouble(amountField.getText());
			Expense expense = new Expense(vendorField.getText(), amount, categoryField.getText(), taxBox.isSelected());
			this.tracker.addExpense(expense);
			this.summaryLabel.setText("Expenses: " + this.tracker.getExpenseCount()
					+ " | Total: " + String.format("%.2f", this.tracker.getTotalSpending()));
			vendorField.clear();
			amountField.clear();
			categoryField.clear();
			taxBox.setSelected(false);
		} catch (RuntimeException exception) {
			this.summaryLabel.setText("Could not add expense. Check your input.");
		}
	}
}
