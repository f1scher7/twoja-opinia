import java.util.Scanner;

import model.User;
import util.SHA256;
import dao.UserDao;

public class Main {
    public static void main(String[] args) {
    	
    	//Testy 31.11.23
    	
        System.out.println("Test aplikacji Twoja Opinia");
        
        User user = new User("admin3", "admin", false);
        
        Scanner scan = new Scanner(System.in);
        
        String login = scan.next();
        String password = scan.next();
        
        if(user.getLogin().equals(login))
        {

        	String passwordSHA256 = SHA256.toSHA256(password);
        	
        	if(user.getPassword().equals(passwordSHA256))
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
