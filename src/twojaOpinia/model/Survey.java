//Survey.java: Klasa reprezentująca ankietę. Może zawierać informacje na temat pytania,
// opcji odpowiedzi, daty rozpoczęcia i zakończenia ankiety, itp

package twojaOpinia.model;

import java.util.Vector;

public class Survey {
	
	private User author;
	private String title;
	private String description;
	
	private Vector <Question> questions;
	private Vector <Response> responses;

	
	
	Survey()
	{
		this.author = null;
		this.title = "";
		this.description = "";
		this.questions = new Vector<Question>();
		this.responses = new Vector<Response>();

	}
	
	public Survey(User author, String title, String description)
	{
		this.author = author;
		this.title = title;
		this.description = description;
		this.questions = new Vector<Question>();
		this.responses = new Vector<Response>();

	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Vector<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Vector<Question> questions) {
		this.questions = questions;
	}

	public Vector<Response> getResponses() {
		return responses;
	}

	public void setResponses(Vector<Response> responses) {
		this.responses = responses;
	}
	
}
