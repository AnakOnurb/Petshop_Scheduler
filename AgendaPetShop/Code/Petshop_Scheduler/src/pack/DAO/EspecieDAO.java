package pack.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import pack.VO.Especie;
import pack.petshop.DBConn;

public class EspecieDAO 
{
	private static ArrayList<Especie> ReadEspecieSet(ResultSet rs)
	{
		ArrayList<Especie> Especies = new ArrayList<Especie>();
		
		try 
		{
			while (rs.next()) 
			{
				Especie especie = new Especie();
				especie.setId(rs.getInt("id"));
				especie.setDescricao(rs.getString("descricao"));
				Especies.add(especie);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return Especies;
	}
	
	public static ArrayList<Especie> ReadSimple()
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Especie_ReadSimple()}");
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
		        return ReadEspecieSet(rs);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}
}
