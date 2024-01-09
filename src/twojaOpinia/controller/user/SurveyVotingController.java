package twojaOpinia.controller.user;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import twojaOpinia.dao.*;
import twojaOpinia.model.*;

import java.io.IOException;
import java.util.*;

import static javafx.scene.control.ContentDisplay.CENTER;
import static twojaOpinia.util.JavaFXMethods.centerStage;

public class SurveyVotingController {
    private SurveyDao surveyDao = new SurveyDao();
    private QuestionDao questionDao = new QuestionDao();
    private AnswerDao answerDao = new AnswerDao();
    private ResponseDao responseDao = new ResponseDao();

    private Survey survey;
    private HashMap<Integer, Question> questionsHashMap;
    TreeMap<Integer, Question> sortedQuestionsTreeMAp;
    private List<Question> questionsList;
    private List<Integer> questionsIDList;
    private List<Response> responses = new ArrayList<>();

    private String userLogin;
    private int surveyID;
    private int nQuestions;
    private int nCurrentQuestions = 0;

    @FXML
    private Label incQuestionLabel;
    @FXML
    private Label questionContentLabel;

    @FXML
    private VBox answersVBox;

    @FXML
    private Button previousQuestionButton;
    @FXML
    private Button nextQuestionButton;


    @FXML
    private Button cancelSurveyButton;

    public SurveyVotingController(String userLogin, int surveyID) {
        this.surveyID = surveyID;
        this.userLogin = userLogin;
        getSurvey(this.userLogin, this.surveyID);
    }

