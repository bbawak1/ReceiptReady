package edu.westga.comp4420.receiptready;

import edu.westga.comp4420.receiptready.view.ReceiptListPage;
import javafx.application.Application;

/**
 * Entry point for the ReceiptReady JavaFX application.
 *
 * @author Bernard Bawak
 * @version Fall 2026
 */
public final class Main {

	private Main() {
	}

	/**
	 * Launches the ReceiptReady JavaFX application.
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		Application.launch(ReceiptListPage.class, args);
	}
}
