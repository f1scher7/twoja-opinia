package twojaOpinia.controller.admin;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Objects;
import javafx.util.Duration;

import twojaOpinia.dao.UserDao;
import twojaOpinia.dao.SurveyDao;

import static twojaOpinia.util.JavaFXMethods.centerStage;
public class HistorySurveysController {
    private String adminLogin;

    @FXML
    private Label twojaOpiniaLabel;
    @FXML
    private Circle adminAvatar;
    @FXML
    private Label adminLoginLabel;
    @FXML
    private Button manageUserButtonMenu;
    @FXML
    private Button analyzeResultsButtonMenu;
    @FXML
    private Button manageSurveyButtonMenu;
    @FXML
    private Button historyOfAddedSurveysButtonMenu;
    @FXML
    private Button backToDashboardButtonMenu;
    @FXML
    private Button logoutButtonMenu;

    @FXML
    public void initialize() {
        manageUserButtonMenu.setOnMouseEntered(e -> manageUserButtonMenu.setCursor(Cursor.HAND));
        manageUserButtonMenu.setOnMouseExited(e -> manageUserButtonMenu.setCursor(Cursor.DEFAULT));

        manageSurveyButtonMenu.setOnMouseEntered(e -> manageSurveyButtonMenu.setCursor(Cursor.HAND));
        manageSurveyButtonMenu.setOnMouseExited(e -> manageSurveyButtonMenu.setCursor(Cursor.DEFAULT));

        analyzeResultsButtonMenu.setOnMouseEntered(e -> analyzeResultsButtonMenu.setCursor(Cursor.HAND));
        analyzeResultsButtonMenu.setOnMouseExited(e -> analyzeResultsButtonMenu.setCursor(Cursor.DEFAULT));

        backToDashboardButtonMenu.setOnMouseEntered(e -> backToDashboardButtonMenu.setCursor(Cursor.HAND));
        backToDashboardButtonMenu.setOnMouseExited(e -> backToDashboardButtonMenu.setCursor(Cursor.DEFAULT));

        logoutButtonMenu.setOnMouseEntered(e -> logoutButtonMenu.setCursor(Cursor.HAND));
        logoutButtonMenu.setOnMouseExited(e -> logoutButtonMenu.setCursor(Cursor.DEFAULT));
    }

    public void setAdminLogin(String login) {
        this.adminLogin = login;
        adminLoginLabel.setText(login);
    }

    //ADMIN_MENU
    //===================================================================================================================
    @FXML
    private void manageUser() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/admin/ManageUser.fxml")));
            Parent manageUser = fxmlLoader.load();

            ManageUserController manageUserController = fxmlLoader.getController();
            manageUserController.setAdminLogin(adminLogin);

            Scene scene = new Scene(manageUser, 1100, 700);
            Stage stage = (Stage) manageUserButtonMenu.getScene().getWindow();

            TranslateTransition tt = new TranslateTransition(Duration.millis(550), scene.getRoot());
            tt.setFromX(-200f);
            tt.setToX(0f);
            tt.play();

            stage.setScene(scene);

            centerStage(stage);
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
    }

    @FXML
    private void analyzeSurveys() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/admin/SurveysAnalysis.fxml")));
            Parent analyzeSurveys = fxmlLoader.load();

            SurveysAnalysisController analysisController = fxmlLoader.getController();
            analysisController.setAdminLogin(adminLogin);

            Scene scene = new Scene(analyzeSurveys, 1100, 700);
            Stage stage = (Stage) analyzeResultsButtonMenu.getScene().getWindow();

            TranslateTransition tt = new TranslateTransition(Duration.millis(550), scene.getRoot());
            tt.setFromX(-200f);
            tt.setToX(0f);
            tt.play();

            stage.setScene(scene);

            centerStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
    }

    @FXML
    private void manageSurvey() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/admin/ManageSurvey.fxml")));
            Parent manageSurvey = fxmlLoader.load();

            ManageSurveyController manageSurveyController = fxmlLoader.getController();
            manageSurveyController.setAdminLogin(adminLogin);

            Scene scene = new Scene(manageSurvey, 1100, 700);
            Stage stage = (Stage) manageSurveyButtonMenu.getScene().getWindow();

            TranslateTransition tt = new TranslateTransition(Duration.millis(550), scene.getRoot());
            tt.setFromX(-200f);
            tt.setToX(0f);
            tt.play();

            stage.setScene(scene);

            centerStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
    }

    @FXML
    private void backToDashboard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/admin/AdminDashboard.fxml")));
            Parent adminDashboard = fxmlLoader.load();

            AdminPulpitController adminPulpitController = fxmlLoader.getController();
            adminPulpitController.setAdminLogin(adminLogin);

            Scene scene = new Scene(adminDashboard, 1100, 700);
            Stage stage = (Stage) backToDashboardButtonMenu.getScene().getWindow();
            stage.setScene(scene);

            centerStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
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
}
