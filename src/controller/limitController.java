// This controller is for the check budget page 

package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import db.SQLiteConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.PaymentModel;
import controller.PaymentController;

public class limitController implements Initializable{
	@FXML
	private TextField food;
	
	@FXML
	private TextField shopping;
	
	@FXML
	private TextField rent;
	
	@FXML
	private TextField transport;
	
	@FXML
	private TextField entertainment;
	
	@FXML
	private TextField other;
	
	@FXML
	private TextField total;
	
	@FXML
	private Label m1; // food budget
	@FXML
	private Label m2; //shopping budget
	@FXML
	private Label m3; //rent budget
	@FXML
	private Label m4; //transport budget
	@FXML 
	private Label m5; //entertainment budget
	@FXML
	private Label m6; //other budget
	@FXML
	private Label m7; //total budget
	
	private String id;
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			Connection con = SQLiteConnection.connector();
			ResultSet res = con.createStatement().executeQuery("SELECT * FROM Limitlist");
			while(res.next()) {
				if(res.getString("id").equals(id)) {
					food.setText(res.getString("food"));
					shopping.setText(res.getString("shopping"));
					rent.setText(res.getString("rent"));
					transport.setText(res.getString("transport"));
					entertainment.setText(res.getString("entertainment"));
					other.setText(res.getString("other"));
					total.setText(res.getString("total"));
				}
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setLimit(ActionEvent event) {
		try {
				
			Connection con = SQLiteConnection.connector();
			
			PreparedStatement  s = con.prepareStatement("UPDATE Limitlist Set food = " + this.food.getText()
																	+ ", shopping =  " + this.shopping.getText()
																	+ ", rent = " + this.rent.getText()
																	+ ", transport = " + this.transport.getText()
																	+ ", entertainment = " + this.entertainment.getText()
																	+ ", other = " + this.other.getText()
																	+ ", total = " + this.total.getText() 
																	+ " Where id =" + id );
			
			s.executeUpdate();
			
			ResultSet res = con.createStatement().executeQuery("SELECT * FROM Paymentlist WHERE Uid = "  + id);
			double tp = 0;
			while(res.next()) {
				tp += Double.valueOf(res.getString(5));
			}
			
			con.close();
			double fd = Double.valueOf(PaymentController.CategoryTotal("food"));	
			m1.setText(Double.toString(Double.valueOf(this.food.getText())-fd));
			
			double sp = Double.valueOf(PaymentController.CategoryTotal("shopping"));	
			m2.setText(Double.toString(Double.valueOf(this.shopping.getText())-sp));
			
			double rt = Double.valueOf(PaymentController.CategoryTotal("rent"));	
			m3.setText(Double.toString(Double.valueOf(this.rent.getText())-rt));
			
			double tr = Double.valueOf(PaymentController.CategoryTotal("transport"));	
			m4.setText(Double.toString(Double.valueOf(this.transport.getText())-tr));
			
			double en = Double.valueOf(PaymentController.CategoryTotal("entertainment"));	
			m5.setText(Double.toString(Double.valueOf(this.entertainment.getText())-en));
			
			double ot = Double.valueOf(PaymentController.CategoryTotal("other"));	
			m6.setText(Double.toString(Double.valueOf(this.other.getText())-ot));
				
			m7.setText(Double.toString(Double.valueOf(this.total.getText())-tp));
			
		} catch(SQLException e) {
			System.out.println(e);
		}
	}

}
