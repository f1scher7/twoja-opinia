package twojaOpinia.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import twojaOpinia.model.Answer;
import twojaOpinia.model.Question;
import twojaOpinia.util.DataBaseUtil;

public class QuestionDao implements InterfaceDAO<Question, Integer>{
	@Override
	public Question getByID(Integer id) {
		Question question = new Question();

		String query = "SELECT * FROM questions WHERE id = ?";
		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				question.setOrder(resultSet.getInt("question_order"));
				question.setQuestionText(resultSet.getString("question_text"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return question;
	}

	public List<Question> getQuestionsBySurveyIDWithoutID(Integer surveyId) {
		List<Question> questions = new ArrayList<>();

		String query = "SELECT * FROM questions WHERE survey_id = ?";
		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, surveyId);

			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Question question = new Question();
				question.setOrder(resultSet.getInt("question_order"));
				question.setQuestionText(resultSet.getString("question_text"));

				questions.add(question);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questions;
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

	public HashMap<Integer, Question> getQuestionsBySurveyID(int surveyID) {
		HashMap<Integer, Question> questions = new HashMap<>();

		String query = "SELECT * FROM questions WHERE questions.survey_id = ?";
		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, surveyID);

			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Question question = new Question();
				question.setQuestionText(resultSet.getString("question_text"));
				question.setSurveyID(surveyID);
				question.setOrder(resultSet.getInt("question_order"));

				questions.put(resultSet.getInt("id"), question);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questions;
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
