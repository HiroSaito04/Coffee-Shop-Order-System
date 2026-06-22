// src/controllers/OrderMenuController.java
package controllers;

import database.OrderQueue;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderMenuController extends MainMenuController {
    
    @FXML private Button add1, add2, add3, add4, add5, add6, add7, add8, add9;
    @FXML private TextField quan1, quan2, quan3, quan4, quan5, quan6, quan7, quan8, quan9;
    @FXML private Button chkout, clr;
    @FXML private ToggleGroup addons;
    @FXML private ToggleGroup size;
    @FXML private ToggleGroup temp;
    @FXML private RadioButton tempA, tempB, sizeA, sizeB;
    @FXML private RadioButton addon1, addon2, addon3, addon4, addon5, addon6, addon7;
    @FXML private Button logoutButton;
    @FXML private TableView<Order> orderTable;
    @FXML private TableColumn<Order, String> flavTab;
    @FXML private TableColumn<Order, String> addonsTab;
    @FXML private TableColumn<Order, Integer> qtyTab;
    @FXML private TableColumn<Order, String> sizeTab;
    @FXML private TableColumn<Order, String> tempTab;
    @FXML private Label tots;

    private int totalQuan = 0;
    public static int ordnumb = 0;
    private int totalPrice = 0;

    private final ObservableList<Order> orders = FXCollections.observableArrayList();
    private OrderQueue que;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        flavTab.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().flavor));
        addonsTab.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().addon));
        sizeTab.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().size));
        tempTab.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().temp));
        qtyTab.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().quantity).asObject());

        orderTable.setItems(orders);

        addNumericInputFormatter(quan1);
        addNumericInputFormatter(quan2);
        addNumericInputFormatter(quan3);
        addNumericInputFormatter(quan4);
        addNumericInputFormatter(quan5);
        addNumericInputFormatter(quan6);
        addNumericInputFormatter(quan7);
        addNumericInputFormatter(quan8);
        addNumericInputFormatter(quan9);
    }

    private void addNumericInputFormatter(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            return newText.matches("\\d*") ? change : null;
        }));
    }

    @FXML
    public void handleAddAction(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        TextField correspondingTextField = getCorrespondingTextField(clickedButton);

        if (correspondingTextField == null) {
            return;
        }

        int quan = getQuantity(correspondingTextField);
        String flav = getFlavor(clickedButton);
        String sayz = getSize();
        String tempz = getTemperature();
        String adons = getAddon();

        if (quan <= 0 || sayz.equals("None") || tempz.equals("None")) {
            showAlert(
                "Incomplete Order",
                "Invalid Order!",
                "Please ensure all orders have quantity, size, and temperature specified.",
                AlertType.WARNING
            );
            return;
        }

        int orderPrice = getPrice(sayz, quan, adons);

        totalPrice += orderPrice;
        totalQuan += quan;

        tots.setText("PHP " + totalPrice);
        orders.add(new Order(flav, quan, sayz, tempz, adons, orderPrice));

        correspondingTextField.clear();
        size.selectToggle(null);
        temp.selectToggle(null);
        addons.selectToggle(null);
    }

    private TextField getCorrespondingTextField(Button button) {
        return switch (button.getId()) {
            case "add1" -> quan1;
            case "add2" -> quan2;
            case "add3" -> quan3;
            case "add4" -> quan4;
            case "add5" -> quan5;
            case "add6" -> quan6;
            case "add7" -> quan7;
            case "add8" -> quan8;
            case "add9" -> quan9;
            default -> null;
        };
    }

    private String getFlavor(Button button) {
        return switch (button.getId()) {
            case "add1" -> "Caramel Macchiato";
            case "add2" -> "Choco Espresso";
            case "add3" -> "Chocolate Pistachio";
            case "add4" -> "Dark Mocha Latte";
            case "add5" -> "Dirty Matcha Latte";
            case "add6" -> "Iced Americano";
            case "add7" -> "Pistachio Espresso";
            case "add8" -> "Pistachio Strawberry";
            case "add9" -> "Strawberry Over Ice";
            default -> "Unknown Flavor";
        };
    }

    private String getAddon() {
        if (addon1.isSelected()) return "Decaf";
        if (addon2.isSelected()) return "Milk Shot";
        if (addon3.isSelected()) return "Drizzle";
        if (addon4.isSelected()) return "Espresso Shot";
        if (addon5.isSelected()) return "Cold Foam";
        if (addon6.isSelected()) return "Coffee Jelly";
        if (addon7.isSelected()) return "Ice Cream Scoop";
        return "None";
    }

    private String getSize() {
        if (sizeA.isSelected()) return "12 Oz";
        if (sizeB.isSelected()) return "20 Oz";
        return "None";
    }

    private String getTemperature() {
        if (tempA.isSelected()) return "Hot";
        if (tempB.isSelected()) return "Cold";
        return "None";
    }

    private int getQuantity(TextField textField) {
        String text = textField.getText();
        return text.isEmpty() ? 0 : Integer.parseInt(text);
    }

    @FXML
    public void placeAllOrders() {
        que = OrderQueue.getInstance();

        if (orders.isEmpty()) {
            showAlert(
                "Incomplete Orders",
                "Invalid Order!",
                "No orders have been placed.",
                AlertType.WARNING
            );
            return;
        }

        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Place Order");
        confirmationAlert.setHeaderText("Are you sure you want to place all orders?");
        confirmationAlert.setContentText("Click OK to confirm.");

        if (confirmationAlert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            ordnumb++;

            System.out.println("All Orders for Bulk Order #" + ordnumb + ":");

            for (Order order : orders) {
                que.addOrderToQueue(
                    order.flavor,
                    order.size,
                    order.temp,
                    order.addon,
                    order.quantity,
                    order.price,
                    ordnumb
                );

                System.out.println(
                    "Bulk Order #: " + ordnumb +
                    " | Flavor: " + order.flavor +
                    " | Size: " + order.size +
                    " | Temp: " + order.temp +
                    " | Add-ons: " + order.addon +
                    " | Quantity: " + order.quantity +
                    " | Price: PHP " + order.price
                );
            }

            orders.clear();
            totalQuan = 0;
            totalPrice = 0;
            tots.setText("PHP 0");

            System.out.println("Orders Added!");
        }
    }

    @FXML
    public void cancelOrders() {
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Cancel Orders");
        confirmationAlert.setHeaderText("Are you sure you want to cancel all orders?");
        confirmationAlert.setContentText("Click OK to confirm.");

        if (confirmationAlert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            orders.clear();
            totalQuan = 0;
            totalPrice = 0;
            tots.setText("PHP 0");

            size.selectToggle(null);
            temp.selectToggle(null);
            addons.selectToggle(null);

            System.out.println("All orders have been canceled.");
        }
    }

    private int getPrice(String size, int quantity, String addon) {
        int price = 0;

        if (size.equals("12 Oz")) {
            price = 20;
        } else if (size.equals("20 Oz")) {
            price = 30;
        }

        int totalCost = price * quantity;

        if (!addon.equals("None")) {
            totalCost += 5;
        }

        return totalCost;
    }

    public static int getOrderNumber() {
        return ordnumb;
    }

    private void showAlert(String title, String header, String content, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private static class Order {
        String flavor;
        int quantity;
        String size;
        String temp;
        String addon;
        int price;

        Order(String flavor, int quantity, String size, String temp, String addon, int price) {
            this.flavor = flavor;
            this.quantity = quantity;
            this.size = size;
            this.temp = temp;
            this.addon = addon;
            this.price = price;
        }
    }
}