package twojaOpinia;

import twojaOpinia.dao.SurveyDao;
import twojaOpinia.dao.UserDao;
import twojaOpinia.model.Answer;
import twojaOpinia.model.Question;
import twojaOpinia.model.Survey;
import twojaOpinia.model.User;
import static twojaOpinia.util.SaltUtil.generateSalt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/login/LoginView.fxml")));
        primaryStage.setTitle("TwojaOpinia");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        //launch(args);


        //Testy 04.12
    	/*
        String testLogin = "KazioF1scher";
        String testPassword = "Kazio";
        String salt = generateSalt();
        boolean testIsAdmin = true;
        
    	User user = new User(testLogin, testPassword, salt, testIsAdmin);
    	UserDao userDao = new UserDao();
        userDao.insert(user);
        System.out.println("Użytkownik dodany pomyślnie");
    	*/

        System.out.println("Test aplikacji Twoja Opinia");

        String testLogin = "KazioF1scher";
        String testPassword = "Kazio";
        String salt = generateSalt();
        boolean testIsAdmin = true;

        User user = new User(testLogin, testPassword, salt, testIsAdmin);

        Survey ankieta = new Survey(user, "Test nr 2", "Wielki test dzialania DAO");
        
        ankieta.getQuestions().add(new Question(0, "Pytanie 1"));
        ankieta.getQuestions().elementAt(0).getAnswers().add(new Answer(0, "Odpowiedz 1"));
        ankieta.getQuestions().elementAt(0).getAnswers().add(new Answer(1, "Odpowiedz 2"));
        ankieta.getQuestions().add(new Question(1, "Pytanie 2"));
        ankieta.getQuestions().elementAt(1).getAnswers().add(new Answer(0, "Odpowiedz 1"));
        ankieta.getQuestions().elementAt(1).getAnswers().add(new Answer(1, "Odpowiedz 2"));
        
        
        SurveyDao surveyDao = new SurveyDao();
        
        surveyDao.insert(ankieta);
    }
}
