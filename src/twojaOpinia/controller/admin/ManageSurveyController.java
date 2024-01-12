package twojaOpinia.controller.admin;

import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import javafx.util.Duration;
import twojaOpinia.dao.SurveyDao;
import twojaOpinia.model.Answer;
import twojaOpinia.model.Question;
import twojaOpinia.model.Survey;

import static twojaOpinia.util.JavaFXMethods.centerStage;

public class ManageSurveyController {
    private Survey survey = new Survey();
    private Question question;
    private Answer answer;
    private SurveyDao surveyDao = new SurveyDao();

    private ArrayList<Answer> answersList = new ArrayList<>();
    private ArrayList<Question> questionsList = new ArrayList<>();
    private int incAnswers = 0;
    private int incQuestions = 0;

    private String adminLogin;

    @FXML
    private TextField surveyTitleField;
    @FXML
    private TextArea surveyDescriptionArea;
    @FXML
    private TextArea surveyTagsArea;
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
    private Label adminLoginLabel;
    @FXML
    private Button manageUserButtonMenu;
    @FXML
    private Button manageSurveyButtonMenu;
    @FXML
    private Button analyzeResultsButtonMenu;
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

        analyzeResultsButtonMenu.setOnMouseEntered(e -> analyzeResultsButtonMenu.setCursor(Cursor.HAND));
        analyzeResultsButtonMenu.setOnMouseExited(e -> analyzeResultsButtonMenu.setCursor(Cursor.DEFAULT));

        historyOfAddedSurveysButtonMenu.setOnMouseEntered(e -> historyOfAddedSurveysButtonMenu.setCursor(Cursor.HAND));
        historyOfAddedSurveysButtonMenu.setOnMouseExited(e -> historyOfAddedSurveysButtonMenu.setCursor(Cursor.DEFAULT));

        backToDashboardButtonMenu.setOnMouseEntered(e -> backToDashboardButtonMenu.setCursor(Cursor.HAND));
        backToDashboardButtonMenu.setOnMouseExited(e -> backToDashboardButtonMenu.setCursor(Cursor.DEFAULT));

        logoutButtonMenu.setOnMouseEntered(e -> logoutButtonMenu.setCursor(Cursor.HAND));
        logoutButtonMenu.setOnMouseExited(e -> logoutButtonMenu.setCursor(Cursor.DEFAULT));

        deleteSurveyButton.setOnMouseEntered(e -> deleteSurveyButton.setCursor(Cursor.HAND));
        deleteSurveyButton.setOnMouseExited(e -> deleteSurveyButton.setCursor(Cursor.DEFAULT));

        addQuestionButton.setOnMouseEntered(e -> addQuestionButton.setCursor(Cursor.HAND));
        addQuestionButton.setOnMouseExited(e -> addQuestionButton.setCursor(Cursor.DEFAULT));

        addAnswerButton.setOnMouseEntered(e -> addAnswerButton.setCursor(Cursor.HAND));
        addAnswerButton.setOnMouseExited(e -> addAnswerButton.setCursor(Cursor.DEFAULT));

        saveSurveyButton.setOnMouseEntered(e -> saveSurveyButton.setCursor(Cursor.HAND));
        saveSurveyButton.setOnMouseExited(e -> saveSurveyButton.setCursor(Cursor.DEFAULT));

