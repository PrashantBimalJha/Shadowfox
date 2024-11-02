import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Inventory extends Application {
    private TableView<InventoryItem> tableView;
    private TextField itemIdField, itemNameField, quantityField, priceField;
    private ObservableList<InventoryItem> inventoryList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inventory Management System");

        // Key Point: Initialize input fields for Item ID, Name, Quantity, and Price.
        itemIdField = new TextField();
        itemIdField.setPromptText("Item ID");

        itemNameField = new TextField();
        itemNameField.setPromptText("Item Name");

        quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        priceField = new TextField();
        priceField.setPromptText("Price");

        // Key Point: Initialize buttons for CRUD operations: Add, Update, and Delete.
        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");

        // Key Point: Set up the TableView to display inventory items.
        tableView = new TableView<>();
        
        // Define table columns and map them to InventoryItem properties.
        TableColumn<InventoryItem, String> idColumn = new TableColumn<>("Item ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<InventoryItem, String> nameColumn = new TableColumn<>("Item Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<InventoryItem, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<InventoryItem, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Add columns to the TableView
        tableView.getColumns().addAll(idColumn, nameColumn, quantityColumn, priceColumn);

        // Initialize the inventory list and bind it to the TableView
        inventoryList = FXCollections.observableArrayList();
        tableView.setItems(inventoryList);

        // Event Handling: Add, Update, and Delete buttons
        addButton.setOnAction(e -> addItem());
        updateButton.setOnAction(e -> updateItem());
        deleteButton.setOnAction(e -> deleteItem());

        // Layout using GridPane for input fields
        GridPane inputGrid = new GridPane();
        inputGrid.setPadding(new Insets(10));
        inputGrid.setVgap(8);
        inputGrid.setHgap(10);
        inputGrid.add(new Label("Item ID:"), 0, 0);
        inputGrid.add(itemIdField, 1, 0);
        inputGrid.add(new Label("Item Name:"), 0, 1);
        inputGrid.add(itemNameField, 1, 1);
        inputGrid.add(new Label("Quantity:"), 0, 2);
        inputGrid.add(quantityField, 1, 2);
        inputGrid.add(new Label("Price:"), 0, 3);
        inputGrid.add(priceField, 1, 3);

        // VBox for organizing the grid, buttons, and TableView
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(inputGrid, addButton, updateButton, deleteButton, tableView);

        Scene scene = new Scene(layout, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to add a new item to the inventory
    private void addItem() {
        try {
            String itemId = itemIdField.getText();
            String itemName = itemNameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());

            InventoryItem newItem = new InventoryItem(itemId, itemName, quantity, price);
            inventoryList.add(newItem);
            clearFields(); // Key Point: Clear input fields after adding
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid numbers for quantity and price.");
        }
    }

    // Method to update selected item in the inventory
    private void updateItem() {
        InventoryItem selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                selected.setItemName(itemNameField.getText());
                selected.setQuantity(Integer.parseInt(quantityField.getText()));
                selected.setPrice(Double.parseDouble(priceField.getText()));
                tableView.refresh(); // Key Point: Refresh the table view to reflect changes
                clearFields();
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter valid numbers for quantity and price.");
            }
        } else {
            showAlert("No Selection", "Please select an item to update.");
        }
    }

    // Method to delete selected item from the inventory
    private void deleteItem() {
        InventoryItem selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            inventoryList.remove(selected);
            clearFields(); // Key Point: Clear fields after deleting
        } else {
            showAlert("No Selection", "Please select an item to delete.");
        }
    }

    // Utility method to clear input fields
    private void clearFields() {
        itemIdField.clear();
        itemNameField.clear();
        quantityField.clear();
        priceField.clear();
    }

    // Utility method to show an alert message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // InventoryItem class (nested for simplicity)
    public static class InventoryItem {
        private String itemId;
        private String itemName;
        private int quantity;
        private double price;

        public InventoryItem(String itemId, String itemName, int quantity, double price) {
            this.itemId = itemId;
            this.itemName = itemName;
            this.quantity = quantity;
            this.price = price;
        }

        // Getters and setters for properties
        public String getItemId() { return itemId; }
        public void setItemId(String itemId) { this.itemId = itemId; }

        public String getItemName() { return itemName; }
        public void setItemName(String itemName) { this.itemName = itemName; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
    }
}
