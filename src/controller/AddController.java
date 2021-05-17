package controller;

import java.net.URL;

/*
 * AddController inserts user inputed values from AddPage.fxml into the database
 * (in final version) app will check that price value is a Decimal value
 */

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import db.SQLiteConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddController implements Initializable {

	@FXML
	private Button addBtn;
	
	@FXML
	private DatePicker date;

	@FXML
	private TextField price;

	@FXML
	private TextField note;

	@FXML
	private Label msg;

	@FXML
	private ChoiceBox<String> categoryBox;

	private int pid = 0;

	private String id;

	public static ObservableList<String> list;

	/*
	 * set user id to add controller, it can keep user id from payment controller.
	 */

	public void setId(String id) {
		this.id = id;
	}

	public void initialize(URL location, ResourceBundle resources) {
		CategoryChoice();

	}

	public void CategoryChoice() {
		list = FXCollections.observableArrayList();
		list.removeAll(list);
		list.addAll("food", "shopping", "rent", "transport", "entertainment", "other");
		categoryBox.getItems().addAll(list);
		categoryBox.setValue("food");
	}

	// checking if string contains a double
	// price must be numeric
	boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/*
	 * connect with database and get user input from fxml file, save the data to
	 * database.
	 */

	public void Submit(ActionEvent event) throws SQLException {
		StringBuilder error = new StringBuilder();
		boolean success=false; //when the entry is successfully added, set to true and window will close
		int counter = 0; // counting total entries to make sure that new entry successfully added
		try {
			Connection con = SQLiteConnection.connector();
			int i = 0;
			ResultSet res = con.createStatement().executeQuery("SELECT * FROM Paymentlist");
			while (res.next()) {
				i = Integer.valueOf(res.getString("Pid"));
				pid = Math.max(pid, i);
			}
			pid++;
			res = con.createStatement().executeQuery("SELECT COUNT(*) FROM Paymentlist WHERE Uid = " + id);
			counter = res.getInt(1);

			if (this.date.getValue() == null) {
				error.append("Please select a date. ");
			}
			
			if (isDouble(this.price.getText())==false || this.price.getLength()==0 || Double.parseDouble(this.price.getText())<0) {
				error.append("Please enter a valid price. ");
			}
			
			if (error.length()==0)
			{
			PreparedStatement s = con.prepareStatement(
					"insert into Paymentlist(Pid, Uid, Date, Category, Price, Note) values(?, ?, ?, ?, ?, ?)");
			s.setString(1, Integer.toString(pid));
			s.setString(2, id);
			s.setString(3, (this.date.getValue()).toString());
			s.setString(4, categoryBox.getValue());
			s.setString(5, this.price.getText());
			s.setString(6, this.note.getText());

			s.executeUpdate();
			}

			res = con.createStatement().executeQuery("SELECT COUNT(*) FROM Paymentlist WHERE Uid = " + id);
			while (res.next()) {
				if(error.length() !=0)
					msg.setText(error.toString());
				else if(res.getInt(1) == counter + 1)
					{
					success=true;
					msg.setText("Added item successfully. Close to refresh.");
					}

			}

			con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		if(success)
		{Stage st = (Stage)this.price.getScene().getWindow();
		st.close();}

	}

}
