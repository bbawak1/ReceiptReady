# ReceiptReady

ReceiptReady helps small-business owners keep their receipts organized for bookkeeping and tax preparation. This MVP focuses on managing receipt entries: adding, editing, categorizing, and deleting them, and viewing spending summaries.

## Features

- **Receipt List window** - lists every receipt entry (vendor, date, amount, category, tax-related) with buttons to add, edit, and delete entries and to open the spending summary.
- **Receipt Details window** - form for adding a new receipt entry or editing an existing one, including its category and tax-related flag. Invalid input is reported on the form.
- **Spending Summary window** - total spent per category, total within a chosen date range, and total of tax-related receipts.

## Documentation

- [Product backlog](documentation/Product_Backlog.xlsx)
- [Sprint backlog](documentation/Sprint_Backlog.xlsx)
- [Wireframe and class diagram](documentation/Wireframe_and_Class_Diagram.png)

## Branches

- `dev` - default development branch; feature branches are created from and merged back into `dev` through pull requests
- `release` - protected release branch (pull request + review + CI required)

## Build and run

Requires JDK 17 and Maven.

```bash
cd code
mvn verify
mvn javafx:run
```

`mvn verify` runs the unit tests, Checkstyle, and the 95 percent JaCoCo line coverage check.
