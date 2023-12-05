package twojaOpinia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
		String query = "INSERT INTO `answers` (`question_id`, `answer_text`, `answer_order`) VALUES (?, ?, ?)";
		try (Connection connection = DataBaseUtil.connect()){
			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, input.getQuestionID());
			preparedStatement.setString(2, input.getAnswerText());
			preparedStatement.setInt(3, input.getOrder());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			//TO_DO
			e.printStackTrace();
		}
	}
}
