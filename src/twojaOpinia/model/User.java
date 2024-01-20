//User.java: Klasa reprezentująca użytkownika. Zawierać może informacje
// takie jak identyfikator użytkownika, login, hasło, itp.

package twojaOpinia.model;

import twojaOpinia.util.SHA256;

public class User {
	private String name;
	private String surname;
	private String email;
	private String birthday;
	private String country;
	private String city;
	private String login;
	private String password;
	private String salt;
	private boolean admin;
	
	public User() {
		this.name = "";
		this.surname = "";
		this.email = "";
		this.birthday = "";
		this.country = "";
		this.city = "";
		this.login = "";
		this.password = "";
		this.salt = "";
		this.admin = false;
	}

	public User(String name, String surname, String email, String birthday, String country,
				String city, String login, String password, String salt, boolean admin) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.birthday = birthday;
		this.country = country;
		this.city = city;
		this.login = login;
		this.password = password;
		this.salt = salt;
		this.admin = admin;
	}

	public String getName() { return this.name; }
	public void setName(String login) { this.name = name; }

	public String getSurname() { return surname; }
	public void setSurname(String surname) { this.surname = surname; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getBirthday() { return birthday; }
	public void setBirthday(String birthday) { this.birthday = birthday; }

	public String getCountry() { return country; }
	public void setCountry(String country) { this.country = country; }

	public String getCity() { return city; }
	public void setCity(String city) { this.city = city; }

	public String getLogin() { return this.login; }
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
				"Name: " + this.name +
				"Surname: " + this.surname +
				"Email: " + this.email +
				"Birthday: " + this.birthday +
				"Country: " + this.country +
				"City: " + this.city +
				"Login: " + this.login +
				"\nPassword: " + this.password +
				"\n===========================================";
	}
}
