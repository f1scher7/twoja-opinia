package twojaOpinia.dao;

import java.sql.*;
import java.util.*;

import javafx.scene.chart.PieChart;
import twojaOpinia.model.Answer;
import twojaOpinia.model.Response;
import twojaOpinia.util.DataBaseUtil;

public class ResponseDao implements InterfaceDAO<Response, Integer>{
	@Override
	public Response getByID(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Response input) {
		String query = "INSERT INTO `responses` (`answer_id`, `survey_id`, `login`) VALUES (?, ?, ?)";
		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
			preparedStatement.setInt(1, input.getAnswerID());
			preparedStatement.setInt(2, input.getSurveyID());
			preparedStatement.setString(3, input.getUserLogin());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			//TO_DO
			e.printStackTrace();
		}
	}

	public int getResponseCount() {
		int res = 0;
		String query = "SELECT COUNT(*) AS responsescount FROM `responses`";
		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					res = resultSet.getInt("responsescount");
				}
			}
		} catch (SQLException e) {
			System.out.println("Błąd podczas pobierania liczby odpowiedzi: " + e.getMessage());
			e.printStackTrace();
		}
		return res;
	}


	public List<Response> getAllResponsesByLogin(String login) {
		List<Response> responses = new ArrayList<>();
		String query = "SELECT * FROM responses WHERE login = ?";
		try(Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, login);

			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Response response = new Response();
				response.setUserLogin(login);
				response.setAnswerID(resultSet.getInt("answer_id"));
				response.setSurveyID(resultSet.getInt("survey_id"));

				responses.add(response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return responses;
	}

	public List<Integer> getAnswersIDsBuySurveyID(Integer surveyId) {
		List<Integer> answersIDs = new ArrayList<>();

		String query = "SELECT * FROM responses WHERE survey_id = ?";
		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, surveyId);

			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				int id = resultSet.getInt("answer_id");
				answersIDs.add(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return answersIDs;
	}

	public void changeUserLogin(String newLogin, String oldLogin) {
		String query = "UPDATE responses SET login = ? WHERE login = ?";
		try(Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, newLogin);
			preparedStatement.setString(2, oldLogin);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Błąd podczas zmiany loginu użytkownika: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public int getResponseCountForAnswer(int answerId) {
		
		if (answerId <= 0) {
	        System.out.println("Niepoprawne answerId");
	        return -1;
	    }
		
	    int res = 0;
	    String query = "SELECT COUNT(*) AS responsescount FROM `responses` WHERE answer_id = ?";
	    try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, answerId);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                res = resultSet.getInt("responsescount");
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Błąd podczas pobierania liczby odpowiedzi: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return res;
	}
	
	public int getResponseCountForAnswerAndLogin(int answerId, String login) {
	    if (answerId <= 0 || login == null || login.isEmpty()) {
	        System.out.println("Niepoprawne argumenty");
	        return -1;
	    }

	    int res = 0;
	    String query = "SELECT COUNT(*) AS responsescount FROM `responses` " +
	                   "JOIN `users` ON `responses`.`login` = `users`.`login` " +
	                   "WHERE `responses`.`answer_id` = ? AND `responses`.`login` = ?";
	    
	    try (Connection connection = DataBaseUtil.connect();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, answerId);
	        preparedStatement.setString(2, login);

	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                res = resultSet.getInt("responsescount");
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Błąd podczas pobierania liczby odpowiedzi: " + e.getMessage());
	        e.printStackTrace();
	    }

	    return res;
	}

	public Map<Integer, Integer> getSixMostPopularSurveysID() {
		Map<Integer, Integer> surveysIDs = new HashMap<>();
		String query = "SELECT survey_id, COUNT(DISTINCT login) as count FROM responses GROUP BY survey_id ORDER BY count DESC LIMIT 6;";
		try(Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				surveysIDs.put(resultSet.getInt("survey_id"), resultSet.getInt("count"));
			}
		} catch (SQLException e) {
			System.out.println("Błąd podczas pobierania ID ankiet " + e.getMessage());
			e.printStackTrace();
		}

		List<Map.Entry<Integer, Integer>> list = new ArrayList<>(surveysIDs.entrySet());
		list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

		Map<Integer, Integer> sortedSurveysIDs = new LinkedHashMap<>();
		for (Map.Entry<Integer, Integer> entry : list) {
			sortedSurveysIDs.put(entry.getKey(), entry.getValue());
		}

		return sortedSurveysIDs;
	}

	@Override
	public void deleteByID(int id) {}
}