        addAnswerButton.setOnAction(e -> addAnswer());
        addQuestionButton.setOnAction(e -> addQuestion());
        saveSurveyButton.setDisable(true);
    }

    public void setAdminLogin(String login) {
        this.adminLogin = login;
        adminLoginLabel.setText(login);
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
        Label answerLabel = new Label("Odpowiedź nr. " + incAnswers);
        answerLabel.setStyle("-fx-text-fill: #ffff;");

        TextArea newAnswer = new TextArea();
        newAnswer.setPromptText("Odpowiedź nr. " + incAnswers);
        newAnswer.setStyle("-fx-max-width: 350.0; -fx-min-width: 120;  -fx-pref-height: 42; -fx-pref-width: 350.0");

        Button submitAnswerButton = new Button("Zatwierdź");
        submitAnswerButton.setStyle("-fx-min-width: 65; -fx-pref-height: 30.0; -fx-pref-width: 65.0");
        submitAnswerButton.getStyleClass().add("button5");

        submitAnswerButton.setOnMouseEntered(e -> submitAnswerButton.setCursor(Cursor.HAND));
        submitAnswerButton.setOnMouseExited(e -> submitAnswerButton.setCursor(Cursor.DEFAULT));

        BooleanBinding isTextFieldEmpty = Bindings.createBooleanBinding(
                () -> newAnswer.getText().trim().isEmpty(),
                newAnswer.textProperty()
        );

        addAnswerButton.setDisable(true);
        addQuestionButton.setDisable(true);
        saveSurveyButton.setDisable(true);

        submitAnswerButton.disableProperty().bind(isTextFieldEmpty);

        submitAnswerButton.setOnAction(e -> {
            scannerAnswer(newAnswer);
            ((HBox) submitAnswerButton.getParent()).getChildren().remove(submitAnswerButton);

            addAnswerButton.setDisable(false);
            if (incAnswers >= 2) {
                addQuestionButton.setDisable(false);
                saveSurveyButton.setDisable(false);
            }
        });
        HBox mainHBox = new HBox();
        VBox vBox = new VBox();
        HBox hBox = new HBox();

        mainHBox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);

        hBox.getChildren().addAll(newAnswer, submitAnswerButton);
        vBox.getChildren().addAll(answerLabel, hBox);
        mainHBox.getChildren().addAll(vBox);

        answersAndQuestionVBox.setSpacing(10);
        int index = answersAndQuestionVBox.getChildren().indexOf(addAnswerButton.getParent());
        answersAndQuestionVBox.getChildren().add(index, mainHBox);
    }

    private void scannerAnswer(TextArea answerField) {
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

        Label questionLabel = new Label("Pytanie nr. " + incQuestions);
        questionLabel.setStyle("-fx-text-fill: #ffff;");

        TextArea newQuestion = new TextArea();
        newQuestion.setWrapText(true);
        newQuestion.setPromptText("Pytanie nr. " + incQuestions);
        newQuestion.setStyle("-fx-max-width: 400.0; -fx-min-width: 120;  -fx-pref-height: 42; -fx-pref-width: 400.0");


        Button submitQuestionButton = new Button("Zatwierdź");
        submitQuestionButton.setStyle("-fx-min-width: 65; -fx-pref-height: 30.0; -fx-pref-width: 65.0");
        submitQuestionButton.getStyleClass().add("button5");

        submitQuestionButton.setOnMouseEntered(e -> submitQuestionButton.setCursor(Cursor.HAND));
        submitQuestionButton.setOnMouseExited(e -> submitQuestionButton.setCursor(Cursor.DEFAULT));

        BooleanBinding isTextFieldEmpty = Bindings.createBooleanBinding(
                () -> newQuestion.getText().trim().isEmpty(),
                newQuestion.textProperty()
        );
        submitQuestionButton.disableProperty().bind(isTextFieldEmpty);

        addAnswerButton.setDisable(true);
        addQuestionButton.setDisable(true);
        saveSurveyButton .setDisable(true);

        submitQuestionButton.setOnAction(e -> {
            scannerQuestion(newQuestion);
            ((HBox) submitQuestionButton.getParent()).getChildren().remove(submitQuestionButton);

            addAnswerButton.setDisable(false);
            if (incAnswers >= 2) {
                addQuestionButton.setDisable(false);
                saveSurveyButton.setDisable(false);
            }
        });

        HBox mainHBox = new HBox();
        VBox vBox = new VBox();
        HBox hBox = new HBox();

        mainHBox.setAlignment(Pos.CENTER);

        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);

        hBox.getChildren().addAll(newQuestion, submitQuestionButton);
        vBox.getChildren().addAll(questionLabel, hBox);
        mainHBox.getChildren().addAll(vBox);

        answersAndQuestionVBox.setSpacing(10);
        int index = answersAndQuestionVBox.getChildren().indexOf(addQuestionButton.getParent());
        answersAndQuestionVBox.getChildren().add(index, mainHBox);
    }

    private void scannerQuestion(TextArea questionField) {
        question = new Question(incQuestions, questionField.getText());
        System.out.println(question.getQuestionText());
        questionsList.add(question);
        survey.getQuestions().add(question);
    }

    @FXML
    private void saveSurvey() {
        String surveyTitle = surveyTitleField.getText();
        String surveyDescription = surveyDescriptionArea.getText();
        String surveyTags = surveyTagsArea.getText();
        if (!surveyTitle.isEmpty() && !surveyDescription.isEmpty()) {
            survey.setAuthorLogin(adminLogin);
            survey.setTitle(surveyTitle);
            survey.setDescription(surveyDescription);
            survey.setTags(surveyTags);
            LocalDateTime addedDate = LocalDateTime.now();
            survey.setSurveyAddedDate(addedDate);
            survey.setNQuestions(incQuestions);
            try {
                surveyDao.insert(survey);
                survey = new Survey();
                answersList = new ArrayList<>();
                questionsList = new ArrayList<>();
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
                FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/admin/ManageSurvey.fxml")));
                Parent parent = fxmlLoader.load();

                ManageSurveyController manageSurveyController = fxmlLoader.getController();
                manageSurveyController.setAdminLogin(adminLogin);

                Scene scene = new Scene(parent, 1100, 700);
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
    private void deleteSurvey() {
        String id = deleteSurveyIdField.getText().replace(" ", "");
        if (!id.isEmpty()) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("TwojaOpinia");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Czy na pewno chcesz usunąć ankietę o ID: " + "'" + id + "' " + "?");

            Stage confirmationStage = (Stage) confirmationAlert.getDialogPane().getScene().getWindow();
            confirmationStage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
            confirmationStage.centerOnScreen();

            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if(result.get() == ButtonType.OK) {
                try {
                    int affectedRows = surveyDao.deleteSurveyByID(Integer.parseInt(id));
                    if (affectedRows == 0) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("TwojaOpinia");
                        alert.setHeaderText(null);
                        alert.setContentText("Ankieta o ID: " + "'" + id + "' " + "nie istnieje!");

                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
                        stage.centerOnScreen();

                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("TwojaOpinia");
                        alert.setHeaderText(null);
                        alert.setContentText("Ankieta o ID: " + "'" + id + "' " + "została pomyślnie usunięta!");

                        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                        stage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
                        stage.centerOnScreen();

                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("TwojaOpinia");
                    alert.setHeaderText(null);
                    alert.setContentText("Ankieta o ID: " + "'" + id + "' " + "nie istnieje!");

                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
                    stage.centerOnScreen();

                    alert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("TwojaOpinia");
            alert.setHeaderText(null);
            alert.setContentText("Pole 'ID ankiety' nie może być puste!");

            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
            stage.centerOnScreen();

            alert.showAndWait();
        }
        deleteSurveyIdField.setText("");
    }

    //ADMIN_MENU
    // ===================================================================================================================
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
        } catch (IOException e) {
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
    private void historyOfAddedSurveys() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/admin/HistorySurveys.fxml")));
            Parent historySurveys = fxmlLoader.load();

            HistorySurveysController historySurveysController = fxmlLoader.getController();
            historySurveysController.setAdminLogin(adminLogin);

            Scene scene = new Scene(historySurveys, 1100, 700);
            Stage stage = (Stage) historyOfAddedSurveysButtonMenu.getScene().getWindow();

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
    //===================================================================================================================
}