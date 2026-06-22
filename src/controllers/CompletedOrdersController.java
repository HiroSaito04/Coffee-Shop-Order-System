// src/controllers/CompletedOrdersController.java
package controllers;

import database.LoginHandler;
import database.OrderQueue;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class CompletedOrdersController extends MainMenuController {
    
    @FXML
    private ListView<String> cOrdList1;

    private OrderQueue que;

    public CompletedOrdersController() {
        que = OrderQueue.getInstance();
    }

    @FXML
    public void initialize() {
        que = OrderQueue.getInstance();
        showCompOrds();
    }

    public void showCompOrds() {
        if (que == null) {
            que = OrderQueue.getInstance();
        }

        if (cOrdList1 == null) {
            return;
        }

        cOrdList1.getItems().clear();

        LinkedList<String> completedOrders = que.getCompletedOrds();

        if (!completedOrders.isEmpty()) {
            cOrdList1.getItems().addAll(completedOrders);
            System.out.println("Completed orders displayed in the ListView.");
        } else {
            System.out.println("No completed orders yet.");
        }
    }
    
    @FXML
    public void clearOrder(ActionEvent event) {
        if (cOrdList1 == null || cOrdList1.getItems().isEmpty()) {
            System.out.println("No completed orders to clear.");
            return;
        }

        LoginHandler loginHandler = LoginHandler.getLoginHandler();
        String username = loginHandler.getUsername();

        File logs= new File("src/logs");
        if (!logs.exists()) {
            logs.mkdirs();
        }

        File logFile = new File(logs, "Logs.txt");

        try {
            if (logFile.createNewFile()) {
                System.out.println("File created: " + logFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the log file.");
            e.printStackTrace();
        }

        try (FileWriter myWriter = new FileWriter(logFile, true)) {
            for (String completedOrder : cOrdList1.getItems()) {
                String strToWrite = username + " : " + completedOrder + System.lineSeparator();
                myWriter.write(strToWrite);
            }

            System.out.println("Successfully wrote completed orders to the log file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the log file.");
            e.printStackTrace();
        }

        cOrdList1.getItems().clear();

        if (que != null) {
            que.getCompletedOrds().clear();
        }

        System.out.println("Completed orders cleared.");
    }
}