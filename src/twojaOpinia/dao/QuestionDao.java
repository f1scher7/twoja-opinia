package twojaOpinia.dao;

import java.sql.*;

import twojaOpinia.model.Question;
import twojaOpinia.util.DataBaseUtil;

public class QuestionDao implements InterfaceDAO<Question, Integer>{
	@Override
	public Question getByID(Integer id) {
		return null;
	}

	@Override
	public void insert(Question input) {
		String query = "INSERT INTO `questions` (`survey_id`, `question_text`, `question_order`) VALUES (?, ?, ?)";
		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setInt(1, input.getSurveyID());
			preparedStatement.setString(2, input.getQuestionText());
			preparedStatement.setInt(3, input.getOrder());
			preparedStatement.executeUpdate();

			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

			if(generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				AnswerDao answerDao = new AnswerDao();
				for(int i = 0; i < input.getAnswers().size(); i++) {
					input.getAnswers().get(i).setQuestionID(id);
					answerDao.insert(input.getAnswers().get(i));
				}
			}
		} catch (SQLException e) {
			//TO_DO
			e.printStackTrace();
		}
	}

	@Override
	public void deleteByID(int id) {
		String query = "DELETE FROM questions WHERE survey_id = ?";
		try(Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Błąd podczas usuwania pytania");
			e.printStackTrace();
		}
	}
}
