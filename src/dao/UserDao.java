//UserDao.java: Klasa odpowiedzialna za operacje związane z dostępem do danych użytkowników w bazie danych.
// Może zawierać metody takie jak dodawanie nowego użytkownika, pobieranie informacji o użytkowniku
// na podstawie identyfikatora, aktualizowanie danych użytkownika, itp.

package dao;

import java.sql.*;
import model.User;
import util.DataBaseUtil;

public class UserDao {
	

 	private DataBaseUtil db;
 	private Connection connection;
 	private Statement statement;
 	private String query;

 	public UserDao()
 	{
 		this.db = new DataBaseUtil();
 	}
 
	public void save(User user) {
 		query = "INSERT INTO `users` (`login`, `password`, `admin`) VALUES ('" + user.getLogin() + "', '" + user.getPassword() + "', '" + 1 + "');";
 		try
 		{
 			this.connection = db.connect();
 			this.statement = connection.createStatement();
 			this.statement.executeUpdate(query);
            this.statement.close();
            this.connection.close();        
 		} 
 		catch (SQLException e)
 		{
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 	}
}

