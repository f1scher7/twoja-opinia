//SurveyDao.java: Klasa odpowiadająca za operacje na danych dotyczących ankiet w bazie danych.
// Wśród metod mogą być takie jak dodawanie nowej ankiety, pobieranie listy ankiet,
// pobieranie szczegółów ankiety na podstawie identyfikatora, itp.

package twojaOpinia.dao;

import java.sql.*;

import twojaOpinia.model.Survey;
import twojaOpinia.util.DataBaseUtil;
import twojaOpinia.util.SHA256;

public class SurveyDao implements InterfaceDAO<Survey, Integer>{
	@Override
	public Survey getByID(Integer id) {
		Survey survey = null;
		String query = "SELECT * FROM `surveys` WHERE surveys.id = ?";
		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					survey = new Survey(resultSet.getString("author"), resultSet.getString("title"), resultSet.getString("description"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return survey;
	}

	@Override
	public void insert(Survey input) {

		String query = "INSERT INTO `surveys`(`author`, `title`, `description`) VALUES (?, ?, ?)";
		try (Connection connection = DataBaseUtil.connect()) {
			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, input.getAuthorLogin());
			preparedStatement.setString(2, input.getTitle());
			preparedStatement.setString(3, input.getDescription());
			preparedStatement.executeUpdate();

			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				int id = resultSet.getInt(1);
				QuestionDao questionDao = new QuestionDao();
				for (int i = 0; i < input.getQuestions().size(); i++) {
					input.getQuestions().get(i).setSurveyID(id);
					questionDao.insert(input.getQuestions().get(i));
				}
			}
		} catch (SQLException e) {
			//TO_DO
			e.printStackTrace();
		}
	}
	public int getSurveyCount() {
		int res = 0;
		String query = "SELECT COUNT(*) AS surveycount FROM `surveys`";
		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					res = resultSet.getInt("surveycount");
				}
			}
		} catch (SQLException e) {
			System.out.println("Błąd podczas pobierania liczby ankiet: " + e.getMessage());
			e.printStackTrace();
		}
		return res;
	}
}
