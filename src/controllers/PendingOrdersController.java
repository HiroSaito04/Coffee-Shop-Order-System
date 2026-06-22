// src/controllers/PendingOrdersController.java
package controllers;

import database.OrderQueue;
import database.OrderQueue.Order;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

public class PendingOrdersController extends MainMenuController {
    
    @FXML private ListView<String> cOrdList1;
    @FXML private ListView<String> lvOrd1;
    @FXML private ListView<String> lvOrd2;
    @FXML private ListView<String> lvOrd3;
    @FXML private ListView<String> lvOrd4;
    @FXML private ListView<String> lvCompletedOrders;

    private OrderQueue que;
    private static int currord = 1;
    private LinkedList<String> compOrd = new LinkedList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        que = OrderQueue.getInstance();
        compOrd = que.getCompletedOrds();
        refresh();
    }

    public void refresh() {
        if (que == null) {
            que = OrderQueue.getInstance();
        }

        clearOrderLists();

        if (que.isQueueEmpty()) {
            System.out.println("No pending orders.");
            return;
        }

        updateOrderList(currord, lvOrd1);
        updateOrderList(currord + 1, lvOrd2);
        updateOrderList(currord + 2, lvOrd3);
        updateOrderList(currord + 3, lvOrd4);

        System.out.println("List Refreshed | Current Queue Size: " + que.getQueueSize());
    }

    private void updateOrderList(int orderNum, ListView<String> listView) {
        if (listView == null) {
            return;
        }

        Queue<Order> orderList = que.peekOrdersWithSameNum(orderNum);
        ObservableList<String> ordersList = FXCollections.observableArrayList();

        for (Order order : orderList) {
            ordersList.add(
                "Flavor: " + order.getFlavor() +
                " | Size: " + order.getSize() +
                " | Temp: " + order.getTemp() +
                " | Add-ons: " + order.getAddons() +
                " | Quantity: " + order.getQuantity()
            );
        }

        listView.setItems(ordersList);
    }

    @FXML
    public void completeOrder() {
        if (que == null) {
            que = OrderQueue.getInstance();
        }

        if (que.isQueueEmpty()) {
            showAlert("Invalid Order!", "No Orders.", AlertType.WARNING);
            return;
        }

        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Order Complete");
        confirmationAlert.setHeaderText("Are you sure the order is complete?");
        confirmationAlert.setContentText("Click OK to confirm.");

        if (confirmationAlert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            Queue<Order> ordersWithSameNum = que.pollOrdersWithSameNum(currord);

            if (ordersWithSameNum.isEmpty()) {
                showAlert("No Order Found", "No pending order found for order number " + currord + ".", AlertType.WARNING);
                currord++;
                refresh();
                return;
            }

            StringBuilder completedOrders = new StringBuilder();

            for (Order order : ordersWithSameNum) {
                completedOrders.append(
                    String.format(
                        "Completed: Flavor: %s | Size: %s | Temp: %s | Add-ons: %s | Quantity: %d%n",
                        order.getFlavor(),
                        order.getSize(),
                        order.getTemp(),
                        order.getAddons(),
                        order.getQuantity()
                    )
                );
            }

            String completedOrdersString = completedOrders.toString();
            compOrd.add(completedOrdersString);
            que.setCompletedOrds(compOrd);

            System.out.println("Completed Orders: " + completedOrdersString);
            System.out.println("compOrd List: " + compOrd);

            currord++;
            refresh();
        }
    }

    private void clearOrderLists() {
        if (lvOrd1 != null) lvOrd1.setItems(FXCollections.observableArrayList());
        if (lvOrd2 != null) lvOrd2.setItems(FXCollections.observableArrayList());
        if (lvOrd3 != null) lvOrd3.setItems(FXCollections.observableArrayList());
        if (lvOrd4 != null) lvOrd4.setItems(FXCollections.observableArrayList());
    }

    private void showAlert(String header, String content, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Pending Orders");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}