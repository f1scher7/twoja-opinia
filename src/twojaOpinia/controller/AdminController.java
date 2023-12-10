package twojaOpinia.controller;

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
    private Survey survey = new Survey();
    private Question question;
    private Answer answer;
    private UserDao userDao = new UserDao();
    private SurveyDao surveyDao = new SurveyDao();
    private QuestionDao questionDao = new QuestionDao();
    private AnswerDao answerDao = new AnswerDao();

    private ArrayList<Answer> answersList;
    private ArrayList<Question> questionsList = new ArrayList<>();
    private int incAnswers = 0;
    private int incQuestions = 0;

    private Timeline timeline;


    @FXML
    private Circle userImage;
    @FXML
    private ImageView userImageView;

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
    private TextField userNameField;
    @FXML
    private TextField userPasswordField;

    //SURVEY
    @FXML
    private TextField surveyTitleField;
    @FXML
    private TextArea surveyDescriptionArea;
    @FXML
    private VBox answersAndQuestionVBox;
    @FXML
    private Button addQuestionButton;
    @FXML
    private Button addAnswerButton;
    @FXML
    private Button saveSurveyButton;


    public void initialize() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> updateUserCount()),
                new KeyFrame(Duration.seconds(3), event -> updateSurveyCount())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        if(userCountLabel != null && surveyCountLabel != null) {
            timeline.play();
        }
        /*
        String imagePath = "avatar.jpg";
        Image image = new Image("file:" + imagePath);
        userImageView.setImage(image);
        userImage.setClip(userImageView);
        */
    }

    //START WINDOW
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


    //SURVEY
    //===================================================================================================================

    @FXML
    private void addAnswer() {
        addAnswerButton.setOnAction(e -> {
            incAnswers++;
            addTextFieldAnswer();
            if (incAnswers >= 2) {
                addQuestionButton.setDisable(false);
                addQuestionButton.setOnAction(i -> addQuestion());
            }
        });
    }

    private void addTextFieldAnswer() {
        TextField newAnswer = new TextField();
        newAnswer.setPromptText("Odpowiedź nr. " + incAnswers);
        newAnswer.setStyle("-fx-max-width: 305.0; -fx-min-width: 100; -fx-pref-height: 30.0; -fx-pref-width: 280.0");

        Button submitAnswerButton = new Button("Zatwierdź");
        submitAnswerButton.setStyle("-fx-min-width: 65; -fx-pref-height: 28.0; -fx-pref-width: 65.0");
        submitAnswerButton.getStyleClass().add("button5");

        submitAnswerButton.setOnAction(e -> {
            scannerAnswer(newAnswer);
            ((HBox) submitAnswerButton.getParent()).getChildren().remove(submitAnswerButton);
        });

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);
        hBox.getChildren().addAll(newAnswer, submitAnswerButton);

        answersAndQuestionVBox.setSpacing(10);
        int index = answersAndQuestionVBox.getChildren().indexOf(addAnswerButton.getParent());
        answersAndQuestionVBox.getChildren().add(index, hBox);
    }

    private void scannerAnswer(TextField answerField) {
        answer = new Answer(incAnswers, answerField.getText());
        answersList.add(answer);
    }

    @FXML
    private void addQuestion() {
        addQuestionButton.setOnAction(e -> {
            incAnswers = 0;
            addTextFieldQuestion();

            addAnswerButton.setOnAction(a -> addAnswer());
            addAnswerButton.getStyleClass().remove("lockButton");
            addAnswerButton.getStyleClass().add("button1");
            addAnswerButton.setDisable(false);

            addQuestionButton.setDisable(true);
        });
        if (incQuestions >= 1) {
            survey.getQuestions().add(question);
            for (Answer answer: answersList) {
                survey.getQuestions().get(incQuestions - 1).getAnswers().add(answer);
            }
        }
        answersList = new ArrayList<>();
    }

    private void addTextFieldQuestion() {
        incQuestions++;

        TextField newQuestion = new TextField();
        newQuestion.setPromptText("Pytanie nr. " + incQuestions);
        newQuestion.setStyle("-fx-max-width: 350.0; -fx-min-width: 100; -fx-pref-height: 30.0; -fx-pref-width: 350.0");

        Button submitQuestionButton = new Button("Zatwierdź");
        submitQuestionButton.setStyle("-fx-min-width: 65; -fx-pref-height: 28.0; -fx-pref-width: 65.0");
        submitQuestionButton.getStyleClass().add("button5");

        submitQuestionButton.setOnAction(e -> {
            scannerQuestion(newQuestion);
            ((HBox) submitQuestionButton.getParent()).getChildren().remove(submitQuestionButton);
        });

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);
        hBox.getChildren().addAll(newQuestion, submitQuestionButton);

        answersAndQuestionVBox.setSpacing(10);
        int index = answersAndQuestionVBox.getChildren().indexOf(addQuestionButton.getParent());
        answersAndQuestionVBox.getChildren().add(index, hBox);
    }

    private void scannerQuestion(TextField questionField) {
        question = new Question(incQuestions, questionField.getText());
        questionsList.add(question);
    }

    @FXML
    private void saveSurvey() {
        String surveyTitle = surveyTitleField.getText();
        String surveyDescription = surveyDescriptionArea.getText();
        survey.setAuthorLogin("Maks Fischer (admin)");
        survey.setTitle(surveyTitle);
        survey.setDescription(surveyDescription);
        try {
            surveyDao.insert(survey);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("TwojaOpinia");
            alert.setHeaderText(null);
            alert.setContentText("Ankieta została pomyślnie stworzona!");

            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
            stage.centerOnScreen();

            alert.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("TwojaOpinia");
            alert.setHeaderText(null);
            alert.setContentText("Nie udało się stworzyć ankiety!");

            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
            stage.centerOnScreen();

            alert.showAndWait();
        }
        try {
            Scene scene;
            Parent adminDashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/admin/AdminDashboard.fxml")));
            scene = new Scene(adminDashboard, 1000, 600);
            Stage stage = (Stage) saveSurveyButton.getScene().getWindow();
            stage.setScene(scene);
            centerStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //===================================================================================================================


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
        }
    }
    //===================================================================================================================
}