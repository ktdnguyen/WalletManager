package controller;

//LoginController takes user input from Login.fxml to check for the user login info 

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.LoginModel;

public class LoginController{
	
	public LoginModel model = new LoginModel();
	
	private String id;
	
	@FXML
	private Label msg;
	
	@FXML
	private TextField username;
	
	@FXML
	private TextField password;
	
	@FXML
	private Button signup;
	
	// control the user login, only the user input username and password as same as  what saved in the database, the function goes to Homepage.
	public void Login(ActionEvent event) throws IOException {
		try {
			if(model.Login(username.getText(), password.getText())) {
				
				msg.setText("Login Successful");
				
				id = model.getId();
				
				Stage st = (Stage)this.password.getScene().getWindow();
				st.close();
				
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/homePage.fxml"));
				loader.setControllerFactory(c -> {
					PaymentController controller = new PaymentController();
					controller.setId(id);
					return controller;
				});
				Parent root = loader.load();
				Scene scene= new Scene(root);
				scene.getStylesheets().add(getClass().getClassLoader().getResource("application/application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
			}
			else {
				msg.setText("Invalied user name or password");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//signup button to change the login page to sign up Page.
	public void Signup(ActionEvent event) throws IOException {
			
			try {
				
				Stage stage = (Stage) signup.getScene().getWindow();
				stage.close();
				
				Stage primaryStage = new Stage();
				
				Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/SignupPage.fxml"));
				Scene scene= new Scene(root);
				scene.getStylesheets().add(getClass().getClassLoader().getResource("application/application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	
	public String getId() {
		return id;
	}

	
}
