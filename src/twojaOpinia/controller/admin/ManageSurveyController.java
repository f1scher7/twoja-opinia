package twojaOpinia.controller.admin;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import twojaOpinia.dao.SurveyDao;
import twojaOpinia.dao.UserDao;
import twojaOpinia.model.Answer;
import twojaOpinia.model.Question;
import twojaOpinia.model.Survey;

import static twojaOpinia.util.JavaFXMethods.centerStage;

public class ManageSurveyController {
    private Survey survey = new Survey();
    private Question question;
    private Answer answer;
    private UserDao userDao = new UserDao();
    private SurveyDao surveyDao = new SurveyDao();

    private ArrayList<Answer> answersList = new ArrayList<>();
    private ArrayList<Question> questionsList = new ArrayList<>();
    private int incAnswers = 0;
    private int incQuestions = 0;

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
    @FXML
    private TextField deleteSurveyIdField;
    @FXML
    private Button deleteSurveyButton;

    @FXML
    private Button manageUserButtonMenu;
    @FXML
    private Button manageSurveyButtonMenu;
    @FXML
    private Button logoutButtonMenu;


    @FXML
    public void initialize() {
        addAnswerButton.setOnAction(e -> addAnswer());
        addQuestionButton.setOnAction(e -> addQuestion());
        saveSurveyButton.setDisable(true);
    }

    @FXML
    private void addAnswer() {
        incAnswers++;
        addTextFieldAnswer();
        if (incAnswers >= 2) {
            addQuestionButton.setOnAction(i -> addQuestion());
        }
    }

    private void addTextFieldAnswer() {
        TextField newAnswer = new TextField();
        newAnswer.setPromptText("Odpowiedź nr. " + incAnswers);
        newAnswer.setStyle("-fx-max-width: 305.0; -fx-min-width: 100; -fx-pref-height: 30.0; -fx-pref-width: 280.0");

        Button submitAnswerButton = new Button("Zatwierdź");
        submitAnswerButton.setStyle("-fx-min-width: 65; -fx-pref-height: 28.0; -fx-pref-width: 65.0");
        submitAnswerButton.getStyleClass().add("button5");

        BooleanBinding isTextFieldEmpty = Bindings.createBooleanBinding(
                () -> newAnswer.getText().trim().isEmpty(),
                newAnswer.textProperty()
        );

        // Przyciski addAnswerButton i addQuestionButton są domyślnie dezaktywowane
        addAnswerButton.setDisable(true);
        addQuestionButton.setDisable(true);
        saveSurveyButton.setDisable(true);

        submitAnswerButton.disableProperty().bind(isTextFieldEmpty);

        submitAnswerButton.setOnAction(e -> {
            scannerAnswer(newAnswer);
            ((HBox) submitAnswerButton.getParent()).getChildren().remove(submitAnswerButton);

            // Aktywuj przyciski addAnswerButton i addQuestionButton po naciśnięciu submitAnswerButton
            addAnswerButton.setDisable(false);
            if (incAnswers >= 2) {
                addQuestionButton.setDisable(false);
                saveSurveyButton.setDisable(false);
            }
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
        System.out.println(answer.getAnswerText());
        answersList.add(answer);
        question.getAnswers().add(answer);
    }


    @FXML
    private void addQuestion() {
        incAnswers = 0;
        addTextFieldQuestion();

        addAnswerButton.setOnAction(a -> addAnswer());
        addAnswerButton.getStyleClass().remove("lockButton");
        addAnswerButton.getStyleClass().add("button1");

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

        BooleanBinding isTextFieldEmpty = Bindings.createBooleanBinding(
                () -> newQuestion.getText().trim().isEmpty(),
                newQuestion.textProperty()
        );
        submitQuestionButton.disableProperty().bind(isTextFieldEmpty);

        // Przyciski addAnswerButton i addQuestionButton są domyślnie dezaktywowane
        addAnswerButton.setDisable(true);
        addQuestionButton.setDisable(true);
        saveSurveyButton .setDisable(true);

        submitQuestionButton.setOnAction(e -> {
            scannerQuestion(newQuestion);
            ((HBox) submitQuestionButton.getParent()).getChildren().remove(submitQuestionButton);

            // Aktywuj przyciski addAnswerButton i addQuestionButton po naciśnięciu submitQuestionButton

            addAnswerButton.setDisable(false);
            if (incAnswers >= 2) {
                addQuestionButton.setDisable(false);
                saveSurveyButton.setDisable(false);
            }
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
        System.out.println(question.getQuestionText());
        questionsList.add(question);
        survey.getQuestions().add(question);
    }

    @FXML
    private void saveSurvey() {
        String surveyTitle = surveyTitleField.getText();
        String surveyDescription = surveyDescriptionArea.getText();
        if (!surveyTitle.isEmpty() && !surveyDescription.isEmpty()) {
            survey.setAuthorLogin("Admin");
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
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("TwojaOpinia");
            alert.setHeaderText(null);
            alert.setContentText("Tytuł i opis ankiety nie mogą być puste!");

            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
            stage.centerOnScreen();

            alert.showAndWait();
        }
    }

    @FXML
    public void deleteSurvey() {
        String id = deleteSurveyIdField.getText().replace(" ", "");
        if (!id.isEmpty()) {
            try {
                surveyDao.delete(Integer.parseInt(id));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("TwojaOpinia");
                alert.setHeaderText(null);
                alert.setContentText("Ankieta została pomyślnie usunięta!");

                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
                stage.centerOnScreen();

                alert.showAndWait();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("TwojaOpinia");
                alert.setHeaderText(null);
                alert.setContentText("Nieprawidłowy ID ankiety!");

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
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("TwojaOpinia");
            alert.setHeaderText(null);
            alert.setContentText("ID ankiety nie może być pusty!");

            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
            stage.centerOnScreen();

            alert.showAndWait();
        }
    }
    //ADMIN_MENU
    // ===================================================================================================================
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