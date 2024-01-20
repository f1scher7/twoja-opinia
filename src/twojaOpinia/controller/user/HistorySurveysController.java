package twojaOpinia.controller.user;

import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import twojaOpinia.dao.AnswerDao;
import twojaOpinia.dao.QuestionDao;
import twojaOpinia.dao.ResponseDao;
import twojaOpinia.dao.SurveyDao;
import twojaOpinia.model.Answer;
import twojaOpinia.model.Question;
import twojaOpinia.model.Response;
import twojaOpinia.model.Survey;

import java.io.IOException;
import java.util.*;

import static twojaOpinia.util.JavaFXMethods.centerStage;

public class HistorySurveysController {
    private SurveyDao surveyDao = new SurveyDao();
    private QuestionDao questionDao = new QuestionDao();
    private AnswerDao answerDao = new AnswerDao();
    private ResponseDao responseDao = new ResponseDao();
    private String userLogin;

    @FXML
    private VBox mainVBoxHistorySurveys;

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
    private Button backToDashboardButtonMenu;
    @FXML
    private Button logoutButtonMenu;


    public HistorySurveysController(String login) { this.userLogin = login; }

    @FXML
    public void initialize() {
        userLoginLabel.setText(this.userLogin);

        availableSurveysButtonMenu.setOnMouseEntered(e -> availableSurveysButtonMenu.setCursor(Cursor.HAND));
        availableSurveysButtonMenu.setOnMouseExited(e -> availableSurveysButtonMenu.setCursor(Cursor.DEFAULT));

        accountSettingsButtonMenu.setOnMouseEntered(e -> accountSettingsButtonMenu.setCursor(Cursor.HAND));
        accountSettingsButtonMenu.setOnMouseExited(e -> accountSettingsButtonMenu.setCursor(Cursor.DEFAULT));

        logoutButtonMenu.setOnMouseEntered(e -> logoutButtonMenu.setCursor(Cursor.HAND));
        logoutButtonMenu.setOnMouseExited(e -> logoutButtonMenu.setCursor(Cursor.DEFAULT));


        List<Response> responses = responseDao.getAllResponsesByLogin(this.userLogin);
        if (responses.isEmpty()) {
            Label noResponses = new Label("Nie masz wypełnionych ankiet");
            noResponses.setStyle("-fx-pref-height: 30; -fx-pref-width: 560; -fx-padding: 100 0 0 0; -fx-text-fill: white; -fx-font-size: 27");
            mainVBoxHistorySurveys.getChildren().add(noResponses);
        } else {
            Label emptyLabel = new Label();
            emptyLabel.setStyle("-fx-pref-height: 30; -fx-pref-width: 560; -fx-font-size: 27");

            Label greetingLabel = new Label("Tu znajdziesz ankiety, które wypełniłeś!");
            greetingLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 25px; -fx-alignment: center;");
            greetingLabel.setPrefHeight(30);
            greetingLabel.setPrefWidth(560);

            mainVBoxHistorySurveys.getChildren().addAll(greetingLabel, emptyLabel);

            LinkedHashSet<Integer> uniqueSurveysIDs = new LinkedHashSet<>();
            for (Response response: responses) {
                uniqueSurveysIDs.add(response.getSurveyID());
            }

            List<Integer> uniqueSurveysIDsList = new ArrayList<>(uniqueSurveysIDs);

            for(int i = 0; i < uniqueSurveysIDsList.size(); i++) {
                Survey survey = surveyDao.getByID(uniqueSurveysIDsList.get(i));

                Polygon triangle = new Polygon();
                triangle.getPoints().addAll(new Double[]{
                        20.0, 0.0,
                        20.0, 20.0,
                        0.0, 10.0
                });
                triangle.setFill(Color.WHITE);

                triangle.setOnMouseEntered(e -> triangle.setCursor(Cursor.HAND));
                triangle.setOnMouseExited(e -> triangle.setCursor(Cursor.DEFAULT));

                Label surveyTitle = new Label(survey.getTitle());
                surveyTitle.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 20px;");
                surveyTitle.setTextAlignment(TextAlignment.JUSTIFY);
                surveyTitle.setContentDisplay(ContentDisplay.CENTER);
                surveyTitle.maxWidth(500);

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                HBox hbox = new HBox(surveyTitle, spacer, triangle);
                hbox.setStyle("-fx-background-color: rgba(105,255,88,0.5); -fx-background-radius: 10;");
                hbox.setSpacing(40);
                hbox.setMinHeight(50);
                hbox.setMaxWidth(660);
                hbox.setAlignment(Pos.CENTER_RIGHT);
                HBox.setMargin(triangle, new Insets(0, 20, 0, 0));
                HBox.setMargin(surveyTitle, new Insets(0, 0, 0, 20));

                ListView questionsAndAnswersListView = new ListView<>();
                questionsAndAnswersListView.setManaged(false);
                questionsAndAnswersListView.setVisible(false);
                questionsAndAnswersListView.setStyle("-fx-max-width: 635px; -fx-background-radius: 0 0 12 12;");

                List<Question> questions = questionDao.getQuestionsBySurveyIDWithoutID(uniqueSurveysIDsList.get(i));
                List<Integer> answersIDs = responseDao.getAnswersIDsBuySurveyID(uniqueSurveysIDsList.get(i));
                ObservableList<VBox> surveyItems = FXCollections.observableArrayList();
                for(int j = 0; j < questions.size(); j++) {
                    Question question = questions.get(j);
                    Answer answer = answerDao.getByID(answersIDs.get(j));

                    VBox questionAndAnswersVBox = new VBox();
                    Label questionLabel = new Label(question.getOrder() + ". " + question.getQuestionText());
                    Label answerLabel = new Label();
                    if (!answer.getAnswerText().isEmpty()) {
                        answerLabel.setText("Twoja odp: " + answer.getAnswerText() + ".");
                    } else {
                        answerLabel.setText("Nie udzieliłeś odpowiedzi na to pytanie.");
                    }
                    questionLabel.setStyle("-fx-font-size: 12");
                    answerLabel.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");

                    questionAndAnswersVBox.getChildren().addAll(questionLabel, answerLabel);
                    surveyItems.add(questionAndAnswersVBox);
                }
                questionsAndAnswersListView.setMinHeight(questions.size() * 41.7);
                questionsAndAnswersListView.setItems(surveyItems);

                VBox container = new VBox();
                container.getChildren().addAll(hbox, questionsAndAnswersListView);
                container.setAlignment(Pos.CENTER);
                VBox.setMargin(hbox, new Insets(20, 0, 0, 0));

                mainVBoxHistorySurveys.getChildren().add(container);

                final RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.3), triangle);
                final FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.3), questionsAndAnswersListView);

                triangle.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (triangle.getRotate() == 0) {
                        rotateTransition.setByAngle(-90);
                        fadeTransition.setFromValue(0.0);
                        fadeTransition.setToValue(1.0);
                        questionsAndAnswersListView.setManaged(true);
                        questionsAndAnswersListView.setVisible(true);
                    } else {
                        rotateTransition.setByAngle(90);
                        fadeTransition.setFromValue(1.0);
                        fadeTransition.setToValue(0.0);
                        questionsAndAnswersListView.setManaged(false);
                        questionsAndAnswersListView.setVisible(false);
                    }
                    rotateTransition.playFromStart();
                    fadeTransition.playFromStart();
                });
            }
        }
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
    private void backToDashboard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/user/UserDashboard.fxml")));
            Parent userDashboard = fxmlLoader.load();

            UserPulpitController userPulpitController = fxmlLoader.getController();
            userPulpitController.setUserLogin(userLogin);

            Scene scene = new Scene(userDashboard, 1100, 700);
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
        }
    }
    //===================================================================================================================
}
