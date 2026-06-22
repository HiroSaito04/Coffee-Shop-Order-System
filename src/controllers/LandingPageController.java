/// src/controllers/LandingPageController.java
package controllers;

import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LandingPageController {

    @FXML private Button LP_button_login;
    @FXML private Button LP_button_signup;
   
    @FXML 
    private void LP_button_loginOnAction(ActionEvent event) {
        loadPage("/layout/LoginPage.fxml", LP_button_login);
    }

    @FXML
    private void LP_button_signupOnAction(ActionEvent event) {
        loadPage("/layout/SignupPage.fxml", LP_button_signup);
    }

    private void loadPage(String fxmlPath, Button sourceButton) {
        try {
            URL fxmlLocation = getClass().getResource(fxmlPath);

            if (fxmlLocation == null) {
                throw new IOException("FXML file not found: " + fxmlPath);
            }

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();

            Stage stage = (Stage) sourceButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}