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
		
		return null;

	}

	@Override
	public void insert(Survey input) {

		String query = "INSERT INTO `surveys`(`author`, `title`, `description`) VALUES (?, ?, ?)";
		try (Connection connection = DataBaseUtil.connect()) {
			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, input.getAuthor().getLogin());
			preparedStatement.setString(2, input.getTitle());
			preparedStatement.setString(3, input.getDescription());
			preparedStatement.executeUpdate();

			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				int id = resultSet.getInt(1);
				QuestionDao questionDao = new QuestionDao();
				for (int i = 0; i < input.getQuestions().size(); i++) {
					input.getQuestions().elementAt(i).setSurveyID(id);
					questionDao.insert(input.getQuestions().elementAt(i));
				}
			}
		} catch (SQLException e) {
			//TO_DO
			e.printStackTrace();
		}
	}
}
