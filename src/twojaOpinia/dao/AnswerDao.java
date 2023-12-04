package twojaOpinia.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import twojaOpinia.model.Answer;
import twojaOpinia.util.DataBaseUtil;

public class AnswerDao implements InterfaceDAO<Answer, Integer>{
	@Override
	public Answer getByID(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Answer input) {
		String query = "INSERT INTO `answers` (`id`, `question_id`, `answer_text`, `answer_order`) VALUES (NULL, '"
				+ input.getQuestionID() + "','" + input.getAnswerText() + "','" + input.getOrder() + "') ";
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
