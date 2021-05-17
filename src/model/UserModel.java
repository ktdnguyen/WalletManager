package model;

import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserModel {
	private IntegerProperty id;
	private StringProperty username;
	private StringProperty password;
	
	public UserModel(String username, String password) {
		this((Integer) null, username, password);
	}
	
	public UserModel(int id, String username, String password) {
		this.id = new SimpleIntegerProperty(id);
		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
	}
	public IntegerProperty getId() {
		return id;
	}

	public void setId(IntegerProperty id) {
		this.id = id;
	}
	
	public StringProperty getUsername(){
		return username;
	}

	public void setUsername(StringProperty username) {
		this.username = username;
	}
	
	public StringProperty getPassword() {
		return password;
	}

	public void setPassword(StringProperty password) {
		this.password = password;
	}
}
