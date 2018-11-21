package pack.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pack.VO.Porte;
import pack.petshop.DBConn;

public class PorteDAO 
{
	private static ArrayList<Porte> ReadPorteSet(ResultSet rs)
	{
		ArrayList<Porte> Portes = new ArrayList<Porte>();
		
		try 
		{
			while (rs.next()) 
			{
				Porte porte = new Porte();
				porte.setId(rs.getInt("id"));
				porte.setDescricao(rs.getString("descricao"));
				Portes.add(porte);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return Portes;
	}
	
	public static ArrayList<Porte> ReadSimple()
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Porte_ReadSimple()}");
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
		        return ReadPorteSet(rs);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}
}
