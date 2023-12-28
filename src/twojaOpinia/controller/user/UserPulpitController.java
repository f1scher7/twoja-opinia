package twojaOpinia.controller.user;

import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import twojaOpinia.dao.SurveyDao;

import java.io.IOException;
import java.util.Objects;

import static twojaOpinia.util.JavaFXMethods.centerStage;

public class UserPulpitController {

    private SurveyDao surveyDao = new SurveyDao();

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
        if (responseCountLabel != null) {
            return 0;
        }
        return -1;
    }

    //USER_MENU
    //===================================================================================================================
    @FXML
    private void logout() {
        try {
            Parent login = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/login/LoginView.fxml")));
            Scene scene = new Scene(login, 400, 350);
            Stage stage = (Stage) logoutButtonMenu.getScene().getWindow();
            stage.setScene(scene);
            centerStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //===================================================================================================================
}
