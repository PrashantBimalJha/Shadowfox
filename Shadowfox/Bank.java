import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.*;

public class Bank extends Application {

    private Map<String, BankAccount> accounts = new HashMap<>();

    public static void main(String[] args) {
        launch(args); // Launch JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        // Set up the primary stage
        primaryStage.setTitle("Bank Account Management System");

        // GridPane layout for the main screen
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        // Creating labels, text fields and buttons
        Label accountNumberLabel = new Label("Account Number:");
        TextField accountNumberField = new TextField();

        Label balanceLabel = new Label("Initial Balance:");
        TextField balanceField = new TextField();

        Label accountTypeLabel = new Label("Account Type:");
        ChoiceBox<String> accountTypeBox = new ChoiceBox<>();
        accountTypeBox.getItems().addAll("Savings", "Checking");

        Label currencyLabel = new Label("Currency:");
        ChoiceBox<String> currencyBox = new ChoiceBox<>();
        currencyBox.getItems().addAll("USD", "EUR", "INR");

        Button createAccountButton = new Button("Create Account");

        // Transaction buttons
        Label transactionAmountLabel = new Label("Transaction Amount:");
        TextField transactionAmountField = new TextField();
        
        Button depositButton = new Button("Deposit");
        Button withdrawButton = new Button("Withdraw");

        // Transaction history
        TextArea transactionHistoryArea = new TextArea();
        transactionHistoryArea.setEditable(false);

        // Button to view transaction history
        Button viewHistoryButton = new Button("View Transaction History");

        // Add components to the grid
        grid.add(accountNumberLabel, 0, 0);
        grid.add(accountNumberField, 1, 0);

        grid.add(balanceLabel, 0, 1);
        grid.add(balanceField, 1, 1);

        grid.add(accountTypeLabel, 0, 2);
        grid.add(accountTypeBox, 1, 2);

        grid.add(currencyLabel, 0, 3);
        grid.add(currencyBox, 1, 3);

        grid.add(createAccountButton, 1, 4);

        grid.add(transactionAmountLabel, 0, 5);
        grid.add(transactionAmountField, 1, 5);

        grid.add(depositButton, 0, 6);
        grid.add(withdrawButton, 1, 6);

        grid.add(viewHistoryButton, 1, 7);
        grid.add(transactionHistoryArea, 1, 8);

        // Set up the scene and show the stage
        Scene scene = new Scene(grid, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Event handling for create account
        createAccountButton.setOnAction(e -> {
            String accountNumber = accountNumberField.getText();
            double initialBalance = Double.parseDouble(balanceField.getText());
            String accountType = accountTypeBox.getValue();
            String currency = currencyBox.getValue();

            if (accountNumber != null && !accountNumber.isEmpty() && initialBalance >= 0 && accountType != null && currency != null) {
                BankAccount account = new BankAccount(accountNumber, initialBalance, accountType, currency);
                accounts.put(accountNumber, account);
                transactionHistoryArea.appendText("Account created: " + accountNumber + "\n");
            } else {
                transactionHistoryArea.appendText("Failed to create account.\n");
            }
        });

        // Event handling for deposit
        depositButton.setOnAction(e -> {
            String accountNumber = accountNumberField.getText();
            BankAccount account = accounts.get(accountNumber);
            if (account != null) {
                double amount = Double.parseDouble(transactionAmountField.getText());
                try {
                    account.deposit(amount);
                    transactionHistoryArea.appendText("Deposited " + amount + " to account: " + accountNumber + "\n");
                } catch (IllegalArgumentException ex) {
                    transactionHistoryArea.appendText("Error: " + ex.getMessage() + "\n");
                }
            } else {
                transactionHistoryArea.appendText("Account not found.\n");
            }
        });

        // Event handling for withdraw
        withdrawButton.setOnAction(e -> {
            String accountNumber = accountNumberField.getText();
            BankAccount account = accounts.get(accountNumber);
            if (account != null) {
                double amount = Double.parseDouble(transactionAmountField.getText());
                try {
                    account.withdraw(amount);
                    transactionHistoryArea.appendText("Withdrew " + amount + " from account: " + accountNumber + "\n");
                } catch (IllegalArgumentException ex) {
                    transactionHistoryArea.appendText("Error: " + ex.getMessage() + "\n");
                }
            } else {
                transactionHistoryArea.appendText("Account not found.\n");
            }
        });

        // Event handling for viewing transaction history
        viewHistoryButton.setOnAction(e -> {
            String accountNumber = accountNumberField.getText();
            BankAccount account = accounts.get(accountNumber);
            if (account != null) {
                transactionHistoryArea.appendText("Transaction history for account: " + accountNumber + "\n");
                for (String entry : account.getTransactionHistory()) {
                    transactionHistoryArea.appendText(entry + "\n");
                }
            } else {
                transactionHistoryArea.appendText("Account not found.\n");
            }
        });
    }
}

// BankAccount class remains the same as before, included for completeness
class BankAccount {
    private String accountNumber;
    private double balance;
    private List<String> transactionHistory;
    private String accountType;
    private double interestRate;
    private String currency;

    // Constructor and methods are the same as before
    public BankAccount(String accountNumber, double initialBalance, String accountType, String currency) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.accountType = accountType;
        this.currency = currency;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account created with initial balance: " + initialBalance + " " + currency);

        if (accountType.equals("Savings")) {
            this.interestRate = 0.05; // 5% interest rate for savings
        } else {
            this.interestRate = 0.00;
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: " + amount + " " + currency);
        } else {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: " + amount + " " + currency);
        } else {
            throw new IllegalArgumentException("Invalid withdrawal amount");
        }
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }
}
