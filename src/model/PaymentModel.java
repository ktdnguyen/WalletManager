package model;

import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;


public class PaymentModel {
	
	private StringProperty pid; //payment id, which is auto generated
	private StringProperty uid; //user id
	private StringProperty date;
	private StringProperty category;
	private DoubleProperty price;
	private StringProperty note;

	
	public PaymentModel(String pid, String uid, String date, String category, Double price, String note) {
		this.pid = new SimpleStringProperty(pid);
		this.uid = new SimpleStringProperty(uid);
		this.date = new SimpleStringProperty(date);
		this.category = new SimpleStringProperty(category);
		this.price = new SimpleDoubleProperty(price);
		this.note = new SimpleStringProperty(note);
		
	}

	public String getPid() {
		return this.pid.get();
	}

	public void setPid(String pid) {
		this.pid.set(pid);
	}
	
	public StringProperty pid() {
	    return this.pid;
	}
	
	
	public String getUid() {
		return this.uid.get();
	}

	public void setUid(String uid) {
		this.uid.set(uid);
	}
	
	public StringProperty uid() {
	    return this.uid;
	}
	

	public String getDate() {
		return this.date.get();
	}

	public void setDate(String date) {
		this.date.set(date);
	}
	
	public StringProperty date() {
	    return this.date;
	}
	

	public String getCategory() {
		return this.category.get();
	}

	public void setCategory(String category) {
		this.category.set(category);
	}
	
	public StringProperty category() {
	    return this.category;
	}

	public Double getPrice() {
		return this.price.get();
	}

	public void setPrice(Double price) {
		this.price.set(price);
	}
	
	public DoubleProperty price() {
	    return this.price;
	}

	public String getNote() {
		return this.note.get();
	}

	public void setNote(String note) {
		this.note.set(note);
	}
	
	public StringProperty note() {
	    return this.note;
	}
	
	
}
