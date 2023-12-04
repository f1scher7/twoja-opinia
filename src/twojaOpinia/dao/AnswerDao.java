package twojaOpinia.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import twojaOpinia.model.Answer;
import twojaOpinia.util.DataBaseUtil;

public class AnswerDao implements InterfaceDAO<Answer, Integer>{
	
	private Connection connection;
	private Statement statement;
	private String query;

	@Override
	public Answer getByID(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Answer input) {
		query = "INSERT INTO `answers` (`id`, `question_id`, `answer_text`, `answer_order`) VALUES (NULL, '" 
		+ input.getQuestionID() + "','" + input.getAnswerText() +  "','" + input.getOrder() + "') ";
		try {
			this.connection = DataBaseUtil.connect();
			this.statement = connection.createStatement();
			this.statement.executeUpdate(query);
			this.statement.close();
			this.connection.close();
			
		} catch (SQLException e) {
			//TO_DO
			e.printStackTrace();
		}
	}

}
