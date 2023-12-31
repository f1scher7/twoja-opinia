package twojaOpinia.controller.user;

import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import twojaOpinia.dao.SurveyDao;
import twojaOpinia.dao.UserDao;
import twojaOpinia.model.Survey;
import twojaOpinia.model.User;

import java.io.IOException;
import java.util.*;

import static twojaOpinia.util.JavaFXMethods.centerStage;

public class AvailableSurveysController {

    private SurveyDao surveyDao = new SurveyDao();
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
        TreeMap<Integer, Survey> lastAddedSurveysTreeMap = surveyDao.getSixLastAddedSurveys();
        List<Survey> lastAddedSurveys = new ArrayList<>(lastAddedSurveysTreeMap.values());
        List<Integer> lastAddedSurveysID = new ArrayList<>(lastAddedSurveysTreeMap.keySet());

        for (int i = 1; i <= 6; i++) {
            VBox vBox = (VBox) blocksWithSurveysInfo.lookup("#surveyShortInfo" + i);
            vBox.setStyle("-fx-background-radius: 12 12 12 12;");
            Label surveyTitleLabel = (Label) vBox.lookup("#surveyTitleLabel" + i);
            Label surveyNQuestionsLabel = (Label) vBox.lookup("#surveyNQuestionsLabel" + i);
            Label surveyAuthorLabel = (Label) vBox.lookup("#surveyAuthorLabel" + i);

            surveyTitleLabel.setText(lastAddedSurveys.get(i - 1).getTitle());
            if (surveyTitleLabel.getText().length() > 20) {
                surveyTitleLabel.setStyle("-fx-font-size: 11px;\n" +
                        "    -fx-font-weight: bold;\n" +
                        "    -fx-text-fill: #333333; -fx-min-height: 25px");
                surveyTitleLabel.setWrapText(true);
                surveyTitleLabel.setTextAlignment(TextAlignment.CENTER);
            }
            surveyAuthorLabel.setText(getAuthorNameAndSurname(lastAddedSurveys.get(i - 1).getAuthorLogin()));

            if (lastAddedSurveys.get(i - 1).getNQuestions() == 1) {
                surveyNQuestionsLabel.setText(1 + " pytanie");
            } else if (lastAddedSurveys.get(i - 1).getNQuestions() == 2 || lastAddedSurveys.get(i - 1).getNQuestions() == 3 || lastAddedSurveys.get(i - 1).getNQuestions() == 4) {
                surveyNQuestionsLabel.setText(lastAddedSurveys.get(i - 1).getNQuestions() + " pytania");
            } else {
                surveyNQuestionsLabel.setText(lastAddedSurveys.get(i - 1).getNQuestions() + " pytań");
            }
            ScaleTransition st = new ScaleTransition(Duration.millis(200), vBox);
            vBox.setOnMouseEntered(e -> {
                st.setToX(1.07);
                st.setToY(1.07);
                st.playFromStart();
            });
            vBox.setOnMouseExited(e -> {
                st.setToX(1.0);
                st.setToY(1.0);
                st.playFromStart();
            });

            for (Node node : blocksWithSurveysInfo.getChildren()) {
                if (node instanceof VBox) {
                    VBox vBoxSurvey = (VBox) node;

                    vBoxSurvey.setOnMouseClicked(event -> {
                        for (int j = 0; j < lastAddedSurveys.size(); j++) {
                            if(((Label) vBoxSurvey.getChildren().get(0)).getText().equals(lastAddedSurveys.get(j).getTitle())) {
                                try {
                                    FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/twojaOpinia/view/user/SurveyVoting.fxml")));
                                    int finalJ = j;
                                    fxmlLoader.setControllerFactory(param -> new SurveyVotingController(this.userLogin, lastAddedSurveysID.get(finalJ)));

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
    }

    public void setUserLogin(String login) {
        this.userLogin = login;
        userLoginLabel.setText(login);
    }
    private String getAuthorNameAndSurname(String login) {
        User user = userDao.getUserDataByLogin(login);
        return "Autor: " + user.getName() + " " + user.getSurname();
    }


    //USER_MENU
    //===================================================================================================================
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
