package twojaOpinia.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

	@Override
	public void deleteByID(int id) {}
}
