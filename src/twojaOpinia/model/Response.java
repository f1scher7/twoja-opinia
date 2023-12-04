package twojaOpinia.model;

public class Response {
	
	private int surveyID;
	private int answerID;
	private User user;
	
	Response() {
		this.setSurveyID(-1);
		this.answerID = -1;
		this.user = null;
	}
	
	public Response(int answerID, User user) {
		this.setSurveyID(-1);
		this.answerID = answerID;
		this.user = user;
	}


	public int getAnswerID() {
		return answerID;
	}

	public void setAnswerID(int answerID) {
		this.answerID = answerID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getSurveyID() {
		return surveyID;
	}

	public void setSurveyID(int surveyID) {
		this.surveyID = surveyID;
	}

}
