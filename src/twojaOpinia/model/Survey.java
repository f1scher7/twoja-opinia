//Survey.java: Klasa reprezentująca ankietę. Może zawierać informacje na temat pytania,
// opcji odpowiedzi, daty rozpoczęcia i zakończenia ankiety, itp

package twojaOpinia.model;

import java.util.Vector;

public class Survey {
	
	private int id;
	private User author;
	private String title;
	private String description;
	private Vector <String> answers;
	
	private Vector <Vote> votes;
	
	
	Survey()
	{
		this.id = -1;
		this.author = null;
		this.title = "";
		this.description = "";
		this.answers = new Vector<String>();
		this.votes = new Vector<Vote>();
	}
	
	public Survey(int id, User author, String title, String description)
	{
		this.id = id;
		this.author = author;
		this.title = title;
		this.description = description;
		this.answers = new Vector<String>();
		this.votes = new Vector<Vote>();
	}
	
}
