package twojaOpinia.dao;

import twojaOpinia.model.User;
import twojaOpinia.util.DataBaseUtil;
import static twojaOpinia.util.SaltUtil.generateSalt;
import twojaOpinia.util.SHA256;

import java.sql.*;

public class UserDao implements InterfaceDAO<User, String>{
	@Override
	public User getByID(String id) {
		return null;
	}

	@Override
	public void insert(User user) {
		String salt = generateSalt();
		user.setSalt(salt);
		String hashedPassword = SHA256.toSHA256(user.getPassword() + salt);
		String query = "INSERT INTO `users` (`login`, `password`, `salt`, `admin`) VALUES (?, ?, ?, ?)";
		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
			preparedStatement.setString(1, user.getLogin());
			preparedStatement.setString(2, hashedPassword);
			preparedStatement.setString(3, salt);
			preparedStatement.setBoolean(4, user.isAdmin());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Użytkownik o tym loginie już istnieje");
		}
	}

	public User getByLogin(String login) {
		User user = null;
		String query = "SELECT * FROM `users` WHERE users.login = ?";
		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, login);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					user = new User(login, resultSet.getString("password"), resultSet.getString("salt"), resultSet.getBoolean("admin"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Nie ma takiego użytkownika");
		}
		return user;
	}
}
