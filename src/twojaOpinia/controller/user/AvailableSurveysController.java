package twojaOpinia.controller.user;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import twojaOpinia.dao.ResponseDao;
import twojaOpinia.dao.SurveyDao;
import twojaOpinia.dao.UserDao;
import twojaOpinia.model.Survey;
import twojaOpinia.model.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static twojaOpinia.util.JavaFXMethods.centerStage;

public class AvailableSurveysController {

    private SurveyDao surveyDao = new SurveyDao();
    private ResponseDao responseDao = new ResponseDao();
    private UserDao userDao = new UserDao();
    private HashMap<Integer, Survey> matchingSurveys = new HashMap<>();
    private String userLogin;
    private String userLoginForReq;

    @FXML
    private StackPane mainContainer;
    @FXML
    private VBox mainLayout;
    @FXML
    private VBox searchVBox;
    @FXML
    private TextField searchSurveysField;
    @FXML
    private ListView searchResultsList;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private GridPane blocksWithSurveysInfo;


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



    @FXML
    public void initialize() {
        availableSurveysButtonMenu.setOnMouseEntered(e -> availableSurveysButtonMenu.setCursor(Cursor.HAND));
        availableSurveysButtonMenu.setOnMouseExited(e -> availableSurveysButtonMenu.setCursor(Cursor.DEFAULT));

        surveysHistoryButtonMenu.setOnMouseEntered(e -> surveysHistoryButtonMenu.setCursor(Cursor.HAND));
        surveysHistoryButtonMenu.setOnMouseExited(e -> surveysHistoryButtonMenu.setCursor(Cursor.DEFAULT));

        accountSettingsButtonMenu.setOnMouseEntered(e -> accountSettingsButtonMenu.setCursor(Cursor.HAND));
        accountSettingsButtonMenu.setOnMouseExited(e -> accountSettingsButtonMenu.setCursor(Cursor.DEFAULT));

        backToDashboardButtonMenu.setOnMouseEntered(e -> backToDashboardButtonMenu.setCursor(Cursor.HAND));
        backToDashboardButtonMenu.setOnMouseExited(e -> backToDashboardButtonMenu.setCursor(Cursor.DEFAULT));

        logoutButtonMenu.setOnMouseEntered(e -> logoutButtonMenu.setCursor(Cursor.HAND));
        logoutButtonMenu.setOnMouseExited(e -> logoutButtonMenu.setCursor(Cursor.DEFAULT));

        choiceBox.getItems().addAll("Ostatnio dodane ankiety", "Najpopularniejsze ankiety");
        choiceBox.setValue("Wybierz filtr");

        choiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue.equals("Ostatnio dodane ankiety")) {
                TreeMap<Integer, Survey> lastAddedSurveysTreeMap = surveyDao.getSixLastAddedSurveys();
                List<Survey> surveys = new ArrayList<>(lastAddedSurveysTreeMap.values());
                List<Integer> surveysID = new ArrayList<>(lastAddedSurveysTreeMap.keySet());

                int incGridPaneLabels = 6;
                for (int i = 1; i <= 6; i++) {
                    VBox vBox = (VBox) blocksWithSurveysInfo.lookup("#surveyShortInfo" + incGridPaneLabels);
                    vBox.setStyle("-fx-background-radius: 12 12 12 12;");
                    Label surveyTitleLabel = (Label) vBox.lookup("#surveyTitleLabel" + incGridPaneLabels);
                    Label surveyNQuestionsLabel = (Label) vBox.lookup("#surveyNQuestionsLabel" + incGridPaneLabels);
                    Label surveyAuthorLabel = (Label) vBox.lookup("#surveyAuthorLabel" + incGridPaneLabels);
                    Label surveyDateLabel = (Label) vBox.lookup("#extraLabel" + incGridPaneLabels);

                    incGridPaneLabels--;

                    surveyTitleLabel.setText(surveys.get(i - 1).getTitle());
                    if (surveyTitleLabel.getText().length() > 20) {
                        surveyTitleLabel.setStyle("-fx-font-size: 11px;\n" +
                                "    -fx-font-weight: bold;\n" +
                                "    -fx-text-fill: #333333; -fx-min-height: 25px");
                        surveyTitleLabel.setWrapText(true);
                        surveyTitleLabel.setTextAlignment(TextAlignment.CENTER);
                    }
                    surveyAuthorLabel.setText(getAuthorNameAndSurname(surveys.get(i - 1).getAuthorLogin()));

                    if (surveys.get(i - 1).getNQuestions() == 1) {
                        surveyNQuestionsLabel.setText(1 + " pytanie");
                    } else if (surveys.get(i - 1).getNQuestions() == 2 || surveys.get(i - 1).getNQuestions() == 3 || surveys.get(i - 1).getNQuestions() == 4) {
                        surveyNQuestionsLabel.setText(surveys.get(i - 1).getNQuestions() + " pytania");
                    } else {
                        surveyNQuestionsLabel.setText(surveys.get(i - 1).getNQuestions() + " pytań");
                    }
                    LocalDateTime date = surveys.get(i - 1).getSurveyAddedDate();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = date.format(formatter);

                    surveyDateLabel.setText("Data dodania: " + formattedDate);

                    ScaleTransition st = new ScaleTransition(Duration.millis(200), vBox);
                    vBox.setOnMouseEntered(e -> {
                        st.setToX(1.07);
                        st.setToY(1.07);
                        st.playFromStart();
                        vBox.setCursor(Cursor.HAND);
                    });
                    vBox.setOnMouseExited(e -> {
                        st.setToX(1.0);
                        st.setToY(1.0);
                        st.playFromStart();
                        vBox.setCursor(Cursor.DEFAULT);
                    });

                    for (Node node : blocksWithSurveysInfo.getChildren()) {
                        if (node instanceof VBox) {
                            VBox vBoxSurvey = (VBox) node;
                            vBoxSurvey.setOnMouseClicked(event -> {
                                for (int j = 0; j < surveys.size(); j++) {
                                    if(((Label) vBoxSurvey.getChildren().get(0)).getText().equals(surveys.get(j).getTitle())) {
                                        try {
                                            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/user/SurveyVoting.fxml")));
                                            int finalJ = j;
                                            fxmlLoader.setControllerFactory(param -> new SurveyVotingController(this.userLogin, surveysID.get(finalJ)));

                                            Parent surveyVoting = fxmlLoader.load();

                                            Scene scene = new Scene(surveyVoting, 1100, 700);
                                            Stage stage = (Stage) vBoxSurvey.getScene().getWindow();
                                            stage.setScene(scene);
                                            centerStage(stage);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            } else if (newValue.equals("Najpopularniejsze ankiety")) {
                Map<Integer, Integer> responsesData = responseDao.getSixMostPopularSurveysID();
                List<Integer> surveysID = new ArrayList<>(responsesData.keySet());
                List<Integer> completedSurveysCount = new ArrayList<>(responsesData.values());
                List<Survey> surveys = new ArrayList<>();

                for (Integer integer : surveysID) {
                    surveys.add(surveyDao.getByID(integer));
                }

                for (int i = 1; i <= 6; i++) {
                    VBox vBox = (VBox) blocksWithSurveysInfo.lookup("#surveyShortInfo" + i);
                    vBox.setStyle("-fx-background-radius: 12 12 12 12;");
                    Label surveyTitleLabel = (Label) vBox.lookup("#surveyTitleLabel" + i);
                    Label surveyNQuestionsLabel = (Label) vBox.lookup("#surveyNQuestionsLabel" + i);
                    Label surveyAuthorLabel = (Label) vBox.lookup("#surveyAuthorLabel" + i);
                    Label completedSurveysCountLabel = (Label) vBox.lookup("#extraLabel" + i);

                    surveyTitleLabel.setText(surveys.get(i - 1).getTitle());
                    if (surveyTitleLabel.getText().length() > 20) {
                        surveyTitleLabel.setStyle("-fx-font-size: 11px;\n" +
                                "    -fx-font-weight: bold;\n" +
                                "    -fx-text-fill: #333333; -fx-min-height: 25px");
                        surveyTitleLabel.setWrapText(true);
                        surveyTitleLabel.setTextAlignment(TextAlignment.CENTER);
                    }
                    surveyAuthorLabel.setText(getAuthorNameAndSurname(surveys.get(i - 1).getAuthorLogin()));

                    if (surveys.get(i - 1).getNQuestions() == 1) {
                        surveyNQuestionsLabel.setText(1 + " pytanie");
                    } else if (surveys.get(i - 1).getNQuestions() == 2 || surveys.get(i - 1).getNQuestions() == 3 || surveys.get(i - 1).getNQuestions() == 4) {
                        surveyNQuestionsLabel.setText(surveys.get(i - 1).getNQuestions() + " pytania");
                    } else {
                        surveyNQuestionsLabel.setText(surveys.get(i - 1).getNQuestions() + " pytań");
                    }

                    if (completedSurveysCount.get(i - 1) == 1) {
                        completedSurveysCountLabel.setText("Ukończono " + 1 + " raz");
                    } else {
                        completedSurveysCountLabel.setText("Ukończono " + completedSurveysCount.get(i - 1) + " razy");
                    }

                    ScaleTransition st = new ScaleTransition(Duration.millis(200), vBox);
                    vBox.setOnMouseEntered(e -> {
                        st.setToX(1.07);
                        st.setToY(1.07);
                        st.playFromStart();
                        vBox.setCursor(Cursor.HAND);
                    });
                    vBox.setOnMouseExited(e -> {
                        st.setToX(1.0);
                        st.setToY(1.0);
                        st.playFromStart();
                        vBox.setCursor(Cursor.DEFAULT);
                    });

                    for (Node node : blocksWithSurveysInfo.getChildren()) {
                        if (node instanceof VBox) {
                            VBox vBoxSurvey = (VBox) node;
                            vBoxSurvey.setOnMouseClicked(event -> {
                                for (int j = 0; j < surveys.size(); j++) {
                                    if(((Label) vBoxSurvey.getChildren().get(0)).getText().equals(surveys.get(j).getTitle())) {
                                        try {
                                            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/user/SurveyVoting.fxml")));
                                            int finalJ = j;
                                            fxmlLoader.setControllerFactory(param -> new SurveyVotingController(this.userLogin, surveysID.get(finalJ)));

                                            Parent surveyVoting = fxmlLoader.load();

                                            Scene scene = new Scene(surveyVoting, 1100, 700);
                                            Stage stage = (Stage) vBoxSurvey.getScene().getWindow();
                                            stage.setScene(scene);
                                            centerStage(stage);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            }

        });



        int itemHeight = 60;

        searchResultsList.setManaged(false);
        searchResultsList.setVisible(false);

        searchResultsList.setStyle("-fx-max-width: 595px; -fx-background-radius: 0 0 12 12;");
        searchVBox.setStyle("-fx-background-radius: 12 12 12 12;");
        searchSurveysField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                searchResultsList.setManaged(false);
                searchResultsList.setVisible(false);
                searchVBox.toBack();

            } else {
                searchVBox.toFront();

                mainContainer.getChildren().remove(searchVBox);
                mainContainer.getChildren().addAll(searchVBox);

                matchingSurveys = surveyDao.searchSurveys(newValue);
                ObservableList<VBox> surveyItems = FXCollections.observableArrayList();
                if (matchingSurveys.isEmpty()) {
                    Label noResultsLabel = new Label("Brak wyników.");
                    noResultsLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

                    VBox noResVBox = new VBox(noResultsLabel);
                    surveyItems.add(noResVBox);

                    searchResultsList.setManaged(true);
                    searchResultsList.setItems(surveyItems);
                    searchResultsList.setVisible(true);
                    searchResultsList.setMaxHeight(25);

                } else {
                    int limitSearchedSurveys = 5;
                    int inc = 0;

                    for (Map.Entry<Integer, Survey> entry: matchingSurveys.entrySet()) {
                        if (inc >= limitSearchedSurveys) {
                            break;
                        }

                        Survey matchingSurvey = entry.getValue();
                        Label surveyTitleLabel = new Label(matchingSurvey.getTitle());
                        surveyTitleLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

                        Label surveyTagsLabel = new Label(matchingSurvey.getTags());
                        surveyTagsLabel.setStyle("-fx-font-size: 11px;");

                        Label surveyNQuestionsLabel = new Label();
                        if (matchingSurvey.getNQuestions() == 1) {
                            surveyNQuestionsLabel.setText(matchingSurvey.getNQuestions() + " pytanie");
                        } else if (matchingSurvey.getNQuestions() == 2 || matchingSurvey.getNQuestions() == 3 || matchingSurvey.getNQuestions() == 4) {
                            surveyNQuestionsLabel.setText(matchingSurvey.getNQuestions() + " pytania");
                        } else {
                            surveyNQuestionsLabel.setText(matchingSurvey.getNQuestions() + " pytań");
                        }
                        surveyNQuestionsLabel.setStyle("-fx-font-size: 11px;");

                        VBox surveyItem = new VBox(surveyTitleLabel, surveyTagsLabel, surveyNQuestionsLabel);
                        surveyItem.setOnMouseEntered(e -> surveyItem.setCursor(Cursor.HAND));
                        surveyItem.setOnMouseExited(e -> surveyItem.setCursor(Cursor.DEFAULT));
                        surveyItems.add(surveyItem);

                        inc++;
                    }
                    searchResultsList.setManaged(true);
                    searchResultsList.setItems(surveyItems);
                    searchResultsList.setVisible(true);

                    int itemCount = Math.min(surveyItems.size(), 5);
                    searchResultsList.setMaxHeight(itemCount * (itemHeight - 3));

                    searchResultsList.setOnMouseClicked(event -> {
                        VBox selectedSurveyItem = (VBox) searchResultsList.getSelectionModel().getSelectedItem();
                        if (selectedSurveyItem != null) {
                            for (Map.Entry<Integer, Survey> entry : matchingSurveys.entrySet()) {
                                if (entry.getValue().getTitle().equals(((Label) selectedSurveyItem.getChildren().get(0)).getText())) {
                                    try {
                                        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/user/SurveyVoting.fxml")));
                                        fxmlLoader.setControllerFactory(param -> new SurveyVotingController(this.userLogin, entry.getKey()));

                                        Parent surveyVoting = fxmlLoader.load();

                                        Scene scene = new Scene(surveyVoting, 1100, 700);
                                        Stage stage = (Stage) selectedSurveyItem.getScene().getWindow();
                                        stage.setScene(scene);
                                        centerStage(stage);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });
        animateElementsSequentially(mainLayout);
    }

    public void setUserLogin(String login) {
        this.userLogin = login;
        userLoginLabel.setText(login);
    }

    private String getAuthorNameAndSurname(String login) {
        User user = userDao.getUserDataByLogin(login);
        return "Autor: " + user.getName() + " " + user.getSurname();
    }

    private void animateElementsSequentially(VBox vbox) {
        SequentialTransition seqTransition = new SequentialTransition();
        for (Node node : vbox.getChildren()) {
            FadeTransition ft = new FadeTransition(Duration.millis(200), node);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            seqTransition.getChildren().add(ft);
        }
        seqTransition.play();
    }

    //USER_MENU
    //===================================================================================================================
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
