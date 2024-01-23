package twojaOpinia.dao;

import twojaOpinia.model.User;
import twojaOpinia.util.DataBaseUtil;
import static twojaOpinia.util.SaltUtil.generateSalt;
import twojaOpinia.util.SHA256;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class UserDao implements InterfaceDAO<User, String>{
	private ResponseDao responseDao = new ResponseDao();

	@Override
	public User getByID(String id) {
		return null;
	}

	@Override
	public void insert(User user) {
		String salt = generateSalt();
		user.setSalt(salt);
		String hashedPassword = SHA256.toSHA256(user.getPassword() + salt);
		String query = "INSERT INTO `users` (`name`, `surname`, `sex`, `email`, `birthday`, `country`, `city`, `login`, `password`, `salt`, `admin`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getSurname());
			preparedStatement.setString(3, user.getSex());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getBirthday());
			preparedStatement.setString(6, user.getCountry());
			preparedStatement.setString(7, user.getCity());
			preparedStatement.setString(8, user.getLogin());
			preparedStatement.setString(9, hashedPassword);
			preparedStatement.setString(10, salt);
			preparedStatement.setBoolean(11, user.isAdmin());
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
					user = new User(resultSet.getString("name"), resultSet.getString("surname"), resultSet.getString("sex"), resultSet.getString("email"), resultSet.getString("birthday"), resultSet.getString("country"), resultSet.getString("city"), login, resultSet.getString("password"), resultSet.getString("salt"), resultSet.getBoolean("admin"));
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


	public Map<String, User> getUsersByCountry(String country) {
        if (country == null || country.isEmpty()) {
            System.out.println("Niepoprawny kraj");
            return null;
        }

        Map<String, User> usersMap = new HashMap<>();
        String query = "SELECT * FROM `users` WHERE `country` = ? AND `admin` = ?";
        try (Connection connection = DataBaseUtil.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, country);
            preparedStatement.setBoolean(2, false);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User(
                            resultSet.getString("name"),
                            resultSet.getString("surname"),
                            resultSet.getString("sex"),
                            resultSet.getString("email"),
                            resultSet.getString("birthday"),
                            resultSet.getString("country"),
                            resultSet.getString("city"),
                            resultSet.getString("login"),
                            resultSet.getString("password"),
                            resultSet.getString("salt"),
                            resultSet.getBoolean("admin")
                    );
                    usersMap.put(user.getLogin(), user);
                }
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas pobierania użytkowników: " + e.getMessage());
            e.printStackTrace();
        }

        return usersMap;
    }
	
	public Map<String, User> getUsersByCity(String city) {
        if (city == null || city.isEmpty()) {
            System.out.println("Niepoprawne miasto");
            return null;
        }

        Map<String, User> usersMap = new HashMap<>();
        String query = "SELECT * FROM `users` WHERE `city` = ? AND `admin` = ?";
        try (Connection connection = DataBaseUtil.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, city);
            preparedStatement.setBoolean(2, false);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User(
                            resultSet.getString("name"),
                            resultSet.getString("surname"),
                            resultSet.getString("sex"),
                            resultSet.getString("email"),
                            resultSet.getString("birthday"),
                            resultSet.getString("country"),
                            resultSet.getString("city"),
                            resultSet.getString("login"),
                            resultSet.getString("password"),
                            resultSet.getString("salt"),
                            resultSet.getBoolean("admin")
                    );
                    usersMap.put(user.getLogin(), user);
                }
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas pobierania użytkowników: " + e.getMessage());
            e.printStackTrace();
        }

        return usersMap;
    }
    
    public Map<String, User> getUsersByBirthYear(String birthYear) {
        if (birthYear == null || birthYear.isEmpty()) {
            System.out.println("Niepoprawny rok urodzenia");
            return null;
        }

        Map<String, User> usersMap = new HashMap<>();
        String query = "SELECT * FROM `users` WHERE SUBSTRING_INDEX(`birthday`, '.', -1) = ? AND `admin` = ?";
        try (Connection connection = DataBaseUtil.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, birthYear);
            preparedStatement.setBoolean(2, false);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User(
                            resultSet.getString("name"),
                            resultSet.getString("surname"),
                            resultSet.getString("sex"),
                            resultSet.getString("email"),
                            resultSet.getString("birthday"),
                            resultSet.getString("country"),
                            resultSet.getString("city"),
                            resultSet.getString("login"),
                            resultSet.getString("password"),
                            resultSet.getString("salt"),
                            resultSet.getBoolean("admin")
                    );
                    usersMap.put(user.getLogin(), user);
                }
            }
        } catch (SQLException e) {
            System.out.println("Błąd podczas pobierania użytkowników: " + e.getMessage());
            e.printStackTrace();
        }

        return usersMap;
    }
	

	public boolean isUserDataTaken(String dataType, String value) {
		String query = "SELECT COUNT(*) > 0 FROM users WHERE " + dataType + "= ?";
		boolean res;
		try(Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, value);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getBoolean(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void changeUserData(String dataType, String value, String login) {
		String query = "UPDATE users SET " + dataType + " = ? WHERE login = ?";
		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, value);
			preparedStatement.setString(2, login);
			preparedStatement.executeUpdate();
			if(dataType.equals("login")) {
				responseDao.changeUserLogin(value, login);
			}
		} catch (SQLException e) {
			System.out.println("Błąd podczas zmiany danych użytkownika: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void changeUserPassword(String newPassword, String login) {
		String salt = generateSalt();
		String password = SHA256.toSHA256(newPassword + salt);
		String query = "UPDATE users SET password = ?, salt = ? WHERE login = ?";
		try(Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, password);
			preparedStatement.setString(2, salt);
			preparedStatement.setString(3, login);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Błąd podczas zmiany hasła użytkownika: " + e.getMessage());
			e.printStackTrace();
		}
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
