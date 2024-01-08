package twojaOpinia.model;

public class Response {
	
	private int surveyID;
	private int answerID;
	private String userLogin;
	
	public Response() {
		this.setSurveyID(-1);
		this.answerID = -1;
		this.userLogin = "";
	}
	
	public Response(int answerID, String userLogin) {
		this.setSurveyID(-1);
		this.answerID = answerID;
		this.userLogin = userLogin;
	}


	public int getAnswerID() {
		return answerID;
	}

	public void setAnswerID(int answerID) {
		this.answerID = answerID;
	}

	public String getUserLogin() {
		return this.userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public int getSurveyID() {
		return surveyID;
	}

	public void setSurveyID(int surveyID) {
		this.surveyID = surveyID;
	}

	@Override
	public String toString() {
		return "Response{" +
				"surveyID=" + surveyID +
				", answerID=" + answerID +
				", userLogin='" + userLogin + '\'' +
				'}';
	}
}
