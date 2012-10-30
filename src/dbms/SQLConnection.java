package dbms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class SQLConnection {
	private static Connection connection;
	private static SQLConnection instance;
	private String strConnection;
	private String driver;
	
	private SQLConnection(String strConnection, String driver){
		this.strConnection = strConnection;
		this.driver = driver;
	}
	
	public static SQLConnection getConnection(String strConnection, String driver){
		if(instance==null)
			instance = new SQLConnection(strConnection, driver);
		return instance;
	}
	
	public static SQLConnection getConnection(){
		return instance;
	}
	
	public Connection startConnection() throws Exception{
		close();
		Class.forName(driver);
		connection= DriverManager.getConnection(strConnection);
		return connection;
	}
	public ResultSet executeQuery(String sql) throws Exception {
		System.out.println(sql);
		ResultSet result= startConnection().createStatement().executeQuery(sql);
		return result;
	}
	public boolean executeUpdate(String sql) throws Exception {
		System.out.println(sql);
		startConnection().createStatement().execute(sql);
		close();
		return true;
	}
	public static void close() {
		try{
			if(connection != null){connection.close();}
		}catch(Exception e){e.printStackTrace();}
		
	}
}
