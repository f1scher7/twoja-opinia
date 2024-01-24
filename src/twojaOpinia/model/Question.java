package twojaOpinia.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {

	private static final long serialVersionUID = 2486360586791980866L;
	
	private int surveyID;
	private int order;
	private String questionText;
	private ArrayList <Answer> answers;
	
	public Question() {
		this.setSurveyID(-1);
		this.order = -1;
		this.questionText = "";
		this.answers = new ArrayList<>();
	}
	
	public Question(int order, String questionText) {
		this.setSurveyID(-1);
		this.order = order;
		this.questionText = questionText;
		this.answers = new ArrayList<>();
	}

	public int getOrder() { return order; }

	public void setOrder(int order) { this.order = order; }

	public String getQuestionText() { return questionText; }

	public void setQuestionText(String questionText) { this.questionText = questionText; }

	public ArrayList<Answer> getAnswers() { return answers; }

	public void setAnswers(ArrayList<Answer> answers) { this.answers = answers; }

	public int getSurveyID() { return surveyID; }

	public void setSurveyID(int surveyID) { this.surveyID = surveyID; }

	@Override
	public String toString() {
		return this.questionText;
	}
}
