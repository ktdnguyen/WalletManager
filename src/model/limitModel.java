package model;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class limitModel {
	private StringProperty id;
	private StringProperty food;
	private StringProperty shopping;
	private StringProperty rent;
	private StringProperty transport;
	private StringProperty entertainment;
	private StringProperty other;
	private StringProperty total;
	
	
	
	public limitModel(String id, String food, String shopping, String rent,String transport,String entertainment, String other, String total) {
		this.id = new SimpleStringProperty(id);
		this.food = new SimpleStringProperty(food);
		this.shopping = new SimpleStringProperty(shopping);
		this.rent = new SimpleStringProperty(rent);
		this.transport = new SimpleStringProperty(transport);
		this.entertainment = new SimpleStringProperty(entertainment);
		this.other = new SimpleStringProperty(other);
		this.total = new SimpleStringProperty(total);
	}



	public StringProperty getTotal() {
		return total;
	}



	public void setTotal(StringProperty total) {
		this.total = total;
	}



	public StringProperty getId() {
		return id;
	}



	public void setId(StringProperty id) {
		this.id = id;
	}



	public StringProperty getFood() {
		return food;
	}



	public void setFood(StringProperty food) {
		this.food = food;
	}



	public StringProperty getShopping() {
		return shopping;
	}



	public void setShopping(StringProperty shopping) {
		this.shopping = shopping;
	}



	public StringProperty getRent() {
		return rent;
	}



	public void setRent(StringProperty rent) {
		this.rent = rent;
	}



	public StringProperty getTransport() {
		return transport;
	}



	public void setTransport(StringProperty transport) {
		this.transport = transport;
	}



	public StringProperty getEntertainment() {
		return entertainment;
	}



	public void setEntertainment(StringProperty entertainment) {
		this.entertainment = entertainment;
	}



	public StringProperty getOther() {
		return other;
	}



	public void setOther(StringProperty other) {
		this.other = other;
	}

}
