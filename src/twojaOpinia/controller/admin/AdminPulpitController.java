package twojaOpinia.controller.admin;

import javafx.animation.KeyFrame;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.animation.Timeline;
import javafx.scene.shape.Circle;


import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Objects;
import javafx.util.Duration;

import twojaOpinia.dao.UserDao;
import twojaOpinia.dao.AnswerDao;
import twojaOpinia.dao.QuestionDao;
import twojaOpinia.dao.SurveyDao;
import twojaOpinia.model.Answer;
import twojaOpinia.model.Question;
import twojaOpinia.model.Survey;

import static twojaOpinia.util.JavaFXMethods.centerStage;

public class AdminController {
    private UserDao userDao = new UserDao();
    private SurveyDao surveyDao = new SurveyDao();
    private Timeline timeline;

    @FXML
    private Button manageUserButtonMenu;
    @FXML
    private Button manageSurveyButtonMenu;
    @FXML
    private Button logoutButtonMenu;

    @FXML
    private Label userCountLabel;
    @FXML
    private Label surveyCountLabel;

    @FXML
    public void initialize() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> updateUserCount()),
                new KeyFrame(Duration.seconds(3), event -> updateSurveyCount())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        if(userCountLabel != null && surveyCountLabel != null) {
            timeline.play();
        }
    }

    //ADMIN_PULPIT
    @FXML
    private void updateUserCount() {
        if (userCountLabel != null) {
            int userCount = userDao.getUserCount();
            userCountLabel.setText("Liczba użytkownikow: " + userCount);
        }
    }

    @FXML
    private void updateSurveyCount() {
        if (surveyCountLabel != null) {
            int surveyCount = surveyDao.getSurveyCount();
            surveyCountLabel.setText("Liczba stworzonych ankiet: " + surveyCount);
        }
    }


    //ADMIN_MENU
    //===================================================================================================================
    @FXML
    private void handleManageSurvey() {
        try {
            Parent manageSurvey = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/admin/ManageSurvey.fxml")));
            Scene scene = new Scene(manageSurvey, 1000, 600);
            Stage stage = (Stage) manageSurveyButtonMenu.getScene().getWindow();
            stage.setScene(scene);
            centerStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
    }

    @FXML
    private void logout() {
        try {
            Parent logout = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/login/LoginView.fxml")));
            Scene scene = new Scene(logout, 400, 350);
            Stage stage = (Stage) logoutButtonMenu.getScene().getWindow();
            stage.setScene(scene);
            centerStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());

        }
    }
    //===================================================================================================================
}