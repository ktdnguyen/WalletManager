package model;

import db.SQLiteConnection;

import java.sql.*;

public class LoginModel {
	Connection connection;
	private int id;
	
	public LoginModel() {
		connection = SQLiteConnection.connector();
		if(connection == null)
			System.exit(1);
		
	}
	
	public boolean isConnected() {
		try {
			return !connection.isClosed();
		} catch(SQLException e){
			return false;
		}
	}
	
	public boolean Login(String user, String password) throws SQLException{
		
		PreparedStatement statement = null;
		ResultSet res = null;
		String query = "select * from User where UserName = ? and Password = ?";
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, user);
			statement.setString(2, password);
			
			res = statement.executeQuery();
			if(res.next()) {
				id = res.getInt("ID");
				return true;
			}
			else {
				return false;
			}
		} catch(Exception e) {
			return false;
		}
		finally {
			statement.close();
			res.close();
		}
		
	}
	
	public String getId() {
		return Integer.toString(id);
	}
	

}
