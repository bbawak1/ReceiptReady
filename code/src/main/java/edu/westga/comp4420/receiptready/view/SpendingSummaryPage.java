package edu.westga.comp4420.receiptready.view;

import java.time.LocalDate;

import edu.westga.comp4420.receiptready.model.Category;
import edu.westga.comp4420.receiptready.model.Receipt;
import edu.westga.comp4420.receiptready.model.ReceiptSummary;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Codebehind for the Spending Summary window, which shows total spending for
 * each category, for a chosen date range, and for tax-related receipt entries.
 *
 * @author Bernard Bawak
 * @version Fall 2026
 */
public class SpendingSummaryPage {
	private static final String BOLD_STYLE = "-fx-font-weight: bold;";

	private final ReceiptSummary summary;
	private final Stage stage;
	private final DatePicker startPicker;
	private final DatePicker endPicker;
	private final Label rangeTotalLabel;

	/**
	 * Creates the Spending Summary window.
	 *
	 * @precondition owner != null && summary != null
	 * @postcondition the window is ready to be shown
	 *
	 * @param owner   the window that opened this window
	 * @param summary the summary used to calculate the totals
	 */
	public SpendingSummaryPage(Window owner, ReceiptSummary summary) {
		this.summary = summary;
		this.startPicker = new DatePicker(LocalDate.now().withDayOfMonth(1));
		this.endPicker = new DatePicker(LocalDate.now());
		this.rangeTotalLabel = new Label();
		this.rangeTotalLabel.setStyle(BOLD_STYLE);

		VBox layout = new VBox(18, this.buildCategoryTotals(), this.buildDateRangeTotal(),
				this.buildTaxRelatedTotal(), this.buildCloseButton());
		layout.setPadding(new Insets(18));

		this.stage = new Stage();
		this.stage.initOwner(owner);
		this.stage.initModality(Modality.WINDOW_MODAL);
		this.stage.setTitle("Spending Summary");
		this.stage.setScene(new Scene(layout));
		this.handleCalculate();
	}

	/**
	 * Shows the window and waits until the user closes it.
	 *
	 * @precondition none
	 * @postcondition the window is closed
	 */
	public void showAndWait() {
		this.stage.showAndWait();
	}

	private VBox buildCategoryTotals() {
		GridPane totals = new GridPane();
		totals.setHgap(40);
		totals.setVgap(6);
		int row = 0;
		for (Category category : Category.values()) {
			Label total = new Label(Receipt.formatAmount(this.summary.totalForCategory(category)));
			totals.addRow(row, new Label(category.getDisplayName()), total);
			row++;
		}
		return this.buildSection("Totals by Category", totals);
	}

	private VBox buildDateRangeTotal() {
		Button calculateButton = new Button("Calculate");
		calculateButton.setOnAction(event -> this.handleCalculate());
		GridPane range = new GridPane();
		range.setHgap(10);
		range.setVgap(8);
		range.addRow(0, new Label("Start"), this.startPicker);
		range.addRow(1, new Label("End"), this.endPicker);
		range.add(calculateButton, 1, 2);
		return this.buildSection("Total for Date Range", range, this.rangeTotalLabel);
	}

	private VBox buildTaxRelatedTotal() {
		Label taxTotal = new Label(Receipt.formatAmount(this.summary.totalTaxRelated()));
		return this.buildSection("Tax-related Total", taxTotal);
	}

	private HBox buildCloseButton() {
		Button closeButton = new Button("Close");
		closeButton.setCancelButton(true);
		closeButton.setOnAction(event -> this.stage.close());
		HBox buttons = new HBox(closeButton);
		buttons.setAlignment(Pos.CENTER_RIGHT);
		return buttons;
	}

	private VBox buildSection(String title, Node... content) {
		Label heading = new Label(title);
		heading.setStyle(BOLD_STYLE);
		VBox section = new VBox(8, heading);
		section.getChildren().addAll(content);
		return section;
	}

	private void handleCalculate() {
		try {
			double total = this.summary.totalBetween(this.startPicker.getValue(), this.endPicker.getValue());
			this.rangeTotalLabel.setText("Total: " + Receipt.formatAmount(total));
		} catch (IllegalArgumentException error) {
			this.rangeTotalLabel.setText(error.getMessage());
		}
	}
}
