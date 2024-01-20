package twojaOpinia.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import twojaOpinia.model.Answer;
import twojaOpinia.model.Question;
import twojaOpinia.util.DataBaseUtil;

public class AnswerDao implements InterfaceDAO<Answer, Integer>{
	@Override
	public Answer getByID(Integer id) {
		Answer answer = new Answer();

		String query = "SELECT * FROM answers WHERE id = ?";
		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				answer.setOrder(resultSet.getInt("answer_order"));
				answer.setAnswerText(resultSet.getString("answer_text"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return answer;
	}

	@Override
	public void insert(Answer input) {
		String query = "INSERT INTO `answers` (`question_id`, `answer_text`, `answer_order`) VALUES (?, ?, ?)";
		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
			preparedStatement.setInt(1, input.getQuestionID());
			preparedStatement.setString(2, input.getAnswerText());
			preparedStatement.setInt(3, input.getOrder());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			//TO_DO
			e.printStackTrace();
		}
	}

	public TreeMap<Integer, Answer> getAnswerByQuestionID(int questionID) {
		TreeMap<Integer, Answer> answers = new TreeMap<>();
		String query = "SELECT * FROM answers WHERE answers.question_id = ?";

		try (Connection connection = DataBaseUtil.connect(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, questionID);

			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Answer answer = new Answer();
				answer.setAnswerText(resultSet.getString("answer_text"));
				answer.setQuestionID(questionID);
				answer.setOrder(resultSet.getInt("answer_order"));

				answers.put(resultSet.getInt("id"), answer);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return answers;
	}
	@Override
	public void deleteByID(int id) {
		String query = "DELETE FROM answers WHERE question_id IN (SELECT id FROM questions WHERE survey_id = ?)";

		try (Connection connectiomn = DataBaseUtil.connect(); PreparedStatement preparedStatement = connectiomn.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Błąd podczas usuwania usuwania odpowiedzi");
			e.printStackTrace();
		}
	}
}