    @FXML
    public void initialize() {
        if (nCurrentQuestions + 1 == nQuestions) {
            nextQuestionButton.setStyle("-fx-font-size: 14px; -fx-background-color: #62ee51; -fx-text-fill: #ffffff;");
            nextQuestionButton.setText("Zakończ ankietę");
        } else {
            nextQuestionButton.setStyle("-fx-font-size: 14px; -fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF;");
        }
        nextQuestionButton.setOnMouseEntered(e -> nextQuestionButton.setStyle("-fx-font-size: 14px; -fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0, 0, 0);"));
        nextQuestionButton.setOnMouseExited(e -> nextQuestionButton.setStyle("-fx-font-size: 14px; -fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF;"));

        previousQuestionButton.setVisible(false);
        previousQuestionButton.setDisable(true);

        incQuestionLabel.setText((nCurrentQuestions + 1) + "/" + this.nQuestions);
        questionContentLabel.setText(String.valueOf(questionsList.get(nCurrentQuestions)));

        if (String.valueOf(questionsList.get(nCurrentQuestions)).length() > 80) {
            questionContentLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 22;");
            questionContentLabel.setWrapText(true);
        } else {
            questionContentLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 30;");
        }

        animationForContentQuestion(questionContentLabel);
        animationForNextQuestion(nextQuestionButton);

        TreeMap<Integer, Answer> answersTreemap = answerDao.getAnswerByQuestionID(questionsIDList.get(nCurrentQuestions));
        List<Answer> answersList = new ArrayList<>(answersTreemap.values());
        List<Integer> answersIDList = new ArrayList<>(answersTreemap.keySet());

        String backgroundLabelColor = backgroundLabelColor();
        for (int i = 0; i < answersList.size(); i++) {
            Label answerLabel = new Label(answersList.get(i).getAnswerText());
            String fontSize;

            if (answersList.get(i).getAnswerText().length() > 50) {
                fontSize = "15px";
                answerLabel.setWrapText(true);
            } else {
                fontSize = "20px";
            }

            String maxHeight;
            String prefHeight;
            if (answersList.size() > 6) {
                maxHeight = "40px;";
                prefHeight = "30px;";
                fontSize = "12px";
                answerLabel.setWrapText(true);
            } else {
                maxHeight = "80px;";
                prefHeight = "50px;";
            }

            String style;
            if (this.responses.get(nCurrentQuestions).getAnswerID() != answersIDList.get(i)) {
                style = "-fx-background-radius: 12;" + backgroundLabelColor + "-fx-font-weight: bold; -fx-font-size:" + fontSize + "; -fx-text-fill: #ffffff; -fx-padding: 10px; -fx-max-width: 580px; -fx-pref-height:" + prefHeight + "; -fx-max-height:" + maxHeight;
            } else {
                style = "-fx-background-radius: 12; -fx-border-color: #54ff00; -fx-border-radius: 7; -fx-border-width: 7;" + backgroundLabelColor + "-fx-font-weight: bold; -fx-font-size:" + fontSize + "; -fx-text-fill: #ffffff; -fx-padding: 10px; -fx-max-width: 580px; -fx-pref-height:" + prefHeight + "; -fx-max-height:" + maxHeight;
            }

            answerLabel.setContentDisplay(CENTER);
            answerLabel.setStyle(style);
            animationForAnswers(answerLabel);

            animationForAnswerLabel(answerLabel);

            String finalFontSize = fontSize;
            int finalI = i;
            final Label[] lastClickedLabel = new Label[1];

            answerLabel.setOnMouseClicked(event -> {
                for (Node node : answersVBox.getChildren()) {
                    if (node instanceof Label) {

                        ((Label) node).setStyle("-fx-background-radius: 12;" + backgroundLabelColor + "-fx-font-weight: bold; -fx-font-size:" + finalFontSize + "; -fx-text-fill: #ffffff; -fx-padding: 10px; -fx-max-width: 580px; -fx-pref-height:" + prefHeight + "; -fx-max-height:" + maxHeight);
                    }
                }

                answerLabel.setStyle("-fx-background-radius: 12; -fx-border-color: #54ff00; -fx-border-radius: 7; -fx-border-width: 7;" + backgroundLabelColor + "-fx-font-weight: bold; -fx-font-size:" + finalFontSize + "; -fx-text-fill: #ffffff; -fx-padding: 10px; -fx-max-width: 580px; -fx-pref-height:" + prefHeight + "; -fx-max-height:" + maxHeight);

                Response response = new Response();
                response.setSurveyID(this.surveyID);
                response.setAnswerID(answersIDList.get(finalI));
                response.setUserLogin(this.userLogin);
                this.responses.set(nCurrentQuestions, response);

                lastClickedLabel[0] = answerLabel;
            });
            answersVBox.getChildren().add(answerLabel);
        }
    }
    private String backgroundLabelColor() {
        double brightnessRed = 0.4 + Math.random() * 0.4;
        double brightnessGreen = 0.7 + Math.random() * 0.3;
        double brightnessBlue = 0.8 + Math.random() * 0.4;

        int baseColorRed = (int) (brightnessRed * 255);
        int baseColorGreen = (int) (brightnessGreen * 255);
        int baseColorBlue = (int) (brightnessBlue * 255);

        int red1 = baseColorRed + (int) (Math.random() * 50 - 25);
        int green1 = baseColorGreen + (int) (Math.random() * 50 - 25);
        int blue1 = baseColorBlue + (int) (Math.random() * 50 - 25);

        red1 = Math.min(Math.max(red1, 0), 255);
        green1 = Math.min(Math.max(green1, 0), 255);
        blue1 = Math.min(Math.max(blue1, 0), 255);
        return String.format("-fx-background-color: rgb(%d,%d,%d, 0.8);", red1, green1, blue1);
    }
    private void animationForContentQuestion(Label label) {
        FadeTransition ftQuestion = new FadeTransition();
        ftQuestion.setDuration(Duration.millis(1000));
        ftQuestion.setNode(label);
        ftQuestion.setFromValue(0.0);
        ftQuestion.setToValue(1.0);
        ftQuestion.play();
    }

    private void animationForNextQuestion(Button button) {
        FadeTransition ftQuestion = new FadeTransition();
        ftQuestion.setDuration(Duration.millis(1000));
        ftQuestion.setNode(button);
        ftQuestion.setFromValue(0.0);
        ftQuestion.setToValue(1.0);
        ftQuestion.play();
    }

