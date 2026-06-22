// src/controllers/MainMenuController.java
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MainMenuController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private final Map<String, Parent> fxmlCache = new HashMap<>();
    private long lastSceneChangeTime = 0;
    private static final long DEBOUNCE_DELAY_MS = 300;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void switchScene(ActionEvent event, String fxmlFile) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastSceneChangeTime < DEBOUNCE_DELAY_MS) {
            return;
        }

        lastSceneChangeTime = currentTime;

        Parent root = fxmlCache.get(fxmlFile);

        if (root == null) {
            try {
                URL fxmlLocation = getClass().getResource("/layout/" + fxmlFile);

                if (fxmlLocation == null) {
                    throw new IOException("FXML file not found: /layout/" + fxmlFile);
                }

                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                root = loader.load();
                fxmlCache.put(fxmlFile, root);

                switch (fxmlFile) {
                    case "PendingOrders.fxml" -> {
                        PendingOrdersController pendingController = loader.getController();
                        if (pendingController != null) {
                            pendingController.refresh();
                        }
                    }
                    case "CompletedOrders.fxml" -> {
                        CompletedOrdersController completedController = loader.getController();
                        if (completedController != null) {
                            completedController.showCompOrds();
                        }
                    }
                    case "OrderMenu.fxml" -> {
                        // No extra refresh needed
                    }
                    default -> {
                        // No extra action needed
                    }
                }

            } catch (IOException e) {
                System.out.println("Unable to load the requested scene: " + fxmlFile);
                e.printStackTrace();
                showErrorAlert("Scene Load Error", "Unable to load the scene: " + fxmlFile);
                return;
            }
        }

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }

    public void toPendingOrders(ActionEvent event) {
        switchScene(event, "PendingOrders.fxml");
    }

    public void toMainMenu(ActionEvent event) {
        switchScene(event, "MainMenu.fxml");
    }

    public void toOrderMenu(ActionEvent event) {
        switchScene(event, "OrderMenu.fxml");
    }

    public void toCompletedOrders(ActionEvent event) {
        switchScene(event, "CompletedOrders.fxml");
    }

    public void toLogin(ActionEvent event) {
        switchScene(event, "LoginPage.fxml");
    }

    public void toSignup(ActionEvent event) {
        switchScene(event, "SignupPage.fxml");
    }

    public void logout(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("LOGGING OUT");
        alert.setContentText("Click OK to Confirm");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            switchScene(event, "LoginPage.fxml");
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}