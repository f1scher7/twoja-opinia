package twojaOpinia.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AdminController {

    @FXML
    private TextField userNameField;

    @FXML
    private TextField surveyNameField;

    @FXML
    private Button createUserButton;

    @FXML
    private Button createSurveyButton;

    @FXML
    private void handleCreateUser() {
        String username = userNameField.getText();
        System.out.println("Creating user: " + username);
    }

    @FXML
    private void handleCreateSurvey() {
        String surveyName = surveyNameField.getText();
        System.out.println("Creating survey: " + surveyName);
    }
}