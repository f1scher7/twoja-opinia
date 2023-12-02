package twojaOpinia;

import twojaOpinia.dao.UserDao;
import twojaOpinia.model.User;
import twojaOpinia.util.SHA256;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Scanner;
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


        //Testy 31.11.23
        System.out.println("Test aplikacji Twoja Opinia");
        
        User user = new User("admin3", "admin", false);
        
        Scanner scan = new Scanner(System.in);
        
        String login = scan.next();
        String password = scan.next();
        
        if(user.getLogin().equals(login))
        {

        	String passwordSHA256 = SHA256.toSHA256(password);
        	if(user.getPassword().equals(password))
        	{
        		System.out.println("Zalogowany pomyślnie");
        	}
        }
        
        UserDao userDao = new UserDao();
        userDao.saveUser(user);
        
        User user2 = userDao.findUser("admin");
        System.out.println(user2.getPassword());
        
        scan.close();
    }
}
