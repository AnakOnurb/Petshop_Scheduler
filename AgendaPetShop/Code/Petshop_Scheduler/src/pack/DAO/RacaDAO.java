package pack.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pack.VO.Raca;
import pack.petshop.DBConn;

public class RacaDAO 
{
	private static ArrayList<Raca> ReadRacaSet(ResultSet rs)
	{
		ArrayList<Raca> Racas = new ArrayList<Raca>();
		
		try 
		{
			while (rs.next()) 
			{
				Raca raca = new Raca();
				raca.setId(rs.getInt("id"));
				raca.setDescricao(rs.getString("descricao"));
				Racas.add(raca);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return Racas;
	}
	
	public static ArrayList<Raca> ReadSimple()
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Raca_ReadSimple()}");
			boolean results = cstmt.execute();
	        int rowsAffected = 0;
	 
	        while (results || rowsAffected != -1) 
	        {
	            if (results) 
	            {
	                rs = cstmt.getResultSet();
	                break;
	            }
	            else 
	            {
	                rowsAffected = cstmt.getUpdateCount();
	            }
	            results = cstmt.getMoreResults();
	        }
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				conn.close();				
		        return ReadRacaSet(rs);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}
}
