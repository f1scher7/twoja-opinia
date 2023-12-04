package twojaOpinia.dao;

import java.sql.Connection;
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
		String query = "INSERT INTO `responses` (`id`, `answer_id`, `survey_id`, `login`) VALUES (NULL, '" +
				input.getAnswerID() + "','" + input.getSurveyID() + "','+ " + input.getUser().getLogin() + "');";
		try {
			Connection connection = DataBaseUtil.connect();
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			//TO_DO
			e.printStackTrace();
		}
		
	}

}
