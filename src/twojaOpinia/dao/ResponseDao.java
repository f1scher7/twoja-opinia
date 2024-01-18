package twojaOpinia.dao;

import java.sql.*;

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
	
	@Override
	public void deleteByID(int id) {

	}
}
