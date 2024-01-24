//Survey.java: Klasa reprezentująca ankietę. Może zawierać informacje na temat pytania,
// opcji odpowiedzi, daty rozpoczęcia i zakończenia ankiety, itp

package twojaOpinia.model;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Survey implements Serializable {
	
	private static final long serialVersionUID = 6767786457485354787L;
	
	private String authorLogin;
	private String title;
	private String description;
	private String tags;
	private int nQuestions;
	private LocalDateTime surveyAddedDate;
	private ArrayList <Question> questions;
	private ArrayList <Response> responses;

	public Survey() {
		this.authorLogin = "";
		this.title = "";
		this.description = "";
		this.tags = "";
		this.surveyAddedDate = null;
		this.nQuestions = 0;
		this.questions = new ArrayList<>();
		this.responses = new ArrayList<>();

	}
	
	public Survey(String authorLogin, String title, String description, String tags, int nQuestions) {
		this.authorLogin = authorLogin;
		this.title = title;
		this.description = description;
		this.tags = tags;
		this.surveyAddedDate = null;
		this.nQuestions = nQuestions;
		this.questions = new ArrayList<>();
		this.responses = new ArrayList<>();
	}

	public String getAuthorLogin() { return this.authorLogin; }
	public void setAuthorLogin(String authorLogin) { this.authorLogin = authorLogin; }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	public String getDescription() { return this.description; }
	public void setDescription(String description) { this.description = description; }

	public String getTags() { return this.tags; }
	public void setTags(String tags) { this.tags = tags; }

	public LocalDateTime getSurveyAddedDate() { return this.surveyAddedDate; }
	public void setSurveyAddedDate(LocalDateTime date) { this.surveyAddedDate = date; }

	public int getNQuestions() { return this.nQuestions; }

	public void setNQuestions(int nQuestions) { this.nQuestions = nQuestions; }

	public ArrayList<Question> getQuestions() { return this.questions; }
	public void setQuestions(ArrayList<Question> questions) { this.questions = questions; }

	public ArrayList<Response> getResponses() { return this.responses; }
	public void setResponses(ArrayList<Response> responses) { this.responses = responses; }

	public int getSurveyIDFromQuestions() {
	    if (!this.questions.isEmpty()) {
	        return this.questions.get(0).getSurveyID();
	    }
	    return -1;
	}
	
	@Override
	public String toString() {
		return "===========================================\n" +
				"DANE ANKIETY\n" +
				"Autor: " + this.authorLogin +
				"\nNazwa: " + this.title +
				"\nOpis: " + this.description +
				"\n===========================================";
	}
	
	
	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException{
		authorLogin = stream.readUTF();
		title = stream.readUTF();
		description = stream.readUTF();
		tags = stream.readUTF();
		nQuestions = stream.readInt();
		questions = (ArrayList<Question>) stream.readObject();
		responses = (ArrayList<Response>) stream.readObject();
	}
	
	private void writeObject(java.io.ObjectOutputStream stream) throws IOException, ClassNotFoundException{
		stream.writeUTF(authorLogin);
		stream.writeUTF(title);
		stream.writeUTF(description);
		stream.writeUTF(tags);
		stream.writeInt(nQuestions);
		stream.writeObject(questions);
		stream.writeObject(responses);
	}
}
