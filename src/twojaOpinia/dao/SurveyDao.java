//SurveyDao.java: Klasa odpowiadająca za operacje na danych dotyczących ankiet w bazie danych.
// Wśród metod mogą być takie jak dodawanie nowej ankiety, pobieranie listy ankiet,
// pobieranie szczegółów ankiety na podstawie identyfikatora, itp.

package twojaOpinia.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import javafx.scene.chart.PieChart;
import twojaOpinia.model.Survey;
import twojaOpinia.util.DataBaseUtil;

public class SurveyDao implements InterfaceDAO<Survey, Integer>{
	@Override
	public Survey getByID(Integer id) {
		Survey survey = null;
		String query = "SELECT * FROM `surveys` WHERE surveys.id = ?";
		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					survey = new Survey(resultSet.getString("author"), resultSet.getString("title"), resultSet.getString("description"), resultSet.getString("tags"), resultSet.getInt("nquestions"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return survey;
	}

	@Override
	public void insert(Survey input) {
		String query = "INSERT INTO `surveys`(`author`, `title`, `description`, `dateAdded`, `tags`, `nquestions`) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection connection = DataBaseUtil.connect()) {
			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, input.getAuthorLogin());
			preparedStatement.setString(2, input.getTitle());
			preparedStatement.setString(3, input.getDescription());
			preparedStatement.setObject(4, input.getSurveyAddedDate());
			preparedStatement.setString(5, input.getTags());
			preparedStatement.setInt(6, input.getNQuestions());
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

	public int deleteSurveyByID(int id) {
		String query = "DELETE FROM surveys WHERE id = ?";
		int affectedRows = 0;
		try (Connection connection  = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			AnswerDao answerDao = new AnswerDao();
			QuestionDao questionDao = new QuestionDao();
			answerDao.deleteByID(id);
			questionDao.deleteByID(id);

			preparedStatement.setInt(1, id);
			affectedRows = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Błąd podczas usuwania ankiety");
			e.printStackTrace();
		}
		return affectedRows;
	}

	public TreeMap<Integer, Survey> getSixLastAddedSurveys() {
		TreeMap<Integer, Survey> sixLastAddedSurveys = new TreeMap<>();
		String query = "SELECT * FROM SURVEYS ORDER BY id DESC LIMIT 6";
		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Survey survey = new Survey();
				survey.setAuthorLogin(resultSet.getString("author"));
				survey.setTitle(resultSet.getString("title"));
				survey.setDescription(resultSet.getString("description"));
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDateTime dateAdded = LocalDateTime.parse(resultSet.getString("dateAdded"), formatter);
				survey.setSurveyAddedDate(dateAdded);
				survey.setNQuestions(resultSet.getInt("nquestions"));
				sixLastAddedSurveys.put(resultSet.getInt("id"), survey);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sixLastAddedSurveys;
	}

	public HashMap<Integer, Survey> searchSurveys(String input) {
		HashMap<Integer, Survey> matchingSurveys = new HashMap<>();
		String query = "SELECT * FROM surveys WHERE title LIKE ? OR description LIKE ? or tags LIKE ?";
		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, "%" + input + "%");
			preparedStatement.setString(2, "%" + input + "%");
			preparedStatement.setString(3, "%" + input + "%");

			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Survey survey = new Survey();
				survey.setAuthorLogin(resultSet.getString("author"));
				survey.setTitle(resultSet.getString("title"));
				survey.setDescription(resultSet.getString("description"));
				survey.setTags(resultSet.getString("tags"));
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDateTime dateAdded = LocalDateTime.parse(resultSet.getString("dateAdded"), formatter);
				survey.setSurveyAddedDate(dateAdded);
				survey.setNQuestions(resultSet.getInt("nquestions"));
				matchingSurveys.put(resultSet.getInt("id"), survey);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return matchingSurveys;
	}

	public List<Survey> getAllSurveysCreatedByAdmin(String login) {
		List<Survey> surveys = new ArrayList<>();
		String query = "SELECT * FROM surveys WHERE author = ?";
		try(Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, login);

			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Survey survey = new Survey();
				survey.setTitle(resultSet.getString("title"));
				survey.setAuthorLogin(resultSet.getString("author"));
				survey.setDescription(resultSet.getString("description"));
				survey.setTags(resultSet.getString("tags"));
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDateTime dateAdded = LocalDateTime.parse(resultSet.getString("dateAdded"), formatter);
				survey.setSurveyAddedDate(dateAdded);
				survey.setNQuestions(resultSet.getInt("nquestions"));

				surveys.add(survey);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return surveys;
	}

	@Override
	public void deleteByID(int id) {}
}
