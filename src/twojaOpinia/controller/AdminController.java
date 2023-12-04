package twojaOpinia.controller;

import static twojaOpinia.util.SaltUtil.generateSalt;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import twojaOpinia.dao.UserDao;
import twojaOpinia.model.User;

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
}