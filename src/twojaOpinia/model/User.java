//User.java: Klasa reprezentująca użytkownika. Zawierać może informacje
// takie jak identyfikator użytkownika, login, hasło, itp.

package twojaOpinia.model;

import twojaOpinia.util.SHA256;

public class User {
	
	private String login;
	private String password;
	private String salt;
	private boolean admin;
	
	User() {
		this.login = "";
		this.password = "";
		this.salt = "";
		this.admin = false;
	}
	
	public User(String login, String password, String salt, boolean admin) {
		this.login = login;
		this.password = password;
		this.salt = salt;
		this.admin = admin;
	}

	public String getLogin() { return login; }

	public void setLogin(String login) { this.login = login; }

	public String getPassword() { return this.password; }

	public void setPassword(String password) { this.password = password; }

	public String getSalt() { return this.salt; }

	public void setSalt(String salt) { this.salt = salt; }

	public boolean isAdmin() { return admin; }

	public void setAdmin(boolean admin) { this.admin = admin; }

	@Override
	public String toString() {
		return "===========================================\n" +
				"DANE UŻYTKOWNIKA\n" +
				"Login: " + this.login +
				"\nPassword: " + this.password +
				"\n===========================================";
	}
}
