//UserDao.java: Klasa odpowiedzialna za operacje związane z dostępem do danych użytkowników w bazie danych.
// Może zawierać metody takie jak dodawanie nowego użytkownika, pobieranie informacji o użytkowniku
// na podstawie identyfikatora, aktualizowanie danych użytkownika, itp.

package twojaOpinia.dao;

import java.sql.*;
import twojaOpinia.model.User;
import twojaOpinia.util.DataBaseUtil;

public class UserDao {
	
 	private Connection connection;
 	private Statement statement;
 	private String query;

	public void saveUser(User user) {
 		query = "INSERT INTO `users` (`login`, `password`, `admin`) VALUES ('" + user.getLogin() + "', '" + user.getPassword() + "', '" + 1 + "');";
 		try
 		{
 			this.connection = DataBaseUtil.connect();
 			this.statement = connection.createStatement();
 			this.statement.executeUpdate(query);
            this.statement.close();
            this.connection.close();        
 		} 
 		catch (SQLException e)
 		{
 			// TODO Auto-generated catch block
 			System.out.println("Użytkownik o tym loginie już istnieje");
 		}
 	}
	
	public User findUser(String login)
	{
		User user = null;
		query = "SELECT * FROM `users` WHERE users.login = '" + login + "'";
 		try
 		{
 			this.connection = DataBaseUtil.connect();
 			this.statement = connection.createStatement();
 			
 			ResultSet result = this.statement.executeQuery(query);
 			
 			result.next();
 			user = new User(login, result.getString("password"), result.getBoolean("admin"));
 			
            this.statement.close();
            this.connection.close();        
 		} 
 		catch (SQLException e)
 		{
 			// TODO Auto-generated catch block
 			//e.printStackTrace();
 			System.out.println("Nie ma takiego uzytkownika");
 		}
 		return user;
		
	}
}

