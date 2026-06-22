// src/main/Main.java
package main;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        URL fxmlLocation = getClass().getResource("/layout/LandingPage.fxml");

        if (fxmlLocation == null) {
            throw new RuntimeException("LandingPage.fxml not found. Make sure it is inside src/layout and copied to build/classes/layout.");
        }

        Parent root = FXMLLoader.load(fxmlLocation);
        Scene scene = new Scene(root);
        
        stage.setTitle("TomoCoffee");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}