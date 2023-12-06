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
        launch(args);

        SurveyDao surveyDao = new SurveyDao();
        UserDao userDao = new UserDao();

        //Testy 04.12
<<<<<<< HEAD
        /*
        String testLogin = "Kazio00000000000000F1scher";
        String testPassword = "Kazio";
        String salt = generateSalt();
        boolean testIsAdmin = true;
        
    	User user = new User(testLogin, testPassword, salt, testIsAdmin);
        userDao.insert(user);
        System.out.println("Użytkownik dodany pomyślnie");
        */
=======
>>>>>>> 493d50656834e391dfedf927a55046387d3a58b1

        /*
        System.out.println("Test aplikacji Twoja Opinia");

        String testLogin = "KazioF1scher";
        String testPassword = "Kazio";
        String salt = generateSalt();
        boolean testIsAdmin = true;

        User user = new User(testLogin, testPassword, salt, testIsAdmin);

        Survey ankieta = new Survey(user.getLogin(), "Test nr 3", "Wielki test dzialania DAO");
        
        ankieta.getQuestions().add(new Question(0, "Pytanie 1"));
        ankieta.getQuestions().get(0).getAnswers().add(new Answer(0, "Odpowiedz 1"));
        ankieta.getQuestions().get(0).getAnswers().add(new Answer(1, "Odpowiedz 2"));
        ankieta.getQuestions().add(new Question(1, "Pytanie 2"));
        ankieta.getQuestions().get(1).getAnswers().add(new Answer(0, "Odpowiedz 1"));
        ankieta.getQuestions().get(1).getAnswers().add(new Answer(1, "Odpowiedz 2"));

        surveyDao.insert(ankieta);
        */

        //Test 06.12.2023
        /*
        System.out.println(surveyDao.getByID(24));
        System.out.println();
        System.out.println(userDao.getByLogin("testUser1"));
        */
    }
}
