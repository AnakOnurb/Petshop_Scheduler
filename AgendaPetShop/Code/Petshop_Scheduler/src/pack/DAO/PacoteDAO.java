package pack.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import pack.VO.Pacote;
import pack.petshop.DBConn;

public class PacoteDAO 
{
	private static ArrayList<Pacote> ReadPacoteSet(ResultSet rs)
	{
		ArrayList<Pacote> Pacotes = new ArrayList<Pacote>();
		
		try 
		{
			while (rs.next()) 
			{
				Pacote pacote = new Pacote();
				pacote.setId(rs.getInt("id"));
				pacote.setPetId(rs.getInt("petId"));
				pacote.setTipo(rs.getInt("tipo"));
				pacote.setFuncionarioId(rs.getInt("funcionarioId"));
				Pacotes.add(pacote);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return Pacotes;
	}
	
	public static int Create(int petId, int tipo, int funcionarioId)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		int returnValue = -1;
		try 
		{
			cstmt = conn.prepareCall("{? = call sp_Pacote_Create(?, ?, ?)}");
			cstmt.setInt ("petId", petId);
			cstmt.setInt ("tipo", tipo);
            cstmt.setInt ("funcionarioId", funcionarioId);
            cstmt.registerOutParameter(1, Types.INTEGER);
            cstmt.execute();
            returnValue = cstmt.getInt(1);
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
				return returnValue;
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	public static ArrayList<Pacote> Read(int id, int petId, int tipo, int funcionarioId)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Pacote_Read(?, ?, ?, ?)}");
			cstmt.setInt ("id", id);
			cstmt.setInt ("petId", petId);
			cstmt.setInt ("tipo", tipo);
            cstmt.setInt ("funcionarioId", funcionarioId);
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
		        return ReadPacoteSet(rs);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static ArrayList<Pacote> ReadSimple()
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
        ResultSet rs = null;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Pacote_ReadSimple()}");
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
		        return ReadPacoteSet(rs);
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static int Update(int id, int petId, int tipo, int funcionarioId)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		boolean results = false;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Pacote_Update(?, ?, ?, ?)}");
			cstmt.setInt ("id", id);
			cstmt.setInt ("petId", petId);
			cstmt.setInt ("tipo", tipo);
            cstmt.setInt ("funcionarioId", funcionarioId);
            results = cstmt.execute();
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
				if(results)
					return 1;
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	public static int Delete(int id)
	{
		Connection conn = DBConn.getConnection();
		CallableStatement cstmt = null;
		boolean results = false;
		try 
		{
			cstmt = conn.prepareCall("{call sp_Pacote_Delete(?)}");
			cstmt.setInt("id", id);
            results = cstmt.execute();
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
				if(results)
					return 1;
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return -1;
	}
}
