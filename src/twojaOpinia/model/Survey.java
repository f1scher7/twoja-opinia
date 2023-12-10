//Survey.java: Klasa reprezentująca ankietę. Może zawierać informacje na temat pytania,
// opcji odpowiedzi, daty rozpoczęcia i zakończenia ankiety, itp

package twojaOpinia.model;

import java.util.ArrayList;

public class Survey {
	
	private String authorLogin;
	private String title;
	private String description;
	private ArrayList <Question> questions;
	private ArrayList <Response> responses;

	public Survey() {
		this.authorLogin = "";
		this.title = "";
		this.description = "";
		this.questions = new ArrayList<>();
		this.responses = new ArrayList<>();
	}
	
	public Survey(String authorLogin, String title, String description) {
		this.authorLogin = authorLogin;
		this.title = title;
		this.description = description;
		this.questions = new ArrayList<>();
		this.responses = new ArrayList<>();
	}

	public String getAuthorLogin() { return this.authorLogin; }
	public void setAuthorLogin(String authorLogin) { this.authorLogin = authorLogin; }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public ArrayList<Question> getQuestions() { return questions; }
	public void setQuestions(ArrayList<Question> questions) { this.questions = questions; }

	public ArrayList<Response> getResponses() { return responses; }

	public void setResponses(ArrayList<Response> responses) { this.responses = responses; }

	@Override
	public String toString() {
		return "===========================================\n" +
				"DANE ANKIETY\n" +
				"Autor: " + this.authorLogin +
				"\nNazwa: " + this.title +
				"\nOpis: " + this.description +
				"\n===========================================";
	}
}
