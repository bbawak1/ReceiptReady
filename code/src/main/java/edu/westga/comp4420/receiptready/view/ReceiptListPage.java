package edu.westga.comp4420.receiptready.view;

import java.util.function.Function;

import edu.westga.comp4420.receiptready.model.Receipt;
import edu.westga.comp4420.receiptready.model.ReceiptBook;
import edu.westga.comp4420.receiptready.model.ReceiptSummary;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Codebehind for the Receipt List window, the main window of ReceiptReady. It
 * shows all receipt entries and opens the other windows.
 *
 * @author Bernard Bawak
 * @version Fall 2026
 */
public class ReceiptListPage extends Application {
	private final ReceiptBook receiptBook;
	private final ReceiptSummary summary;
	private final TableView<Receipt> receiptTable;
	private final Label statusLabel;
	private final TextField searchField;
	private Stage stage;

	/**
	 * Creates the Receipt List window with an empty receipt book.
	 *
	 * @precondition none
	 * @postcondition the window is ready to be started
	 */
	public ReceiptListPage() {
		this.receiptBook = new ReceiptBook();
		this.summary = new ReceiptSummary(this.receiptBook);
		this.receiptTable = new TableView<Receipt>();
		this.statusLabel = new Label();
		this.searchField = new TextField();
	}

	/**
	 * Builds and shows the Receipt List window.
	 *
	 * @precondition primaryStage != null
	 * @postcondition the window is showing
	 *
	 * @param primaryStage the stage provided by JavaFX
	 */
	@Override
	public void start(Stage primaryStage) {
		this.stage = primaryStage;
		this.buildTable();
		VBox.setVgrow(this.receiptTable, Priority.ALWAYS);
		VBox layout = new VBox(12, this.buildSearchBar(), this.receiptTable, this.statusLabel, this.buildButtons());
		layout.setPadding(new Insets(16));
		
		this.stage.setTitle("ReceiptReady - Receipt List");
		this.stage.setScene(new Scene(layout, 640, 460));
		this.stage.show();
	}

	private void buildTable() {
		this.receiptTable.setPlaceholder(new Label("No receipts yet. Click Add to record a purchase."));
		this.receiptTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.receiptTable.getColumns().add(this.createColumn("Vendor", Receipt::getVendor));
		this.receiptTable.getColumns().add(this.createColumn("Date", Receipt::getFormattedDate));
		this.receiptTable.getColumns().add(this.createColumn("Amount", Receipt::getFormattedAmount));
		this.receiptTable.getColumns().add(this.createColumn("Category", receipt -> receipt.getCategory().toString()));
		this.receiptTable.getColumns().add(this.createColumn("Tax", receipt -> receipt.isTaxRelated() ? "Yes" : "No"));
	}

	private TableColumn<Receipt, String> createColumn(String title, Function<Receipt, String> value) {
		TableColumn<Receipt, String> column = new TableColumn<Receipt, String>(title);
		column.setCellValueFactory(cell -> new ReadOnlyStringWrapper(value.apply(cell.getValue())));
		return column;
	}

	private HBox buildButtons() {
		Button addButton = new Button("Add");
		addButton.setOnAction(event -> this.handleAdd());
		Button editButton = new Button("Edit");
		editButton.setOnAction(event -> this.handleEdit());
		Button deleteButton = new Button("Delete");
		deleteButton.setOnAction(event -> this.handleDelete());
		Button summaryButton = new Button("View Summary");
		summaryButton.setOnAction(event -> this.handleViewSummary());
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		return new HBox(10, addButton, editButton, deleteButton, spacer, summaryButton);
	}
	
	private HBox buildSearchBar() {
		this.searchField.setPromptText("Search by vendor");
		this.searchField.setOnAction(event -> this.handleSearch());
		HBox.setHgrow(this.searchField, Priority.ALWAYS);
		Button searchButton = new Button("Search");
		searchButton.setOnAction(event -> this.handleSearch());
		Button clearButton = new Button("Clear");
		clearButton.setOnAction(event -> this.handleClear());
		return new HBox(10, this.searchField, searchButton, clearButton);
	}
	
	private void handleSearch() {
		this.receiptTable.getItems().setAll(this.receiptBook.searchByVendor(this.searchField.getText()));
		this.statusLabel.setText(this.receiptTable.getItems().size() + " receipt(s) found");
	}

	private void handleClear() {
		this.searchField.clear();
		this.refreshReceipts();
	}
	
	private void handleAdd() {
		new ReceiptDetailsPage(this.stage, this.receiptBook, null).showAndWait();
		this.refreshReceipts();
	}

	private void handleEdit() {
		Receipt selected = this.receiptTable.getSelectionModel().getSelectedItem();
		if (selected == null) {
			this.statusLabel.setText("Select a receipt to edit.");
			return;
		}
		new ReceiptDetailsPage(this.stage, this.receiptBook, selected).showAndWait();
		this.refreshReceipts();
	}

	private void handleDelete() {
		Receipt selected = this.receiptTable.getSelectionModel().getSelectedItem();
		if (selected == null) {
			this.statusLabel.setText("Select a receipt to delete.");
			return;
		}
		this.receiptBook.removeReceipt(selected);
		this.refreshReceipts();
	}

	private void handleViewSummary() {
		new SpendingSummaryPage(this.stage, this.summary).showAndWait();
	}

	private void refreshReceipts() {
		this.receiptTable.getItems().setAll(this.receiptBook.getReceipts());
		this.receiptTable.refresh();
		this.statusLabel.setText(this.receiptBook.size() + " receipt(s)");
	}
}
