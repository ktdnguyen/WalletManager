package controller;

//PaymentController facilitates the functions of Payment model using the user input from AddPage and EditPage

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import db.SQLiteConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.PaymentModel;

public class PaymentController implements Initializable {

	private static String id;

	private static Double tp = 0.0;
	
	private String str;

	@FXML
	private Label msg;

	@FXML
	private Label showli;

	@FXML
	private PieChart pieChart;
	
	@FXML
	private Button chartBtn;
	
	@FXML
	private TableView<PaymentModel> table;

	@FXML
	private TableColumn<PaymentModel, String> pid;

	@FXML
	private TableColumn<PaymentModel, String> uid;

	@FXML
	private TableColumn<PaymentModel, String> date;

	@FXML
	private TableColumn<PaymentModel, String> category;

	@FXML
	private TableColumn<PaymentModel, Double> price;

	@FXML
	private TableColumn<PaymentModel, String> note;


	public static ObservableList<PaymentModel> data;

	public static ObservableList<String> cblist;

	
	@FXML
	private ChoiceBox<String> choice;

	public void setId(String id) {
		this.id = id;
	}

	// initialize the tableView
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		this.pid.setCellValueFactory(new PropertyValueFactory<PaymentModel, String>("pid"));
//		this.uid.setCellValueFactory(new PropertyValueFactory<PaymentModel, String>("uid"));
		this.date.setCellValueFactory(new PropertyValueFactory<PaymentModel, String>("date"));
		this.category.setCellValueFactory(new PropertyValueFactory<PaymentModel, String>("category"));
		this.price.setCellValueFactory(new PropertyValueFactory<PaymentModel, Double>("price"));
		this.note.setCellValueFactory(new PropertyValueFactory<PaymentModel, String>("note"));

//		table.setEditable(true);
//		date.setCellFactory(TextFieldTableCell.forTableColumn());
//		category.setCellFactory(TextFieldTableCell.forTableColumn());
//		price.setCellFactory(TextFieldTableCell.forTableColumn());
//		note.setCellFactory(TextFieldTableCell.forTableColumn());
		
		Choice();
		try {
			LoadPaymentData("total");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//showli.setText(CategoryTotal("total"));
	}
	// refresh button to load the update
	public void LoadPaymentData(String s) throws SQLException {
		try {
			str = s;
			Connection con = SQLiteConnection.connector();
			data = FXCollections.observableArrayList();
			data.removeAll(data);
			if(s.equals("total")) {
				ResultSet res = con.createStatement().executeQuery("SELECT * FROM Paymentlist WHERE Uid = " + id);
				tp = 0.0;
				while (res.next()) {
					data.add(new PaymentModel(res.getString(1), res.getString(2), res.getString(3), res.getString(4),
							Double.valueOf(res.getString(5)), res.getString(6)));
					tp += Double.valueOf(res.getString(5));
				}
			}
			else {
				data.removeAll(data);
				ResultSet res = con.createStatement().executeQuery("SELECT * FROM Paymentlist WHERE Uid = " + id
																			+ " AND Category = " + "'" + s + "'");
				while (res.next()) {
					data.add(new PaymentModel(res.getString(1), res.getString(2), res.getString(3), res.getString(4),
							Double.valueOf(res.getString(5)), res.getString(6)));
				}
			}

			con.close();

		} catch (SQLException e) {
			System.out.println(e);
		}

		table.setItems(null);
		table.setItems(data);
		generateChart();
		showli.setText("Amount spent in " + str +": "+CategoryTotal(s));

	}

