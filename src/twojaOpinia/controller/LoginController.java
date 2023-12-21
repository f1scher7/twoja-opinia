package twojaOpinia.controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import twojaOpinia.controller.admin.AdminPulpitController;
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
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

import java.io.IOException;
import java.util.Objects;

import twojaOpinia.dao.UserDao;
import twojaOpinia.model.User;
import twojaOpinia.util.SHA256;

import static twojaOpinia.util.JavaFXMethods.centerStage;

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

        User user = userDao.getUserDataByLogin(login);
        
        if (user == null || !user.getPassword().equals(SHA256.toSHA256(password + user.getSalt()))) {
            errorMess.setText("Nieprawidłowe dane logowania");
            return;
        } else {
            try {
                Scene scene;
                if (user.isAdmin()) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/twojaOpinia/view/admin/AdminDashboard.fxml"));
                    Parent adminDashboard = fxmlLoader.load();
                    scene = new Scene(adminDashboard, 1100, 700);
                    AdminPulpitController adminPulpitController = fxmlLoader.getController();
                    adminPulpitController.setAdminLogin(login);
                } else {
                    Parent userDashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/user/UserDashboard.fxml")));
                    scene = new Scene(userDashboard, 1100, 700);
                }
                Stage stage = (Stage) loginButton.getScene().getWindow();

                stage.setScene(scene);
                centerStage(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}