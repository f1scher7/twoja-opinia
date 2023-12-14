package twojaOpinia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
			preparedStatement.setString(3, input.getUser().getLogin());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			//TO_DO
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id) {

	}
}
