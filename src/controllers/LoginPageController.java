// src/controllers/LoginPageController.java
package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBUtility;
import database.LoginHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginPageController {
    
    @FXML private TextField Login_tf_username;
    @FXML private PasswordField Login_tf_password;
    @FXML private TextField Login_tf_password_visible;
    @FXML private Button Login_button_login;
    @FXML private Button Login_button_signup;
    @FXML private Button Login_button_show_password;
    
    private boolean passwordVisible = false;

    @FXML
    private void Login_button_loginOnAction(ActionEvent event) {
        String username = Login_tf_username.getText();
        String password = passwordVisible ? Login_tf_password_visible.getText() : Login_tf_password.getText();

        LoginHandler loginHandler = LoginHandler.getLoginHandler();
        loginHandler.setLastLogin(username);

        if (checkUserExists(username, password)) {
            loadPage("/layout/MainMenu.fxml", Login_button_login, "Failed to load the main menu.");
        } else {
            showAlert("Error", "User does not exist. Please create an account.", AlertType.ERROR);
        }
    }

    private boolean checkUserExists(String username, String password) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBUtility.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();
            return rs.next() && rs.getInt(1) > 0;

        } catch (SQLException e) {
            showAlert("Error", "Database error occurred.", AlertType.ERROR);
            e.printStackTrace();
        }

        return false;
    }

    @FXML
    private void Login_button_signupOnAction(ActionEvent event) {
        loadPage("/layout/SignupPage.fxml", Login_button_signup, "Failed to load the signup page.");
    }

    @FXML
    private void Login_button_show_passwordOnAction(ActionEvent event) {
        passwordVisible = !passwordVisible;

        if (passwordVisible) {
            Login_tf_password_visible.setText(Login_tf_password.getText());
            Login_tf_password_visible.setVisible(true);
            Login_tf_password_visible.setManaged(true);

            Login_tf_password.setVisible(false);
            Login_tf_password.setManaged(false);

            Login_button_show_password.setText("Hide Password");
        } else {
            Login_tf_password.setText(Login_tf_password_visible.getText());
            Login_tf_password.setVisible(true);
            Login_tf_password.setManaged(true);

            Login_tf_password_visible.setVisible(false);
            Login_tf_password_visible.setManaged(false);

            Login_button_show_password.setText("Show Password");
        }
    }

    private void loadPage(String fxmlPath, Button sourceButton, String errorMessage) {
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
            showAlert("Error", errorMessage, AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}