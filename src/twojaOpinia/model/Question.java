package twojaOpinia.model;

import java.util.Vector;

public class Question {
	
	private int surveyID;
	private int order;
	private String questionText;
	
	private Vector <Answer> answers;
	
	Question()
	{
		this.setSurveyID(-1);
		this.order = -1;
		this.questionText = "";
		this.answers = new Vector<Answer>();
	}
	
	public Question(int order, String questionText)
	{
		this.setSurveyID(-1);
		this.order = order;
		this.questionText = questionText;
		this.answers = new Vector<Answer>();
	}


	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public Vector<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Vector<Answer> answers) {
		this.answers = answers;
	}

	public int getSurveyID() {
		return surveyID;
	}

	public void setSurveyID(int surveyID) {
		this.surveyID = surveyID;
	}

}
