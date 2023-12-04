//SurveyDao.java: Klasa odpowiadająca za operacje na danych dotyczących ankiet w bazie danych.
// Wśród metod mogą być takie jak dodawanie nowej ankiety, pobieranie listy ankiet,
// pobieranie szczegółów ankiety na podstawie identyfikatora, itp.

package twojaOpinia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import twojaOpinia.model.Survey;
import twojaOpinia.util.DataBaseUtil;
import twojaOpinia.util.SHA256;

public class SurveyDao implements InterfaceDAO<Survey, Integer>{

	private Connection connection;
	private Statement statement;
	private String query;
	
	@Override
	public Survey getByID(Integer id) {
		
		return null;

	}

	@Override
	public void insert(Survey input) {
		
		query = "INSERT INTO `surveys`(`id`, `author`, `title`, `description`) VALUES (null, + '" +
				input.getAuthor().getLogin() + "','" + input.getTitle() + "','" + input.getDescription() + "')";
		try {
			this.connection = DataBaseUtil.connect();
			this.statement = connection.createStatement();
			this.statement.executeUpdate(query);

			query = "SELECT id FROM `surveys` ORDER BY id DESC LIMIT 1";
			ResultSet result = this.statement.executeQuery(query);
			result.next();
			
			int id = result.getInt("id");
			
			QuestionDao qDao = new QuestionDao();
			
			for(int i = 0; i < input.getQuestions().size(); i++)
			{
				input.getQuestions().elementAt(i).setSurveyID(id);
				qDao.insert(input.getQuestions().elementAt(i));
			}
			
			this.statement.close();
			this.connection.close();
			
		} catch (SQLException e) {
			//TO_DO
			e.printStackTrace();
		}
		
	}

}
