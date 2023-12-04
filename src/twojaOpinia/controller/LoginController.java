package twojaOpinia.controller;

import twojaOpinia.dao.UserDao;
import twojaOpinia.model.User;
import twojaOpinia.util.SHA256;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    @FXML
    private TextField userNameField;
    @FXML
    private TextField userPasswordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorMess;
    private UserDao userDao = new UserDao();

    @FXML
    private void login() {
        String login = userNameField.getText();
        String password = userPasswordField.getText();

        if (login.isEmpty() || password.isEmpty()) {
            errorMess.setText("Pola użytkownika i hasła nie mogą być puste!");
            return;
        }

        User user = userDao.getByID(login);
        if (user == null || !user.getPassword().equals(SHA256.toSHA256(password))) {
            errorMess.setText("Nieprawidłowe dane logowania");
            return;
        } else {
            try {
                Scene scene;
                if (user.isAdmin()) {
                    Parent adminDashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/admin/AdminDashboard.fxml")));
                    scene = new Scene(adminDashboard);
                } else {
                    Parent userDashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/user/UserDashboard.fxml")));
                    scene = new Scene(userDashboard);
                }
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}