//User.java: Klasa reprezentująca użytkownika. Zawierać może informacje
// takie jak identyfikator użytkownika, login, hasło, itp.

package model;

import util.SHA256;

public class User {
	
	private String login;
	private String password;
	
	private boolean admin;
	
	User()
	{
		this.login = "";
		this.password = "";
		this.admin = false;
	}
	
	public User(String login, String password, boolean admin)
	{
		
		SHA256 hash = new SHA256();
		this.login = login;
		this.password = hash.toSHA256(password);
		this.admin = admin;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	


}
