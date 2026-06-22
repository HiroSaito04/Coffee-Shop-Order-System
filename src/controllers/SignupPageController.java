// src/controllers/SignupPageController.java
package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBUtility;
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

public class SignupPageController {

    @FXML private TextField Signup_tf_username;
    @FXML private PasswordField Signup_tf_password;
    @FXML private TextField Signup_tf_password_visible;
    @FXML private Button Signup_button_create;
    @FXML private Button Signup_button_login;
    @FXML private Button Signup_Show_Password;

    private boolean isPasswordVisible = false;

    @FXML
    private void Signup_button_createOnAction(ActionEvent event) {
        String username = Signup_tf_username.getText();
        String password = isPasswordVisible ? Signup_tf_password_visible.getText() : Signup_tf_password.getText();

        if (username.isBlank() || password.isBlank()) {
            showAlert("Error", "Username and password cannot be empty.", AlertType.ERROR);
            return;
        }

        if (insertDataIntoDatabase(username, password)) {
            showAlert("Success", "User registered successfully! Please log in.", AlertType.INFORMATION);
            loadLoginPage();
        } else {
            showAlert("Error", "Registration failed. Please try again.", AlertType.ERROR);
        }
    }

    private boolean insertDataIntoDatabase(String username, String password) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = DBUtility.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            if (conn == null) {
                showAlert("Error", "Database connection failed.", AlertType.ERROR);
                return false;
            }

            pst.setString(1, username);
            pst.setString(2, password);
            pst.executeUpdate();

            return true;

        } catch (SQLException e) {
            showAlert("Error", "Database error occurred.", AlertType.ERROR);
            e.printStackTrace();
        }

        return false;
    }

    @FXML
    private void Signup_Show_PasswordOnAction(ActionEvent event) {
        isPasswordVisible = !isPasswordVisible;

        if (isPasswordVisible) {
            Signup_tf_password_visible.setText(Signup_tf_password.getText());
            Signup_tf_password_visible.setVisible(true);
            Signup_tf_password_visible.setManaged(true);

            Signup_tf_password.setVisible(false);
            Signup_tf_password.setManaged(false);

            Signup_Show_Password.setText("Hide Password");
        } else {
            Signup_tf_password.setText(Signup_tf_password_visible.getText());
            Signup_tf_password.setVisible(true);
            Signup_tf_password.setManaged(true);

            Signup_tf_password_visible.setVisible(false);
            Signup_tf_password_visible.setManaged(false);

            Signup_Show_Password.setText("Show Password");
        }
    }
    
    @FXML
    private void Signup_button_loginOnAction(ActionEvent event) {
        loadLoginPage();
    }

    private void loadLoginPage() {
        try {
            URL fxmlLocation = getClass().getResource("/layout/LoginPage.fxml");

            if (fxmlLocation == null) {
                throw new IOException("FXML file not found: /layout/LoginPage.fxml");
            }

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();

            Stage stage = (Stage) Signup_button_login.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            showAlert("Error", "Failed to load the login page.", AlertType.ERROR);
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