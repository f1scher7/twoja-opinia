package twojaOpinia.controller.user;

import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import twojaOpinia.dao.ResponseDao;
import twojaOpinia.dao.SurveyDao;

import java.io.IOException;
import java.util.Objects;

import static twojaOpinia.util.JavaFXMethods.centerStage;

public class UserPulpitController {

    private SurveyDao surveyDao = new SurveyDao();
    private ResponseDao responseDao = new ResponseDao();

    private String userLogin;
    private int surveyCount;
    private int responseCount;

    @FXML
    private Label twojaOpiniaLabel;
    @FXML
    private Circle userAvatar;
    @FXML
    private Label userLoginLabel;
    @FXML
    private Button availableSurveysButtonMenu;
    @FXML
    private Button surveysHistoryButtonMenu;
    @FXML
    private Button accountSettingsButtonMenu;
    @FXML
    private Button logoutButtonMenu;

    @FXML
    private Label greetingLabel;
    @FXML
    private Label surveyCountLabel;
    @FXML
    private Label responseCountLabel;


    @FXML
    public void initialize() {
        availableSurveysButtonMenu.setOnMouseEntered(e -> availableSurveysButtonMenu.setCursor(Cursor.HAND));
        availableSurveysButtonMenu.setOnMouseExited(e -> availableSurveysButtonMenu.setCursor(Cursor.DEFAULT));

        surveysHistoryButtonMenu.setOnMouseEntered(e -> surveysHistoryButtonMenu.setCursor(Cursor.HAND));
        surveysHistoryButtonMenu.setOnMouseExited(e -> surveysHistoryButtonMenu.setCursor(Cursor.DEFAULT));

        accountSettingsButtonMenu.setOnMouseEntered(e -> accountSettingsButtonMenu.setCursor(Cursor.HAND));
        accountSettingsButtonMenu.setOnMouseExited(e -> accountSettingsButtonMenu.setCursor(Cursor.DEFAULT));

        logoutButtonMenu.setOnMouseEntered(e -> logoutButtonMenu.setCursor(Cursor.HAND));
        logoutButtonMenu.setOnMouseExited(e -> logoutButtonMenu.setCursor(Cursor.DEFAULT));

        this.surveyCount = updateSurveyCount();
        this.responseCount = updateResponseCount();

        String surveyCountStr = "Liczba stworzonych ankiet: " + this.surveyCount;
        String responseCountStr = "Liczba odpowiedzi użytkowników: " + this.responseCount;
        String greetingText = "Witamy w TwojaOpinia! Twoja opinia się liczy!";

        twojaOpiniaLabel.setOpacity(0);
        userAvatar.setOpacity(0);
        userLoginLabel.setOpacity(0);
        availableSurveysButtonMenu.setOpacity(0);
        surveysHistoryButtonMenu.setOpacity(0);
        accountSettingsButtonMenu.setOpacity(0);
        logoutButtonMenu.setOpacity(0);

        FadeTransition ftMenu = animationForMenu(logoutButtonMenu, 1800);

        ftMenu.setOnFinished(event -> {
            animateLabel(greetingLabel, greetingText, 50);
            animateLabel(surveyCountLabel, surveyCountStr, 70);
            animateLabel(responseCountLabel, responseCountStr, 70);
        });

        animationForMenu(twojaOpiniaLabel, 0);
        animationForMenu(userAvatar, 300);
        animationForMenu(userLoginLabel, 600);
        animationForMenu(availableSurveysButtonMenu, 900);
        animationForMenu(surveysHistoryButtonMenu, 1200);
        animationForMenu(accountSettingsButtonMenu, 1500);
        animationForMenu(logoutButtonMenu, 1800);
    }

    private FadeTransition animationForMenu(Node node, int delay) {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setDelay(Duration.millis(delay));
        ft.playFromStart();
        return ft;
    }

    private void animateLabel(Label label, String fullText, int delay) {
        final StringBuilder sb = new StringBuilder();
        final int textLength = fullText.length();
        Duration duration = Duration.millis(delay * textLength);

        Transition transition = new Transition() {
            {
                setCycleDuration(duration);
            }

            @Override
            protected void interpolate(double frac) {
                final int length = (int) Math.round(frac * textLength);
                label.setText(fullText.substring(0, length));
            }
        };

        transition.playFromStart();
    }

    public void setUserLogin(String login) {
        this.userLogin = login;
        userLoginLabel.setText(login);
    }

    private int updateSurveyCount() {
        if (surveyCountLabel != null) {
            return surveyDao.getSurveyCount();
        }
        return -1;
    }

    private int updateResponseCount() {
        int res = -1;
        if (responseCountLabel != null) {
            res = responseDao.getResponseCount();
        }
        return res;
    }

    //USER_MENU
    //===================================================================================================================
    @FXML
    private void availableSurveys() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/user/AvailableSurveys.fxml")));
            Parent availableSurveys = fxmlLoader.load();

            AvailableSurveysController availableSurveysController = fxmlLoader.getController();
            availableSurveysController.setUserLogin(userLogin);

            Scene scene = new Scene(availableSurveys, 1100, 700);
            Stage stage = (Stage) availableSurveysButtonMenu.getScene().getWindow();
            stage.setScene(scene);

            centerStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
    }

    @FXML
    private void surveysHistory() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/user/HistorySurveys.fxml")));
            fxmlLoader.setControllerFactory(param -> new HistorySurveysController(this.userLogin));
            Parent surveysHistory = fxmlLoader.load();

            Scene scene = new Scene(surveysHistory, 1100, 700);
            Stage stage = (Stage) surveysHistoryButtonMenu.getScene().getWindow();
            stage.setScene(scene);

            centerStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
        }
    }

    @FXML
    private void settingsAccount() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/user/SettingsAccount.fxml")));
            fxmlLoader.setControllerFactory(param -> new SettingsAccountController(this.userLogin));
            Parent settingsAccount = fxmlLoader.load();

            Scene scene = new Scene(settingsAccount, 1100, 700);
            Stage stage = (Stage) accountSettingsButtonMenu.getScene().getWindow();
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
        }
    }
    //===================================================================================================================
}
