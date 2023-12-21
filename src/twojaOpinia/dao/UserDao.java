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
		String query = "INSERT INTO `users` (`name`, `surname`, `email`, `birthday`, `country`, `city`, `login`, `password`, `salt`, `admin`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getSurname());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getBirthday());
			preparedStatement.setString(5, user.getCountry());
			preparedStatement.setString(6, user.getCity());
			preparedStatement.setString(7, user.getLogin());
			preparedStatement.setString(8, hashedPassword);
			preparedStatement.setString(9, salt);
			preparedStatement.setBoolean(10, user.isAdmin());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Użytkownik o tym loginie już istnieje");
		}
	}

	public User getUserDataByLogin(String login) {
		User user = null;
		String query = "SELECT * FROM `users` WHERE users.login = ?";
		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, login);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					user = new User(resultSet.getString("name"), resultSet.getString("surname"), resultSet.getString("email"), resultSet.getString("birthday"), resultSet.getString("country"), resultSet.getString("city"), login, resultSet.getString("password"), resultSet.getString("salt"), resultSet.getBoolean("admin"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Nie ma takiego użytkownika");
		}
		return user;
	}

	public int getUserCount() {
		int res = 0;
		String query = "SELECT COUNT(*) AS usercount FROM `users` WHERE users.admin = 0";
		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					res = resultSet.getInt("usercount");
				}
			}
		} catch (SQLException e) {
			System.out.println("Błąd podczas pobierania liczby użytkowników: " + e.getMessage());
			e.printStackTrace();
		}
		return res;
	}

	public int deleteUserByLogin(String userLogin) {
		String query = "DELETE FROM users WHERE login = ?";
		int affectedRows = 0;
		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
			preparedStatement.setString(1, userLogin);
			affectedRows = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Błąd podczas usuwania użytkownika");
			e.printStackTrace();
		}
		return affectedRows;
	}

	@Override
	public void deleteByID(int id) {}
}
