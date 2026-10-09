# ReceiptReady — Product Backlog

Format matches the Sample Product Backlog: **Priority | As a… | I want to… | So that…**

Priorities were updated based on the Lab 2 proposal feedback to focus on editing existing receipt entries: categorizing, editing details, and generating summary views.

| Priority | As a… | I want to… | So that… |
|---:|---|---|---|
| 1 | Owner | add a receipt entry with a vendor, date, and amount | I can record a purchase I made |
| 1 | Owner | view my list of receipt entries | I can see all purchases I have recorded |
| 1 | Owner | edit the details of a receipt entry | I can correct mistakes in a recorded purchase |
| 1 | Owner | assign a category to a receipt entry | I can organize where my money is going |
| 1 | Owner | mark a receipt entry as tax-related | I can identify business costs for tax time |
| 1 | Owner | delete a receipt entry | I can remove a purchase I no longer need to track |
| 2 | Owner | view total spending for each category | I can see which areas of the business cost the most |
| 2 | Owner | view total spending within a date range | I can review a month or quarter of purchases |
| 2 | Owner | view the total of tax-related receipt entries | I know how much to report for tax preparation |
| 3 | Owner | filter receipt entries by category | I can review purchases in one area of the business |
| 3 | Owner | add a short note to a receipt entry | I can remember the business purpose of a purchase |
| 3 | Owner | save my receipt entries to a file | I keep my records between uses of the application |
| 4 | Owner | export tax-related receipt entries to a file | I can share a tax-ready list with my tax preparer |
| 4 | Owner | attach a photo of the receipt to a receipt entry | I keep proof of each purchase |
| 4 | Owner | take a picture of a receipt | I can capture proof of a purchase quickly |
| 5 | Owner | create an account | I can keep my business receipts private |
| 5 | Owner | log in to my account | I can access my receipt entries securely |
| 5 | Owner | update my credentials | I can keep my account secure |
| 5 | Owner | delete my account | I can remove my information from the system |
| 5 | Owner | have receipt details suggested from a photo | I spend less time entering vendors and amounts |
| 5 | Admin | deactivate a user account | I can stop access for someone who should no longer use the system |

## MVP Scope

The MVP includes all priority 1 and 2 user stories, implemented as a JavaFX application with three windows:

- **Receipt List window:** shows all receipt entries and provides Add, Edit, Delete, and View Summary actions.
- **Receipt Details window:** used to add a new receipt entry or edit an existing one (vendor, date, amount, category, tax-related).
- **Spending Summary window:** shows total spending for each category, total spending within a selected date range, and the total of tax-related receipt entries.

Priority 3 through 5 user stories are outside the MVP.
