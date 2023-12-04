package twojaOpinia;

import twojaOpinia.dao.UserDao;
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
        //Test 04.12.23
        launch(args);
        /*
        System.out.println("Test aplikacji Twoja Opinia");

        String testLogin = "KazioF1scher";
        String testPassword = "Kazio";
        String salt = generateSalt();
        boolean testIsAdmin = true;

        User user = new User(testLogin, testPassword, salt, testIsAdmin);

        UserDao userDao = new UserDao();
        userDao.saveUser(user);
        System.out.println("Użytkownik dodany pomyślnie");
        */
    }
}
