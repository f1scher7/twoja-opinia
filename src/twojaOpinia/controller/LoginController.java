package twojaOpinia.controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Cursor;
import javafx.util.Duration;
import twojaOpinia.controller.admin.AdminPulpitController;
import twojaOpinia.controller.user.UserPulpitController;
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
    private Button registrationButton;
    @FXML
    private Label errorMess;
    private UserDao userDao = new UserDao();

    @FXML
    public void initialize() {
        loginButton.setOnMouseEntered(e -> loginButton.setCursor(Cursor.HAND));
        loginButton.setOnMouseExited(e -> loginButton.setCursor(Cursor.DEFAULT));

        registrationButton.setOnMouseEntered(e -> registrationButton.setCursor(Cursor.HAND));
        registrationButton.setOnMouseExited(e -> registrationButton.setCursor(Cursor.DEFAULT));

    }
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
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/twojaOpinia/view/user/UserDashboard.fxml"));
                    Parent userDashboard = fxmlLoader.load();
                    scene = new Scene(userDashboard, 1100, 700);
                    UserPulpitController userPulpitController = fxmlLoader.getController();
                    userPulpitController.setUserLogin(login);
                }
                Stage stage = (Stage) loginButton.getScene().getWindow();

                stage.setScene(scene);
                centerStage(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void registration() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/registration/RegistrationView.fxml")));
            Parent registration = fxmlLoader.load();

            Scene scene = new Scene(registration,430, 570);
            Stage stage = (Stage) registrationButton.getScene().getWindow();
            stage.setScene(scene);
            centerStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
    }
}