package twojaOpinia.controller.admin;

import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javafx.util.Duration;

import twojaOpinia.dao.UserDao;
import twojaOpinia.model.Survey;
import twojaOpinia.model.User;
import twojaOpinia.controller.user.SurveyVotingController;
import twojaOpinia.dao.SurveyDao;

import static twojaOpinia.util.JavaFXMethods.centerStage;

public class SurveysAnalysisController {

    private String adminLogin;

    private SurveyDao surveyDao = new SurveyDao();
    private UserDao userDao = new UserDao();
    private HashMap<Integer, Survey> matchingSurveys = new HashMap<>();
    private String userLogin;
    private String userLoginForReq;
    
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
    private Button historyOfAddedSurveysButtonMenu;
    @FXML
    private Button backToDashboardButtonMenu;
    @FXML
    private Button logoutButtonMenu;
    
    @FXML
    private ListView searchResultsList;
    @FXML
    private VBox searchVBox;
    @FXML
    private TextField searchSurveysField;
    @FXML
    private StackPane mainContainer;

    @FXML
    private BarChart<CategoryAxis, NumberAxis> BarChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    
    @FXML
    private Label Filters;
    @FXML
    private CheckBox CountryCB;
    @FXML
    private CheckBox CityCB;
    @FXML
    private CheckBox BirthdayYearCB;
    @FXML
    private Button previousQuestionButton;
    @FXML
    private Button nextQuestionButton;
    @FXML
    private TextField searchByFilter;
    
    @FXML
    public void initialize() {
        manageUserButtonMenu.setOnMouseEntered(e -> manageUserButtonMenu.setCursor(Cursor.HAND));
        manageUserButtonMenu.setOnMouseExited(e -> manageUserButtonMenu.setCursor(Cursor.DEFAULT));

        manageSurveyButtonMenu.setOnMouseEntered(e -> manageSurveyButtonMenu.setCursor(Cursor.HAND));
        manageSurveyButtonMenu.setOnMouseExited(e -> manageSurveyButtonMenu.setCursor(Cursor.DEFAULT));

        historyOfAddedSurveysButtonMenu.setOnMouseEntered(e -> historyOfAddedSurveysButtonMenu.setCursor(Cursor.HAND));
        historyOfAddedSurveysButtonMenu.setOnMouseExited(e -> historyOfAddedSurveysButtonMenu.setCursor(Cursor.DEFAULT));

        backToDashboardButtonMenu.setOnMouseEntered(e -> backToDashboardButtonMenu.setCursor(Cursor.HAND));
        backToDashboardButtonMenu.setOnMouseExited(e -> backToDashboardButtonMenu.setCursor(Cursor.DEFAULT));

        logoutButtonMenu.setOnMouseEntered(e -> logoutButtonMenu.setCursor(Cursor.HAND));
        logoutButtonMenu.setOnMouseExited(e -> logoutButtonMenu.setCursor(Cursor.DEFAULT));
        
        int itemHeight = 60;
        
		
		BarChart.setManaged(false); 
		BarChart.setVisible(false);
		CountryCB.setManaged(false); 
		CountryCB.setVisible(false);
		CityCB.setManaged(false); 
		CityCB.setVisible(false);
		BirthdayYearCB.setManaged(false); 
		BirthdayYearCB.setVisible(false);
		Filters.setManaged(false);
		Filters.setVisible(false);
		previousQuestionButton.setManaged(false);
		previousQuestionButton.setVisible(false);
		nextQuestionButton.setManaged(false);
		nextQuestionButton.setVisible(false);
		searchByFilter.setManaged(false);
		searchByFilter.setVisible(false);
		
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

					//DODAĆ tutaj poniżej logike odpowiadającą za wyświetlenie się diagramu i odniesienia się do question and responses dla danej wyszukanej ankiety z searchResultList
                    searchResultsList.setOnMouseClicked(event -> {
                        VBox selectedSurveyItem = (VBox) searchResultsList.getSelectionModel().getSelectedItem();
                        if (selectedSurveyItem != null) {
                            for (Map.Entry<Integer, Survey> entry : matchingSurveys.entrySet()) {
                                if (entry.getValue().getTitle().equals(((Label) selectedSurveyItem.getChildren().get(0)).getText())) {
                                    	BarChart.setManaged(true); 
                                		BarChart.setVisible(true);
                                		CountryCB.setManaged(true); 
                                		CountryCB.setVisible(true);
                                		CityCB.setManaged(true); 
                                		CityCB.setVisible(true);
                                		BirthdayYearCB.setManaged(true); 
                                		BirthdayYearCB.setVisible(true);
                                		Filters.setManaged(true);
                                		Filters.setVisible(true);
                                		previousQuestionButton.setManaged(true);
                                		previousQuestionButton.setVisible(true);
                                		nextQuestionButton.setManaged(true);
                                		nextQuestionButton.setVisible(true);
                                		
                                		CountryCB.setOnMouseClicked(innerEvent -> {
                                		if(CountryCB.isSelected()) {
                                			searchByFilter.setManaged(true);
                                			searchByFilter.setVisible(true);
                                			BirthdayYearCB.setManaged(false); 
                                    		BirthdayYearCB.setVisible(false);
                                    		CityCB.setManaged(false);
                                    		CityCB.setVisible(false);
                                		}else {
                                			searchByFilter.setManaged(false);
                                			searchByFilter.setVisible(false);
                                			BirthdayYearCB.setManaged(true); 
                                    		BirthdayYearCB.setVisible(true);
                                    		CityCB.setManaged(true);
                                    		CityCB.setVisible(true);}
                                		});
                                		
                                		CityCB.setOnMouseClicked(innerEvent -> {
                                    		if(CityCB.isSelected()) {
                                    			searchByFilter.setManaged(true);
                                    			searchByFilter.setVisible(true);
                                    			BirthdayYearCB.setManaged(false); 
                                        		BirthdayYearCB.setVisible(false);
                                        		CountryCB.setManaged(false);
                                        		CountryCB.setVisible(false);
                                    		}else {
                                    			searchByFilter.setManaged(false);
                                    			searchByFilter.setVisible(false);
                                    			BirthdayYearCB.setManaged(true); 
                                        		BirthdayYearCB.setVisible(true);
                                        		CountryCB.setManaged(true);
                                        		CountryCB.setVisible(true);}
                                    	});
                                		
                                		BirthdayYearCB.setOnMouseClicked(innerEvent -> {
                                    		if(BirthdayYearCB.isSelected()) {
                                    			searchByFilter.setManaged(true);
                                    			searchByFilter.setVisible(true);
                                    			CityCB.setManaged(false); 
                                    			CityCB.setVisible(false);
                                        		CountryCB.setManaged(false);
                                        		CountryCB.setVisible(false);
                                    		}else {
                                    			searchByFilter.setManaged(false);
                                    			searchByFilter.setVisible(false);
                                    			CityCB.setManaged(true); 
                                    			CityCB.setVisible(true);
                                        		CountryCB.setManaged(true);
                                        		CountryCB.setVisible(true);}
                                    	});
                          
                                		
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    public void setAdminLogin(String login) {
        this.adminLogin = login;
        adminLoginLabel.setText(login);
    }
    
    private String getUserCountry(String login) {
        User user = userDao.getUserDataByLogin(login);
        return user.getCountry();
    }
    
    private String getUserCity(String login) {
        User user = userDao.getUserDataByLogin(login);
        return user.getCity();
    }
    
    private String getUserBirthday(String login) {
        User user = userDao.getUserDataByLogin(login);
        return user.getBirthday();
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
