package twojaOpinia.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import twojaOpinia.dao.UserDao;
import twojaOpinia.model.User;

import static twojaOpinia.util.SaltUtil.generateSalt;
import static twojaOpinia.util.JavaFXMethods.centerStage;

public class AdminController {

    @FXML
    private TextField userNameField;
    
    @FXML
    private TextField userPasswordField;

    @FXML
    private TextField surveyNameField;

    @FXML
    private Button createUserButton;

    @FXML
    private Button createSurveyButton;
    @FXML
    private Button logout;

    @FXML
    private void handleCreateUser() {
        String username = userNameField.getText();
        String salt = generateSalt();
        String password = userPasswordField.getText();
        System.out.println("Creating user: " + username);
        
        User user = new User(username, password, salt, false);
        UserDao userDao = new UserDao();
        
        userDao.insert(user);
        
    }

    @FXML
    private void handleCreateSurvey() {
        String surveyName = surveyNameField.getText();
        System.out.println("Creating survey: " + surveyName);
    }

    @FXML
    private void logout() {
        try {
            Parent login = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/login/LoginView.fxml")));
            Scene scene = new Scene(login, 400, 350);
            Stage stage = (Stage) logout.getScene().getWindow();
            stage.setScene(scene);
            centerStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}