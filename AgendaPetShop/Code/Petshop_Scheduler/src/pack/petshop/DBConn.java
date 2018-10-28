package pack.petshop;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn 
{
	private static byte[] password;
	private static byte[] username;
	private static String host;
	
	private static void getLogin()
	{
		username = Utils.Encrypt("sa");
		password = Utils.Encrypt("bruno02");
		host = "BRUNO-NOTE";
	}
	
	public static void getConnection()
	{
		getLogin();
		//String url = "jdbc:sqlserver://"+ host +"\\sqlexpress;databaseName=Petshop_DBase";
		//String dbURL = "jdbc:sqlserver://BRUNO-NOTE\\MSSQLSERVER;user=sa;password=bruno02";
		//String dbURL = "jdbc:sqlserver://BRUNO-NOTE;databaseName=Petshop_DBase;user=sa;password=bruno02";
		String dbURL = "jdbc:sqlserver://BRUNO-NOTE:1433;user=sa;password=bruno02;databaseName=Petshop_DBase";

		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager.getConnection(dbURL);
            if (conn != null) 
            {
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
}
