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

public class AdminPulpitController {
    private UserDao userDao = new UserDao();
    private SurveyDao surveyDao = new SurveyDao();
    private Timeline timeline;
    private String adminLogin;
    private int userCount;
    private int surveyCount;

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
    private Button logoutButtonMenu;

    @FXML
    private Label greetingLabel;
    @FXML
    private Label userCountLabel;
    @FXML
    private Label surveyCountLabel;

    @FXML
    public void initialize() {
        manageUserButtonMenu.setOnMouseEntered(e -> manageUserButtonMenu.setCursor(Cursor.HAND));
        manageUserButtonMenu.setOnMouseExited(e -> manageUserButtonMenu.setCursor(Cursor.DEFAULT));

        analyzeResultsButtonMenu.setOnMouseEntered(e -> analyzeResultsButtonMenu.setCursor(Cursor.HAND));
        analyzeResultsButtonMenu.setOnMouseExited(e -> analyzeResultsButtonMenu.setCursor(Cursor.DEFAULT));

        manageSurveyButtonMenu.setOnMouseEntered(e -> manageSurveyButtonMenu.setCursor(Cursor.HAND));
        manageSurveyButtonMenu.setOnMouseExited(e -> manageSurveyButtonMenu.setCursor(Cursor.DEFAULT));

        logoutButtonMenu.setOnMouseEntered(e -> logoutButtonMenu.setCursor(Cursor.HAND));
        logoutButtonMenu.setOnMouseExited(e -> logoutButtonMenu.setCursor(Cursor.DEFAULT));

        this.userCount = updateUserCount();
        this.surveyCount = updateSurveyCount();

        String userCountStr = "Liczba użytkowników: " + this.userCount;
        String surveyCountStr = "Liczba stworzonych ankiet: " + this.surveyCount;
        String greetingText = "Witamy w panelu administracyjnym!";

        twojaOpiniaLabel.setOpacity(0);
        adminAvatar.setOpacity(0);
        adminLoginLabel.setOpacity(0);
        manageUserButtonMenu.setOpacity(0);
        manageSurveyButtonMenu.setOpacity(0);
        analyzeResultsButtonMenu.setOpacity(0);
        logoutButtonMenu.setOpacity(0);

        FadeTransition ftMenu = animationForMenu(logoutButtonMenu, 1800);

        ftMenu.setOnFinished(event -> {
            animateLabel(greetingLabel, greetingText, 50);
            animateLabel(userCountLabel, userCountStr, 70);
            animateLabel(surveyCountLabel, surveyCountStr, 70);
        });

        animationForMenu(twojaOpiniaLabel, 0);
        animationForMenu(adminAvatar, 300);
        animationForMenu(adminLoginLabel, 600);
        animationForMenu(manageUserButtonMenu, 900);
        animationForMenu(manageSurveyButtonMenu, 1200);
        animationForMenu(analyzeResultsButtonMenu, 1500);
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

    //ADMIN_PULPIT
    public void setAdminLogin(String login) {
        this.adminLogin = login;
        adminLoginLabel.setText(login);
    }

    private int updateUserCount() {
        if (userCountLabel != null) {
            return userDao.getUserCount();
        }
        return 0;
    }

    private int updateSurveyCount() {
        if (surveyCountLabel != null) {
            return surveyDao.getSurveyCount();
        }
        return 0;
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