package twojaOpinia.model;

import java.io.Serializable;

public class Answer implements Serializable {

	private static final long serialVersionUID = -7496401066520889070L;
	
	private int questionID;
	private int order;
	private String answerText;

	public Answer() {
		this.setQuestionID(-1);
		this.order = -1;
		this.answerText = "";
	}
	
	public Answer(int order, String answerText) {
		this.setQuestionID(-1);
		this.order = order;
		this.answerText = answerText;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getAnswerText() {
		return this.answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	public int getQuestionID() {
		return questionID;
	}

	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}
}
