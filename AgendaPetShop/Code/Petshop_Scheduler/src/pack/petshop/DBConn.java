package pack.petshop;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;

public class DBConn 
{
	private static String password;
	private static String username;
	private static String host;
	private static void getLogin()
	{
		BufferedReader br = null;
		try 
		{
			br = new BufferedReader(new FileReader("./src/resources/authentication.txt"));
		    String line = br.readLine();
		    int i = 1;
		    while (line != null) 
		    {
		    	if(i == 1)
		    		username = line.toString();
		    	if(i == 2)
		    		password = line.toString();
		    	if(i == 3)
		    		host = line.toString();
		        line = br.readLine();
		        i++;
		    }
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
		    try 
		    {
				br.close();
			} 
		    catch (IOException e) 
		    {
				e.printStackTrace();
			}
		}
	}
	
	public static Connection getConnection()
	{
		getLogin();
		String dbURL = "jdbc:sqlserver://"+ host +":1433;user="+ username +";password="+ password +";databaseName=Petshop_DBase";

		Connection conn = null;
		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(dbURL);
            if (conn != null) 
            {
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
                return conn;
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
		finally 
		{
			try 
			{
				conn.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}
}
