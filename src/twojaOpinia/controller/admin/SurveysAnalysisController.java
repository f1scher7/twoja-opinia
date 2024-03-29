package twojaOpinia.controller.admin;

import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import javafx.util.Duration;
import twojaOpinia.dao.UserDao;
import twojaOpinia.model.Answer;
import twojaOpinia.model.Question;
import twojaOpinia.model.Response;
import twojaOpinia.model.Survey;
import twojaOpinia.model.User;
import twojaOpinia.controller.user.SurveyVotingController;
import twojaOpinia.controller.user.UserPulpitController;
import twojaOpinia.dao.AnswerDao;
import twojaOpinia.dao.QuestionDao;
import twojaOpinia.dao.ResponseDao;
import twojaOpinia.dao.SurveyDao;

import static javafx.scene.control.ContentDisplay.CENTER;
import static twojaOpinia.util.JavaFXMethods.centerStage;

public class SurveysAnalysisController {

    private String adminLogin;

    private SurveyDao surveyDao = new SurveyDao();
    private UserDao userDao = new UserDao();
    private QuestionDao questionDao = new QuestionDao();
    private AnswerDao answerDao = new AnswerDao();
    private ResponseDao responseDao = new ResponseDao();
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
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private BarChart<CategoryAxis, NumberAxis> BarChart;
    @FXML
    private Button previousQuestionButton;
    @FXML
    private Button nextQuestionButton;
    @FXML
    private TextField searchByFilter;
    @FXML
    private Button SubmitButton;
    @FXML 
    private HBox HBoxChartAndFilters;
    @FXML 
    private VBox VBoxFilters;
    @FXML 
    private HBox HBoxButtons;
    @FXML 
    private ChoiceBox<String> FiltersCB;
    private String[] Filters = {"Brak", "Kraj", "Miasto", "Rok Urodzenia"};
    @FXML
    private ListView QuestionInfoList;
   
    private Survey survey;
    private HashMap<Integer, Question> questionsHashMap;
    TreeMap<Integer, Question> sortedQuestionsTreeMAp;
    private List<Question> questionsList;
    private List<Integer> questionsIDList;
    private List<Response> responses = new ArrayList<>();
    private int surveyID;
    private int nQuestions;
    private int nAnswers;
    private int nCurrentQuestions = 0;
    private Map<String, User> usersMap = new HashMap<>();
    
    
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
        
        SubmitButton.setOnMouseEntered(e ->SubmitButton.setCursor(Cursor.HAND) );
    	SubmitButton.setOnMouseExited(e -> SubmitButton.setCursor(Cursor.DEFAULT));
    	
    	nextQuestionButton.setOnMouseEntered(e ->nextQuestionButton.setCursor(Cursor.HAND) );
		nextQuestionButton.setOnMouseExited(e -> nextQuestionButton.setCursor(Cursor.DEFAULT));
		
		previousQuestionButton.setOnMouseEntered(e -> previousQuestionButton.setCursor(Cursor.HAND));
		previousQuestionButton.setOnMouseExited(e -> previousQuestionButton.setCursor(Cursor.DEFAULT));
		
		FiltersCB.setOnMouseEntered(e -> FiltersCB.setCursor(Cursor.HAND));
		FiltersCB.setOnMouseExited(e -> FiltersCB.setCursor(Cursor.DEFAULT));
        
        int itemHeight = 60;
        
        VBoxFilters.setManaged(false); 
        VBoxFilters.setVisible(false);
        BarChart.setManaged(false); 
        BarChart.setVisible(false);
		HBoxButtons.setManaged(false); 
		HBoxButtons.setVisible(false);
		searchByFilter.setManaged(false);
		searchByFilter.setVisible(false);
		FiltersCB.getItems().addAll(Filters);
		
		
		
		
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
                                	searchSurveysField.clear();
                                	searchVBox.toFront();
                                	HBoxButtons.toBack();
                                	HBoxChartAndFilters.toBack();
                            		
                                	
                                	nCurrentQuestions = 0;	
                                	BarChart.getData().clear();
                                	survey = entry.getValue();
                                   	this.nQuestions = survey.getNQuestions();
                                    this.questionsHashMap = questionDao.getQuestionsBySurveyID(entry.getKey()); 
                                    this.sortedQuestionsTreeMAp = new TreeMap<>(this.questionsHashMap);
                                    this.questionsList = new ArrayList<>(this.sortedQuestionsTreeMAp.values());
                                    this.questionsIDList = new ArrayList<>(this.sortedQuestionsTreeMAp.keySet());
                                    searchByFilter.setManaged(true);
                              		searchResultsList.setManaged(false);
                               		searchResultsList.setVisible(false);
                               		HBoxButtons.setManaged(true); 
                               		HBoxButtons.setVisible(true);
                               		BarChart.setManaged(true); 
                               		BarChart.setVisible(true);
                               		VBoxFilters.setManaged(true); 
                               		VBoxFilters.setVisible(true);
                               		previousQuestionButton.setManaged(true);
                               		previousQuestionButton.setVisible(false);
                               		BarChart.setVisible(true);
                               		BarChart.setTitle("Pytanie nr." + (nCurrentQuestions + 1));
                               		xAxis.setLabel("Odpowiedzi");
                               		yAxis.setLabel("Liczba udzielonych odpowiedzi");
                              
