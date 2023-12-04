package twojaOpinia.dao;

import twojaOpinia.model.User;
import twojaOpinia.util.DataBaseUtil;
import static twojaOpinia.util.SaltUtil.generateSalt;
import twojaOpinia.util.SHA256;

import java.sql.*;

public class UserDao implements InterfaceDAO<User, String>{

	private Connection connection;
	private Statement statement;
	private String query;

	public void insert(User user) {
		String salt = generateSalt();
		user.setSalt(salt);
		String hashedPassword = SHA256.toSHA256(user.getPassword() + salt);
		query = "INSERT INTO `users` (`login`, `password`, `salt`, `admin`) VALUES ('" + user.getLogin() + "', '" + hashedPassword + "', '" + salt + "', '" + (user.isAdmin() ? 1 : 0) + "');";
		try {
			this.connection = DataBaseUtil.connect();
			this.statement = connection.createStatement();
			this.statement.executeUpdate(query);
			this.statement.close();
			this.connection.close();
		} catch (SQLException e) {
			System.out.println("Użytkownik o tym loginie już istnieje");
		}
	}

	public User getByID(String login) {
		User user = null;
		query = "SELECT * FROM `users` WHERE users.login = '" + login + "'";
		try {
			this.connection = DataBaseUtil.connect();
			this.statement = connection.createStatement();
			ResultSet result = this.statement.executeQuery(query);
			result.next();
			user = new User(login, result.getString("password"), result.getString("salt"), result.getBoolean("admin"));
			this.statement.close();
			this.connection.close();
		} catch (SQLException e) {
			System.out.println("Nie ma takiego użytkownika");
		}
		return user;
	}
}
