package twojaOpinia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import twojaOpinia.model.Question;
import twojaOpinia.util.DataBaseUtil;

public class QuestionDao implements InterfaceDAO<Question, Integer>{
	@Override
	public Question getByID(Integer id) {
		return null;
	}

	@Override
	public void insert(Question input) {
		String query = "INSERT INTO `questions` (`id`, `survey_id`, `question_text`, `question_order`) VALUES (NULL, '" +
				input.getSurveyID() + "','" + input.getQuestionText() + "','" + input.getOrder() + "') ";
		try {
			Connection connection = DataBaseUtil.connect();
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);

			query = "SELECT id FROM `questions` ORDER BY id DESC LIMIT 1";
			ResultSet result = statement.executeQuery(query);
			result.next();
			
			int id = result.getInt("id");
			
			AnswerDao aDao = new AnswerDao();
			
			for(int i = 0; i < input.getAnswers().size(); i++) {
				input.getAnswers().elementAt(i).setQuestionID(id);
				aDao.insert(input.getAnswers().elementAt(i));
			}
			statement.close();
			connection.close();

		} catch (SQLException e) {
			//TO_DO
			e.printStackTrace();
		}
		
	}

}
