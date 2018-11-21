package pack.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pack.VO.Pelagem;
import pack.petshop.DBConn;

public class PelagemDAO 
{
	private static ArrayList<Pelagem> ReadPelagemSet(ResultSet rs)
	{
		ArrayList<Pelagem> Pelagens = new ArrayList<Pelagem>();
		
		try 
		{
			while (rs.next()) 
			{
				Pelagem pelagem = new Pelagem();
				pelagem.setId(rs.getInt("id"));
				pelagem.setDescricao(rs.getString("descricao"));
				Pelagens.add(pelagem);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return Pelagens;
	}
	
	public static ArrayList<Pelagem> ReadSimple()
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Pelagem_ReadSimple()}");
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
		        return ReadPelagemSet(rs);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}
}
