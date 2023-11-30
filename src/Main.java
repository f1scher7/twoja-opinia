import java.util.Scanner;

import model.User;
import util.SHA256;
import dao.UserDao;

public class Main {
    public static void main(String[] args) {
        System.out.println("Test aplikacji Twoja Opinia");
        
        User user = new User("admin3", "admin", false);
        
        //Scanner scan = new Scanner(System.in);
        
//        String login = scan.next();
//        String password = scan.next();
//        
//        System.out.println(user.getPassword());
//        
//        if(user.getLogin().equals(login))
//        {
//        	SHA256 hash = new SHA256();
//        	String passwordSHA256 = hash.toSHA256(password);
//        	
//        	if(user.getPassword().equals(passwordSHA256))
//        	{
//        		System.out.println("Zalogowany pomyślnie");
//        	}
//        }
        
        UserDao userDao = new UserDao();
        userDao.save(user);
        
        //scan.close();
    }
}