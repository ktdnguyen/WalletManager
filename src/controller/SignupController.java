package controller;

//the signupController class help user create new user in database.

import java.io.IOException;
import java.sql.*;

import db.SQLiteConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class SignupController {
	
	@FXML
	private TextField username;
	
	@FXML
	private TextField pass1;
	
	@FXML
	private TextField pass2;
	
	@FXML
	private Label msg;	
	
	@FXML
	private Button login;
	
	
	//add new user object to database with user input with username and password.
	public void SignUp(ActionEvent event) {
		try {
				int i = 0;
				Connection con = SQLiteConnection.connector();
				ResultSet res1 = con.createStatement().executeQuery("SELECT UserName FROM User");
				while(res1.next()) {
					if(res1.getString("UserName").equals(this.username.getText())){
						i = 1;
					}
				}			
				if(i == 1) {
					msg.setText("user name exist");
				}
				else if(!this.pass1.getText().equals(this.pass2.getText())) {
					msg.setText("password does not match");
					i = 1;
				}
				else if(this.pass1.getText().equals("") || this.pass2.getText().equals("")){
					msg.setText("password is empty");
					i = 1;
				}
				if(i == 0){
					PreparedStatement  s = con.prepareStatement("insert into User(UserName, Password) values(?, ?)");
					s.setString(1, this.username.getText());
					s.setString(2, this.pass1.getText());
					
					s.executeUpdate();
					msg.setText("Registration Successful, click to login");
					
					
					ResultSet res = con.createStatement().executeQuery("SELECT MAX(ID) FROM User");
					String id = res.getString(1);
					
					PreparedStatement  s2 = con.prepareStatement("insert into Limitlist(id, food, shopping, rent, transport, entertainment, other, total) values(?, ?, ?, ?, ?, ?, ?, ?)");
					s2.setString(1, id);
					s2.setString(2, "0");
					s2.setString(3, "0");
					s2.setString(4, "0");
					s2.setString(5, "0");
					s2.setString(6, "0");
					s2.setString(7, "0");
					s2.setString(8, "0");
					s2.executeUpdate();
					res.close();
				}
				con.close();

			
		} catch(SQLException e) {
			System.out.println(e);
		}
	}
	
	public void Login(ActionEvent event) throws IOException {
		Stage stage = (Stage) login.getScene().getWindow();
		stage.close();
		
		Stage primaryStage = new Stage();
		
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/Login.fxml"));
		Scene scene= new Scene(root);
		scene.getStylesheets().add(getClass().getClassLoader().getResource("application/application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
}