    private void animationForAnswerLabel (Label label) {
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis(1000));
        ft.setNode(label);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }
    private void animationForAnswers(Label label) {
        ScaleTransition st = new ScaleTransition(Duration.millis(200), label);
        label.setOnMouseEntered(e -> {
            st.setToX(1.07);
            st.setToY(1.07);
            st.playFromStart();
        });
        label.setOnMouseExited(e -> {
            st.setToX(1.0);
            st.setToY(1.0);
            st.playFromStart();
        });
    }

    public void getSurvey(String userLogin, int id) {
        this.userLogin = userLogin;
        this.surveyID = id;

        this.survey = surveyDao.getByID(this.surveyID);
        this.nQuestions = this.survey.getNQuestions();
        this.questionsHashMap = questionDao.getQuestionsBySurveyID(this.surveyID);
        this.sortedQuestionsTreeMAp = new TreeMap<>(this.questionsHashMap);
        this.questionsList = new ArrayList<>(this.sortedQuestionsTreeMAp.values());
        this.questionsIDList = new ArrayList<>(this.sortedQuestionsTreeMAp.keySet());

        for (int i = 0; i < this.nQuestions; i++) {
            this.responses.add(new Response());
        }
    }

    @FXML
    private void nextQuestion() {
        nCurrentQuestions++;

        if (nCurrentQuestions == nQuestions - 1) {
            nextQuestionButton.setOnMouseEntered(e -> nextQuestionButton.setStyle("-fx-font-size: 14px; -fx-background-color: #5afa67; -fx-text-fill: #FFFFFF; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0, 0, 0);"));
            nextQuestionButton.setOnMouseExited(e -> nextQuestionButton.setStyle("-fx-font-size: 14px; -fx-background-color: #5afa67; -fx-text-fill: #FFFFFF;"));
            nextQuestionButton.setText("Zakończ ankietę");
        } else {

            previousQuestionButton.setOnMouseEntered(e -> previousQuestionButton.setStyle("-fx-font-size: 14px; -fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0, 0, 0);"));
            previousQuestionButton.setOnMouseExited(e -> previousQuestionButton.setStyle("-fx-font-size: 14px; -fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF;"));
        }
        if(nCurrentQuestions == nQuestions) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("TwojaOpinia");
            alert.setHeaderText(null);
            alert.getDialogPane().setPrefHeight(250); // Ustawienie minimalnej szerokości na 500
            Image image = new Image(getClass().getResourceAsStream("/twojaOpinia/view/user/img/giphy.gif"));
            ImageView imageView = new ImageView(image);

            Label thankYouLabel = new Label("Dziękujemy za poświęcenie czasu na wypełnienie naszej ankiety!            ");
            thankYouLabel.setTextFill(Color.WHITE);
            thankYouLabel.setAlignment(Pos.CENTER);
            thankYouLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 17; -fx-padding: 200 0 0 0");

            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(thankYouLabel);

            alert.getDialogPane().setContent(stackPane);

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/user/userStyles.css")).toExternalForm());

            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
            stage.centerOnScreen();

            alert.showAndWait();

            for(Response response: responses) {
                responseDao.insert(response);
            }
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/user/UserDashboard.fxml")));
                Parent userDashboard = fxmlLoader.load();

                UserPulpitController userPulpitController = fxmlLoader.getController();
                userPulpitController.setUserLogin(this.userLogin);

                Scene scene = new Scene(userDashboard, 1100, 700);
                Stage stage1 = (Stage) nextQuestionButton.getScene().getWindow();
                stage1.setScene(scene);

                centerStage(stage1);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
            }
        } else {
            previousQuestionButton.setVisible(true);
            previousQuestionButton.setDisable(false);
            answersVBox.getChildren().clear();

            incQuestionLabel.setText((nCurrentQuestions + 1) + "/" + this.nQuestions);
            questionContentLabel.setText(String.valueOf(questionsList.get(nCurrentQuestions)));

            if (String.valueOf(questionsList.get(nCurrentQuestions)).length() > 80) {
                questionContentLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 22;");
                questionContentLabel.setWrapText(true);
            } else {
                questionContentLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 30;");
            }

            animationForContentQuestion(questionContentLabel);

            TreeMap<Integer, Answer> answersTreemap = answerDao.getAnswerByQuestionID(questionsIDList.get(nCurrentQuestions));
            List<Answer> answersList = new ArrayList<>(answersTreemap.values());
            List<Integer> answersIDList = new ArrayList<>(answersTreemap.keySet());

            String backgroundLabelColor = backgroundLabelColor();
            for (int i = 0; i < answersList.size(); i++) {
                Label answerLabel = new Label(answersList.get(i).getAnswerText());

                String fontSize;
                if (answersList.get(i).getAnswerText().length() > 50) {
                    fontSize = "15px";
                    answerLabel.setWrapText(true);
                } else {
                    fontSize = "20px";
                }

                String maxHeight;
                String prefHeight;
                if (answersList.size() > 6) {
                    maxHeight = "40px;";
                    prefHeight = "30px;";
                    fontSize = "12px";
                    answerLabel.setWrapText(true);
                } else {
                    maxHeight = "80px;";
                    prefHeight = "50px;";
                }

                String style;
                if (this.responses.get(nCurrentQuestions).getAnswerID() != answersIDList.get(i)) {
                    style = "-fx-background-radius: 12;" + backgroundLabelColor + "-fx-font-weight: bold; -fx-font-size:" + fontSize + "; -fx-text-fill: #ffffff; -fx-padding: 10px; -fx-max-width: 580px; -fx-pref-height:" + prefHeight + "; -fx-max-height:" + maxHeight;
                } else {
                    style = "-fx-background-radius: 12; -fx-border-color: #54ff00; -fx-border-radius: 7; -fx-border-width: 7;" + backgroundLabelColor + "-fx-font-weight: bold; -fx-font-size:" + fontSize + "; -fx-text-fill: #ffffff; -fx-padding: 10px; -fx-max-width: 580px; -fx-pref-height:" + prefHeight + "; -fx-max-height:" + maxHeight;
                }


                answerLabel.setContentDisplay(CENTER);
                answerLabel.setStyle(style);
                animationForAnswers(answerLabel);

                animationForAnswerLabel(answerLabel);

                String finalFontSize = fontSize;
                int finalI = i;
                final Label[] lastClickedLabel = new Label[1];

                answerLabel.setOnMouseClicked(event -> {
                    for (Node node : answersVBox.getChildren()) {
                        if (node instanceof Label) {
                            ((Label) node).setStyle("-fx-background-radius: 12;" + backgroundLabelColor + "-fx-font-weight: bold; -fx-font-size:" + finalFontSize + "; -fx-text-fill: #ffffff; -fx-padding: 10px; -fx-max-width: 580px; -fx-pref-height:" + prefHeight + "; -fx-max-height:" + maxHeight);
                        }
                    }

                    answerLabel.setStyle("-fx-background-radius: 12; -fx-border-color: #54ff00; -fx-border-radius: 7; -fx-border-width: 7;" + backgroundLabelColor + "-fx-font-weight: bold; -fx-font-size:" + finalFontSize + "; -fx-text-fill: #ffffff; -fx-padding: 10px; -fx-max-width: 580px; -fx-pref-height:" + prefHeight + "; -fx-max-height:" + maxHeight);

                    Response response = new Response();
                    response.setSurveyID(this.surveyID);
                    response.setAnswerID(answersIDList.get(finalI));
                    response.setUserLogin(this.userLogin);
                    this.responses.set(nCurrentQuestions, response);

                    lastClickedLabel[0] = answerLabel;
                });

                answersVBox.getChildren().add(answerLabel);
            }
        }
    }

    @FXML
    private void previousQuestion() {
        if (nCurrentQuestions == nQuestions) {
            nextQuestionButton.setStyle("-fx-font-size: 14px; -fx-background-color: #62ee51; -fx-text-fill: #ffffff;");
            nextQuestionButton.setText("Zakończ ankietę");
        } else {
            nextQuestionButton.setText("Następne pytanie");
            nextQuestionButton.setStyle("-fx-font-size: 14px; -fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF;");
            nextQuestionButton.setOnMouseEntered(e -> nextQuestionButton.setStyle("-fx-font-size: 14px; -fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0, 0, 0);"));
            nextQuestionButton.setOnMouseExited(e -> nextQuestionButton.setStyle("-fx-font-size: 14px; -fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF;"));
        }

        if (nCurrentQuestions > 1) {
            previousQuestionButton.setVisible(true);
            previousQuestionButton.setDisable(false);
        } else {
            previousQuestionButton.setVisible(false);
            previousQuestionButton.setDisable(true);
        }
        previousQuestionButton.setOnMouseEntered(e -> previousQuestionButton.setStyle("-fx-font-size: 14px; -fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 5, 0, 0, 0);"));
        previousQuestionButton.setOnMouseExited(e -> previousQuestionButton.setStyle("-fx-font-size: 14px; -fx-background-color: #7ba0ff; -fx-text-fill: #FFFFFF;"));

        nCurrentQuestions--;
        answersVBox.getChildren().clear();

        incQuestionLabel.setText((nCurrentQuestions + 1) + "/" + this.nQuestions);
        questionContentLabel.setText(String.valueOf(questionsList.get(nCurrentQuestions)));

        if (String.valueOf(questionsList.get(nCurrentQuestions)).length() > 80) {
            questionContentLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 22;");
            questionContentLabel.setWrapText(true);
        } else {
            questionContentLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 30;");
        }

        animationForContentQuestion(questionContentLabel);

        TreeMap<Integer, Answer> answersTreemap = answerDao.getAnswerByQuestionID(questionsIDList.get(nCurrentQuestions));
        List<Answer> answersList = new ArrayList<>(answersTreemap.values());
        List<Integer> answersIDList = new ArrayList<>(answersTreemap.keySet());

        String backgroundLabelColor = backgroundLabelColor();
        for (int i = 0; i < answersList.size(); i++) {
            Label answerLabel =  new Label(answersList.get(i).getAnswerText());
            String fontSize;
            if (answersList.get(i).getAnswerText().length() > 50) {
                fontSize = "15px";
                answerLabel.setWrapText(true);
            } else {
                fontSize = "20px";
            }

            String maxHeight;
            String prefHeight;
            if (answersList.size() > 6) {
                maxHeight = "40px;";
                prefHeight = "30px;";
                fontSize = "12px";
                answerLabel.setWrapText(true);
            } else {
                maxHeight = "80px;";
                prefHeight = "50px;";
            }

            String style;
            if (this.responses.get(nCurrentQuestions).getAnswerID() != answersIDList.get(i)) {
                style = "-fx-background-radius: 12;" + backgroundLabelColor + "-fx-font-weight: bold; -fx-font-size:" + fontSize + "; -fx-text-fill: #ffffff; -fx-padding: 10px; -fx-max-width: 580px; -fx-pref-height:" + prefHeight + "; -fx-max-height:" + maxHeight;
            } else {
                style = "-fx-background-radius: 12; -fx-border-color: #54ff00; -fx-border-radius: 7; -fx-border-width: 7;" + backgroundLabelColor + "-fx-font-weight: bold; -fx-font-size:" + fontSize + "; -fx-text-fill: #ffffff; -fx-padding: 10px; -fx-max-width: 580px; -fx-pref-height:" + prefHeight + "; -fx-max-height:" + maxHeight;
            }

            answerLabel.setContentDisplay(CENTER);
            answerLabel.setStyle(style);
            animationForAnswers(answerLabel);

            animationForAnswerLabel(answerLabel);

            String finalFontSize = fontSize;
            int finalI = i;
            final Label[] lastClickedLabel = new Label[1];
            answerLabel.setOnMouseClicked(event -> {
                for (Node node : answersVBox.getChildren()) {
                    if (node instanceof Label) {
                        ((Label) node).setStyle("-fx-background-radius: 12;" + backgroundLabelColor + "-fx-font-weight: bold; -fx-font-size:" + finalFontSize + "; -fx-text-fill: #ffffff; -fx-padding: 10px; -fx-max-width: 580px; -fx-pref-height:" + prefHeight + "; -fx-max-height:" + maxHeight);
                    }
                }

                answerLabel.setStyle("-fx-background-radius: 12; -fx-border-color: #54ff00; -fx-border-radius: 7; -fx-border-width: 7;" + backgroundLabelColor + "-fx-font-weight: bold; -fx-font-size:" + finalFontSize + "; -fx-text-fill: #ffffff; -fx-padding: 10px; -fx-max-width: 580px; -fx-pref-height:" + prefHeight + "; -fx-max-height:" + maxHeight);

                Response response = new Response();
                response.setSurveyID(this.surveyID);
                response.setAnswerID(answersIDList.get(finalI));
                response.setUserLogin(this.userLogin);
                this.responses.set(nCurrentQuestions, response);

                lastClickedLabel[0] = answerLabel;
            });

            answersVBox.getChildren().add(answerLabel);
        }
    }

    @FXML
    private void cancelSurvey() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("TwojaOpinia");
        confirmationAlert.setHeaderText(null);

        confirmationAlert.getDialogPane().setPrefHeight(500);

        Image image = new Image(getClass().getResourceAsStream("/twojaOpinia/view/user/img/jim-carrey-liar-liar.gif"));
        ImageView imageView = new ImageView(image);

        Label thankYouLabel = new Label("Czy na pewno chcecz przerwać uzupełnienie ankiety?            ");
        thankYouLabel.setTextFill(Color.WHITE);
        thankYouLabel.setAlignment(Pos.CENTER);
        thankYouLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 17; -fx-padding: 400 0 0 0");

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(thankYouLabel);

        confirmationAlert.getDialogPane().setContent(stackPane);

        DialogPane dialogPane = confirmationAlert.getDialogPane();
        dialogPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/login/loginStyles.css")).toExternalForm());

        Stage stage = (Stage) confirmationAlert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("file:src/twojaOpinia/iconTwojaOpinia.png"));
        stage.centerOnScreen();

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/user/UserDashboard.fxml")));
                Parent userDashboard = fxmlLoader.load();

                UserPulpitController userPulpitController = fxmlLoader.getController();
                userPulpitController.setUserLogin(this.userLogin);

                Scene scene = new Scene(userDashboard, 1100, 700);
                Stage stage1 = (Stage) cancelSurveyButton.getScene().getWindow();
                stage1.setScene(scene);

                centerStage(stage1);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Błąd podczas ładowania pliku FXML: " + e.getMessage());
            }
        }
    }
}