	// add button to switch page to add page.
	public void Add(ActionEvent event) throws IOException{
		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/AddPage.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets()
					.add(getClass().getClassLoader().getResource("application/application.css").toExternalForm());
			primaryStage.setScene(scene);
			AddController addctl = loader.<AddController>getController();
			addctl.setId(id);
			primaryStage.showAndWait();
			LoadPaymentData(str);
		} catch (IOException | SQLException e) {
			System.out.println(e);
		}
	}

	public void Set(ActionEvent event) throws IOException {
		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/limit.fxml"));
			loader.setControllerFactory(c -> {
				limitController controller = new limitController();
				controller.setId(id);
				return controller;
			});
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets()
					.add(getClass().getClassLoader().getResource("application/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void Choice() {
		cblist = FXCollections.observableArrayList();
		cblist.removeAll(cblist);
		cblist.addAll("food", "shopping", "rent", "transport", "entertainment", "other", "total");
		choice.getItems().addAll(cblist);
		choice.setValue("total");
		choice.getSelectionModel().selectedItemProperty()
				.addListener((v, oldValue, newValue) -> showli.setText(CategoryTotal(newValue)));
		choice.getSelectionModel().selectedItemProperty()
		.addListener((v, oldValue, newValue) -> {
			try {
				LoadPaymentData(newValue);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public static String CategoryTotal(String s) {
		double spend = 0.0;
		if(s.equals("total")) {
			spend = tp;
		}
		else {
			Connection con = SQLiteConnection.connector();
			try {
				ResultSet res = con.createStatement().executeQuery("SELECT * FROM Paymentlist WHERE Uid = " + id);
				while (res.next()) {
					if (res.getString("Category").equals(s))
						spend += Double.valueOf(res.getString("Price"));
				}
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Double.toString(spend);

	}

	// delete button to delete the item user selected.
	public void Delete(ActionEvent event) {
		PaymentModel model = table.getSelectionModel().getSelectedItem();
		if(model==null) {
			System.out.println("did not delete the item");
		}
		else {
			ObservableList<PaymentModel> all, single;
			all = table.getItems();
			single = table.getSelectionModel().getSelectedItems();
			single.forEach(all::remove);
			try {
				Connection con = SQLiteConnection.connector();
				PreparedStatement s = con.prepareStatement("DELETE FROM Paymentlist WHERE Pid = " + model.getPid());
				s.executeUpdate();
				con.close();
				LoadPaymentData(str);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void loadChart(ActionEvent event)
	{
		try {
			generateChart();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// to generate a pie chart with the user records
	public void generateChart() throws SQLException{
		try {
			Connection con = SQLiteConnection.connector();
			data = FXCollections.observableArrayList();
			
			ResultSet res = con.createStatement().executeQuery("SELECT * FROM Paymentlist WHERE Uid = "  + id);
			tp = 0.0;
			while(res.next()) {
				data.add(new PaymentModel(res.getString(1), res.getString(2), res.getString(3), res.getString(4), Double.valueOf(res.getString(5)), res.getString(6)));
				tp += Double.valueOf(res.getString(5));
			}
			
			
			double foodSum = 0.0;
			double shoppingSum = 0.0;
			double rentSum = 0.0;
			double transportSum = 0.0;
			double entertainmentSum = 0.0;
			double otherSum = 0.0;
			
			data = FXCollections.observableArrayList();
			
			
			res = con.createStatement().executeQuery("SELECT SUM(Price) FROM Paymentlist WHERE Uid = "  + id +" AND Category ='food'");

			if(res.getString(1)==null)
			{
				foodSum=0.0;
			}
			else {
			foodSum+=Double.valueOf(res.getString(1));	
			}
			
			res = con.createStatement().executeQuery("SELECT SUM(Price) FROM Paymentlist WHERE Uid = "  + id +" AND Category ='shopping'");
			if(res.getString(1)==null)
			{
				shoppingSum=0.0;
			}
			else{ 
				shoppingSum+=Double.valueOf(res.getString(1));
			}
			
			res = con.createStatement().executeQuery("SELECT SUM(Price) FROM Paymentlist WHERE Uid = "  + id +" AND Category ='rent'");
			if(res.getString(1)==null)
			{
				rentSum=0.0;
			}
			else{
				rentSum+=Double.valueOf(res.getString(1)); 
			}
			
			res = con.createStatement().executeQuery("SELECT SUM(Price) FROM Paymentlist WHERE Uid = "  + id +" AND Category ='transport'");
			if(res.getString(1)==null)
			{
				transportSum=0.0;
			}
			else{
				transportSum+=Double.valueOf(res.getString(1));
			}
			
			res = con.createStatement().executeQuery("SELECT SUM(Price) FROM Paymentlist WHERE Uid = "  + id +" AND Category ='entertainment'");
			if(res.getString(1)==null)
			{
				entertainmentSum=0.0;
			}
			else{
				entertainmentSum+=Double.valueOf(res.getString(1));
			}
			
			res = con.createStatement().executeQuery("SELECT SUM(Price) FROM Paymentlist WHERE Uid = "  + id +" AND Category ='other'");
			if(res.getString(1)==null)
			{
				otherSum=0.0;
			}
			else{
				otherSum+=Double.valueOf(res.getString(1));
			}
			
			con.close();
		
		ObservableList<Data> list = FXCollections.observableArrayList(
				new PieChart.Data("food",  foodSum),
				new PieChart.Data("shopping",  shoppingSum),
				new PieChart.Data("rent",  rentSum),
				new PieChart.Data("transport",  transportSum),
				new PieChart.Data("entertainment",  entertainmentSum),
				new PieChart.Data("other",  otherSum));
		pieChart.setData(list);
			
		}  catch(SQLException e){
			System.out.println(e);
		}	
		
		}

}
