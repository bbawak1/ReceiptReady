package edu.westga.comp4420.receiptready.view;

import java.time.LocalDate;

import edu.westga.comp4420.receiptready.model.Category;
import edu.westga.comp4420.receiptready.model.Receipt;
import edu.westga.comp4420.receiptready.model.ReceiptBook;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Codebehind for the Receipt Details window, which is used both to add a new
 * receipt entry and to edit the details of an existing one.
 *
 * @author Bernard Bawak
 * @version Fall 2026
 */
public class ReceiptDetailsPage {
	private final ReceiptBook receiptBook;
	private final Receipt receipt;
	private final Stage stage;
	private final TextField vendorField;
	private final DatePicker datePicker;
	private final TextField amountField;
	private final ComboBox<Category> categoryBox;
	private final CheckBox taxRelatedBox;
	private final Label errorLabel;

	/**
	 * Creates the Receipt Details window.
	 *
	 * @precondition owner != null && receiptBook != null
	 * @postcondition the window is ready to be shown
	 *
	 * @param owner       the window that opened this window
	 * @param receiptBook the receipt book a new receipt entry is added to
	 * @param receipt     the receipt entry to edit, or null to add a new receipt
	 *                    entry
	 */
	public ReceiptDetailsPage(Window owner, ReceiptBook receiptBook, Receipt receipt) {
		this.receiptBook = receiptBook;
		this.receipt = receipt;
		this.vendorField = new TextField();
		this.datePicker = new DatePicker();
		this.amountField = new TextField();
		this.categoryBox = new ComboBox<Category>();
		this.categoryBox.getItems().setAll(Category.values());
		this.taxRelatedBox = new CheckBox("Tax-related");
		this.errorLabel = new Label();
		this.errorLabel.setStyle("-fx-text-fill: #b00020;");
		this.loadReceiptDetails();

		this.stage = new Stage();
		this.stage.initOwner(owner);
		this.stage.initModality(Modality.WINDOW_MODAL);
		this.stage.setTitle("Receipt Details");
		this.stage.setScene(new Scene(this.buildLayout()));
	}

	/**
	 * Shows the window and waits until the user saves or cancels.
	 *
	 * @precondition none
	 * @postcondition the window is closed
	 */
	public void showAndWait() {
		this.stage.showAndWait();
	}

	private void loadReceiptDetails() {
		if (this.receipt == null) {
			this.datePicker.setValue(LocalDate.now());
			this.categoryBox.setValue(Category.OTHER);
			return;
		}
		this.vendorField.setText(this.receipt.getVendor());
		this.datePicker.setValue(this.receipt.getDate());
		this.amountField.setText(String.format("%.2f", this.receipt.getAmount()));
		this.categoryBox.setValue(this.receipt.getCategory());
		this.taxRelatedBox.setSelected(this.receipt.isTaxRelated());
	}

	private VBox buildLayout() {
		GridPane form = new GridPane();
		form.setHgap(12);
		form.setVgap(10);
		form.addRow(0, new Label("Vendor"), this.vendorField);
		form.addRow(1, new Label("Date"), this.datePicker);
		form.addRow(2, new Label("Amount"), this.amountField);
		form.addRow(3, new Label("Category"), this.categoryBox);
		form.add(this.taxRelatedBox, 1, 4);
		this.vendorField.setPrefColumnCount(20);
		this.categoryBox.setMaxWidth(Double.MAX_VALUE);
		this.datePicker.setMaxWidth(Double.MAX_VALUE);

		Button saveButton = new Button("Save");
		saveButton.setDefaultButton(true);
		saveButton.setOnAction(event -> this.handleSave());
		Button cancelButton = new Button("Cancel");
		cancelButton.setCancelButton(true);
		cancelButton.setOnAction(event -> this.stage.close());
		HBox buttons = new HBox(10, saveButton, cancelButton);
		buttons.setAlignment(Pos.CENTER_RIGHT);

		VBox layout = new VBox(14, form, this.errorLabel, buttons);
		layout.setPadding(new Insets(18));
		return layout;
	}

	private void handleSave() {
		try {
			Receipt details = new Receipt(this.vendorField.getText(), this.datePicker.getValue(),
					Receipt.parseAmount(this.amountField.getText()), this.categoryBox.getValue(),
					this.taxRelatedBox.isSelected());
			if (this.receipt == null) {
				this.receiptBook.addReceipt(details);
			} else {
				this.receipt.setVendor(details.getVendor());
				this.receipt.setDate(details.getDate());
				this.receipt.setAmount(details.getAmount());
				this.receipt.setCategory(details.getCategory());
				this.receipt.setTaxRelated(details.isTaxRelated());
			}
			this.stage.close();
		} catch (IllegalArgumentException error) {
			this.errorLabel.setText(error.getMessage());
		}
	}
}
