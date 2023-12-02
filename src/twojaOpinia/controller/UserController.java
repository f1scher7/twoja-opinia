package twojaOpinia.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import twojaOpinia.dao.UserDao;
import twojaOpinia.model.User;

import java.io.IOException;
import java.util.Objects;

public class UserController {
    @FXML
    private Button logout;
    @FXML
    private void logout() {
        try {
            Parent login = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/login/LoginView.fxml")));
            Scene scene = new Scene(login);
            Stage stage = (Stage) logout.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