                               		XYChart.Series<CategoryAxis, NumberAxis> data = new XYChart.Series<>();
                               		data.setName("Wszyskie dane");
                                	
                               	    TreeMap<Integer, Answer> answersTreemap = answerDao.getAnswerByQuestionID(questionsIDList.get(nCurrentQuestions));
                               	    List<Answer> answersList = new ArrayList<>(answersTreemap.values());
                               	    List<Integer> answersIDList = new ArrayList<>(answersTreemap.keySet());
                                	    
                               		nAnswers = answersList.size();
                               		for(int i = 0; i < nAnswers; i++) {
                               			Integer responseAmount = responseDao.getResponseCountForAnswer(answersIDList.get(i));
                               			data.getData().add(new XYChart.Data(String.valueOf(i + 1), responseAmount));
                               		}
                               		
                               		BarChart.getData().add(data);
                               		
                               		
                               		BarChart.setStyle("-fx-text-fill: #FF5733; -fx-font-size: 16px; -fx-font-weight: bold");
                               		BarChart.lookup(".chart-title").setStyle("-fx-font-weight: bold; -fx-text-fill: white;");//#FFA54B
                               		xAxis.lookup(".axis-label").setStyle("-fx-font-weight: bold; -fx-text-fill: #FF9D3B;");
                                    yAxis.lookup(".axis-label").setStyle("-fx-font-weight: bold; -fx-text-fill: #FFA54B;");
                                    FiltersCB.getSelectionModel().selectedItemProperty().addListener((CBObservable, CBOldValue, CBnewValue) -> {
                               		    if (CBnewValue.equals("Kraj")) {
                                   			searchByFilter.setVisible(true);
                                    			
                               		    } else if(CBnewValue.equals("Miasto")){
                                   			searchByFilter.setVisible(true);
                                    		
                               		    }else  if(CBnewValue.equals("Rok Urodzenia")){
                                   			searchByFilter.setVisible(true);
                                    		
                               		    }else {
                                   			searchByFilter.setVisible(false);
                               		    }
                               		});
                                    FiltersCB.setConverter(new StringConverter<String>(){
                       		    		@Override
										public String toString(String s) {
											return (s==null) ? "Filtry" : s;
										}
                       		    		@Override
		                       		      public String fromString(String string) {
		                       		          return null;
		                       		      }
                       		    	});
                                    QuestionInfoList.setItems(FXCollections.observableArrayList());
                                    ObservableList<String> dataInList = FXCollections.observableArrayList();
                                    dataInList.add("Tytuł ankiety: " + survey.getTitle());
                                    dataInList.add("Treść pytania: " + questionsList.get(nCurrentQuestions).getQuestionText());
                                    for(int i = 0; i < nAnswers; i++) {
                                    	dataInList.add("Odpowiedź nr. " + String.valueOf(i + 1) + " - "+ answersList.get(i).getAnswerText());
                                    }
                                    QuestionInfoList.setItems(dataInList);
                                    QuestionInfoList.setFocusTraversable(false);
                                    QuestionInfoList.getStyleClass().add("my-list-view");
                                    QuestionInfoList.setStyle("-fx-background-color: null;  -fx-blend-mode: SRC_OVER;");
                                    QuestionInfoList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                                        @Override
                                        public ListCell<String> call(ListView<String> param) {
                                            return new ListCell<String>() {
                                                @Override
                                                protected void updateItem(String item, boolean empty) {
                                                    super.updateItem(item, empty);
                                                    if (item == null || empty) {
                                                        setText(null);
                                                        setStyle("-fx-background-color: transparent;");
                                                    } else {
                                                        setText(item);
                                                        setStyle("-fx-background-color: transparent;");
                                                    }
                                                }
                                            };
                                        }
                                    });
                                        
                                    
                                   
                                    
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    @FXML
    private void submitButton() {
    	
    	
    	String FilterName = searchByFilter.getText();
    	BarChart.getData().clear();
    	BarChart.setTitle("Pytanie nr." + (nCurrentQuestions + 1));
		xAxis.setLabel("Odpowiedzi");
		yAxis.setLabel("Liczba udzielonych odpowiedzi");
		XYChart.Series<CategoryAxis, NumberAxis> data = new XYChart.Series<>();
		data.setName("Wszyskie dane");
		data.getData().clear();
		XYChart.Series<CategoryAxis, NumberAxis> dataCountry = new XYChart.Series<>();
    	dataCountry.setName("Filtr: " + FilterName);
		
    	XYChart.Series<CategoryAxis, NumberAxis> dataCity = new XYChart.Series<>();
		dataCity.setName("Filtr: " + FilterName);
		
		XYChart.Series<CategoryAxis, NumberAxis> dataBirthday = new XYChart.Series<>();
		dataBirthday.setName("Filtr: " + FilterName);

	    TreeMap<Integer, Answer> answersTreemap = answerDao.getAnswerByQuestionID(questionsIDList.get(nCurrentQuestions));
	    List<Answer> answersList = new ArrayList<>(answersTreemap.values());
	    List<Integer> answersIDList = new ArrayList<>(answersTreemap.keySet());
	    
		nAnswers = answersList.size();			
        String selectedValue = FiltersCB.getValue();
        if (selectedValue.equals("Kraj")) {
        		
        	usersMap = userDao.getUsersByCountry(FilterName);
    		Collection<User> usersFromMap = usersMap.values();
    		
		    for(int i = 0; i < nAnswers; i++) {
		    	
		    	Integer responseAmountCountries = 0;
		    	for(User user : usersFromMap) {
		    		responseAmountCountries += responseDao.getResponseCountForAnswerAndLogin(answersIDList.get(i), user.getLogin());
		    	}
		    	Integer responseAmount = responseDao.getResponseCountForAnswer(answersIDList.get(i));
     			data.getData().add(new XYChart.Data(String.valueOf(i + 1), responseAmount)); //answersList.get(i).getOrder()
		        dataCountry.getData().add(new XYChart.Data(String.valueOf(i + 1), responseAmountCountries));
		        
		    }
		    BarChart.getData().addAll(data, dataCountry);
		    
		}else if(selectedValue.equals("Miasto")) {

        	usersMap = userDao.getUsersByCity(FilterName);
    		Collection<User> usersFromMap = usersMap.values();
    		
			for(int i = 0; i < nAnswers; i++) {
					    	
		    	Integer responseAmountCities = 0;
		    	for(User user : usersFromMap) {
		    		responseAmountCities += responseDao.getResponseCountForAnswerAndLogin(answersIDList.get(i), user.getLogin());
		    	}
		    	Integer responseAmount = responseDao.getResponseCountForAnswer(answersIDList.get(i));
     			data.getData().add(new XYChart.Data(String.valueOf(i + 1), responseAmount));
		        dataCountry.getData().add(new XYChart.Data(String.valueOf(i + 1), responseAmountCities));
		        
		    }
		    BarChart.getData().addAll(data, dataCountry);
		    
		}else if(selectedValue.equals("Rok Urodzenia")) {

        	usersMap = userDao.getUsersByBirthYear(FilterName);
    		Collection<User> usersFromMap = usersMap.values();
    		
			for(int i = 0; i < nAnswers; i++) {
					    	
		    	Integer responseAmountBirthYear = 0;
		    	for(User user : usersFromMap) {
		    		responseAmountBirthYear += responseDao.getResponseCountForAnswerAndLogin(answersIDList.get(i), user.getLogin());
		    	}
		    	Integer responseAmount = responseDao.getResponseCountForAnswer(answersIDList.get(i));
     			data.getData().add(new XYChart.Data(String.valueOf(i + 1), responseAmount));
		        dataCountry.getData().add(new XYChart.Data(String.valueOf(i + 1), responseAmountBirthYear));
		        
		    }
		    BarChart.getData().addAll(data, dataCountry);
		    
		}else if(selectedValue.equals("Brak")) {
			for(int i = 0; i < nAnswers; i++) {
				
		    	Integer responseAmount = responseDao.getResponseCountForAnswer(answersIDList.get(i));
     			data.getData().add(new XYChart.Data(String.valueOf(i + 1), responseAmount));
		        
		    }
			 BarChart.getData().add(data);
		}
        QuestionInfoList.setItems(FXCollections.observableArrayList());
        ObservableList<String> dataInList = FXCollections.observableArrayList();
        dataInList.add("Tytuł ankiety: " + survey.getTitle());
        dataInList.add("Treść pytania: " + questionsList.get(nCurrentQuestions).getQuestionText());
        for(int i = 0; i < nAnswers; i++) {
        	dataInList.add("Odpowiedź nr. " + String.valueOf(i + 1) + " - "+ answersList.get(i).getAnswerText());
        }
        QuestionInfoList.setItems(dataInList);
    }
    //#FF5733
    @FXML
    private void nextQuestion() {
	
        
		nCurrentQuestions++;
        if(nCurrentQuestions + 1 == nQuestions) {
        	nextQuestionButton.setVisible(false);
        	nextQuestionButton.setDisable(true);
        } 
        previousQuestionButton.setVisible(true);
        previousQuestionButton.setDisable(false);
        
        BarChart.getData().clear();
        BarChart.setTitle("Pytanie nr." + (nCurrentQuestions + 1));
		xAxis.setLabel("Odpowiedzi");
		yAxis.setLabel("Liczba udzielonych odpowiedzi");
		XYChart.Series<CategoryAxis, NumberAxis> data = new XYChart.Series<>();
		data.setName("Wszyskie dane");
		data.getData().clear();
	    TreeMap<Integer, Answer> answersTreemap = answerDao.getAnswerByQuestionID(questionsIDList.get(nCurrentQuestions));
	    List<Answer> answersList = new ArrayList<>(answersTreemap.values());
	    List<Integer> answersIDList = new ArrayList<>(answersTreemap.keySet());
	    
		nAnswers = answersList.size();
		
		for(int i = 0; i < nAnswers; i++) {
			Integer responseAmount = responseDao.getResponseCountForAnswer(answersIDList.get(i));
			data.getData().add(new XYChart.Data(String.valueOf(i + 1), responseAmount));
			
		}
		BarChart.getData().add(data);
		
		 QuestionInfoList.setItems(FXCollections.observableArrayList());
         ObservableList<String> dataInList = FXCollections.observableArrayList();
         dataInList.add("Tytuł ankiety: " + survey.getTitle());
         dataInList.add("Treść pytania: " + questionsList.get(nCurrentQuestions).getQuestionText());
         for(int i = 0; i < nAnswers; i++) {
         	dataInList.add("Odpowiedź nr. " + String.valueOf(i + 1) + " - "+ answersList.get(i).getAnswerText());
         }
         QuestionInfoList.setItems(dataInList);
    }

    @FXML
    private void previousQuestion() {
        if (nCurrentQuestions > 1) {
            previousQuestionButton.setVisible(true);
            previousQuestionButton.setDisable(false);
        } else {
            previousQuestionButton.setVisible(false);
            previousQuestionButton.setDisable(true);
        }
		nCurrentQuestions--;
        nextQuestionButton.setVisible(true);
    	nextQuestionButton.setDisable(false);

    	
    	BarChart.getData().clear();
        BarChart.setTitle("Pytanie nr." + (nCurrentQuestions + 1));
		xAxis.setLabel("Odpowiedzi");
		yAxis.setLabel("Liczba udzielonych odpowiedzi");
		XYChart.Series<CategoryAxis, NumberAxis> data = new XYChart.Series<>();
		data.setName("Wszyskie dane");
		data.getData().clear();
	    TreeMap<Integer, Answer> answersTreemap = answerDao.getAnswerByQuestionID(questionsIDList.get(nCurrentQuestions));
	    List<Answer> answersList = new ArrayList<>(answersTreemap.values());
	    List<Integer> answersIDList = new ArrayList<>(answersTreemap.keySet());
	    
		nAnswers = answersList.size();
		for(int i = 0; i < nAnswers; i++) {
			Integer responseAmount = responseDao.getResponseCountForAnswer(answersIDList.get(i));
			data.getData().add(new XYChart.Data(String.valueOf(i + 1), responseAmount));
		}
		
		BarChart.getData().add(data);
        
		 QuestionInfoList.setItems(FXCollections.observableArrayList());
         ObservableList<String> dataInList = FXCollections.observableArrayList();
         dataInList.add("Tytuł ankiety: " + survey.getTitle());
         dataInList.add("Treść pytania: " + questionsList.get(nCurrentQuestions).getQuestionText());
         for(int i = 0; i < nAnswers; i++) {
         	dataInList.add("Odpowiedź nr. " + String.valueOf(i + 1) + " - "+ answersList.get(i).getAnswerText());
         }
         QuestionInfoList.setItems(dataInList);
		
    }
    
    public void setAdminLogin(String login) {
        this.adminLogin = login;
        adminLoginLabel.setText(login);
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

            Scene scene = new Scene(logout, 400, 420);
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
