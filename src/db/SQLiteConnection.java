package db;
//it is the SQLite database connection.
import java.sql.*;

public class SQLiteConnection {
	
	public static Connection connector() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:project.db");
			return con;
			
		} catch(Exception e) {
			return null;
		}
	}

}
